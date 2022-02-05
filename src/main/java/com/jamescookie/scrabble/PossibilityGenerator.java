package com.jamescookie.scrabble;

import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

/**
 * @author ukjamescook
 */
@RequiredArgsConstructor
public class PossibilityGenerator {
    private final Wordsmith wordsmith;
    private final Board board;
    private PossibilityThreadCollector possibilityThreadCollector;
    private WordGeneratingThreadCollector wordGeneratingThreadCollector;

    public Board getBoard() {
        return board;
    }

    public Collection<String> findVariations(String word) {
        Filter f = new Filter();
        f.setMustContain(word);
        f.setLength(word.length() + 3);
        f.setOperator(Operator.LESS_THAN);
        return f.filter(wordsmith.getWords());
    }

    public void generate(String letters, int number, ResultExpector expector) {
        if (number < 1) {
            number = 1;
        }
        board.setDryRun(true);
        possibilityThreadCollector = new PossibilityThreadCollector(number, expector);
        wordGeneratingThreadCollector = new WordGeneratingThreadCollector(possibilityThreadCollector);
        wordGeneratingThreadCollector.add(new WordGeneratingThread(letters, possibilityThreadCollector, wordsmith, board));
        findPossibilitiesWithBoardLetters(letters);
        wordGeneratingThreadCollector.start();
    }

    public void waitForResults() {
        if (possibilityThreadCollector != null) {
            try {
                wordGeneratingThreadCollector.join();
                possibilityThreadCollector.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public Collection<Possibility> getResults() {
        Collection<Possibility> possibilities = null;
        if (possibilityThreadCollector != null) {
            possibilities = possibilityThreadCollector.getResults();
            possibilityThreadCollector = null;
            board.setDryRun(false);
        }
        return possibilities;
    }

    public void stop() {
        if (wordGeneratingThreadCollector != null) {
            wordGeneratingThreadCollector.stopProcessing();
            wordGeneratingThreadCollector = null;
        }
        if (possibilityThreadCollector != null) {
            possibilityThreadCollector.stopProcessing();
            possibilityThreadCollector = null;
        }
    }

    private void findPossibilitiesWithBoardLetters(String letters) {
        Map<String, String[]> rowsAndCols = board.getRowsAndColumns();
        String[] rows = rowsAndCols.get("rows");
        String[] cols = rowsAndCols.get("cols");
        Collection<String> boardLetters = new HashSet<>();

        for (String s : rows) {
            if (s.length() > 0) {
                boardLetters.add(s);
            }
        }
        for (String s : cols) {
            if (s.length() > 0) {
                boardLetters.add(s);
            }
        }
        boardLetters = extractCombinations(boardLetters);
        for (String s : boardLetters) {
            wordGeneratingThreadCollector.add(new WordGeneratingThread(s, letters, rows, cols, possibilityThreadCollector, wordsmith, board));
        }
    }

    static Collection<String> extractCombinations(Collection<String> strings) {
        Collection<String> combinations = new HashSet<String>();

        for (String string : strings) {
            extractCombinations(string, combinations, true, true);
        }

        return combinations;
    }

    private static void extractCombinations(String string, Collection<String> combinations, boolean canAdd, boolean canRecurse) {
        char[] chars = string.toCharArray();
        StringBuilder sb = new StringBuilder();
        int spaceCount = 0;

        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (c == ' ') {
                spaceCount++;
            } else {
                if (spaceCount != 0) {
                    if (spaceCount > 1 || canAdd) {
                        combinations.add(sb.toString().trim());
                    }
                    if (canRecurse) {
                        extractCombinations(String.valueOf(chars, i, chars.length - i), combinations, (spaceCount > 1), false);
                    }
                    spaceCount = 0;
                }
            }
            sb.append(c);
        }
        combinations.add(sb.toString().trim());
    }

}
