package com.jamescookie.scrabble;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ukjamescook
 */
public class PossibilityGenerator {
    private final Board board;
    private final Wordsmith wordsmith;
    private static final boolean[] TRY_ALL = new boolean[Board.BOARD_SIZE];

    static {
        for (int i = 0; i < TRY_ALL.length; i++) {
            TRY_ALL[i] = true;
        }
    }

    public PossibilityGenerator(Wordsmith wordsmith, Board board) {
        this.wordsmith = wordsmith;
        this.board = board;
    }

    public Board getBoard() {
        return board;
    }

    public Collection<Possibility> generate(String letters, int number) {
        if (number < 1) {
            number = 1;
        }
        board.setTesting(true);
        Collection<String> words = getWords(letters, new Filter());
        PossibilityThread t = findPossibilities(addWildcardBackIn(words, letters, ""), TRY_ALL, TRY_ALL);
        try {
            t.join();
        } catch (InterruptedException e) {
            // do nothing
        }
        Collection<Possibility> possibilities = t.getPossibilities();
        findPossibilitiesWithBoardLetters(letters, possibilities);
        board.setTesting(false);
        return findTopPossibilities(possibilities, number);
    }

    private void findPossibilitiesWithBoardLetters(String letters, Collection<Possibility> possibilities) {
        String[][] rowsAndCols = Board.getBoardLetters();
        String[] rows = rowsAndCols[0];
        String[] cols = rowsAndCols[1];
        Collection<String> boardLetters = new HashSet<String>();
        Collection<PossibilityThread> threads = new ArrayList<PossibilityThread>();

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
        Filter filter = new Filter();
        String replacement = "" + Utils.WILDCARD;
        String tmp;
        for (String s : boardLetters) {
            tmp = s.replaceAll(" ", replacement);
            filter.setMustContain(tmp);
            Collection<String> words = getWords(letters, filter);
            words.remove(tmp);
            if (words.size() > 0) {
                Collection<String> newWords = new HashSet<String>();
                for (String word : words) {
                    newWords.add(removeLetters(tmp, word));
                }
                threads.add(findPossibilities(addWildcardBackIn(newWords, letters, tmp), findEntries(rows, s), findEntries(cols, s)));
            }
        }
        for (PossibilityThread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                // do nothing
            }
            possibilities.addAll(thread.getPossibilities());
        }
    }

    private static boolean[] findEntries(String[] row, String letters) {
        boolean[] entries = new boolean[row.length];

        for (int i = 0; i < entries.length; i++) {
            entries[i] = row[i].contains(letters);
        }

        return entries;
    }

    private static Collection<String> addWildcardBackIn(Collection<String> words, String letters, String boardLetters) {
        ArrayList<String> newWords = new ArrayList<String>();
        for (String word : words) {
            newWords.add(addWildcardBackIn(word, letters, boardLetters));
        }
        return newWords;
    }

    private static String addWildcardBackIn(String word, String letters, String boardLetters) {
        char[] chars = word.toCharArray();
        Collection<Character> lettersList = new ArrayList<Character>();
        StringBuffer sb = new StringBuffer();

        for (char c : letters.toCharArray()) {
            lettersList.add(c);
        }
        for (char c : boardLetters.toCharArray()) {
            lettersList.add(c);
        }
        for (char c : chars) {
            if (lettersList.contains(c)) {
                lettersList.remove(c);
            } else {
                sb.append(Utils.WILDCARD);
            }
            sb.append(c);
        }
        return sb.toString();
    }

    private static String removeLetters(String letters, String word) {
        Pattern p = Pattern.compile(letters.replaceAll("["+Utils.WILDCARD+"]", "(.)"));
        Matcher m = p.matcher(word);
        String replacement;

        char[] chars = letters.toCharArray();
        StringBuffer sb = new StringBuffer();
        int count = 0;
        for (char c : chars) {
            if (c == Utils.WILDCARD) {
                sb.append('$').append(++count);
            }
        }
        replacement = sb.toString();

//todo I dont account for the pattern occuring twice in one word!
//        int start = 0;
//
//        while (m.find(start)) {
//            start = m.start() + 1;
//            System.out.println(m.group());
//            System.out.println(p.pattern());
//            System.out.println(m.group().replaceFirst(p.pattern(), replacement));
//        }

        return m.replaceFirst(replacement);
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

    private List<Possibility> findTopPossibilities(Collection<Possibility> possibilities, int numberReturned) {
        ArrayList<Possibility> topPossibilities = new ArrayList<Possibility>(possibilities);
        Collections.sort(topPossibilities, new Comparator<Possibility>() {
            public int compare(Possibility p1, Possibility p2) {
                int score1 = p1.getScore();
                int score2 = p2.getScore();
                return (score2 < score1 ? -1 : (score2 == score1 ? 0 : 1));
            }
        });
        return topPossibilities.subList(0, (topPossibilities.size() > numberReturned ? numberReturned : topPossibilities.size()));
    }

    private PossibilityThread findPossibilities(Collection<String> words, boolean[] rowsToTry, boolean [] colsToTry) {
        PossibilityThread thread = new PossibilityThread(words, rowsToTry, colsToTry, board);
        thread.start();
        return thread;
    }

    private Collection<String> getWords(String letters, Filter filter) {
        Tray t = new Tray(letters, filter, wordsmith);
        WordProcessing thread = new WordProcessing(t, filter);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            // do nothing...this is not likely
        }

        return thread.getWords();
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

        public static String removeLetters(String letters, String word) {
            return PossibilityGenerator.removeLetters(letters, word);
        }

        public static String addWildcardBackIn(String word, String letters, String boardLetters) {
            return PossibilityGenerator.addWildcardBackIn(word, letters, boardLetters);
        }

        public static boolean[] findEntries(String[] row, String letters) {
            return PossibilityGenerator.findEntries(row, letters);
        }

    }

}
