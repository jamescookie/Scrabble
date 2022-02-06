package com.jamescookie.scrabble;

import com.jamescookie.scrabble.types.Game;
import com.jamescookie.scrabble.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.jamescookie.scrabble.Board.BOARD_SIZE;
import static com.jamescookie.scrabble.Board.MID_POINT;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


/**
 * @author ukjamescook
 */
@ExtendWith(MockitoExtension.class)
public class BoardTest {
    @Mock
    private Wordsmith wordsmith;

    private Board board;

    @BeforeEach
    public void setup() {
        board = new Board(wordsmith, Game.testing());
    }

    @Test
    public void testBoardConstruction() throws Exception {
        Square sq;
        for (int row = 1; row <= Board.BOARD_SIZE; row++) {
            for (int col = 1; col <= Board.BOARD_SIZE; col++) {
                sq = board.getSquare(row - 1, col - 1);
                assertEquals((row - 1), sq.getRow(), sq + " has incorrect row");
                assertEquals((col - 1), sq.getCol(), sq + " has incorrect col");
            }
        }
    }

    @Test
    public void testTripleWords() throws Exception {
        for (int row = 1; row <= Board.BOARD_SIZE; row++) {
            for (int col = 1; col <= Board.BOARD_SIZE; col++) {
                if ((row == 1 && (col == 4 || col == 12)) ||
                        (row == 4 && (col == 1 || col == 15)) ||
                        (row == 12 && (col == 1 || col == 15)) ||
                        (row == 15 && (col == 4 || col == 12))) {
                    assertTrue(Square.getTripleWord(0, 0).equivalentMods(board.getSquare(row - 1, col - 1)), "row " + row + " & col " + col + " is not a triple word!");
                } else {
                    assertFalse(Square.getTripleWord(0, 0).equivalentMods(board.getSquare(row - 1, col - 1)), "row " + row + " & col " + col + " is a triple word!");
                }
            }
        }
    }

    @Test
    public void testDoubleWords() throws Exception {
        for (int row = 1; row <= Board.BOARD_SIZE; row++) {
            for (int col = 1; col <= Board.BOARD_SIZE; col++) {
                if ((row == 2 && (col == 3 || col == 13)) ||
                        (row == 3 && (col == 2 || col == 14)) ||
                        (row == 5 && (col == 6 || col == 10)) ||
                        (row == 6 && (col == 5 || col == 11)) ||
                        (row == 8 && col == 8) ||
                        (row == 10 && (col == 5 || col == 11)) ||
                        (row == 11 && (col == 6 || col == 10)) ||
                        (row == 13 && (col == 2 || col == 14)) ||
                        (row == 14 && (col == 3 || col == 13))) {
                    assertTrue(Square.getDoubleWord(0, 0).equivalentMods(board.getSquare(row - 1, col - 1)), "row " + row + " & col " + col + " is not a double word!");
                } else {
                    assertFalse(Square.getDoubleWord(0, 0).equivalentMods(board.getSquare(row - 1, col - 1)), "row " + row + " & col " + col + " is a double word!");
                }
            }
        }
    }

    @Test
    public void testTripleLetters() throws Exception {
        for (int row = 1; row <= Board.BOARD_SIZE; row++) {
            for (int col = 1; col <= Board.BOARD_SIZE; col++) {
                if ((row == 2 && col == 10) ||
                        (row == 4 && col == 7) ||
                        (row == 6 && col == 2) ||
                        (row == 7 && col == 12) ||
                        (row == 9 && col == 4) ||
                        (row == 10 && col == 14) ||
                        (row == 12 && col == 9) ||
                        (row == 14 && col == 6)) {
                    assertTrue(Square.getTripleLetter(0, 0).equivalentMods(board.getSquare(row - 1, col - 1)), "row " + row + " & col " + col + " is not a triple letter!");
                } else {
                    assertFalse(Square.getTripleLetter(0, 0).equivalentMods(board.getSquare(row - 1, col - 1)), "row " + row + " & col " + col + " is a triple letter!");
                }
            }
        }
    }

    @Test
    public void testDoubleLetters() throws Exception {
        for (int row = 1; row <= Board.BOARD_SIZE; row++) {
            for (int col = 1; col <= Board.BOARD_SIZE; col++) {
                if ((row == 1 && (col == 1 || col == 8 || col == 15)) ||
                        (row == 2 && col == 6) ||
                        (row == 4 && (col == 4 || col == 9 || col == 12)) ||
                        (row == 6 && col == 14) ||
                        (row == 7 && (col == 4 || col == 7 || col == 9)) ||
                        (row == 8 && (col == 1 || col == 15)) ||
                        (row == 9 && (col == 7 || col == 9 || col == 12)) ||
                        (row == 10 && col == 2) ||
                        (row == 12 && (col == 4 || col == 7 || col == 12)) ||
                        (row == 14 && col == 10) ||
                        (row == 15 && (col == 1 || col == 8 || col == 15))) {
                    assertTrue(Square.getDoubleLetter(0, 0).equivalentMods(board.getSquare(row - 1, col - 1)), "row " + row + " & col " + col + " is not a double letter!");
                } else {
                    assertFalse(Square.getDoubleLetter(0, 0).equivalentMods(board.getSquare(row - 1, col - 1)), "row " + row + " & col " + col + " is a double letter!");
                }
            }
        }
    }

    @Test
    public void testGetSquaresAcross() throws Exception {
        String word = "tests";
        int wordLength = word.length();
        int row = MID_POINT;
        int column = 5;

        String[] words = new String[]{word};
        expectWordVerification(words);

        board.putLetters(word, board.getSquare(row, column), Direction.ACROSS);
        checkWords(words);

        List<Square> squares = TestUtils.getOccupiedSquares(board);

        assertNotNull(squares);
        assertEquals(wordLength, squares.size());
        int i = column;
        for (Square square : squares) {
            Integer expected = i++;
            assertEquals(expected, square.getCol());
            assertEquals(row, square.getRow());
        }
    }

    @Test
    public void testGetSquaresDown() throws Exception {
        String word = "tested";
        int wordLength = word.length();
        int row = 4;
        int column = MID_POINT;

        String[] words = new String[]{word};
        expectWordVerification(words);

        board.putLetters(word, board.getSquare(row, column), Direction.DOWN);
        checkWords(words);

        List<Square> squares = TestUtils.getOccupiedSquares(board);

        assertNotNull(squares);
        assertEquals(wordLength, squares.size());
        int i = row;
        for (Square square : squares) {
            Integer expected = i++;
            assertEquals(expected, square.getRow());
            assertEquals(column, square.getCol());
        }
    }

    @Test
    public void testGetSquaresThrowsExceptionForLongWordOffBoardInNormalDirection() {
        ScrabbleException scrabbleException = assertThrows(ScrabbleException.class, () -> board.putLetters("reallylongword", board.getSquare(MID_POINT, MID_POINT), Direction.DOWN));
        assertEquals(scrabbleException.getMessage(), "Too many squares!");
    }

    @Test
    public void testGetSquaresThrowsExceptionForLongWordInOppositeDirection() {
        ScrabbleException scrabbleException = assertThrows(ScrabbleException.class, () -> board.putLetters("reallylongword", board.getSquare(MID_POINT, MID_POINT), Direction.UP));
        assertEquals(scrabbleException.getMessage(), "Too many squares!");
    }

    @Test
    public void testGetSquaresThrowsExceptionForBadStartingPoint() {
        int row = Board.BOARD_SIZE;
        int column = Board.BOARD_SIZE;

        ScrabbleException scrabbleException = assertThrows(ScrabbleException.class, () -> board.putLetters("a", board.getSquare(row, column), Direction.DOWN));
        assertEquals(scrabbleException.getMessage(), "Square invalid: row - " + row + ", col - " + column);
    }

    @Test
    public void testGetSquaresAtLimit() throws Exception {
        char c = 'a';
        String singleLetterWord = Character.toString(c);
        int row = Board.BOARD_SIZE - 1;
        int column = Board.BOARD_SIZE - 1;
        String[] words = new String[]{"testing", "testingt", "testinga"};
        expectWordVerification(words);

        board.putLetters("testing", board.getSquare(MID_POINT, MID_POINT), Direction.ACROSS);
        board.putLetters("testing", board.getSquare(MID_POINT, column), Direction.DOWN);
        board.putLetters(singleLetterWord, board.getSquare(row, column), Direction.ACROSS);

        List<Square> squares = TestUtils.getOccupiedSquares(board);

        Square square = squares.stream().filter(s -> s.getCharacter() == c).findFirst().orElseThrow();
        assertEquals(row, square.getRow());
        assertEquals(column, square.getCol());
    }

    @Test
    public void testGetSquare() throws Exception {
        int row = MID_POINT;
        int column = MID_POINT;
        Square square = board.getSquare(row, column);
        assertEquals(row, square.getRow());
        assertEquals(column, square.getCol());
    }

    @Test
    public void testGetSquareThrowsExceptionForBadStartingPoint() {
        ScrabbleException scrabbleException = assertThrows(ScrabbleException.class, () ->
                board.getSquare(Board.BOARD_SIZE, Board.BOARD_SIZE));
        assertEquals(scrabbleException.getMessage(), "Square invalid: row - " + BOARD_SIZE + ", col - " + BOARD_SIZE);
    }

    @Test
    public void testIsNewWordGoingToTouchExistingWord() throws Exception {
        // given
        String word = "existing";
        String newWord = "newword";
        Square existingWordStart = board.getSquare(MID_POINT - (word.length() / 2), MID_POINT);
        Square newWordStart = board.getSquare(MID_POINT, MID_POINT - (newWord.length() / 2));
        String[] words = new String[]{word, "newtword"};
        expectWordVerification(words);

        // and one word is played
        board.putLetters(word, existingWordStart, Direction.DOWN);

        // then new word must touch it
        ScrabbleException scrabbleException = assertThrows(ScrabbleException.class, () ->
                board.putLetters(newWord, newWordStart, Direction.DOWN));
        assertTrue(scrabbleException.getMessage().contains("does not touch any other word"));

        // now it crosses at the midpoint
        board.putLetters(newWord, newWordStart, Direction.ACROSS);
        checkWords(words);
    }

    @Test
    public void testDetermineDirection() {
        assertEquals(Direction.ACROSS, Board.determineDirection(Square.getNormal(0, 0), Square.getNormal(0, 2)));
        assertEquals(Direction.ACROSS, Board.determineDirection(Square.getNormal(1, 10), Square.getNormal(1, 2)));
        assertEquals(Direction.DOWN, Board.determineDirection(Square.getNormal(3, 5), Square.getNormal(1, 5)));
        assertEquals(Direction.DOWN, Board.determineDirection(Square.getNormal(3, 7), Square.getNormal(10, 7)));
        assertNull(Board.determineDirection(Square.getNormal(0, 0), Square.getNormal(MID_POINT, MID_POINT)));
    }

    @Test
    public void testPutLetters() throws Exception {
        String word = "a";
        String[] words = new String[]{word};
        expectWordVerification(words);

        board.putLetters(word, board.getSquare(MID_POINT, MID_POINT), Direction.DOWN);

        checkWords(words);
        assertEquals((Character) word.toCharArray()[0], (Character) board.getWords().get(0).getStartingPoint().getCharacter());
    }

    @Test
    public void testPutLettersWithWord() throws Exception {
        String word = "across";
        String[] words = new String[]{word};
        expectWordVerification(words);

        board.putLetters(word, board.getSquare(MID_POINT, MID_POINT), Direction.ACROSS);

        checkWords(words);
        Word returnedWord = board.getWords().get(0);
        char[] chars = word.toCharArray();
        List<Square> squares = returnedWord.getSquares();
        for (int i = 0; i < chars.length; i++) {
            assertEquals((Character) chars[i], (Character) squares.get(i).getCharacter());
        }
    }

    @Test
    public void testCannotAddFirstSimpleWordToAnywhereOtherThanMiddle() throws Exception {
        String word = "a";
        String[] words = new String[]{word};

        for (int row = 0; row < Board.BOARD_SIZE; row++) {
            for (int col = 0; col < Board.BOARD_SIZE; col++) {
                if (row != MID_POINT && col != MID_POINT) {
                    int finalRow = row;
                    int finalCol = col;
                    assertThrows(ScrabbleException.class, () ->
                            board.putLetters(word, board.getSquare(finalRow, finalCol), Direction.DOWN));
                }
            }
        }

        expectWordVerification(words);

        board.putLetters(word, board.getSquare(MID_POINT, MID_POINT), Direction.DOWN);
    }

    @Test
    public void testCannotAddFirstComplexWordToAnywhereOtherThanAcrossMiddle() throws Exception {
        String word = "across";
        String[] words = new String[]{word};

        for (int row = 0; row < Board.BOARD_SIZE; row++) {
            for (int col = 0; col < Board.BOARD_SIZE; col++) {
                if (row != MID_POINT || col > MID_POINT) {
                    int finalRow = row;
                    int finalCol = col;
                    assertThrows(ScrabbleException.class, () ->
                            board.putLetters(word, board.getSquare(finalRow, finalCol), Direction.ACROSS));
                }
            }
        }

        expectWordVerification(words);

        board.putLetters(word, board.getSquare(MID_POINT, MID_POINT - word.length() + 1), Direction.ACROSS);
    }

    @Test
    public void testCannotAddFirstComplexWordToAnywhereOtherThanDownMiddle() throws Exception {
        String word = "down";
        String[] words = new String[]{word};

        for (int row = 0; row < Board.BOARD_SIZE; row++) {
            for (int col = 0; col < Board.BOARD_SIZE; col++) {
                if (col != MID_POINT || row > MID_POINT) {
                    int finalRow = row;
                    int finalCol = col;
                    assertThrows(ScrabbleException.class, () ->
                            board.putLetters(word, board.getSquare(finalRow, finalCol), Direction.DOWN));
                }
            }
        }

        expectWordVerification(words);

        board.putLetters(word, board.getSquare(MID_POINT - word.length() + 1, MID_POINT), Direction.DOWN);
    }

    @Test
    public void testCannotAddSecondWordToAnywhereOtherThanAdjacentFirstWord() throws Exception {
        String word = "a";
        String madeWord = "aa";
        String[] words = new String[]{word, madeWord};

        expectWordVerification(words);

        board.putLetters(word, board.getSquare(MID_POINT, MID_POINT), Direction.DOWN);

        for (int row = 0; row < Board.BOARD_SIZE; row++) {
            for (int col = 0; col < Board.BOARD_SIZE; col++) {
                if ((row != MID_POINT - 1 || col != MID_POINT) &&
                        (row != MID_POINT || (col != MID_POINT - 1 && col != MID_POINT + 1)) &&
                        (row != MID_POINT + 1 || col != MID_POINT)) {
                    int finalRow = row;
                    int finalCol = col;
                    assertThrows(ScrabbleException.class, () ->
                            board.putLetters(word, board.getSquare(finalRow, finalCol), Direction.DOWN));
                }
            }
        }

        board.putLetters(word, board.getSquare(MID_POINT - 1, MID_POINT), Direction.ACROSS);
    }

    @Test
    public void testGetBoardLetters() throws Exception {
        String word = "abc";
        String[] words = new String[]{word};
        expectWordVerification(words);

        board.putLetters(word, board.getSquare(MID_POINT, MID_POINT), Direction.ACROSS);

        Map<String, String[]> rowsAndCols = board.getRowsAndColumns();
        String[] rows = rowsAndCols.get("rows");
        String[] columns = rowsAndCols.get("cols");
        assertEquals(Board.BOARD_SIZE, rows.length);
        assertEquals(Board.BOARD_SIZE, columns.length);
        for (int i = 0; i < rows.length; i++) {
            String row = rows[i];
            if (i == MID_POINT) {
                assertEquals(word, row);
            } else {
                assertEquals("", row);
            }
        }
        for (int i = 0; i < columns.length; i++) {
            String column = columns[i];
            if (i == MID_POINT) {
                assertEquals("a", column);
            } else if (i == MID_POINT + 1) {
                assertEquals("b", column);
            } else if (i == MID_POINT + 2) {
                assertEquals("c", column);
            } else {
                assertEquals("", column);
            }
        }
    }

    @Test
    public void testGetBoardLetters2() throws Exception {
        String letters1 = "aaa";
        String letters2 = "bc";
        String madeWord = "abc";
        String[] words = new String[]{letters1, madeWord, madeWord};
        expectWordVerification(words);

        board.putLetters(letters1, board.getSquare(MID_POINT, MID_POINT), Direction.ACROSS);
        board.putLetters(letters2, board.getSquare(MID_POINT + 1, MID_POINT), Direction.DOWN);
        board.putLetters(letters2, board.getSquare(MID_POINT + 1, MID_POINT + 2), Direction.DOWN);

        Map<String, String[]> rowsAndCols = board.getRowsAndColumns();
        String[] rows = rowsAndCols.get("rows");
        String[] columns = rowsAndCols.get("cols");
        assertEquals(Board.BOARD_SIZE, rows.length);
        assertEquals(Board.BOARD_SIZE, columns.length);
        for (int i = 0; i < rows.length; i++) {
            String row = rows[i];
            if (i == MID_POINT) {
                assertEquals(letters1, row);
            } else if (i == MID_POINT + 1) {
                assertEquals("b b", row);
            } else if (i == MID_POINT + 2) {
                assertEquals("c c", row);
            } else {
                assertEquals("", row);
            }
        }
        for (int i = 0; i < columns.length; i++) {
            String column = columns[i];
            if (i == MID_POINT) {
                assertEquals(madeWord, column);
            } else if (i == MID_POINT + 1) {
                assertEquals("a", column);
            } else if (i == MID_POINT + 2) {
                assertEquals(madeWord, column);
            } else {
                assertEquals("", column);
            }
        }

    }

    @Test
    public void testGetCharactersFromSquares() throws Exception {
        Square square1 = Square.getNormal(0, 0);
        Square square2 = Square.getNormal(0, 0);
        Square square3 = Square.getNormal(0, 0);
        Square square4 = Square.getNormal(0, 0);
        Square square5 = Square.getNormal(0, 0);

        square2.setLetter(new Letter('a', 1));
        square3.setLetter(new Wildcard('b'));
        square4.setLetter(new Letter('c', 1));

        Square[] squares = new Square[]{square1, square2, square3, square4, square5};

        assertEquals(" abc ", Board.getCharactersFromSquares(squares, false));
        assertEquals(" a*bc ", Board.getCharactersFromSquares(squares, true));
    }

    @Test
    public void testGetCharactersFromBoard() throws Exception {
        String madeWord = "abc";

        expectWordVerification(new String[]{madeWord, madeWord});

        assertEquals("", TestUtils.getCharactersFromBoard(board));
        board.putLetters("a*bc", board.getSquare(MID_POINT, MID_POINT), Direction.ACROSS);
        assertEquals("a*c", TestUtils.getCharactersFromBoard(board));
        board.putLetters("bc", board.getSquare(MID_POINT + 1, MID_POINT), Direction.DOWN);
        assertEquals("a*cbc", TestUtils.getCharactersFromBoard(board));
    }

    @Test
    public void testScoreForSingleLetter() throws Exception {
        String letters = "a";

        expectWordVerification(new String[]{letters});

        int score = board.putLetters(letters, board.getSquare(MID_POINT, MID_POINT), Direction.ACROSS);

        assertEquals(2, score);
    }

    @Test
    public void testScoreForSingleWord() throws Exception {
        String letters = "jump";

        expectWordVerification(new String[]{letters});

        int score = board.putLetters(letters, board.getSquare(MID_POINT, MID_POINT), Direction.ACROSS);

        assertEquals(28, score);
    }

    @Test
    public void testScoreForAllLetters() throws Exception {
        String letters = "aaaaaaa";

        expectWordVerification(new String[]{letters});

        int score = board.putLetters(letters, board.getSquare(MID_POINT, MID_POINT - 3), Direction.ACROSS);

        assertEquals(54, score);
    }

    @Test
    public void testWildCard() throws Exception {
        String letters = "abc";
        String[] words = new String[]{letters};

        expectWordVerification(words);

        int score = board.putLetters(Utils.WILDCARD + letters, board.getSquare(MID_POINT, MID_POINT), Direction.ACROSS);

        checkWords(words);
        assertEquals(10, score);
    }

    @Test
    public void testAddingSingleLetterBeforeSingleLetter() throws Exception {
        String word1 = "b";
        String word2 = "a";
        String madeWord = "ab";

        expectWordVerification(new String[]{word1, madeWord});

        board.putLetters(word1, board.getSquare(MID_POINT, MID_POINT), Direction.ACROSS);
        int score = board.putLetters(word2, board.getSquare(MID_POINT - 1, MID_POINT), Direction.ACROSS);

        checkWords(new String[]{madeWord});
        assertEquals(Direction.DOWN, board.getWords().get(0).getDirection());
        assertEquals(4, score);
    }

    @Test
    public void testAddingSingleLetterAfterSingleLetter() throws Exception {
        String word1 = "a";
        String word2 = "b";
        String madeWord = "ab";

        expectWordVerification(new String[]{word1, madeWord});

        board.putLetters(word1, board.getSquare(MID_POINT, MID_POINT), Direction.DOWN);
        int score = board.putLetters(word2, board.getSquare(MID_POINT + 1, MID_POINT), Direction.ACROSS);

        checkWords(new String[]{madeWord});
        assertEquals(4, score);
    }

    @Test
    public void testAddingWordBeforeSingleLetter() throws Exception {
        String word1 = "c";
        String word2 = "ab";
        String madeWord = "abc";

        expectWordVerification(new String[]{word1, madeWord});

        board.putLetters(word1, board.getSquare(MID_POINT, MID_POINT), Direction.DOWN);
        int score = board.putLetters(word2, board.getSquare(MID_POINT - (word2.length()), MID_POINT), Direction.DOWN);

        checkWords(new String[]{madeWord});
        assertEquals(6, score);
    }

    @Test
    public void testAddingWordAfterSingleLetter() throws Exception {
        String word1 = "a";
        String word2 = "bc";
        String madeWord = "abc";

        expectWordVerification(new String[]{word1, madeWord});

        board.putLetters(word1, board.getSquare(MID_POINT, MID_POINT), Direction.DOWN);
        int score = board.putLetters(word2, board.getSquare(MID_POINT + 1, MID_POINT), Direction.DOWN);

        checkWords(new String[]{madeWord});
        assertEquals(6, score);
    }

    @Test
    public void testAddingWordBeforeWord() throws Exception {
        String word1 = "cd";
        String word2 = "ab";
        String madeWord = "abcd";

        expectWordVerification(new String[]{word1, madeWord});

        board.putLetters(word1, board.getSquare(MID_POINT, MID_POINT), Direction.DOWN);
        int score = board.putLetters(word2, board.getSquare(MID_POINT - (word2.length()), MID_POINT), Direction.DOWN);

        checkWords(new String[]{madeWord});
        assertEquals(7, score);
    }

    @Test
    public void testAddingWordAfterWord() throws Exception {
        String word1 = "ab";
        String word2 = "cd";
        String madeWord = "abcd";

        expectWordVerification(new String[]{word1, madeWord});

        board.putLetters(word1, board.getSquare(MID_POINT, MID_POINT), Direction.DOWN);
        int score = board.putLetters(word2, board.getSquare(MID_POINT + (word1.length()), MID_POINT), Direction.DOWN);

        checkWords(new String[]{madeWord});
        assertEquals(7, score);
    }

    @Test
    public void testEncompassSingleLetter() throws Exception {
        String word1 = "b";
        String word2 = "acd";
        String madeWord = "abcd";

        expectWordVerification(new String[]{word1, madeWord});

        board.putLetters(word1, board.getSquare(MID_POINT, MID_POINT), Direction.ACROSS);
        int score = board.putLetters(word2, board.getSquare(MID_POINT, MID_POINT - 1), Direction.ACROSS);

        checkWords(new String[]{madeWord});
        assertEquals(7, score);
    }

    @Test
    public void testEncompassWord() throws Exception {
        String word1 = "bc";
        String word2 = "ad";
        String madeWord = "abcd";

        expectWordVerification(new String[]{word1, madeWord});

        board.putLetters(word1, board.getSquare(MID_POINT, MID_POINT), Direction.ACROSS);
        int score = board.putLetters(word2, board.getSquare(MID_POINT, MID_POINT - 1), Direction.ACROSS);

        checkWords(new String[]{madeWord});
        assertEquals(7, score);
    }

    @Test
    public void testSaveAndLoad() throws Exception {
        // given
        String letters1 = "test";
        String expected =
                "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "       test    \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n";
        String[] words = new String[]{letters1};
        expectWordVerification(words);
        board.putLetters(letters1, board.getSquare(MID_POINT, MID_POINT), Direction.ACROSS);

        // expect
        checkWords(words);
        checkBoard(expected);

        // when exporting
        String export = board.exportBoard();

        // expect
        assertTrue(export.startsWith(expected));

        // and new board
        board = new Board(wordsmith, Game.testing());
        checkWords(new String[]{});

        // then import
        board.importBoard(export);

        // expect
        checkWords(words);
        checkBoard(expected);
    }

    @Test
    public void testClear() throws Exception {
        String letters1 = "abc";
        String[] words = new String[]{letters1};

        expectWordVerification(words);

        board.putLetters(letters1, board.getSquare(MID_POINT, MID_POINT), Direction.DOWN);
        checkWords(words);
        board.clear();

        checkWords(new String[]{});
        checkBoard(
                "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n");
    }

    @Test
    public void testSimpleIntersection1() throws Exception {
        String letters1 = "abc";
        String letters2 = "ab";
        String[] words = new String[]{letters1, letters1};

        expectWordVerification(words);

        board.putLetters(letters1, board.getSquare(MID_POINT, MID_POINT), Direction.DOWN);
        int score = board.putLetters(letters2, board.getSquare(MID_POINT + (letters1.length() - 1), MID_POINT - letters2.length()), Direction.ACROSS);

        checkWords(words);
        assertEquals(6, score);
        checkBoard(
                "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "       a       \n" +
                        "       b       \n" +
                        "     abc       \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n");
    }

    @Test
    public void testSimpleIntersection2() throws Exception {
        String letters1 = "ab";
        String letters2 = "abc";

        expectWordVerification(new String[]{letters1, letters2, letters2});

        board.putLetters(letters1, board.getSquare(MID_POINT, MID_POINT), Direction.DOWN);
        int score = board.putLetters(letters2, board.getSquare(MID_POINT + letters1.length(), MID_POINT - (letters2.length() - 1)), Direction.ACROSS);

        checkWords(new String[]{letters2, letters2});
        assertEquals((6 + 6), score);
        checkBoard(
                "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "       a       \n" +
                        "       b       \n" +
                        "     abc       \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n");
    }

    @Test
    public void testSimpleIntersection3() throws Exception {
        String letters1 = "abc";
        String letters2 = "ac";
        String[] words = new String[]{letters1, letters1};

        expectWordVerification(words);

        board.putLetters(letters1, board.getSquare(MID_POINT, MID_POINT), Direction.DOWN);
        int score = board.putLetters(letters2, board.getSquare(MID_POINT + 1, MID_POINT - 1), Direction.ACROSS);

        checkWords(words);
        assertEquals(9, score);
        checkBoard(
                "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "       a       \n" +
                        "      abc      \n" +
                        "       c       \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n");
    }

    @Test
    public void testSimpleIntersection4() throws Exception {
        String letters1 = "abc";
        String letters2 = "b";
        String[] words = new String[]{letters1, "ab"};

        expectWordVerification(words);

        board.putLetters(letters1, board.getSquare(MID_POINT, MID_POINT), Direction.DOWN);
        int score = board.putLetters(letters2, board.getSquare(MID_POINT, MID_POINT + 1), Direction.ACROSS);

        checkWords(words);
        assertEquals(4, score);
        checkBoard(
                "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "       ab      \n" +
                        "       b       \n" +
                        "       c       \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n");
    }

    @Test
    public void testSimpleIntersection5() throws Exception {
        String letters1 = "ab";
        String letters2 = "c";
        String[] words = new String[]{letters1, "abc"};

        expectWordVerification(words);

        board.putLetters(letters1, board.getSquare(MID_POINT, MID_POINT), Direction.DOWN);
        int score = board.putLetters(letters2, board.getSquare(MID_POINT + 2, MID_POINT), Direction.ACROSS);

        checkWords(new String[]{"abc"});
        assertEquals(6, score);
        checkBoard(
                "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "       a       \n" +
                        "       b       \n" +
                        "       c       \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n");
    }

    @Test
    public void testSlidingIntersection1() throws Exception {
        String letters1 = "abc";
        String[] expectedWords = new String[]{letters1, letters1, "aa", "bb", "cc"};

        expectWordVerification(expectedWords);

        board.putLetters(letters1, board.getSquare(MID_POINT, MID_POINT), Direction.ACROSS);
        int score = board.putLetters(letters1, board.getSquare(MID_POINT + 1, MID_POINT), Direction.ACROSS);

        checkWords(expectedWords);
        assertEquals((9 + 3 + 6 + 6), score);
        checkBoard(
                "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "       abc     \n" +
                        "       abc     \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n");
    }

    @Test
    public void testSlidingIntersection2() throws Exception {
        String letters1 = "abc";
        String[] expectedWords = new String[]{letters1, letters1, "ba", "cb"};

        expectWordVerification(expectedWords);

        board.putLetters(letters1, board.getSquare(MID_POINT, MID_POINT), Direction.ACROSS);
        int score = board.putLetters(letters1, board.getSquare(MID_POINT + 1, MID_POINT + 1), Direction.ACROSS);

        checkWords(expectedWords);
        assertEquals((7 + 5 + 5), score);
        checkBoard(
                "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "       abc     \n" +
                        "        abc    \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n");
    }

    @Test
    public void testSlidingIntersection3() throws Exception {
        String letters1 = "abc";
        String[] expectedWords = new String[]{letters1, letters1, "ca"};

        expectWordVerification(expectedWords);

        board.putLetters(letters1, board.getSquare(MID_POINT, MID_POINT), Direction.DOWN);
        int score = board.putLetters(letters1, board.getSquare(MID_POINT + 2, MID_POINT + 1), Direction.DOWN);

        checkWords(expectedWords);
        assertEquals((10 + 3), score);
        checkBoard(
                "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "       a       \n" +
                        "       b       \n" +
                        "       ca      \n" +
                        "        b      \n" +
                        "        c      \n" +
                        "               \n" +
                        "               \n" +
                        "               \n");
    }

    @Test
    public void testBoxIntersection1() throws Exception {
        String letters1 = "aaa";
        String letters2 = "b";
        String letters3 = "ccc";
        String madeWord1 = "ab";
        String madeWord2 = "abc";

        expectWordVerification(new String[]{letters1, madeWord1, madeWord1, letters3, madeWord2, madeWord2});

        board.putLetters(letters1, board.getSquare(MID_POINT, MID_POINT), Direction.ACROSS);
        board.putLetters(letters2, board.getSquare(MID_POINT + 1, MID_POINT), Direction.ACROSS);
        board.putLetters(letters2, board.getSquare(MID_POINT + 1, MID_POINT + 2), Direction.ACROSS);
        int score = board.putLetters(letters3, board.getSquare(MID_POINT + 2, MID_POINT), Direction.ACROSS);

        checkWords(new String[]{letters1, madeWord2, madeWord2, letters3});
        assertEquals((6 + 6 + 6), score);
        checkBoard(
                "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "       aaa     \n" +
                        "       b b     \n" +
                        "       ccc     \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n");
    }

    @Test
    public void testBoxIntersection2() throws Exception {
        String letters1 = "aba";
        String letters2 = "ba";
        String letters3 = "b";
        String letters4 = "c";
        String madeWord1 = "ab";
        String madeWord2 = "abc";

        expectWordVerification(new String[]{letters1, letters1, madeWord1, madeWord1, madeWord2, madeWord2});

        board.putLetters(letters1, board.getSquare(MID_POINT, MID_POINT), Direction.ACROSS);
        board.putLetters(letters2, board.getSquare(MID_POINT + 1, MID_POINT), Direction.DOWN);
        board.putLetters(letters3, board.getSquare(MID_POINT + 1, MID_POINT + 2), Direction.ACROSS);
        board.putLetters(letters3, board.getSquare(MID_POINT + 2, MID_POINT + 1), Direction.ACROSS);
        int score = board.putLetters(letters4, board.getSquare(MID_POINT + 2, MID_POINT + 2), Direction.ACROSS);

        checkWords(new String[]{letters1, letters1, madeWord2, madeWord2});
        assertEquals((6 + 6), score);
        checkBoard(
                "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "       aba     \n" +
                        "       b b     \n" +
                        "       abc     \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n");
    }

    @Test
    public void testBoxIntersection3() throws Exception {
        String letters1 = "aba";
        String letters2 = "bc";
        String letters3 = "b";
        String letters4 = "aac";
        String madeWord1 = "ab";
        String madeWord2 = "abc";
        String madeWord3 = "acac";

        expectWordVerification(new String[]{letters1, madeWord1, madeWord2, madeWord2, madeWord3});

        board.putLetters(letters1, board.getSquare(MID_POINT, MID_POINT), Direction.ACROSS);
        board.putLetters(letters2, board.getSquare(MID_POINT + 1, MID_POINT), Direction.DOWN);
        board.putLetters(letters3, board.getSquare(MID_POINT + 1, MID_POINT + 2), Direction.ACROSS);
        int score = board.putLetters(letters4, board.getSquare(MID_POINT + 2, MID_POINT - 1), Direction.ACROSS);

        checkWords(new String[]{letters1, madeWord2, madeWord2, madeWord3});
        assertEquals((6 + 6), score);
        checkBoard(
                "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "       aba     \n" +
                        "       b b     \n" +
                        "      acac     \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n");
    }

    @Test
    public void testBoxIntersection4() throws Exception {
        String letters1 = "aaaaa";
        String letters2 = "bbb";
        String letters3 = "b";
        String letters4 = "abc";
        String madeWord1 = "abbb";
        String madeWord2 = "ab";
        String madeWord3 = "abbba";
        String madeWord4 = "ba";
        String madeWord5 = "abcba";

        expectWordVerification(new String[]{letters1, madeWord1, madeWord2, letters1, madeWord3, madeWord4, letters4, madeWord5});

        board.putLetters(letters1, board.getSquare(MID_POINT, MID_POINT), Direction.ACROSS);
        board.putLetters(letters2, board.getSquare(MID_POINT + 1, MID_POINT), Direction.DOWN);
        board.putLetters(letters3, board.getSquare(MID_POINT + 1, MID_POINT + 4), Direction.DOWN);
        board.putLetters(letters1, board.getSquare(MID_POINT + 4, MID_POINT), Direction.ACROSS);
        board.putLetters(letters3, board.getSquare(MID_POINT + 3, MID_POINT + 4), Direction.ACROSS);
        int score = board.putLetters(letters4, board.getSquare(MID_POINT + 2, MID_POINT + 2), Direction.ACROSS);

        checkWords(new String[]{letters1, letters1, letters4, madeWord3, madeWord5});
        assertEquals((12 + 10), score);
        checkBoard(
                "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "       aaaaa   \n" +
                        "       b   b   \n" +
                        "       b abc   \n" +
                        "       b   b   \n" +
                        "       aaaaa   \n" +
                        "               \n" +
                        "               \n" +
                        "               \n");
    }

    @Test
    public void testBoxIntersections5() throws Exception {
        String letters1 = "aaa";
        String letters2 = "aa";
        String letters3 = "b";
        String letters4 = "bc";
        String madeWord1 = "aaa";
        String madeWord2 = "ab";
        String madeWord3 = "abc";

        expectWordVerification(new String[]{letters1, madeWord1, madeWord2, madeWord3, madeWord3});

        board.putLetters(letters1, board.getSquare(MID_POINT, MID_POINT), Direction.ACROSS);
        board.putLetters(letters2, board.getSquare(MID_POINT + 1, MID_POINT), Direction.DOWN);
        board.putLetters(letters3, board.getSquare(MID_POINT + 1, MID_POINT + 2), Direction.ACROSS);
        int score = board.putLetters(letters4, board.getSquare(MID_POINT + 2, MID_POINT + 1), Direction.ACROSS);

        checkWords(new String[]{letters1, madeWord1, madeWord3, madeWord3});
        assertEquals((6 + 6), score);
        checkBoard(
                "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "       aaa     \n" +
                        "       a b     \n" +
                        "       abc     \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n");
    }

    @Test
    public void testSurroundedIntersection1() throws Exception {
        String letters1 = "aaa";
        String letters2 = "b";
        String letters3 = "ccc";
        String madeWord1 = "ab";
        String madeWord2 = "abc";
        String madeWord3 = "bbb";

        expectWordVerification(new String[]{letters1, madeWord1, madeWord1, madeWord2, madeWord2, madeWord2, letters3, madeWord3});

        board.putLetters(letters1, board.getSquare(MID_POINT, MID_POINT), Direction.ACROSS);
        board.putLetters(letters2, board.getSquare(MID_POINT + 1, MID_POINT), Direction.ACROSS);
        board.putLetters(letters2, board.getSquare(MID_POINT + 1, MID_POINT + 2), Direction.ACROSS);
        board.putLetters(letters3, board.getSquare(MID_POINT + 2, MID_POINT), Direction.ACROSS);
        int score = board.putLetters(letters2, board.getSquare(MID_POINT + 1, MID_POINT + 1), Direction.DOWN);

        checkWords(new String[]{letters1, madeWord2, madeWord2, madeWord2, letters3, madeWord3});
        assertEquals((12 + 9), score);
        checkBoard(
                "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "       aaa     \n" +
                        "       bbb     \n" +
                        "       ccc     \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n");
    }

    @Test
    public void testSurroundedIntersection2() throws Exception {
        String letters1 = "aaa";
        String letters2 = "b";
        String letters3 = "ccc";
        String letters4 = "bb";
        String madeWord1 = "ab";
        String madeWord2 = "abc";
        String madeWord3 = "bbbb";

        expectWordVerification(new String[]{letters1, madeWord1, madeWord1, madeWord2, madeWord2, madeWord2, letters3, madeWord3});

        board.putLetters(letters1, board.getSquare(MID_POINT, MID_POINT), Direction.ACROSS);
        board.putLetters(letters2, board.getSquare(MID_POINT + 1, MID_POINT), Direction.ACROSS);
        board.putLetters(letters2, board.getSquare(MID_POINT + 1, MID_POINT + 2), Direction.ACROSS);
        board.putLetters(letters3, board.getSquare(MID_POINT + 2, MID_POINT), Direction.ACROSS);
        int score = board.putLetters(letters4, board.getSquare(MID_POINT + 1, MID_POINT + 1), Direction.ACROSS);

        checkWords(new String[]{letters1, madeWord2, madeWord2, madeWord2, letters3, madeWord3});
        assertEquals((15 + 9), score);
        checkBoard(
                "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "       aaa     \n" +
                        "       bbbb    \n" +
                        "       ccc     \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n");
    }

    @Test
    public void testMultipleIntersections1() throws Exception {
        String letters1 = "abcd";
        String letters2 = "abc";
        String madeWord1 = "abcc";
        String madeWord2 = "aa";
        String madeWord3 = "bb";
        String madeWord4 = "cc";

        expectWordVerification(new String[] {letters1, letters1, madeWord1, madeWord2, madeWord3, madeWord4});

        board.putLetters(letters1, board.getSquare(MID_POINT, MID_POINT), Direction.DOWN);
        board.putLetters(letters2, board.getSquare(MID_POINT + 3, MID_POINT - 3), Direction.ACROSS);
        int score = board.putLetters(letters2, board.getSquare(MID_POINT + 2, MID_POINT - 3), Direction.ACROSS);

        checkWords(new String[] {letters1, letters1, madeWord1, madeWord2, madeWord3, madeWord4});
        assertEquals((16 + 4 + 6 + 4), score);
        checkBoard(
                "               \n" +
                "               \n" +
                "               \n" +
                "               \n" +
                "               \n" +
                "               \n" +
                "               \n" +
                "       a       \n" +
                "       b       \n" +
                "    abcc       \n" +
                "    abcd       \n" +
                "               \n" +
                "               \n" +
                "               \n" +
                "               \n");
    }

    @Test
    public void testMultipleIntersections2() throws Exception {
        String letters1 = "abcd";
        String letters2 = "abc";
        String letters3 = "abd";
        String madeWord1 = "ab";
        String madeWord2 = "bc";

        expectWordVerification(new String[]{letters1, letters1, letters1, madeWord1, madeWord2});

        board.putLetters(letters1, board.getSquare(MID_POINT, MID_POINT), Direction.DOWN);
        board.putLetters(letters2, board.getSquare(MID_POINT + 3, MID_POINT - 3), Direction.ACROSS);
        int score = board.putLetters(letters3, board.getSquare(MID_POINT + 2, MID_POINT - 2), Direction.ACROSS);

        checkWords(new String[]{letters1, letters1, letters1, madeWord1, madeWord2});
        assertEquals((7 + 4 + 5), score);
        checkBoard(
                "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "       a       \n" +
                        "       b       \n" +
                        "     abcd      \n" +
                        "    abcd       \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n");
    }

    @Test
    public void testMultipleIntersections3() throws Exception {
        String letters1 = "aaaaa";
        String letters2 = "bcba";
        String letters3 = "aaaa";
        String letters4 = "b";
        String letters5 = "ccccc";
        String madeWord1 = "abcba";
        String madeWord2 = "aaaaa";
        String madeWord3 = "ab";
        String madeWord4 = "ba";
        String madeWord5 = "cccccc";

        expectWordVerification(new String[]{letters1, madeWord1, madeWord1, madeWord2, madeWord3, madeWord4, madeWord5});

        board.putLetters(letters1, board.getSquare(MID_POINT, MID_POINT), Direction.ACROSS);
        board.putLetters(letters2, board.getSquare(MID_POINT + 1, MID_POINT + 4), Direction.DOWN);
        board.putLetters(letters3, board.getSquare(MID_POINT + 4, MID_POINT), Direction.ACROSS);
        board.putLetters(letters4, board.getSquare(MID_POINT + 1, MID_POINT), Direction.DOWN);
        board.putLetters(letters4, board.getSquare(MID_POINT + 3, MID_POINT), Direction.DOWN);
        int score = board.putLetters(letters5, board.getSquare(MID_POINT + 2, MID_POINT - 1), Direction.ACROSS);

        checkWords(new String[]{letters1, madeWord1, madeWord1, madeWord2, madeWord5});
        assertEquals((24 + 10), score);
        checkBoard(
                "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "       aaaaa   \n" +
                        "       b   b   \n" +
                        "      cccccc   \n" +
                        "       b   b   \n" +
                        "       aaaaa   \n" +
                        "               \n" +
                        "               \n" +
                        "               \n");
    }

    @Test
    public void testMultipleIntersections4() throws Exception {
        String letters1 = "aaaaa";
        String letters2 = "bc";
        String letters3 = "b";
        String letters4 = "c";
        String letters5 = "ccc";
        String madeWord1 = "abc";
        String madeWord2 = "ab";
        String madeWord3 = "cc";
        String madeWord4 = "ccccc";

        expectWordVerification(new String[]{letters1, madeWord1, madeWord1, madeWord1, madeWord2, madeWord2, madeWord3, madeWord4});

        board.putLetters(letters1, board.getSquare(MID_POINT, MID_POINT), Direction.ACROSS);
        board.putLetters(letters2, board.getSquare(MID_POINT + 1, MID_POINT), Direction.DOWN);
        board.putLetters(letters3, board.getSquare(MID_POINT + 1, MID_POINT + 2), Direction.ACROSS);
        board.putLetters(letters3, board.getSquare(MID_POINT + 1, MID_POINT + 4), Direction.DOWN);
        board.putLetters(letters4, board.getSquare(MID_POINT + 2, MID_POINT + 1), Direction.DOWN);
        int score = board.putLetters(letters5, board.getSquare(MID_POINT + 2, MID_POINT + 2), Direction.ACROSS);

        checkWords(new String[]{letters1, madeWord1, madeWord1, madeWord1, madeWord4});
        assertEquals((20 + 6 + 6), score);
        checkBoard(
                "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "       aaaaa   \n" +
                        "       b b b   \n" +
                        "       ccccc   \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n");
    }

    @Test
    public void testMultipleIntersections5() throws Exception {
        String letters1 = "abc";
        String madeWord1 = "aa";
        String madeWord2 = "bb";
        String madeWord3 = "cc";
        String madeWord4 = "baa";
        String madeWord5 = "cbb";

        expectWordVerification(new String[]{letters1, letters1, letters1, madeWord1, madeWord2, madeWord3, madeWord4, madeWord5});

        board.putLetters(letters1, board.getSquare(MID_POINT, MID_POINT), Direction.ACROSS);
        board.putLetters(letters1, board.getSquare(MID_POINT + 1, MID_POINT), Direction.ACROSS);
        int score = board.putLetters(letters1, board.getSquare(MID_POINT - 1, MID_POINT - 1), Direction.ACROSS);

        checkWords(new String[]{letters1, letters1, letters1, madeWord3, madeWord4, madeWord5});
        assertEquals((9 + 5 + 10), score);
        checkBoard(
                "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "      abc      \n" +
                        "       abc     \n" +
                        "       abc     \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n");
    }

    @Test
    public void testMultipleIntersections6() throws Exception {
        String letters1 = "abc";
        String letters2 = "c";
        String letters3 = "ddd";
        String madeWord1 = "cc";
        String madeWord2 = "abcd";
        String madeWord3 = "cd";

        expectWordVerification(new String[]{letters1, madeWord1, madeWord2, madeWord3, letters3});

        board.putLetters(letters1, board.getSquare(MID_POINT, MID_POINT), Direction.ACROSS);
        board.putLetters(letters2, board.getSquare(MID_POINT + 1, MID_POINT + 2), Direction.ACROSS);
        int score = board.putLetters(letters3, board.getSquare(MID_POINT, MID_POINT + 3), Direction.DOWN);

        checkWords(new String[]{madeWord1, madeWord2, madeWord3, letters3});
        assertEquals((6 + 7 + 3), score);
        checkBoard(
                "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "       abcd    \n" +
                        "         cd    \n" +
                        "          d    \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n");
    }

    @Test
    public void testMultipleIntersections7() throws Exception {
        String letters1 = "ab" + Utils.WILDCARD + "c";
        String letters2 = "cc";
        String letters3 = "ac";
        String madeWord1 = "abc";
        String madeWord2 = "ccc";
        String madeWord3 = "ac";
        String madeWord4 = "bac";

        expectWordVerification(new String[]{madeWord1, madeWord2, madeWord3, madeWord4});

        board.putLetters(letters1, board.getSquare(MID_POINT, MID_POINT), Direction.DOWN);
        board.putLetters(letters2, board.getSquare(MID_POINT + 2, MID_POINT - 1), Direction.ACROSS);
        int score = board.putLetters(letters3, board.getSquare(MID_POINT + 1, MID_POINT + 1), Direction.ACROSS);

        checkWords(new String[]{madeWord1, madeWord2, madeWord3, madeWord4});
        assertEquals((7 + 4), score);
        checkBoard(
                "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "       a       \n" +
                        "       bac     \n" +
                        "      ccc      \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n");
    }

    @Test
    public void testTestingMode1() throws Exception {
        String letters1 = "aaaaa";
        String letters2 = "bc";
        String letters3 = "b";
        String letters4 = "c";
        String letters5 = "ccc";
        String madeWord1 = "abc";
        String madeWord2 = "ab";
        String madeWord3 = "cc";
        String madeWord4 = "ccccc";

        expectWordVerification(new String[]{letters1, madeWord1, madeWord1, madeWord1, madeWord2, madeWord2, madeWord3, madeWord4});

        board.putLetters(letters1, board.getSquare(MID_POINT, MID_POINT), Direction.ACROSS);
        board.putLetters(letters2, board.getSquare(MID_POINT + 1, MID_POINT), Direction.DOWN);
        board.putLetters(letters3, board.getSquare(MID_POINT + 1, MID_POINT + 2), Direction.ACROSS);
        board.putLetters(letters3, board.getSquare(MID_POINT + 1, MID_POINT + 4), Direction.DOWN);
        board.putLetters(letters4, board.getSquare(MID_POINT + 2, MID_POINT + 1), Direction.DOWN);
        board.setDryRun(true);
        int score = board.putLetters(letters5, board.getSquare(MID_POINT + 2, MID_POINT + 2), Direction.ACROSS);

        checkWords(new String[]{letters1, madeWord2, madeWord2, madeWord1, madeWord3});
        assertEquals((20 + 6 + 6), score);
        checkBoard(
                "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "       aaaaa   \n" +
                        "       b b b   \n" +
                        "       cc      \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n");
    }

    @Test
    public void testTestingMode2() throws Exception {
        String letters1 = "abc";
        String[] expectedWords = new String[]{letters1, letters1, "ba", "cb"};

        expectWordVerification(expectedWords);

        board.putLetters(letters1, board.getSquare(MID_POINT, MID_POINT), Direction.ACROSS);
        board.setDryRun(true);
        int score = board.putLetters(letters1, board.getSquare(MID_POINT + 1, MID_POINT + 1), Direction.ACROSS);

        checkWords(new String[]{letters1});
        assertEquals((7 + 5 + 5), score);
        checkBoard(
                "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "       abc     \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n");
    }

    @Test
    public void testTestingMode3() throws Exception {
        String letters1 = "aaa";
        String letters2 = "b";
        String letters3 = "ccc";
        String madeWord1 = "ab";
        String madeWord2 = "abc";
        String madeWord3 = "bbb";

        expectWordVerification(new String[]{letters1, madeWord1, madeWord1, madeWord2, madeWord2, madeWord2, letters3, madeWord3});

        board.putLetters(letters1, board.getSquare(MID_POINT, MID_POINT), Direction.ACROSS);
        board.putLetters(letters2, board.getSquare(MID_POINT + 1, MID_POINT), Direction.ACROSS);
        board.putLetters(letters2, board.getSquare(MID_POINT + 1, MID_POINT + 2), Direction.ACROSS);
        board.putLetters(letters3, board.getSquare(MID_POINT + 2, MID_POINT), Direction.ACROSS);
        board.setDryRun(true);
        int score = board.putLetters(letters2, board.getSquare(MID_POINT + 1, MID_POINT + 1), Direction.DOWN);

        checkWords(new String[]{letters1, madeWord2, madeWord2, letters3});
        assertEquals((12 + 9), score);
        checkBoard(
                "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "       aaa     \n" +
                        "       b b     \n" +
                        "       ccc     \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n");
    }

    @Test
    public void testTestingMode4() throws Exception {
        String letters1 = "abc";
        String madeWord1 = "aa";
        String madeWord2 = "bb";
        String madeWord3 = "cc";
        String madeWord4 = "baa";
        String madeWord5 = "cbb";

        expectWordVerification(new String[]{letters1, letters1, letters1, madeWord1, madeWord2, madeWord3, madeWord4, madeWord5});

        board.putLetters(letters1, board.getSquare(MID_POINT, MID_POINT), Direction.ACROSS);
        board.putLetters(letters1, board.getSquare(MID_POINT + 1, MID_POINT), Direction.ACROSS);
        board.setDryRun(true);
        int score = board.putLetters(letters1, board.getSquare(MID_POINT - 1, MID_POINT - 1), Direction.ACROSS);

        checkWords(new String[]{letters1, letters1, madeWord1, madeWord2, madeWord3});
        assertEquals((9 + 5 + 10), score);
        checkBoard(
                "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "       abc     \n" +
                        "       abc     \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n");
    }

    @Test
    public void testTestingMode5() throws Exception {
        String letters1 = "abc";
        String letters2 = "c";
        String letters3 = "ddd";
        String madeWord1 = "cc";
        String madeWord2 = "abcd";
        String madeWord3 = "cd";

        expectWordVerification(new String[]{letters1, madeWord1, madeWord2, madeWord3, letters3});

        board.putLetters(letters1, board.getSquare(MID_POINT, MID_POINT), Direction.ACROSS);
        board.putLetters(letters2, board.getSquare(MID_POINT + 1, MID_POINT + 2), Direction.ACROSS);
        board.setDryRun(true);
        int score = board.putLetters(letters3, board.getSquare(MID_POINT, MID_POINT + 3), Direction.DOWN);

        checkWords(new String[]{letters1, madeWord1});
        assertEquals((6 + 7 + 3), score);
        checkBoard(
                "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "       abc     \n" +
                        "         c     \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n");
    }

    @Test
    public void testTestingMode6() throws Exception {
        String letters1 = "chubs";
        String letters2 = "or*tz";
        String letters3 = "tuti";
        String letters4 = "js";
        String letters5 = "t*a";
        String madeWord1 = "bortz";
        String madeWord2 = "tutti";
        String madeWord3 = "jus";
        String madeWord4 = "taj";
        String madeWord5 = "at";

        expectWordVerification(new String[]{letters1, madeWord1, madeWord2, madeWord3, madeWord4, madeWord5});

        board.putLetters(letters1, board.getSquare(MID_POINT, MID_POINT - 2), Direction.ACROSS);
        board.putLetters(letters2, board.getSquare(MID_POINT + 1, MID_POINT + 1), Direction.DOWN);
        board.putLetters(letters3, board.getSquare(MID_POINT + 3, MID_POINT - 2), Direction.ACROSS);
        board.putLetters(letters4, board.getSquare(MID_POINT + 2, MID_POINT - 1), Direction.DOWN);
        board.setDryRun(true);
        int score = board.putLetters(letters5, board.getSquare(9, 4), Direction.ACROSS);

        checkWords(new String[]{letters1, madeWord1, madeWord2, madeWord3});
        assertEquals((16 + 1), score);
        checkBoard(
                "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "               \n" +
                        "     chubs     \n" +
                        "        o      \n" +
                        "      j r      \n" +
                        "     tutti     \n" +
                        "      s z      \n" +
                        "               \n" +
                        "               \n" +
                        "               \n");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PRIVATE METHODS
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void expectWordVerification(String[] expectedWords) {
        for (String word : expectedWords) {
            when(wordsmith.isValidWord(word)).thenReturn(true);
        }
    }

    private void checkWords(String[] expectedWords) {
        List<Word> words = board.getWords();
        assertEquals(expectedWords.length, words.size());
        ArrayList<String> actualWords = new ArrayList<>();
        for (Word word : words) {
            actualWords.add(word.getWord());
        }
        for (String s : expectedWords) {
            assertTrue(actualWords.contains(s), actualWords + " does not contain " + s);
            actualWords.remove(s);
        }
    }

    private void checkBoard(String expected) {
        assertEquals(expected, TestUtils.boardToString(board));
    }

}
