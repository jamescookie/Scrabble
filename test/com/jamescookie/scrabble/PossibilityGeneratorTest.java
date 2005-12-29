package com.jamescookie.scrabble;

import java.util.ArrayList;
import java.util.List;
import java.util.Collection;

/**
 * @author ukjamescook
 */
public class PossibilityGeneratorTest extends Tester {

    protected void setUp() throws Exception {
        super.setUp();
    }

    public void testExtractCombinationsWithNothing() throws Exception {
        List<String> strings = new ArrayList<String>();

        strings = PossibilityGenerator.Tester.extractCombinations(strings);

        assertEquals(0, strings.size());
    }

    public void testExtractCombinationsWithOneString() throws Exception {
        List<String> strings = new ArrayList<String>();
        String s = "a";
        strings.add(s);

        strings = PossibilityGenerator.Tester.extractCombinations(strings);

        assertEquals(1, strings.size());
        assertTrue(strings.contains(s));
    }

    public void testExtractCombinationsWithOneSpace() throws Exception {
        List<String> strings = new ArrayList<String>();
        String s = "a b";
        strings.add(s);

        strings = PossibilityGenerator.Tester.extractCombinations(strings);

        assertEquals(3, strings.size());
        assertTrue(strings.contains(s));
        assertTrue(strings.contains("a"));
        assertTrue(strings.contains("b"));
    }

    public void testExtractCombinationsWithTwoSpacesInARow() throws Exception {
        List<String> strings = new ArrayList<String>();
        String s = "a  b";
        strings.add(s);

        strings = PossibilityGenerator.Tester.extractCombinations(strings);

        assertEquals(3, strings.size());
        assertTrue(strings.contains(s));
        assertTrue(strings.contains("a"));
        assertTrue(strings.contains("b"));
    }

    public void testExtractCombinationsWithThreeSetsOfLetters() throws Exception {
        List<String> strings = new ArrayList<String>();
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
        List<String> strings = new ArrayList<String>();
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
        List<String> strings = new ArrayList<String>();
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
        List<String> strings = new ArrayList<String>();
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
        List<String> strings = new ArrayList<String>();
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


    public void testPossibilities() throws Exception {
        WordsmithImpl wordsmith = new WordsmithImpl(WordLoaderImpl.getInstance());
        PossibilityGenerator generator = new PossibilityGenerator(wordsmith);
        Board board = new Board(wordsmith);

        replay();
        board.putLetters("chubs", board.getSquare(Board.MID_POINT, Board.MID_POINT - 2), Direction.ACROSS);
        board.putLetters("elk", board.getSquare(Board.MID_POINT - 1, Board.MID_POINT + 2), Direction.ACROSS);
        board.putLetters("dog", board.getSquare(Board.MID_POINT - 4, Board.MID_POINT + 2), Direction.DOWN);
        board.putLetters("gel", board.getSquare(Board.MID_POINT - 4, Board.MID_POINT - 1), Direction.ACROSS);
        board.putLetters("ortz", board.getSquare(Board.MID_POINT + 1, Board.MID_POINT + 1), Direction.DOWN);
        board.putLetters("tuti", board.getSquare(Board.MID_POINT + 3, Board.MID_POINT - 2), Direction.ACROSS);
        board.putLetters("phon", board.getSquare(Board.MID_POINT - 3, Board.MID_POINT - 3), Direction.ACROSS);
        board.putLetters("ax", board.getSquare(Board.MID_POINT + 2, Board.MID_POINT + 2), Direction.ACROSS);
        board.putLetters("efts", board.getSquare(Board.MID_POINT + 1, Board.MID_POINT + 3), Direction.ACROSS);
        board.putLetters("runbac", board.getSquare(0, Board.MID_POINT + 4), Direction.DOWN);
        board.putLetters("whinnie", board.getSquare(1, Board.MID_POINT + 6), Direction.DOWN);
        board.putLetters("js", board.getSquare(Board.MID_POINT + 2, Board.MID_POINT - 1), Direction.DOWN);
        board.putLetters("sqa", board.getSquare(1, Board.MID_POINT + 2), Direction.ACROSS);


        assertEquals(
                "           r   \n" +
                "         squaw \n" +
                "           n h \n" +
                "      geld b i \n" +
                "    phon o a n \n" +
                "         g c n \n" +
                "         elk i \n" +
                "     chubs   e \n" +
                "        o efts \n" +
                "      j rax    \n" +
                "     tutti     \n" +
                "      s z      \n" +
                "               \n" +
                "               \n" +
                "               \n",
                board.getBoard()
        );
        generator.setBoard(board);
        Collection<Possibility> possibilities = generator.generate("afiovy");
        System.out.println("possibilities = " + possibilities);

    }

}
