package com.jamescookie.scrabble;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordGeneratingThread extends Thread {
    private final String letters;
    private final PossibilityThreadCollector possibilityThreadCollector;
    private final boolean justGenerate;
    private final Wordsmith wordsmith;
    private final Board board;

    private String boardLetters;
    private String[] rows;
    private String[] cols;

    private boolean stop = false;
    private WordProcessing thread;

    private static final String WILDCARD_STRING = "" + Utils.WILDCARD;
    private static final boolean[] TRY_ALL = new boolean[Board.BOARD_SIZE];

    static {
        for (int i = 0; i < TRY_ALL.length; i++) {
            TRY_ALL[i] = true;
        }
    }

    private WordGeneratingThread(String letters, PossibilityThreadCollector possibilityThreadCollector, Wordsmith wordsmith, Board board, boolean justGenerate) {
        this.letters = letters;
        this.possibilityThreadCollector = possibilityThreadCollector;
        this.wordsmith = wordsmith;
        this.board = board;
        this.justGenerate = justGenerate;
    }

    public WordGeneratingThread(String boardLetters, String letters, String[] rows, String[] cols, PossibilityThreadCollector possibilityThreadCollector, Wordsmith wordsmith, Board board) {
        this(letters, possibilityThreadCollector, wordsmith, board, false);
        this.boardLetters = boardLetters;
        this.rows = rows;
        this.cols = cols;
    }

    public WordGeneratingThread(String letters, PossibilityThreadCollector possibilityThreadCollector, Wordsmith wordsmith, Board board) {
        this(letters, possibilityThreadCollector, wordsmith, board, true);
    }

    public void run() {
        if (justGenerate) {
            Collection<String> words = getWords(letters, new Filter());
            if (!stop) {
                possibilityThreadCollector.add(findPossibilities(addWildcardBackIn(words, letters, ""), TRY_ALL, TRY_ALL));
            }
        } else {
            doBoardLetters();
        }
    }

    public void stopProcessing() {
        stop = true;
        if (thread != null) {
            thread.stopProcessing();
        }
    }

    private void doBoardLetters() {
        String tmp = boardLetters.replaceAll(" ", WILDCARD_STRING);
        Filter filter = new Filter();
        filter.setMustContain(tmp);
        Collection<String> words = getWords(letters, filter);
        words.remove(tmp);
        if (words.size() > 0) {
            Collection<String> newWords = new HashSet<String>();
            for (String word : words) {
                newWords.add(removeLetters(tmp, word));
            }
            if (!stop) {
                possibilityThreadCollector.add(findPossibilities(addWildcardBackIn(newWords, letters, tmp), findEntries(rows, boardLetters), findEntries(cols, boardLetters)));
            }
        }
    }

    private Collection<String> getWords(String letters, Filter filter) {
        Tray t = new Tray(letters, filter, wordsmith);
        thread = new WordProcessing(t, filter);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Collection<String> words = thread.getWords();
        thread = null;
        yield();

        return words;
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

    private PossibilityThread findPossibilities(Collection<String> words, boolean[] rowsToTry, boolean [] colsToTry) {
        PossibilityThread thread = new PossibilityThread(words, rowsToTry, colsToTry, board);
        thread.start();
        return thread;
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

    private static boolean[] findEntries(String[] row, String letters) {
        boolean[] entries = new boolean[row.length];

        for (int i = 0; i < entries.length; i++) {
            entries[i] = row[i].contains(letters);
        }

        return entries;
    }

    /**
     * Inner class to allow testing of private methods.
     */
    static class Tester {
        private Tester() {
        }

        public static String removeLetters(String letters, String word) {
            return WordGeneratingThread.removeLetters(letters, word);
        }

        public static String addWildcardBackIn(String word, String letters, String boardLetters) {
            return WordGeneratingThread.addWildcardBackIn(word, letters, boardLetters);
        }

        public static boolean[] findEntries(String[] row, String letters) {
            return WordGeneratingThread.findEntries(row, letters);
        }

    }

}
