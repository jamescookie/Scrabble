package com.jamescookie.scrabble;

import java.util.ArrayList;
import java.util.List;

import org.easymock.MockControl;

/**
 * @author ukjamescook
 */
public class BoardTest extends Tester {
    private Board board;

    private MockControl wordsmithControl;
    private Wordsmith wordsmith;

    protected void setUp() throws Exception {
        super.setUp();
        wordsmithControl = createControl(Wordsmith.class);
        wordsmith = (Wordsmith) wordsmithControl.getMock();
        board = new Board(wordsmith);
    }

    public void testBoardConstruction() throws Exception {
        Square sq;
        for (int row = 1; row <= Board.BOARD_SIZE; row++) {
            for (int col = 1; col <= Board.BOARD_SIZE; col++) {
                sq = Board.getSquare(row-1, col-1);
                assertEquals(sq + " has incorrect row", row-1, sq.getRow());
                assertEquals(sq + " has incorrect col", col-1, sq.getColumn());
            }
        }
    }

    public void testTripleWords() throws Exception {
        for (int row = 1; row <= Board.BOARD_SIZE; row++) {
            for (int col = 1; col <= Board.BOARD_SIZE; col++) {
                if ((row == 1 && (col == 4 || col == 12)) ||
                    (row == 4 && (col == 1 || col == 15)) ||
                    (row == 12 && (col == 1 || col == 15)) ||
                    (row == 15 && (col == 4 || col == 12))) {
                    assertTrue("row "+row+" & col "+col+" is not a triple word!", Square.getTripleWord(0,0).equivalentMods(Board.getSquare(row-1, col-1)));
                } else {
                    assertFalse("row "+row+" & col "+col+" is a triple word!", Square.getTripleWord(0,0).equivalentMods(Board.getSquare(row-1, col-1)));
                }
            }
        }
    }

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
                    assertTrue("row "+row+" & col "+col+" is not a double word!", Square.getDoubleWord(0,0).equivalentMods(Board.getSquare(row-1, col-1)));
                } else {
                    assertFalse("row "+row+" & col "+col+" is a double word!", Square.getDoubleWord(0,0).equivalentMods(Board.getSquare(row-1, col-1)));
                }
            }
        }
    }

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
                    assertTrue("row "+row+" & col "+col+" is not a triple letter!", Square.getTripleLetter(0,0).equivalentMods(Board.getSquare(row-1, col-1)));
                } else {
                    assertFalse("row "+row+" & col "+col+" is a triple letter!", Square.getTripleLetter(0,0).equivalentMods(Board.getSquare(row-1, col-1)));
                }
            }
        }
    }

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
                    assertTrue("row "+row+" & col "+col+" is not a double letter!", Square.getDoubleLetter(0,0).equivalentMods(Board.getSquare(row-1, col-1)));
                } else {
                    assertFalse("row "+row+" & col "+col+" is a double letter!", Square.getDoubleLetter(0,0).equivalentMods(Board.getSquare(row-1, col-1)));
                }
            }
        }
    }

    public void testGetSquaresAcross() throws Exception {
        int wordLength = 5;
        int row = 1;
        int column = 3;

        replay();
        List<Square> squares = Board.Tester.getSquares(board, Direction.ACROSS, Board.getSquare(row, column), wordLength);
        verify();

        assertEquals(wordLength, squares.size());
        int i = column;
        for (Square square : squares) {
            assertEquals(i++, square.getColumn());
            assertEquals(row, square.getRow());
        }
    }

    public void testGetSquaresDown() throws Exception {
        int wordLength = 6;
        int row = 4;
        int column = 7;

        replay();
        List<Square> squares = Board.Tester.getSquares(board, Direction.DOWN, Board.getSquare(row, column), wordLength);
        verify();

        assertEquals(wordLength, squares.size());
        int i = row;
        for (Square square : squares) {
            assertEquals(i++, square.getRow());
            assertEquals(column, square.getColumn());
        }
    }

    public void testGetSquaresThrowsExceptionForLongWordOffBoardInNormalDirection() throws Exception {
        int wordLength = Board.BOARD_SIZE;
        int row = Board.MID_POINT;
        int column = Board.MID_POINT;

        replay();
        try {
            Board.Tester.getSquares(board, Direction.DOWN, Board.getSquare(row, column), wordLength);
            fail("Exception expected");
        } catch (ScrabbleException e) {
        }
        verify();
    }

    public void testGetSquaresThrowsExceptionForLongWordInOppositeDirection() throws Exception {
        int wordLength = Board.BOARD_SIZE;
        int row = Board.MID_POINT;
        int column = Board.MID_POINT;

        replay();
        try {
            Board.Tester.getSquares(board, Direction.UP, Board.getSquare(row, column), wordLength);
            fail("Exception expected");
        } catch (ScrabbleException e) {
        }
        verify();
    }

    public void testGetSquaresThrowsExceptionForBadStartingPoint() throws Exception {
        int wordLength = 1;
        int row = Board.BOARD_SIZE;
        int column = Board.BOARD_SIZE;

        replay();
        try {
            Board.Tester.getSquares(board, Direction.DOWN, Square.getNormal(row, column), wordLength);
            fail("Exception expected");
        } catch (ScrabbleException e) {
        }
        verify();
    }

    public void testGetSquaresAtLimit() throws Exception {
        int wordLength = 1;
        int row = Board.BOARD_SIZE - 1;
        int column = Board.BOARD_SIZE - 1;

        replay();
        List<Square> squares = Board.Tester.getSquares(board, Direction.ACROSS, Board.getSquare(row, column), wordLength);
        verify();

        assertEquals(wordLength, squares.size());
        Square square = squares.get(0);
        assertEquals(row, square.getRow());
        assertEquals(column, square.getColumn());
    }

    public void testGetSquare() throws Exception {
        int row = Board.MID_POINT;
        int column = Board.MID_POINT;
        replay();
        Square square = Board.getSquare(row, column);
        verify();
        assertEquals(row, square.getRow());
        assertEquals(column, square.getColumn());
    }

    public void testGetSquareThrowsExceptionForBadStartingPoint() throws Exception {
        replay();
        try {
            Board.getSquare(Board.BOARD_SIZE, Board.BOARD_SIZE);
            fail("Exception expected");
        } catch (ScrabbleException e) {
        }
        verify();
    }

    public void testIsNewWordGoingToTouchExistingWord() throws Exception {
        String word = "existing";
        String newWord = "newword";
        Square existingWordStart = Board.getSquare(Board.MID_POINT - (word.length() / 2), Board.MID_POINT);
        Square newWordStart = Board.getSquare(Board.MID_POINT, Board.MID_POINT - (newWord.length() / 2));
        List<Square> existingWordSquares = Board.Tester.getSquares(board, Direction.DOWN, existingWordStart, word.length());
        List<Square> newWordSquares = Board.Tester.getSquares(board, Direction.ACROSS, newWordStart, newWord.length());
        ArrayList<Word> words = new ArrayList<Word>();

        words.add(new Word(Direction.DOWN, existingWordSquares));

        replay();
        assertTrue(Board.Tester.isNewWordGoingToTouchExistingWord(board, newWordSquares, words));
        verify();
    }

    public void testFindNextSquareAcross() throws Exception {
        replay();
        Square square = Board.Tester.findNextSquare(Board.getSquare(Board.MID_POINT, Board.MID_POINT), Direction.ACROSS);
        verify();

        assertEquals(Board.MID_POINT, square.getRow());
        assertEquals(Board.MID_POINT+1, square.getColumn());
    }

    public void testFindNextSquareDown() throws Exception {
        replay();
        Square square = Board.Tester.findNextSquare(Board.getSquare(Board.MID_POINT, Board.MID_POINT), Direction.DOWN);
        verify();

        assertEquals(Board.MID_POINT+1, square.getRow());
        assertEquals(Board.MID_POINT, square.getColumn());
    }

    public void testFindNextSquareOffBoard() throws Exception {
        replay();
        try {
            Board.Tester.findNextSquare(Board.getSquare(0, 0), Direction.UP);
            fail("Excpetion expected");
        } catch (ScrabbleException e) {
        }
        verify();
    }

    public void testDetermineDirection() throws Exception {
        assertEquals(Direction.ACROSS, Board.Tester.determineDirection(Square.getNormal(0, 0), Square.getNormal(0, 2)));
        assertEquals(Direction.ACROSS, Board.Tester.determineDirection(Square.getNormal(1, 10), Square.getNormal(1, 2)));
        assertEquals(Direction.DOWN, Board.Tester.determineDirection(Square.getNormal(3, 5), Square.getNormal(1, 5)));
        assertEquals(Direction.DOWN, Board.Tester.determineDirection(Square.getNormal(3, 7), Square.getNormal(10, 7)));
        assertNull(Board.Tester.determineDirection(Square.getNormal(0, 0), Square.getNormal(Board.MID_POINT, Board.MID_POINT)));
    }

    public void testPutLetters() throws Exception {
        String word = "a";
        String[] words = new String[] {word};
        expectWordVerification(words);

        replay();
        board.putLetters(word, Board.getSquare(Board.MID_POINT, Board.MID_POINT), Direction.DOWN);
        verify();

        checkWords(words);
        assertEquals(word.toCharArray()[0], board.getWords().get(0).getStartingPoint().getCharacter());
    }

    public void testPutLettersWithWord() throws Exception {
        String word = "across";
        String[] words = new String[] {word};
        expectWordVerification(words);

        replay();
        board.putLetters(word, Board.getSquare(Board.MID_POINT, Board.MID_POINT), Direction.ACROSS);
        verify();

        checkWords(words);
        Word returnedWord =  board.getWords().get(0);
        char[] chars = word.toCharArray();
        List<Square> squares = returnedWord.getSquares();
        for (int i = 0; i < chars.length; i++) {
            assertEquals(chars[i], squares.get(i).getCharacter());
        }
    }

    public void testCannotAddFirstSimpleWordToAnywhereOtherThanMiddle() throws Exception {
        String word = "a";
        String[] words = new String[] {word};

        for (int row = 0; row < Board.BOARD_SIZE; row++) {
            for (int col = 0; col < Board.BOARD_SIZE; col++) {
                if (row != Board.MID_POINT && col != Board.MID_POINT) {
                    try {
                        board.putLetters(word, Board.getSquare(row, col), Direction.DOWN);
                        fail("Exception expected");
                    } catch (ScrabbleException e) {
                        // expected
                    }
                }
            }
        }

        expectWordVerification(words);

        replay();
        board.putLetters(word, Board.getSquare(Board.MID_POINT, Board.MID_POINT), Direction.DOWN);
        verify();
    }

    public void testCannotAddFirstComplexWordToAnywhereOtherThanAcrossMiddle() throws Exception {
        String word = "across";
        String[] words = new String[] {word};

        for (int row = 0; row < Board.BOARD_SIZE; row++) {
            for (int col = 0; col < Board.BOARD_SIZE; col++) {
                if (row != Board.MID_POINT || col > Board.MID_POINT) {
                    try {
                        board.putLetters(word, Board.getSquare(row, col), Direction.ACROSS);
                        fail("Exception expected");
                    } catch (ScrabbleException e) {
                        // expected
                    }
                }
            }
        }

        expectWordVerification(words);

        replay();
        board.putLetters(word, Board.getSquare(Board.MID_POINT, Board.MID_POINT-word.length()), Direction.ACROSS);
        verify();
    }

    public void testCannotAddFirstComplexWordToAnywhereOtherThanDownMiddle() throws Exception {
        String word = "down";
        String[] words = new String[] {word};

        for (int row = 0; row < Board.BOARD_SIZE; row++) {
            for (int col = 0; col < Board.BOARD_SIZE; col++) {
                if (col != Board.MID_POINT || row > Board.MID_POINT) {
                    try {
                        board.putLetters(word, Board.getSquare(row, col), Direction.DOWN);
                        fail("Exception expected");
                    } catch (ScrabbleException e) {
                        // expected
                    }
                }
            }
        }

        expectWordVerification(words);

        replay();
        board.putLetters(word, Board.getSquare(Board.MID_POINT-word.length(), Board.MID_POINT), Direction.DOWN);
        verify();
    }

    public void testCannotAddSecondWordToAnywhereOtherThanAdjacentFirstWord() throws Exception {
        String word = "a";
        String madeWord = "aa";
        String[] words = new String[] {word, madeWord};

        expectWordVerification(words);

        replay();
        board.putLetters(word, Board.getSquare(Board.MID_POINT, Board.MID_POINT), Direction.DOWN);

        for (int row = 0; row < Board.BOARD_SIZE; row++) {
            for (int col = 0; col < Board.BOARD_SIZE; col++) {
                if ((row != Board.MID_POINT-1 || col != Board.MID_POINT) &&
                    (row != Board.MID_POINT || (col != Board.MID_POINT-1 && col != Board.MID_POINT+1)) &&
                    (row != Board.MID_POINT+1 || col != Board.MID_POINT)) {
                    try {
                        board.putLetters(word, Board.getSquare(row, col), Direction.DOWN);
                        fail("Incorrectly able to add "+word+" to row "+row+", col "+col);
                    } catch (ScrabbleException e) {

                    }
                }
            }
        }

        board.putLetters(word, Board.getSquare(Board.MID_POINT-1, Board.MID_POINT), Direction.ACROSS);
        verify();
    }

    public void testGetBoardLetters() throws Exception {
        String word = "abc";
        String[] words = new String[] {word};
        expectWordVerification(words);

        replay();
        board.putLetters(word, Board.getSquare(Board.MID_POINT, Board.MID_POINT), Direction.ACROSS);
        verify();

        String[][] boardLetters = board.getBoardLetters();
        String[] rows = boardLetters[0];
        String[] columns = boardLetters[1];
        assertEquals(Board.BOARD_SIZE, rows.length);
        assertEquals(Board.BOARD_SIZE, columns.length);
        for (int i = 0; i < rows.length; i++) {
            String row = rows[i];
            if (i == Board.MID_POINT) {
                assertEquals(word, row);
            } else {
                assertEquals("", row);
            }
        }
        for (int i = 0; i < columns.length; i++) {
            String column = columns[i];
            if (i == Board.MID_POINT) {
                assertEquals("a", column);
            } else if (i == Board.MID_POINT + 1) {
                assertEquals("b", column);
            } else if (i == Board.MID_POINT + 2) {
                assertEquals("c", column);
            } else {
                assertEquals("", column);
            }
        }
    }

    public void testGetBoardLetters2() throws Exception {
        String letters1 = "aaa";
        String letters2 = "bc";
        String madeWord = "abc";
        String[] words = new String[] {letters1, madeWord, madeWord};
        expectWordVerification(words);

        replay();
        board.putLetters(letters1, Board.getSquare(Board.MID_POINT, Board.MID_POINT), Direction.ACROSS);
        board.putLetters(letters2, Board.getSquare(Board.MID_POINT + 1, Board.MID_POINT), Direction.DOWN);
        board.putLetters(letters2, Board.getSquare(Board.MID_POINT + 1, Board.MID_POINT + 2), Direction.DOWN);
        verify();

        String[][] boardLetters = board.getBoardLetters();
        String[] rows = boardLetters[0];
        String[] columns = boardLetters[1];
        assertEquals(Board.BOARD_SIZE, rows.length);
        assertEquals(Board.BOARD_SIZE, columns.length);
        for (int i = 0; i < rows.length; i++) {
            String row = rows[i];
            if (i == Board.MID_POINT) {
                assertEquals(letters1, row);
            } else if (i == Board.MID_POINT + 1) {
                assertEquals("b b", row);
            } else if (i == Board.MID_POINT + 2) {
                assertEquals("c c", row);
            } else {
                assertEquals("", row);
            }
        }
        for (int i = 0; i < columns.length; i++) {
            String column = columns[i];
            if (i == Board.MID_POINT) {
                assertEquals(madeWord, column);
            } else if (i == Board.MID_POINT + 1) {
                assertEquals("a", column);
            } else if (i == Board.MID_POINT + 2) {
                assertEquals(madeWord, column);
            } else {
                assertEquals("", column);
            }
        }

    }

    public void testScoreForSingleLetter() throws Exception {
        String letters = "a";

        expectWordVerification(new String[] {letters});

        replay();
        int score = board.putLetters(letters, Board.getSquare(Board.MID_POINT, Board.MID_POINT), Direction.ACROSS);
        verify();

        assertEquals(2, score);
    }

    public void testScoreForSingleWord() throws Exception {
        String letters = "jump";

        expectWordVerification(new String[] {letters});

        replay();
        int score = board.putLetters(letters, Board.getSquare(Board.MID_POINT, Board.MID_POINT), Direction.ACROSS);
        verify();

        assertEquals(28, score);
    }

    public void testScoreForAllLetters() throws Exception {
        String letters = "aaaaaaa";

        expectWordVerification(new String[] {letters});

        replay();
        int score = board.putLetters(letters, Board.getSquare(Board.MID_POINT, Board.MID_POINT - 3), Direction.ACROSS);
        verify();

        assertEquals(54, score);
    }

    public void testWildCard() throws Exception {
        String letters = "abc";
        String[] words = new String[] {letters};

        expectWordVerification(words);

        replay();
        int score = board.putLetters(Utils.WILDCARD + letters, Board.getSquare(Board.MID_POINT, Board.MID_POINT), Direction.ACROSS);
        verify();

        checkWords(words);
        assertEquals(10, score);
    }

    public void testAddingSingleLetterBeforeSingleLetter() throws Exception {
        String word1 = "b";
        String word2 = "a";
        String madeWord = "ab";

        expectWordVerification(new String[] {word1, madeWord});

        replay();
        board.putLetters(word1, Board.getSquare(Board.MID_POINT, Board.MID_POINT), Direction.ACROSS);
        int score = board.putLetters(word2, Board.getSquare(Board.MID_POINT-1, Board.MID_POINT), Direction.ACROSS);
        verify();

        checkWords(new String[] {madeWord});
        assertEquals(Direction.DOWN, board.getWords().get(0).getDirection());
        assertEquals(4, score);
    }

    public void testAddingSingleLetterAfterSingleLetter() throws Exception {
        String word1 = "a";
        String word2 = "b";
        String madeWord = "ab";

        expectWordVerification(new String[] {word1, madeWord});

        replay();
        board.putLetters(word1, Board.getSquare(Board.MID_POINT, Board.MID_POINT), Direction.DOWN);
        int score = board.putLetters(word2, Board.getSquare(Board.MID_POINT+1, Board.MID_POINT), Direction.ACROSS);
        verify();

        checkWords(new String[] {madeWord});
        assertEquals(4, score);
    }

    public void testAddingWordBeforeSingleLetter() throws Exception {
        String word1 = "c";
        String word2 = "ab";
        String madeWord = "abc";

        expectWordVerification(new String[] {word1, madeWord});

        replay();
        board.putLetters(word1, Board.getSquare(Board.MID_POINT, Board.MID_POINT), Direction.DOWN);
        int score = board.putLetters(word2, Board.getSquare(Board.MID_POINT-(word2.length()), Board.MID_POINT), Direction.DOWN);
        verify();

        checkWords(new String[] {madeWord});
        assertEquals(6, score);
    }

    public void testAddingWordAfterSingleLetter() throws Exception {
        String word1 = "a";
        String word2 = "bc";
        String madeWord = "abc";

        expectWordVerification(new String[] {word1, madeWord});

        replay();
        board.putLetters(word1, Board.getSquare(Board.MID_POINT, Board.MID_POINT), Direction.DOWN);
        int score = board.putLetters(word2, Board.getSquare(Board.MID_POINT+1, Board.MID_POINT), Direction.DOWN);
        verify();

        checkWords(new String[] {madeWord});
        assertEquals(6, score);
    }

    public void testAddingWordBeforeWord() throws Exception {
        String word1 = "cd";
        String word2 = "ab";
        String madeWord = "abcd";

        expectWordVerification(new String[] {word1, madeWord});

        replay();
        board.putLetters(word1, Board.getSquare(Board.MID_POINT, Board.MID_POINT), Direction.DOWN);
        int score = board.putLetters(word2, Board.getSquare(Board.MID_POINT-(word2.length()), Board.MID_POINT), Direction.DOWN);
        verify();

        checkWords(new String[] {madeWord});
        assertEquals(7, score);
    }

    public void testAddingWordAfterWord() throws Exception {
        String word1 = "ab";
        String word2 = "cd";
        String madeWord = "abcd";

        expectWordVerification(new String[] {word1, madeWord});

        replay();
        board.putLetters(word1, Board.getSquare(Board.MID_POINT, Board.MID_POINT), Direction.DOWN);
        int score = board.putLetters(word2, Board.getSquare(Board.MID_POINT+(word1.length()), Board.MID_POINT), Direction.DOWN);
        verify();

        checkWords(new String[] {madeWord});
        assertEquals(7, score);
    }

    public void testEncompassSingleLetter() throws Exception {
        String word1 = "b";
        String word2 = "acd";
        String madeWord = "abcd";

        expectWordVerification(new String[] {word1, madeWord});

        replay();
        board.putLetters(word1, Board.getSquare(Board.MID_POINT, Board.MID_POINT), Direction.ACROSS);
        int score = board.putLetters(word2, Board.getSquare(Board.MID_POINT, Board.MID_POINT-1), Direction.ACROSS);
        verify();

        checkWords(new String[] {madeWord});
        assertEquals(7, score);
    }

    public void testEncompassWord() throws Exception {
        String word1 = "bc";
        String word2 = "ad";
        String madeWord = "abcd";

        expectWordVerification(new String[] {word1, madeWord});

        replay();
        board.putLetters(word1, Board.getSquare(Board.MID_POINT, Board.MID_POINT), Direction.ACROSS);
        int score = board.putLetters(word2, Board.getSquare(Board.MID_POINT, Board.MID_POINT-1), Direction.ACROSS);
        verify();

        checkWords(new String[] {madeWord});
        assertEquals(7, score);
    }

    public void testSimpleIntersection1() throws Exception {
        String letters1 = "abc";
        String letters2 = "ab";
        String[] words = new String[] {letters1, letters1};

        expectWordVerification(words);

        replay();
        board.putLetters(letters1, Board.getSquare(Board.MID_POINT, Board.MID_POINT), Direction.DOWN);
        int score = board.putLetters(letters2, Board.getSquare(Board.MID_POINT + (letters1.length()-1), Board.MID_POINT - letters2.length()), Direction.ACROSS);
        verify();

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

    public void testSimpleIntersection2() throws Exception {
        String letters1 = "ab";
        String letters2 = "abc";

        expectWordVerification(new String[] {letters1, letters2, letters2});

        replay();
        board.putLetters(letters1, Board.getSquare(Board.MID_POINT, Board.MID_POINT), Direction.DOWN);
        int score = board.putLetters(letters2, Board.getSquare(Board.MID_POINT + letters1.length(), Board.MID_POINT - (letters2.length()-1)), Direction.ACROSS);
        verify();

        checkWords(new String[] {letters2, letters2});
        assertEquals(6 + 6, score);
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

    public void testSimpleIntersection3() throws Exception {
        String letters1 = "abc";
        String letters2 = "ac";
        String[] words = new String[] {letters1, letters1};

        expectWordVerification(words);

        replay();
        board.putLetters(letters1, Board.getSquare(Board.MID_POINT, Board.MID_POINT), Direction.DOWN);
        int score = board.putLetters(letters2, Board.getSquare(Board.MID_POINT + 1, Board.MID_POINT - 1), Direction.ACROSS);
        verify();

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

    public void testSimpleIntersection4() throws Exception {
        String letters1 = "abc";
        String letters2 = "b";
        String[] words = new String[] {letters1, "ab"};

        expectWordVerification(words);

        replay();
        board.putLetters(letters1, Board.getSquare(Board.MID_POINT, Board.MID_POINT), Direction.DOWN);
        int score = board.putLetters(letters2, Board.getSquare(Board.MID_POINT, Board.MID_POINT + 1), Direction.ACROSS);
        verify();

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

    public void testSlidingIntersection1() throws Exception {
        String letters1 = "abc";
        String[] expectedWords = new String[] {letters1, letters1, "aa", "bb", "cc"};

        expectWordVerification(expectedWords);

        replay();
        board.putLetters(letters1, Board.getSquare(Board.MID_POINT, Board.MID_POINT), Direction.ACROSS);
        int score = board.putLetters(letters1, Board.getSquare(Board.MID_POINT + 1, Board.MID_POINT), Direction.ACROSS);
        verify();

        checkWords(expectedWords);
        assertEquals(9 + 3 + 6 + 6, score);
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

    public void testSlidingIntersection2() throws Exception {
        String letters1 = "abc";
        String[] expectedWords = new String[] {letters1, letters1, "ba", "cb"};

        expectWordVerification(expectedWords);

        replay();
        board.putLetters(letters1, Board.getSquare(Board.MID_POINT, Board.MID_POINT), Direction.ACROSS);
        int score = board.putLetters(letters1, Board.getSquare(Board.MID_POINT + 1, Board.MID_POINT + 1), Direction.ACROSS);
        verify();

        checkWords(expectedWords);
        assertEquals(7 + 5 + 5, score);
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

    public void testSlidingIntersection3() throws Exception {
        String letters1 = "abc";
        String[] expectedWords = new String[] {letters1, letters1, "ca"};

        expectWordVerification(expectedWords);

        replay();
        board.putLetters(letters1, Board.getSquare(Board.MID_POINT, Board.MID_POINT), Direction.DOWN);
        int score = board.putLetters(letters1, Board.getSquare(Board.MID_POINT + 2, Board.MID_POINT + 1), Direction.DOWN);
        verify();

        checkWords(expectedWords);
        assertEquals(10 + 3, score);
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

    public void testBoxIntersection1() throws Exception {
        String letters1 = "aaa";
        String letters2 = "b";
        String letters3 = "ccc";
        String madeWord1 = "ab";
        String madeWord2 = "abc";

        expectWordVerification(new String[] {letters1, madeWord1, madeWord1, letters3, madeWord2, madeWord2});

        replay();
        board.putLetters(letters1, Board.getSquare(Board.MID_POINT, Board.MID_POINT), Direction.ACROSS);
        board.putLetters(letters2, Board.getSquare(Board.MID_POINT + 1, Board.MID_POINT), Direction.ACROSS);
        board.putLetters(letters2, Board.getSquare(Board.MID_POINT + 1, Board.MID_POINT + 2), Direction.ACROSS);
        int score = board.putLetters(letters3, Board.getSquare(Board.MID_POINT + 2, Board.MID_POINT), Direction.ACROSS);
        verify();

        checkWords(new String[] {letters1, madeWord2, madeWord2, letters3});
        assertEquals(6 + 6 + 6, score);
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

    public void testBoxIntersection2() throws Exception {
        String letters1 = "aba";
        String letters2 = "ba";
        String letters3 = "b";
        String letters4 = "c";
        String madeWord1 = "ab";
        String madeWord2 = "abc";

        expectWordVerification(new String[] {letters1, letters1, madeWord1, madeWord1, madeWord2, madeWord2});

        replay();
        board.putLetters(letters1, Board.getSquare(Board.MID_POINT, Board.MID_POINT), Direction.ACROSS);
        board.putLetters(letters2, Board.getSquare(Board.MID_POINT + 1, Board.MID_POINT), Direction.DOWN);
        board.putLetters(letters3, Board.getSquare(Board.MID_POINT + 1, Board.MID_POINT + 2), Direction.ACROSS);
        board.putLetters(letters3, Board.getSquare(Board.MID_POINT + 2, Board.MID_POINT + 1), Direction.ACROSS);
        int score = board.putLetters(letters4, Board.getSquare(Board.MID_POINT + 2, Board.MID_POINT + 2), Direction.ACROSS);
        verify();

        checkWords(new String[] {letters1, letters1, madeWord2, madeWord2});
        assertEquals(6 + 6, score);
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

    public void testBoxIntersection3() throws Exception {
        String letters1 = "aba";
        String letters2 = "bc";
        String letters3 = "b";
        String letters4 = "aac";
        String madeWord1 = "ab";
        String madeWord2 = "abc";
        String madeWord3 = "acac";

        expectWordVerification(new String[] {letters1, madeWord1, madeWord2, madeWord2, madeWord3});

        replay();
        board.putLetters(letters1, Board.getSquare(Board.MID_POINT, Board.MID_POINT), Direction.ACROSS);
        board.putLetters(letters2, Board.getSquare(Board.MID_POINT + 1, Board.MID_POINT), Direction.DOWN);
        board.putLetters(letters3, Board.getSquare(Board.MID_POINT + 1, Board.MID_POINT + 2), Direction.ACROSS);
        int score = board.putLetters(letters4, Board.getSquare(Board.MID_POINT + 2, Board.MID_POINT - 1), Direction.ACROSS);
        verify();

        checkWords(new String[] {letters1, madeWord2, madeWord2, madeWord3});
        assertEquals(6 + 6, score);
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

        expectWordVerification(new String[] {letters1, madeWord1, madeWord2, letters1, madeWord3, madeWord4, letters4, madeWord5});

        replay();
        board.putLetters(letters1, Board.getSquare(Board.MID_POINT, Board.MID_POINT), Direction.ACROSS);
        board.putLetters(letters2, Board.getSquare(Board.MID_POINT + 1, Board.MID_POINT), Direction.DOWN);
        board.putLetters(letters3, Board.getSquare(Board.MID_POINT + 1, Board.MID_POINT + 4), Direction.DOWN);
        board.putLetters(letters1, Board.getSquare(Board.MID_POINT + 4, Board.MID_POINT), Direction.ACROSS);
        board.putLetters(letters3, Board.getSquare(Board.MID_POINT + 3, Board.MID_POINT + 4), Direction.ACROSS);
        int score = board.putLetters(letters4, Board.getSquare(Board.MID_POINT + 2, Board.MID_POINT + 2), Direction.ACROSS);
        verify();

        checkWords(new String[] {letters1, letters1, letters4, madeWord3, madeWord5});
        assertEquals(12 + 10, score);
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

    public void testBoxIntersections5() throws Exception {
        String letters1 = "aaa";
        String letters2 = "aa";
        String letters3 = "b";
        String letters4 = "bc";
        String madeWord1 = "aaa";
        String madeWord2 = "ab";
        String madeWord3 = "abc";

        expectWordVerification(new String[] {letters1, madeWord1, madeWord2, madeWord3, madeWord3});

        replay();
        board.putLetters(letters1, Board.getSquare(Board.MID_POINT, Board.MID_POINT), Direction.ACROSS);
        board.putLetters(letters2, Board.getSquare(Board.MID_POINT + 1, Board.MID_POINT), Direction.DOWN);
        board.putLetters(letters3, Board.getSquare(Board.MID_POINT + 1, Board.MID_POINT + 2), Direction.ACROSS);
        int score = board.putLetters(letters4, Board.getSquare(Board.MID_POINT + 2, Board.MID_POINT + 1), Direction.ACROSS);
        verify();

        checkWords(new String[] {letters1, madeWord1, madeWord3, madeWord3});
        assertEquals(6 + 6, score);
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

    public void testSurroundedIntersection1() throws Exception {
        String letters1 = "aaa";
        String letters2 = "b";
        String letters3 = "ccc";
        String madeWord1 = "ab";
        String madeWord2 = "abc";
        String madeWord3 = "bbb";

        expectWordVerification(new String[] {letters1, madeWord1, madeWord1, madeWord2, madeWord2, madeWord2, letters3, madeWord3});

        replay();
        board.putLetters(letters1, Board.getSquare(Board.MID_POINT, Board.MID_POINT), Direction.ACROSS);
        board.putLetters(letters2, Board.getSquare(Board.MID_POINT + 1, Board.MID_POINT), Direction.ACROSS);
        board.putLetters(letters2, Board.getSquare(Board.MID_POINT + 1, Board.MID_POINT + 2), Direction.ACROSS);
        board.putLetters(letters3, Board.getSquare(Board.MID_POINT + 2, Board.MID_POINT), Direction.ACROSS);
        int score = board.putLetters(letters2, Board.getSquare(Board.MID_POINT + 1, Board.MID_POINT + 1), Direction.DOWN);
        verify();

        checkWords(new String[] {letters1, madeWord2, madeWord2, madeWord2, letters3, madeWord3});
        assertEquals(12 + 9, score);
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

    public void testSurroundedIntersection2() throws Exception {
        String letters1 = "aaa";
        String letters2 = "b";
        String letters3 = "ccc";
        String letters4 = "bb";
        String madeWord1 = "ab";
        String madeWord2 = "abc";
        String madeWord3 = "bbbb";

        expectWordVerification(new String[] {letters1, madeWord1, madeWord1, madeWord2, madeWord2, madeWord2, letters3, madeWord3});

        replay();
        board.putLetters(letters1, Board.getSquare(Board.MID_POINT, Board.MID_POINT), Direction.ACROSS);
        board.putLetters(letters2, Board.getSquare(Board.MID_POINT + 1, Board.MID_POINT), Direction.ACROSS);
        board.putLetters(letters2, Board.getSquare(Board.MID_POINT + 1, Board.MID_POINT + 2), Direction.ACROSS);
        board.putLetters(letters3, Board.getSquare(Board.MID_POINT + 2, Board.MID_POINT), Direction.ACROSS);
        int score = board.putLetters(letters4, Board.getSquare(Board.MID_POINT + 1, Board.MID_POINT + 1), Direction.ACROSS);
        verify();

        checkWords(new String[] {letters1, madeWord2, madeWord2, madeWord2, letters3, madeWord3});
        assertEquals(15 + 9, score);
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

    public void testMultipleIntersections1() throws Exception {
        String letters1 = "abcd";
        String letters2 = "abc";
        String madeWord1 = "abcc";
        String madeWord2 = "aa";
        String madeWord3 = "bb";
        String madeWord4 = "cc";

        expectWordVerification(new String[] {letters1, letters1, madeWord1, madeWord2, madeWord3, madeWord4});

        replay();
        board.putLetters(letters1, Board.getSquare(Board.MID_POINT, Board.MID_POINT), Direction.DOWN);
        board.putLetters(letters2, Board.getSquare(Board.MID_POINT + 3, Board.MID_POINT - 3), Direction.ACROSS);
        int score = board.putLetters(letters2, Board.getSquare(Board.MID_POINT + 2, Board.MID_POINT - 3), Direction.ACROSS);
        verify();

        checkWords(new String[] {letters1, letters1, madeWord1, madeWord2, madeWord3, madeWord4});
        assertEquals(16 + 4 + 6 + 4, score);
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

    public void testMultipleIntersections2() throws Exception {
        String letters1 = "abcd";
        String letters2 = "abc";
        String letters3 = "abd";
        String madeWord1 = "ab";
        String madeWord2 = "bc";

        expectWordVerification(new String[] {letters1, letters1, letters1, madeWord1, madeWord2});

        replay();
        board.putLetters(letters1, Board.getSquare(Board.MID_POINT, Board.MID_POINT), Direction.DOWN);
        board.putLetters(letters2, Board.getSquare(Board.MID_POINT + 3, Board.MID_POINT - 3), Direction.ACROSS);
        int score = board.putLetters(letters3, Board.getSquare(Board.MID_POINT + 2, Board.MID_POINT - 2), Direction.ACROSS);
        verify();

        checkWords(new String[] {letters1, letters1, letters1, madeWord1, madeWord2});
        assertEquals(7 + 4 + 5, score);
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

        expectWordVerification(new String[] {letters1, madeWord1, madeWord1, madeWord2, madeWord3, madeWord4, madeWord5});

        replay();
        board.putLetters(letters1, Board.getSquare(Board.MID_POINT, Board.MID_POINT), Direction.ACROSS);
        board.putLetters(letters2, Board.getSquare(Board.MID_POINT + 1, Board.MID_POINT + 4), Direction.DOWN);
        board.putLetters(letters3, Board.getSquare(Board.MID_POINT + 4, Board.MID_POINT), Direction.ACROSS);
        board.putLetters(letters4, Board.getSquare(Board.MID_POINT + 1, Board.MID_POINT), Direction.DOWN);
        board.putLetters(letters4, Board.getSquare(Board.MID_POINT + 3, Board.MID_POINT), Direction.DOWN);
        int score = board.putLetters(letters5, Board.getSquare(Board.MID_POINT + 2, Board.MID_POINT - 1), Direction.ACROSS);
        verify();

        checkWords(new String[] {letters1, madeWord1, madeWord1, madeWord2, madeWord5});
        assertEquals(24 + 10, score);
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

        expectWordVerification(new String[] {letters1, madeWord1, madeWord1, madeWord1, madeWord2, madeWord2, madeWord3, madeWord4});

        replay();
        board.putLetters(letters1, Board.getSquare(Board.MID_POINT, Board.MID_POINT), Direction.ACROSS);
        board.putLetters(letters2, Board.getSquare(Board.MID_POINT + 1, Board.MID_POINT), Direction.DOWN);
        board.putLetters(letters3, Board.getSquare(Board.MID_POINT + 1, Board.MID_POINT + 2), Direction.ACROSS);
        board.putLetters(letters3, Board.getSquare(Board.MID_POINT + 1, Board.MID_POINT + 4), Direction.DOWN);
        board.putLetters(letters4, Board.getSquare(Board.MID_POINT + 2, Board.MID_POINT + 1), Direction.DOWN);
        int score = board.putLetters(letters5, Board.getSquare(Board.MID_POINT + 2, Board.MID_POINT + 2), Direction.ACROSS);
        verify();

        checkWords(new String[] {letters1, madeWord1, madeWord1, madeWord1, madeWord4});
        assertEquals(20 + 6 + 6, score);
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

    public void testMultipleIntersections5() throws Exception {
        String letters1 = "abc";
        String madeWord1 = "aa";
        String madeWord2 = "bb";
        String madeWord3 = "cc";
        String madeWord4 = "baa";
        String madeWord5 = "cbb";

        expectWordVerification(new String[] {letters1, letters1, letters1, madeWord1, madeWord2, madeWord3, madeWord4, madeWord5});

        replay();
        board.putLetters(letters1, Board.getSquare(Board.MID_POINT, Board.MID_POINT), Direction.ACROSS);
        board.putLetters(letters1, Board.getSquare(Board.MID_POINT + 1, Board.MID_POINT), Direction.ACROSS);
        int score = board.putLetters(letters1, Board.getSquare(Board.MID_POINT - 1, Board.MID_POINT - 1), Direction.ACROSS);
        verify();

        checkWords(new String[] {letters1, letters1, letters1, madeWord3, madeWord4, madeWord5});
        assertEquals(9 + 5 + 10, score);
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

    public void testMultipleIntersections6() throws Exception {
        String letters1 = "abc";
        String letters2 = "c";
        String letters3 = "ddd";
        String madeWord1 = "cc";
        String madeWord2 = "abcd";
        String madeWord3 = "cd";

        expectWordVerification(new String[] {letters1, madeWord1, madeWord2, madeWord3, letters3});

        replay();
        board.putLetters(letters1, Board.getSquare(Board.MID_POINT, Board.MID_POINT), Direction.ACROSS);
        board.putLetters(letters2, Board.getSquare(Board.MID_POINT + 1, Board.MID_POINT + 2), Direction.ACROSS);
        int score = board.putLetters(letters3, Board.getSquare(Board.MID_POINT, Board.MID_POINT + 3), Direction.DOWN);
        verify();

        checkWords(new String[] {madeWord1, madeWord2, madeWord3, letters3});
        assertEquals(6 + 7 + 3, score);
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

    public void testMultipleIntersections7() throws Exception {
        String letters1 = "ab" + Utils.WILDCARD + "c";
        String letters2 = "cc";
        String letters3 = "ac";
        String madeWord1 = "abc";
        String madeWord2 = "ccc";
        String madeWord3 = "ac";
        String madeWord4 = "bac";

        expectWordVerification(new String[] {madeWord1, madeWord2, madeWord3, madeWord4});

        replay();
        board.putLetters(letters1, Board.getSquare(Board.MID_POINT, Board.MID_POINT), Direction.DOWN);
        board.putLetters(letters2, Board.getSquare(Board.MID_POINT + 2, Board.MID_POINT - 1), Direction.ACROSS);
        int score = board.putLetters(letters3, Board.getSquare(Board.MID_POINT + 1, Board.MID_POINT + 1), Direction.ACROSS);
        verify();

        checkWords(new String[] {madeWord1, madeWord2, madeWord3, madeWord4});
        assertEquals(7 + 4, score);
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

        expectWordVerification(new String[] {letters1, madeWord1, madeWord1, madeWord1, madeWord2, madeWord2, madeWord3, madeWord4});

        replay();
        board.putLetters(letters1, Board.getSquare(Board.MID_POINT, Board.MID_POINT), Direction.ACROSS);
        board.putLetters(letters2, Board.getSquare(Board.MID_POINT + 1, Board.MID_POINT), Direction.DOWN);
        board.putLetters(letters3, Board.getSquare(Board.MID_POINT + 1, Board.MID_POINT + 2), Direction.ACROSS);
        board.putLetters(letters3, Board.getSquare(Board.MID_POINT + 1, Board.MID_POINT + 4), Direction.DOWN);
        board.putLetters(letters4, Board.getSquare(Board.MID_POINT + 2, Board.MID_POINT + 1), Direction.DOWN);
        board.setTesting(true);
        int score = board.putLetters(letters5, Board.getSquare(Board.MID_POINT + 2, Board.MID_POINT + 2), Direction.ACROSS);
        verify();

        checkWords(new String[] {letters1, madeWord2, madeWord2, madeWord1, madeWord3});
        assertEquals(20 + 6 + 6, score);
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

    public void testTestingMode2() throws Exception {
        String letters1 = "abc";
        String[] expectedWords = new String[] {letters1, letters1, "ba", "cb"};

        expectWordVerification(expectedWords);

        replay();
        board.putLetters(letters1, Board.getSquare(Board.MID_POINT, Board.MID_POINT), Direction.ACROSS);
        board.setTesting(true);
        int score = board.putLetters(letters1, Board.getSquare(Board.MID_POINT + 1, Board.MID_POINT + 1), Direction.ACROSS);
        verify();

        checkWords(new String[] {letters1});
        assertEquals(7 + 5 + 5, score);
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

    public void testTestingMode3() throws Exception {
        String letters1 = "aaa";
        String letters2 = "b";
        String letters3 = "ccc";
        String madeWord1 = "ab";
        String madeWord2 = "abc";
        String madeWord3 = "bbb";

        expectWordVerification(new String[] {letters1, madeWord1, madeWord1, madeWord2, madeWord2, madeWord2, letters3, madeWord3});

        replay();
        board.putLetters(letters1, Board.getSquare(Board.MID_POINT, Board.MID_POINT), Direction.ACROSS);
        board.putLetters(letters2, Board.getSquare(Board.MID_POINT + 1, Board.MID_POINT), Direction.ACROSS);
        board.putLetters(letters2, Board.getSquare(Board.MID_POINT + 1, Board.MID_POINT + 2), Direction.ACROSS);
        board.putLetters(letters3, Board.getSquare(Board.MID_POINT + 2, Board.MID_POINT), Direction.ACROSS);
        board.setTesting(true);
        int score = board.putLetters(letters2, Board.getSquare(Board.MID_POINT + 1, Board.MID_POINT + 1), Direction.DOWN);
        verify();

        checkWords(new String[] {letters1, madeWord2, madeWord2, letters3});
        assertEquals(12 + 9, score);
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

    public void testTestingMode4() throws Exception {
        String letters1 = "abc";
        String madeWord1 = "aa";
        String madeWord2 = "bb";
        String madeWord3 = "cc";
        String madeWord4 = "baa";
        String madeWord5 = "cbb";

        expectWordVerification(new String[] {letters1, letters1, letters1, madeWord1, madeWord2, madeWord3, madeWord4, madeWord5});

        replay();
        board.putLetters(letters1, Board.getSquare(Board.MID_POINT, Board.MID_POINT), Direction.ACROSS);
        board.putLetters(letters1, Board.getSquare(Board.MID_POINT + 1, Board.MID_POINT), Direction.ACROSS);
        board.setTesting(true);
        int score = board.putLetters(letters1, Board.getSquare(Board.MID_POINT - 1, Board.MID_POINT - 1), Direction.ACROSS);
        verify();

        checkWords(new String[] {letters1, letters1, madeWord1, madeWord2, madeWord3});
        assertEquals(9 + 5 + 10, score);
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

    public void testTestingMode5() throws Exception {
        String letters1 = "abc";
        String letters2 = "c";
        String letters3 = "ddd";
        String madeWord1 = "cc";
        String madeWord2 = "abcd";
        String madeWord3 = "cd";

        expectWordVerification(new String[] {letters1, madeWord1, madeWord2, madeWord3, letters3});

        replay();
        board.putLetters(letters1, Board.getSquare(Board.MID_POINT, Board.MID_POINT), Direction.ACROSS);
        board.putLetters(letters2, Board.getSquare(Board.MID_POINT + 1, Board.MID_POINT + 2), Direction.ACROSS);
        board.setTesting(true);
        int score = board.putLetters(letters3, Board.getSquare(Board.MID_POINT, Board.MID_POINT + 3), Direction.DOWN);
        verify();

        checkWords(new String[] {letters1, madeWord1});
        assertEquals(6 + 7 + 3, score);
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

        expectWordVerification(new String[] {letters1, madeWord1, madeWord2, madeWord3, madeWord4, madeWord5});

        replay();
        board.putLetters(letters1, Board.getSquare(Board.MID_POINT, Board.MID_POINT - 2), Direction.ACROSS);
        board.putLetters(letters2, Board.getSquare(Board.MID_POINT + 1, Board.MID_POINT + 1), Direction.DOWN);
        board.putLetters(letters3, Board.getSquare(Board.MID_POINT + 3, Board.MID_POINT - 2), Direction.ACROSS);
        board.putLetters(letters4, Board.getSquare(Board.MID_POINT + 2, Board.MID_POINT - 1), Direction.DOWN);
        board.setTesting(true);
        int score = board.putLetters(letters5, Board.getSquare(9, 4), Direction.ACROSS);
        verify();

        checkWords(new String[] {letters1, madeWord1, madeWord2, madeWord3});
        assertEquals(16 + 1, score);
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
            wordsmithControl.expectAndReturn(wordsmith.isValidWord(word), true);
        }
    }

    private void checkWords(String[] expectedWords) {
        List<Word> words = board.getWords();
        assertEquals(expectedWords.length, words.size());
        ArrayList<String> actualWords = new ArrayList<String>();
        for (Word word : words) {
            actualWords.add(word.getWord());
        }
        for (String s : expectedWords) {
            assertTrue(actualWords.contains(s));
            actualWords.remove(s);
        }
    }

    private void checkBoard(String expected) {
        assertEquals(expected, board.getBoard());
    }

}
