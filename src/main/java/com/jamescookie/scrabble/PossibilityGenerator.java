package com.jamescookie.scrabble;

import java.util.Collection;
import java.util.HashSet;

/**
 * @author ukjamescook
 */
public class PossibilityGenerator {
    private final Board board;
    private final Wordsmith wordsmith;
    private PossibilityThreadCollector possibilityThreadCollector;
    private WordGeneratingThreadCollector wordGeneratingThreadCollector;

    public PossibilityGenerator(Wordsmith wordsmith, Board board) {
        this.wordsmith = wordsmith;
        this.board = board;
    }

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

    public void generate(String letters, int number, ResultExpecter expecter) {
        if (number < 1) {
            number = 1;
        }
        board.setTesting(true);
        possibilityThreadCollector = new PossibilityThreadCollector(number, expecter);
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
            board.setTesting(false);
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
        String[][] rowsAndCols = board.getBoardLetters();
        String[] rows = rowsAndCols[0];
        String[] cols = rowsAndCols[1];
        Collection<String> boardLetters = new HashSet<String>();

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

    private static Collection<String> extractCombinations(Collection<String> strings) {
        Collection<String> combinations = new HashSet<String>();

        for (String string : strings) {
            extractCombinations(string, combinations, true, true);
        }

        return combinations;
    }

    private static void extractCombinations(String string, Collection<String> combinations, boolean canAdd, boolean canRecurse) {
        char[] chars = string.toCharArray();
        StringBuffer sb = new StringBuffer();
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
                        extractCombinations(new StringBuffer().append(chars, i, chars.length - i).toString(), combinations, (spaceCount > 1), false);
                    }
                    spaceCount = 0;
                }
            }
            sb.append(c);
        }
        combinations.add(sb.toString().trim());
    }

    /**
     * Inner class to allow testing of private methods.
     */
    static class Tester {
        private Tester() {
        }

        public static Collection<String> extractCombinations(Collection<String> strings) {
            return PossibilityGenerator.extractCombinations(strings);
        }

    }

}
