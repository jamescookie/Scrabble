package com.jamescookie.scrabble;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author ukjamescook
 */
public class PossibilityGeneratorTest extends Tester {

    protected void setUp() throws Exception {
        super.setUp();
    }

    public void testExtractCombinationsWithNothing() throws Exception {
        Collection<String> strings = new ArrayList<String>();

        strings = PossibilityGenerator.Tester.extractCombinations(strings);

        assertEquals(0, strings.size());
    }

    public void testExtractCombinationsWithOneString() throws Exception {
        Collection<String> strings = new ArrayList<String>();
        String s = "a";
        strings.add(s);

        strings = PossibilityGenerator.Tester.extractCombinations(strings);

        assertEquals(1, strings.size());
        assertTrue(strings.contains(s));
    }

    public void testExtractCombinationsWithOneSpace() throws Exception {
        Collection<String> strings = new ArrayList<String>();
        String s = "a b";
        strings.add(s);

        strings = PossibilityGenerator.Tester.extractCombinations(strings);

        assertEquals(3, strings.size());
        assertTrue(strings.contains(s));
        assertTrue(strings.contains("a"));
        assertTrue(strings.contains("b"));
    }

    public void testExtractCombinationsWithTwoSpacesInARow() throws Exception {
        Collection<String> strings = new ArrayList<String>();
        String s = "a  b";
        strings.add(s);

        strings = PossibilityGenerator.Tester.extractCombinations(strings);

        assertEquals(3, strings.size());
        assertTrue(strings.contains(s));
        assertTrue(strings.contains("a"));
        assertTrue(strings.contains("b"));
    }

    public void testExtractCombinationsWithThreeSetsOfLetters() throws Exception {
        Collection<String> strings = new ArrayList<String>();
        String s = "a b c";
        strings.add(s);

        strings = PossibilityGenerator.Tester.extractCombinations(strings);

        assertEquals(5, strings.size());
        assertTrue(strings.contains(s));
        assertTrue(strings.contains("a b"));
        assertTrue(strings.contains("b c"));
        assertTrue(strings.contains("a"));
        assertTrue(strings.contains("c"));
    }

    public void testExtractCombinationsWithThreeSetsOfSingleLettersWithEnoughSpace3() throws Exception {
        Collection<String> strings = new ArrayList<String>();
        String s = "a  b c";
        strings.add(s);

        strings = PossibilityGenerator.Tester.extractCombinations(strings);

        assertEquals(6, strings.size());
        assertTrue(strings.contains(s));
        assertTrue(strings.contains("a  b"));
        assertTrue(strings.contains("b c"));
        assertTrue(strings.contains("a"));
        assertTrue(strings.contains("b"));
        assertTrue(strings.contains("c"));
    }

    public void testExtractCombinationsWithThreeSetsOfSingleLettersWithEnoughSpace2() throws Exception {
        Collection<String> strings = new ArrayList<String>();
        String s = "a b  c";
        strings.add(s);

        strings = PossibilityGenerator.Tester.extractCombinations(strings);

        assertEquals(6, strings.size());
        assertTrue(strings.contains(s));
        assertTrue(strings.contains("a b"));
        assertTrue(strings.contains("b  c"));
        assertTrue(strings.contains("a"));
        assertTrue(strings.contains("b"));
        assertTrue(strings.contains("c"));
    }

    public void testExtractCombinationsWithThreeSetsOfDoubleLetters() throws Exception {
        Collection<String> strings = new ArrayList<String>();
        String s = "aa bb cc";
        strings.add(s);

        strings = PossibilityGenerator.Tester.extractCombinations(strings);

        assertEquals(5, strings.size());
        assertTrue(strings.contains(s));
        assertTrue(strings.contains("aa"));
        assertTrue(strings.contains("cc"));
        assertTrue(strings.contains("aa bb"));
        assertTrue(strings.contains("bb cc"));
    }

    public void testExtractCombinationsWithThreeSetsOfDoubleLettersWithEnoughSpace() throws Exception {
        Collection<String> strings = new ArrayList<String>();
        String s = "aa  bb  cc";
        strings.add(s);

        strings = PossibilityGenerator.Tester.extractCombinations(strings);

        assertEquals(6, strings.size());
        assertTrue(strings.contains(s));
        assertTrue(strings.contains("aa"));
        assertTrue(strings.contains("bb"));
        assertTrue(strings.contains("cc"));
        assertTrue(strings.contains("aa  bb"));
        assertTrue(strings.contains("bb  cc"));
    }

    public void testRemoveLetters() throws Exception {
        assertEquals("abc", PossibilityGenerator.Tester.removeLetters("", "abc"));
        assertEquals("abc", PossibilityGenerator.Tester.removeLetters("b", "abbc"));
        assertEquals("aac", PossibilityGenerator.Tester.removeLetters("b*b", "ababc"));
    }

    public void testAddWildcardBackIn() throws Exception {
        assertEquals("abc", PossibilityGenerator.Tester.addWildcardBackIn("abc", "a", "bc"));
        assertEquals("aaa*a", PossibilityGenerator.Tester.addWildcardBackIn("aaaa", "a*", "aa"));
        assertEquals("a*bc", PossibilityGenerator.Tester.addWildcardBackIn("abc", "a*c", ""));
        assertEquals("*ab*c", PossibilityGenerator.Tester.addWildcardBackIn("abc", "**b", ""));
    }

    public void testFindEntries1() throws Exception {
        boolean[] entries = PossibilityGenerator.Tester.findEntries(new String[] {"", "", "has", "not", ""}, "a");
        boolean[] expected = new boolean[]{false, false, true, false, false};
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], entries[i]);
        }
    }

    public void testFindEntries2() throws Exception {
        boolean[] entries = PossibilityGenerator.Tester.findEntries(new String[] {"a a", "", "a"}, "a");
        boolean[] expected = new boolean[]{true, false, true};
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], entries[i]);
        }
    }

    public void testFindEntries3() throws Exception {
        boolean[] entries = PossibilityGenerator.Tester.findEntries(new String[] {"a a a", "", "a"}, "a a");
        boolean[] expected = new boolean[]{true, false, false};
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], entries[i]);
        }
    }

    public void testPossibilitiesTemp() throws Exception {
        WordsmithImpl wordsmith = new WordsmithImpl(WordLoaderImpl.getInstance());
        Board board = new Board(wordsmith);
        PossibilityGenerator generator = new PossibilityGenerator(wordsmith, board);

        replay();
        board.putLetters("chubs", Board.getSquare(Board.MID_POINT, Board.MID_POINT - 2), Direction.ACROSS);
        board.putLetters("doge", Board.getSquare(Board.MID_POINT - 4, Board.MID_POINT + 2), Direction.DOWN);
        board.putLetters("gel", Board.getSquare(Board.MID_POINT - 4, Board.MID_POINT - 1), Direction.ACROSS);
        board.putLetters("phon", Board.getSquare(Board.MID_POINT - 3, Board.MID_POINT - 3), Direction.ACROSS);
        board.putLetters("via", Board.getSquare(Board.MID_POINT - 2, 3), Direction.ACROSS);

        assertEquals(
                "               \n" +
                "               \n" +
                "               \n" +
                "      geld     \n" +
                "    phon o     \n" +
                "   via   g     \n" +
                "         e     \n" +
                "     chubs     \n" +
                "               \n" +
                "               \n" +
                "               \n" +
                "               \n" +
                "               \n" +
                "               \n" +
                "               \n",
                board.getBoard()
        );
        Collection<Possibility> possibilities = generator.generate("i*e", 10);
        System.out.println("possibilities = " + possibilities);

    }

    public void testPossibilities() throws Exception {
        WordsmithImpl wordsmith = new WordsmithImpl(WordLoaderImpl.getInstance());
        Board board = new Board(wordsmith);
        PossibilityGenerator generator = new PossibilityGenerator(wordsmith, board);

        replay();
        board.putLetters("chubs", Board.getSquare(Board.MID_POINT, Board.MID_POINT - 2), Direction.ACROSS);
        board.putLetters("elk", Board.getSquare(Board.MID_POINT - 1, Board.MID_POINT + 2), Direction.ACROSS);
        board.putLetters("dog", Board.getSquare(Board.MID_POINT - 4, Board.MID_POINT + 2), Direction.DOWN);
        board.putLetters("gel", Board.getSquare(Board.MID_POINT - 4, Board.MID_POINT - 1), Direction.ACROSS);
        board.putLetters("or*tz", Board.getSquare(Board.MID_POINT + 1, Board.MID_POINT + 1), Direction.DOWN);
        board.putLetters("tuti", Board.getSquare(Board.MID_POINT + 3, Board.MID_POINT - 2), Direction.ACROSS);
        board.putLetters("phon", Board.getSquare(Board.MID_POINT - 3, Board.MID_POINT - 3), Direction.ACROSS);
        board.putLetters("ax", Board.getSquare(Board.MID_POINT + 2, Board.MID_POINT + 2), Direction.ACROSS);
        board.putLetters("efts", Board.getSquare(Board.MID_POINT + 1, Board.MID_POINT + 3), Direction.ACROSS);
        board.putLetters("runbac", Board.getSquare(0, Board.MID_POINT + 4), Direction.DOWN);
        board.putLetters("whinni*e", Board.getSquare(1, Board.MID_POINT + 6), Direction.DOWN);
        board.putLetters("js", Board.getSquare(Board.MID_POINT + 2, Board.MID_POINT - 1), Direction.DOWN);
        board.putLetters("sqa", Board.getSquare(1, Board.MID_POINT + 2), Direction.ACROSS);
        board.putLetters("via", Board.getSquare(Board.MID_POINT - 2, 3), Direction.ACROSS);
        board.putLetters("s*it", Board.getSquare(1, Board.BOARD_SIZE - 1), Direction.DOWN);
        board.putLetters("to", Board.getSquare(Board.MID_POINT + 5, Board.MID_POINT - 1), Direction.ACROSS);
        board.putLetters("sh", Board.getSquare(Board.MID_POINT + 6, Board.MID_POINT - 1), Direction.ACROSS);
        board.putLetters("it", Board.getSquare(9, 12), Direction.ACROSS);

        assertEquals(
                "           r   \n" +
                "         squaws\n" +
                "           n hi\n" +
                "      geld b it\n" +
                "    phon o a n \n" +
                "   via   g c n \n" +
                "         elk i \n" +
                "     chubs   e \n" +
                "        o efts \n" +
                "      j rax it \n" +
                "     tutti     \n" +
                "      s z      \n" +
                "      to       \n" +
                "      sh       \n" +
                "               \n",
                board.getBoard()
        );
        Collection<Possibility> possibilities = generator.generate("*efitty", 10);
        System.out.println("possibilities = " + possibilities);

    }

    public void testPossibilities2() throws Exception {
        WordsmithImpl wordsmith = new WordsmithImpl(WordLoaderImpl.getInstance());
        Board board = new Board(wordsmith);
        PossibilityGenerator generator = new PossibilityGenerator(wordsmith, board);

        replay();
        board.putLetters("hob", Board.getSquare(Board.MID_POINT, Board.MID_POINT - 1), Direction.ACROSS);
        board.putLetters("habit", Board.getSquare(Board.MID_POINT - 1, Board.MID_POINT ), Direction.ACROSS);
        board.putLetters("jnx*ed", Board.getSquare(Board.MID_POINT - 2, Board.MID_POINT + 3), Direction.DOWN);
        board.putLetters("steeks", Board.getSquare(1, Board.MID_POINT + 5), Direction.DOWN);
        board.putLetters("ant*igne", Board.getSquare(3, Board.MID_POINT), Direction.ACROSS);
        board.putLetters("dents", Board.getSquare(Board.MID_POINT + 2, Board.MID_POINT + 2), Direction.DOWN);
        board.putLetters("ue", Board.getSquare(Board.MID_POINT - 2, Board.MID_POINT + 4), Direction.ACROSS);
        board.putLetters("arils", Board.getSquare(0, Board.BOARD_SIZE - 1), Direction.DOWN);
        board.putLetters("toea", Board.getSquare(Board.MID_POINT + 1, Board.MID_POINT - 2), Direction.ACROSS);
        board.putLetters("hillo", Board.getSquare(Board.MID_POINT + 3, Board.MID_POINT + 4), Direction.DOWN);
        board.putLetters("courant", Board.getSquare(4, 1), Direction.ACROSS);
        board.putLetters("fiq*e", Board.getSquare(1, 3), Direction.DOWN);
        board.putLetters("ray", Board.getSquare(2, 1), Direction.DOWN);
        board.putLetters("whop", Board.getSquare(0, 0), Direction.DOWN);
        board.putLetters("sew", Board.getSquare(6, 3), Direction.ACROSS);
        board.putLetters("z*ag", Board.getSquare(Board.MID_POINT + 2, Board.MID_POINT - 3), Direction.ACROSS);
        board.putLetters("s", Board.getSquare(Board.MID_POINT + 2, Board.MID_POINT), Direction.ACROSS);
        board.putLetters("ruh", Board.getSquare(1, Board.MID_POINT + 2), Direction.DOWN);
        board.putLetters("at", Board.getSquare(3, 4), Direction.ACROSS);

        assertEquals(
                "w             a\n" +
                "h  f     r  s r\n" +
                "or i     u  t i\n" +
                "pa qat antigene\n" +
                " courant h  e l\n" +
                " y e      jukes\n" +
                "   sew habits  \n" +
                "      hob n    \n" +
                "     toea x    \n" +
                "    zags de    \n" +
                "         edh   \n" +
                "         n i   \n" +
                "         t l   \n" +
                "         s l   \n" +
                "           o   \n",
                board.getBoard()
        );
        Collection<Possibility> possibilities = generator.generate("addmtty", 10);
        System.out.println("possibilities = " + possibilities);

    }

}
