package com.jamescookie.scrabble;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * @author ukjamescook
 */
public class PossibilityGenerator {
    private Board board;
    private Wordsmith wordsmith;
    private int numberReturned = 9;

    public PossibilityGenerator(Wordsmith wordsmith) {
        this.wordsmith = wordsmith;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Collection<Possibility> generate(String letters) {
        Set<Possibility> possibilities = new HashSet<Possibility>();
        board.setTesting(true);
        Collection<String> words = getWords(letters, new Filter());
        findPossibilities(words, possibilities);
        findPossibilitiesWithBoardLetters(letters, possibilities);
        return findTopTen(possibilities);
    }

    private void findPossibilitiesWithBoardLetters(String letters, Collection<Possibility> possibilities) {
        List<String> boardLetters = board.getBoardLetters();
        boardLetters = extractCombinations(boardLetters);
        Filter filter = new Filter();
        String replacement = "" + Utils.WILDCARD;
        for (String s : boardLetters) {
            s = s.replaceAll(" ", replacement);
            filter.setMustContain(s);
            Collection<String> words = getWords(letters, filter);
            words.remove(s);
            ArrayList<String> newWords = new ArrayList<String>();
            for (String word : words) {
                newWords.add(removeLetters(s, word));
            }
            findPossibilities(newWords, possibilities);
        }
    }

    private static String removeLetters(String letters, String word) {
        Pattern p = Pattern.compile(letters.replaceAll("["+Utils.WILDCARD+"]", "(.)"));
        Matcher m = p.matcher(word);
        String replacement;

        char[] chars = letters.toCharArray();
        StringBuffer sb = new StringBuffer();
        int count = 0;
        for (int i = 0; i < chars.length; i++) {
            char c = letters.toCharArray()[i];
            if (c == Utils.WILDCARD) {
                sb.append('$').append(++count);
            }
        }
        replacement = sb.toString();
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

    private static List<String> extractCombinations(List<String> strings) {
        List<String> combinations = new ArrayList<String>();

        for (String string : strings) {
            extractCombinations(string, combinations, true, true);
        }

        return combinations;
    }

    private static void extractCombinations(String string, List<String> combinations, boolean canAdd, boolean canRecurse) {
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

    private List<Possibility> findTopTen(Collection<Possibility> possibilities) {
        ArrayList<Possibility> topTenPossibilities = new ArrayList<Possibility>(possibilities);
        Collections.sort(topTenPossibilities, new Comparator<Possibility>() {
            public int compare(Possibility p1, Possibility p2) {
                int score1 = p1.getScore();
                int score2 = p2.getScore();
                return (score2 < score1 ? -1 : (score2 == score1 ? 0 : 1));
            }
        });
        return topTenPossibilities.subList(0, (topTenPossibilities.size() > numberReturned ? numberReturned : topTenPossibilities.size()));
    }

    private void findPossibilities(Collection<String> words, Collection<Possibility> possibilities) {
        Square square;
        Direction direction;
        for (String word : words) {
            for (int row = 0; row < Board.BOARD_SIZE; row++) {
                for (int col = 0; col < Board.BOARD_SIZE; col++) {
                    try {
                        square = board.getSquare(row, col);
                    } catch (ScrabbleException e) {
                        // this would be really bad if this happens, but it wont!
                        continue;
                    }
                    direction = Direction.ACROSS;
                    try {
                        possibilities.add(new Possibility(
                            board.putLetters(word, square, direction),
                            word,
                            square,
                            direction
                        ));
                    } catch (ScrabbleException e) {
                        // do nothing...invalid word
                    }
                    direction = Direction.DOWN;
                    try {
                        possibilities.add(new Possibility(
                            board.putLetters(word, square, direction),
                            word,
                            square,
                            direction
                        ));
                    } catch (ScrabbleException e) {
                        // do nothing...invalid word
                    }
                }
            }
        }
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

        public static List<String> extractCombinations(List<String> strings) {
            return PossibilityGenerator.extractCombinations(strings);
        }

        public static String removeLetters(String letters, String word) {
            return PossibilityGenerator.removeLetters(letters, word);
        }
    }

}
