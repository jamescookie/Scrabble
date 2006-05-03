package com.jamescookie.scrabble;

import org.easymock.MockControl;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author ukjamescook
 */
public class PossibilityGeneratorTest extends Tester {
    private ResultExpecter expecter;
    private MockControl expecterControl;
    public static final String TEST_BOARD =
        "               \n" +
        "               \n" +
        "               \n" +
        "               \n" +
        "               \n" +
        "               \n" +
        "               \n" +
        "     test      \n" +
        "               \n" +
        "               \n" +
        "               \n" +
        "               \n" +
        "               \n" +
        "               \n" +
        "               \n";

    protected void setUp() throws Exception {
        super.setUp();
        expecterControl = MockControl.createControl(ResultExpecter.class);
        expecter = (ResultExpecter) expecterControl.getMock();
    }

    private void expectResults() {
        expecter.resultsAreReady();
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

    public void testReturnSize1() throws Exception {
        WordsmithImpl wordsmith = new WordsmithImpl(WordLoaderImpl.getInstance());
        Board board = new Board(wordsmith);
        PossibilityGenerator generator = new PossibilityGenerator(wordsmith, board);

        replay();
        board.putLetters("test", Board.getSquare(Board.MID_POINT, Board.MID_POINT - 2), Direction.ACROSS);

        assertEquals(TEST_BOARD, Board.getBoard());
        expectResults();
        expecterControl.replay();
        generator.generate("test", 5, expecter);
        generator.waitForResults();
        Collection<Possibility> possibilities = generator.getResults();
        expecterControl.verify();

        assertEquals(5, possibilities.size());
    }

    public void testReturnSize2() throws Exception {
        WordsmithImpl wordsmith = new WordsmithImpl(WordLoaderImpl.getInstance());
        Board board = new Board(wordsmith);
        PossibilityGenerator generator = new PossibilityGenerator(wordsmith, board);

        replay();
        board.putLetters("test", Board.getSquare(Board.MID_POINT, Board.MID_POINT - 2), Direction.ACROSS);

        assertEquals(TEST_BOARD, Board.getBoard());
        expectResults();
        expecterControl.replay();
        generator.generate("test", 15, expecter);
        generator.waitForResults();
        Collection<Possibility> possibilities = generator.getResults();
        expecterControl.verify();

        assertEquals(15, possibilities.size());
    }

    public void testNegativeReturnSize() throws Exception {
        WordsmithImpl wordsmith = new WordsmithImpl(WordLoaderImpl.getInstance());
        Board board = new Board(wordsmith);
        PossibilityGenerator generator = new PossibilityGenerator(wordsmith, board);

        replay();
        board.putLetters("test", Board.getSquare(Board.MID_POINT, Board.MID_POINT - 2), Direction.ACROSS);

        assertEquals(TEST_BOARD, Board.getBoard());
        expectResults();
        expecterControl.replay();
        generator.generate("test", -1, expecter);
        generator.waitForResults();
        Collection<Possibility> possibilities = generator.getResults();
        expecterControl.verify();

        assertEquals(1, possibilities.size());
    }

    public void testZeroReturnSize() throws Exception {
        WordsmithImpl wordsmith = new WordsmithImpl(WordLoaderImpl.getInstance());
        Board board = new Board(wordsmith);
        PossibilityGenerator generator = new PossibilityGenerator(wordsmith, board);

        replay();
        board.putLetters("test", Board.getSquare(Board.MID_POINT, Board.MID_POINT - 2), Direction.ACROSS);

        assertEquals(TEST_BOARD, Board.getBoard());
        expectResults();
        expecterControl.replay();
        generator.generate("test", 0, expecter);
        generator.waitForResults();
        Collection<Possibility> possibilities = generator.getResults();
        expecterControl.verify();

        assertEquals(1, possibilities.size());
    }

    public void testGettingResultsTwice() throws Exception {
        WordsmithImpl wordsmith = new WordsmithImpl(WordLoaderImpl.getInstance());
        Board board = new Board(wordsmith);
        PossibilityGenerator generator = new PossibilityGenerator(wordsmith, board);

        replay();
        board.putLetters("test", Board.getSquare(Board.MID_POINT, Board.MID_POINT - 2), Direction.ACROSS);

        assertEquals(TEST_BOARD, Board.getBoard());
        expectResults();
        expecterControl.replay();
        generator.generate("test", 5, expecter);
        generator.waitForResults();
        generator.getResults();
        Collection<Possibility> possibilities = generator.getResults();
        expecterControl.verify();

        assertNull(possibilities);
    }

    public void testStop() throws Exception {
        WordsmithImpl wordsmith = new WordsmithImpl(WordLoaderImpl.getInstance());
        Board board = new Board(wordsmith);
        PossibilityGenerator generator = new PossibilityGenerator(wordsmith, board);

        replay();
        board.putLetters("test", Board.getSquare(Board.MID_POINT, Board.MID_POINT - 2), Direction.ACROSS);

        assertEquals(TEST_BOARD, Board.getBoard());
        expecterControl.replay();
        generator.generate("test", 5, expecter);
        generator.stop();
        Collection<Possibility> possibilities = generator.getResults();
        expecterControl.verify();

        assertNull(possibilities);
    }

    public void testPossibilitiesSize() throws Exception {
        WordsmithImpl wordsmith = new WordsmithImpl(WordLoaderImpl.getInstance());
        Board board = new Board(wordsmith);
        PossibilityGenerator generator = new PossibilityGenerator(wordsmith, board);

        replay();
        board.putLetters("test", Board.getSquare(Board.MID_POINT, Board.MID_POINT - 2), Direction.ACROSS);
        board.putLetters("geting", Board.getSquare(Board.MID_POINT - 2, Board.MID_POINT + 1), Direction.DOWN);

        assertEquals(
                "               \n" +
                "               \n" +
                "               \n" +
                "               \n" +
                "               \n" +
                "        g      \n" +
                "        e      \n" +
                "     test      \n" +
                "        t      \n" +
                "        i      \n" +
                "        n      \n" +
                "        g      \n" +
                "               \n" +
                "               \n" +
                "               \n",
                Board.getBoard()
        );
        expectResults();
        expecterControl.replay();
        long startTime = System.currentTimeMillis();
        generator.generate("tesid", 1000, expecter);
        generator.waitForResults();
        Collection<Possibility> possibilities = generator.getResults();
        assertTrue(1700 > System.currentTimeMillis()-startTime);
        expecterControl.verify();

        assertEquals(324, possibilities.size());
    }

    public void possibilitiesError1Test() throws Exception {
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
                Board.getBoard()
        );
        generator.generate("i*e", 10, expecter);
        Collection<Possibility> possibilities = generator.getResults();
        System.out.println("possibilities = " + possibilities);
    }

    public void possibilitiesTest() throws Exception {
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
                Board.getBoard()
        );
        generator.generate("*efitty", 10, expecter);
        Collection<Possibility> possibilities = generator.getResults();
        System.out.println("possibilities = " + possibilities);

    }

    public void possibilitiesTest2() throws Exception {
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
                Board.getBoard()
        );
        generator.generate("addmtty", 10, expecter);
        Collection<Possibility> possibilities = generator.getResults();
        System.out.println("possibilities = " + possibilities);

    }

}
