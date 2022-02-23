package com.jamescookie.scrabble;

import com.jamescookie.scrabble.types.Game;
import com.jamescookie.scrabble.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author ukjamescook
 */
public class PossibilityGeneratorTest {
    private final AtomicBoolean finished = new AtomicBoolean(false);
    private final Wordsmith wordsmith = new WordsmithImpl(WordLoaderImpl.getInstance());
    private ResultExpector expector;
    private Game game;
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

    @BeforeEach
    protected void setUp() {
        game = Game.itsYourTurn();
        finished.set(false);
        expector = () -> finished.set(true);
    }

    @Test
    public void testExtractCombinationsWithNothing() {
        Collection<String> strings = new ArrayList<>();

        strings = PossibilityGenerator.extractCombinations(strings);

        assertEquals(0, strings.size());
    }

    @Test
    public void testExtractCombinationsWithOneString() {
        Collection<String> strings = new ArrayList<>();
        String s = "a";
        strings.add(s);

        strings = PossibilityGenerator.extractCombinations(strings);

        assertEquals(1, strings.size());
        assertTrue(strings.contains(s));
    }

    @Test
    public void testExtractCombinationsWithOneSpace() {
        Collection<String> strings = new ArrayList<>();
        String s = "a b";
        strings.add(s);

        strings = PossibilityGenerator.extractCombinations(strings);

        assertEquals(3, strings.size());
        assertTrue(strings.contains(s));
        assertTrue(strings.contains("a"));
        assertTrue(strings.contains("b"));
    }

    @Test
    public void testExtractCombinationsWithTwoSpacesInARow() {
        Collection<String> strings = new ArrayList<>();
        String s = "a  b";
        strings.add(s);

        strings = PossibilityGenerator.extractCombinations(strings);

        assertEquals(3, strings.size());
        assertTrue(strings.contains(s));
        assertTrue(strings.contains("a"));
        assertTrue(strings.contains("b"));
    }

    @Test
    public void testExtractCombinationsWithThreeSetsOfLetters() {
        Collection<String> strings = new ArrayList<>();
        String s = "a b c";
        strings.add(s);

        strings = PossibilityGenerator.extractCombinations(strings);

        assertEquals(5, strings.size());
        assertTrue(strings.contains(s));
        assertTrue(strings.contains("a b"));
        assertTrue(strings.contains("b c"));
        assertTrue(strings.contains("a"));
        assertTrue(strings.contains("c"));
    }

    @Test
    public void testExtractCombinationsWithThreeSetsOfSingleLettersWithEnoughSpace3() {
        Collection<String> strings = new ArrayList<>();
        String s = "a  b c";
        strings.add(s);

        strings = PossibilityGenerator.extractCombinations(strings);

        assertEquals(6, strings.size());
        assertTrue(strings.contains(s));
        assertTrue(strings.contains("a  b"));
        assertTrue(strings.contains("b c"));
        assertTrue(strings.contains("a"));
        assertTrue(strings.contains("b"));
        assertTrue(strings.contains("c"));
    }

    @Test
    public void testExtractCombinationsWithThreeSetsOfSingleLettersWithEnoughSpace2() {
        Collection<String> strings = new ArrayList<>();
        String s = "a b  c";
        strings.add(s);

        strings = PossibilityGenerator.extractCombinations(strings);

        assertEquals(6, strings.size());
        assertTrue(strings.contains(s));
        assertTrue(strings.contains("a b"));
        assertTrue(strings.contains("b  c"));
        assertTrue(strings.contains("a"));
        assertTrue(strings.contains("b"));
        assertTrue(strings.contains("c"));
    }

    @Test
    public void testExtractCombinationsWithThreeSetsOfDoubleLetters() {
        Collection<String> strings = new ArrayList<>();
        String s = "aa bb cc";
        strings.add(s);

        strings = PossibilityGenerator.extractCombinations(strings);

        assertEquals(5, strings.size());
        assertTrue(strings.contains(s));
        assertTrue(strings.contains("aa"));
        assertTrue(strings.contains("cc"));
        assertTrue(strings.contains("aa bb"));
        assertTrue(strings.contains("bb cc"));
    }

    @Test
    public void testExtractCombinationsWithThreeSetsOfDoubleLettersWithEnoughSpace() {
        Collection<String> strings = new ArrayList<>();
        String s = "aa  bb  cc";
        strings.add(s);

        strings = PossibilityGenerator.extractCombinations(strings);

        assertEquals(6, strings.size());
        assertTrue(strings.contains(s));
        assertTrue(strings.contains("aa"));
        assertTrue(strings.contains("bb"));
        assertTrue(strings.contains("cc"));
        assertTrue(strings.contains("aa  bb"));
        assertTrue(strings.contains("bb  cc"));
    }

    @Test
    public void testReturnSize1() throws ScrabbleException {
        Board board = new Board(wordsmith, game);
        PossibilityGenerator generator = new PossibilityGenerator(wordsmith, board);

        board.putLetters("test", board.getSquare(Board.MID_POINT, Board.MID_POINT - 2), Direction.ACROSS);

        assertEquals(TEST_BOARD, TestUtils.boardToString(board));
        generator.generate("test", 5, expector);
        generator.waitForResults();
        Collection<Possibility> possibilities = generator.getResults();
        verifyFinished();

        assertEquals(5, possibilities.size());
    }

    @Test
    public void testReturnSize2() throws ScrabbleException {
        Board board = new Board(wordsmith, game);
        PossibilityGenerator generator = new PossibilityGenerator(wordsmith, board);

        board.putLetters("test", board.getSquare(Board.MID_POINT, Board.MID_POINT - 2), Direction.ACROSS);

        assertEquals(TEST_BOARD, TestUtils.boardToString(board));
        generator.generate("test", 15, expector);
        generator.waitForResults();
        Collection<Possibility> possibilities = generator.getResults();
        verifyFinished();

        assertEquals(15, possibilities.size());
    }

    @Test
    public void testNegativeReturnSize() throws ScrabbleException {
        Board board = new Board(wordsmith, game);
        PossibilityGenerator generator = new PossibilityGenerator(wordsmith, board);

        board.putLetters("test", board.getSquare(Board.MID_POINT, Board.MID_POINT - 2), Direction.ACROSS);

        assertEquals(TEST_BOARD, TestUtils.boardToString(board));
        generator.generate("test", -1, expector);
        generator.waitForResults();
        Collection<Possibility> possibilities = generator.getResults();
        verifyFinished();

        assertEquals(1, possibilities.size());
    }

    @Test
    public void testZeroReturnSize() throws ScrabbleException {
        Board board = new Board(wordsmith, game);
        PossibilityGenerator generator = new PossibilityGenerator(wordsmith, board);

        board.putLetters("test", board.getSquare(Board.MID_POINT, Board.MID_POINT - 2), Direction.ACROSS);

        assertEquals(TEST_BOARD, TestUtils.boardToString(board));
        generator.generate("test", 0, expector);
        generator.waitForResults();
        Collection<Possibility> possibilities = generator.getResults();
        verifyFinished();

        assertEquals(1, possibilities.size());
    }

    @Test
    public void testGettingResultsTwice() throws ScrabbleException {
        Board board = new Board(wordsmith, game);
        PossibilityGenerator generator = new PossibilityGenerator(wordsmith, board);

        board.putLetters("test", board.getSquare(Board.MID_POINT, Board.MID_POINT - 2), Direction.ACROSS);

        assertEquals(TEST_BOARD, TestUtils.boardToString(board));
        generator.generate("test", 5, expector);
        generator.waitForResults();
        generator.getResults();
        Collection<Possibility> possibilities = generator.getResults();
        verifyFinished();

        assertNull(possibilities);
    }

    @Test
    public void testStop() throws ScrabbleException {
        Board board = new Board(wordsmith, game);
        PossibilityGenerator generator = new PossibilityGenerator(wordsmith, board);

        board.putLetters("test", board.getSquare(Board.MID_POINT, Board.MID_POINT - 2), Direction.ACROSS);

        assertEquals(TEST_BOARD, TestUtils.boardToString(board));
        generator.generate("test", 5, expector);
        generator.stop();
        Collection<Possibility> possibilities = generator.getResults();

        assertNull(possibilities);
    }

    @Test
    public void testPossibilities() {
        Board board = new Board(wordsmith, game);
        PossibilityGenerator generator = new PossibilityGenerator(wordsmith, board);

        long startTime = System.currentTimeMillis();
        String letters = "test";
        generator.generate(letters, 1000, expector);
        generator.waitForResults();
        Collection<Possibility> possibilities = generator.getResults();
        long timeTaken = System.currentTimeMillis() - startTime;
        assertTrue(timeTaken < 500);
        verifyFinished();

        assertEquals(letters.length() * 2, possibilities.stream().filter(p -> p.getLetters().equals(letters)).count());
        assertEquals(52, possibilities.size());
    }

    @Test
    public void testPossibilitiesWithWildcard1() {
        Board board = new Board(wordsmith, game);
        PossibilityGenerator generator = new PossibilityGenerator(wordsmith, board);

        long startTime = System.currentTimeMillis();
        String letters = "*t";
        generator.generate(letters, 1000, expector);
        generator.waitForResults();
        Collection<Possibility> possibilities = generator.getResults();
        long timeTaken = System.currentTimeMillis() - startTime;
        assertTrue(timeTaken < 1000);
        verifyFinished();

        int expected = 7;
        assertEquals(expected * 4, possibilities.size());
        Set<String> words = possibilities.stream().map(Possibility::getLetters).collect(Collectors.toSet());
        assertEquals(expected, words.size());
        assertTrue(words.stream().allMatch(p -> p.contains("t")));
        assertTrue(words.stream().noneMatch(p -> p.contains("*t")));
        assertTrue(words.contains("*it"));
        assertTrue(words.contains("t*a"));
    }

    @Test
    public void testPossibilitiesWithWildcard2() {
        Board board = new Board(wordsmith, game);
        PossibilityGenerator generator = new PossibilityGenerator(wordsmith, board);

        long startTime = System.currentTimeMillis();
        String letters = "t*st";
        generator.generate(letters, 1000, expector);
        generator.waitForResults();
        Collection<Possibility> possibilities = generator.getResults();
        long timeTaken = System.currentTimeMillis() - startTime;
        assertTrue(timeTaken < 1000);
        verifyFinished();

        assertEquals(236, possibilities.size());
    }

    @Test
    public void testPossibilitiesSize() throws ScrabbleException {
        Board board = new Board(wordsmith, game);
        PossibilityGenerator generator = new PossibilityGenerator(wordsmith, board);

        board.putLetters("test", board.getSquare(Board.MID_POINT, Board.MID_POINT - 2), Direction.ACROSS);
        board.putLetters("geting", board.getSquare(Board.MID_POINT - 2, Board.MID_POINT + 1), Direction.DOWN);

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
                TestUtils.boardToString(board)
        );
        long startTime = System.currentTimeMillis();
        generator.generate("tesid", 1000, expector);
        generator.waitForResults();
        Collection<Possibility> possibilities = generator.getResults();
        assertTrue(1700 > System.currentTimeMillis() - startTime);
        verifyFinished();

        assertEquals(324, possibilities.size());
    }

    @Test
    public void possibilitiesError1Test() throws ScrabbleException {
        Board board = new Board(wordsmith, game);
        PossibilityGenerator generator = new PossibilityGenerator(wordsmith, board);

        board.putLetters("chubs", board.getSquare(Board.MID_POINT, Board.MID_POINT - 2), Direction.ACROSS);
        board.putLetters("doge", board.getSquare(Board.MID_POINT - 4, Board.MID_POINT + 2), Direction.DOWN);
        board.putLetters("gel", board.getSquare(Board.MID_POINT - 4, Board.MID_POINT - 1), Direction.ACROSS);
        board.putLetters("phon", board.getSquare(Board.MID_POINT - 3, Board.MID_POINT - 3), Direction.ACROSS);
        board.putLetters("via", board.getSquare(Board.MID_POINT - 2, 3), Direction.ACROSS);

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
                TestUtils.boardToString(board)
        );
        generator.generate("i*e", 10, expector);
        Collection<Possibility> possibilities = generator.getResults();
        System.out.println("possibilities = " + possibilities);
    }

    @Test
    public void possibilitiesTest() throws ScrabbleException {
        Board board = new Board(wordsmith, Game.itsYourTurnWild());
        PossibilityGenerator generator = new PossibilityGenerator(wordsmith, board);

        board.putLetters("chubs", board.getSquare(Board.MID_POINT, Board.MID_POINT - 2), Direction.ACROSS);
        board.putLetters("elk", board.getSquare(Board.MID_POINT - 1, Board.MID_POINT + 2), Direction.ACROSS);
        board.putLetters("dog", board.getSquare(Board.MID_POINT - 4, Board.MID_POINT + 2), Direction.DOWN);
        board.putLetters("gel", board.getSquare(Board.MID_POINT - 4, Board.MID_POINT - 1), Direction.ACROSS);
        board.putLetters("or*tz", board.getSquare(Board.MID_POINT + 1, Board.MID_POINT + 1), Direction.DOWN);
        board.putLetters("tuti", board.getSquare(Board.MID_POINT + 3, Board.MID_POINT - 2), Direction.ACROSS);
        board.putLetters("phon", board.getSquare(Board.MID_POINT - 3, Board.MID_POINT - 3), Direction.ACROSS);
        board.putLetters("ax", board.getSquare(Board.MID_POINT + 2, Board.MID_POINT + 2), Direction.ACROSS);
        board.putLetters("efts", board.getSquare(Board.MID_POINT + 1, Board.MID_POINT + 3), Direction.ACROSS);
        board.putLetters("runbac", board.getSquare(0, Board.MID_POINT + 4), Direction.DOWN);
        board.putLetters("whinni*e", board.getSquare(1, Board.MID_POINT + 6), Direction.DOWN);
        board.putLetters("js", board.getSquare(Board.MID_POINT + 2, Board.MID_POINT - 1), Direction.DOWN);
        board.putLetters("sqa", board.getSquare(1, Board.MID_POINT + 2), Direction.ACROSS);
        board.putLetters("via", board.getSquare(Board.MID_POINT - 2, 3), Direction.ACROSS);
        board.putLetters("s*it", board.getSquare(1, Board.BOARD_SIZE - 1), Direction.DOWN);
        board.putLetters("to", board.getSquare(Board.MID_POINT + 5, Board.MID_POINT - 1), Direction.ACROSS);
        board.putLetters("sh", board.getSquare(Board.MID_POINT + 6, Board.MID_POINT - 1), Direction.ACROSS);
        board.putLetters("it", board.getSquare(9, 12), Direction.ACROSS);

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
                TestUtils.boardToString(board)
        );
        generator.generate("*efitty", 10, expector);
        generator.waitForResults();
        Collection<Possibility> possibilities = generator.getResults();
        System.out.println("possibilities = " + possibilities);
        assertEquals(10, possibilities.size());

    }

    @Test
    public void possibilitiesTest2() throws ScrabbleException {
        Board board = new Board(wordsmith, Game.itsYourTurnWild());
        PossibilityGenerator generator = new PossibilityGenerator(wordsmith, board);

        board.putLetters("hob", board.getSquare(Board.MID_POINT, Board.MID_POINT - 1), Direction.ACROSS);
        board.putLetters("habit", board.getSquare(Board.MID_POINT - 1, Board.MID_POINT), Direction.ACROSS);
        board.putLetters("jnx*ed", board.getSquare(Board.MID_POINT - 2, Board.MID_POINT + 3), Direction.DOWN);
        board.putLetters("steeks", board.getSquare(1, Board.MID_POINT + 5), Direction.DOWN);
        board.putLetters("ant*igne", board.getSquare(3, Board.MID_POINT), Direction.ACROSS);
        board.putLetters("dents", board.getSquare(Board.MID_POINT + 2, Board.MID_POINT + 2), Direction.DOWN);
        board.putLetters("ue", board.getSquare(Board.MID_POINT - 2, Board.MID_POINT + 4), Direction.ACROSS);
        board.putLetters("arils", board.getSquare(0, Board.BOARD_SIZE - 1), Direction.DOWN);
        board.putLetters("toea", board.getSquare(Board.MID_POINT + 1, Board.MID_POINT - 2), Direction.ACROSS);
        board.putLetters("hillo", board.getSquare(Board.MID_POINT + 3, Board.MID_POINT + 4), Direction.DOWN);
        board.putLetters("courant", board.getSquare(4, 1), Direction.ACROSS);
        board.putLetters("fiq*e", board.getSquare(1, 3), Direction.DOWN);
        board.putLetters("ray", board.getSquare(2, 1), Direction.DOWN);
        board.putLetters("whop", board.getSquare(0, 0), Direction.DOWN);
        board.putLetters("sew", board.getSquare(6, 3), Direction.ACROSS);
        board.putLetters("z*ag", board.getSquare(Board.MID_POINT + 2, Board.MID_POINT - 3), Direction.ACROSS);
        board.putLetters("s", board.getSquare(Board.MID_POINT + 2, Board.MID_POINT), Direction.ACROSS);
        board.putLetters("ruh", board.getSquare(1, Board.MID_POINT + 2), Direction.DOWN);
        board.putLetters("at", board.getSquare(3, 4), Direction.ACROSS);

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
                TestUtils.boardToString(board)
        );
        generator.generate("addmtty", 10, expector);
        generator.waitForResults();
        Collection<Possibility> possibilities = generator.getResults();
        System.out.println("possibilities = " + possibilities);
        assertEquals(10, possibilities.size());

    }

    private void verifyFinished() {
        assertTrue(finished.get());
    }
}
