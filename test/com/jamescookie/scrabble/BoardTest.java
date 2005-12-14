package com.jamescookie.scrabble;

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
                sq = board.getSquare(row-1, col-1);
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
                    assertEquals("row "+row+" & col "+col+" is not a triple word!", Square.getTripleWord(0,0), board.getSquare(row-1, col-1));
                } else {
                    assertTrue("row "+row+" & col "+col+" is a triple word!", !Square.getTripleWord(0,0).equals(board.getSquare(row-1, col-1)));
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
                    assertEquals("row "+row+" & col "+col+" is not a double word!", Square.getDoubleWord(0,0), board.getSquare(row-1, col-1));
                } else {
                    assertTrue("row "+row+" & col "+col+" is a double word!", !Square.getDoubleWord(0,0).equals(board.getSquare(row-1, col-1)));
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
                    assertEquals("row "+row+" & col "+col+" is not a triple letter!", Square.getTripleLetter(0,0), board.getSquare(row-1, col-1));
                } else {
                    assertTrue("row "+row+" & col "+col+" is a triple letter!", !Square.getTripleLetter(0,0).equals(board.getSquare(row-1, col-1)));
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
                    assertEquals("row "+row+" & col "+col+" is not a double letter!", Square.getDoubleLetter(0,0), board.getSquare(row-1, col-1));
                } else {
                    assertTrue("row "+row+" & col "+col+" is a double letter!", !Square.getDoubleLetter(0,0).equals(board.getSquare(row-1, col-1)));
                }
            }
        }
    }

    public void testPutLetters() throws Exception {
        String word = "a";
        wordsmithControl.expectAndReturn(wordsmith.isValidWord(word), true);

        replay();
        board.putLetters(word, board.getSquare(Board.MID_POINT, Board.MID_POINT), Direction.DOWN);
        verify();

        List<Word> words = board.getWords();
        assertEquals(1, words.size());
        assertEquals(word, words.get(0).getWord());
    }

    public void testCannotAddFirstSimpleWordToAnywhereOtherThanMiddle() throws Exception {
        String word = "a";

        for (int row = 0; row < Board.BOARD_SIZE; row++) {
            for (int col = 0; col < Board.BOARD_SIZE; col++) {
                if (row != Board.MID_POINT && col != Board.MID_POINT) {
                    try {
                        board.putLetters(word, board.getSquare(row, col), Direction.DOWN);
                        fail("Exception expected");
                    } catch (ScrabbleException e) {
                        // expected
                    }
                }
            }
        }

        wordsmithControl.expectAndReturn(wordsmith.isValidWord(word), true);

        replay();
        board.putLetters(word, board.getSquare(Board.MID_POINT, Board.MID_POINT), Direction.DOWN);
        verify();
    }

    public void testCannotAddFirstComplexWordToAnywhereOtherThanAcrossMiddle() throws Exception {
        String word = "across";

        for (int row = 0; row < Board.BOARD_SIZE; row++) {
            for (int col = 0; col < Board.BOARD_SIZE; col++) {
                if (row != Board.MID_POINT || col > Board.MID_POINT) {
                    try {
                        board.putLetters(word, board.getSquare(row, col), Direction.ACROSS);
                        fail("Exception expected");
                    } catch (ScrabbleException e) {
                        // expected
                    }
                }
            }
        }

        wordsmithControl.expectAndReturn(wordsmith.isValidWord(word), true);

        replay();
        board.putLetters(word, board.getSquare(Board.MID_POINT, Board.MID_POINT-word.length()), Direction.ACROSS);
        verify();
    }

    public void testCannotAddFirstComplexWordToAnywhereOtherThanDownMiddle() throws Exception {
        String word = "down";

        for (int row = 0; row < Board.BOARD_SIZE; row++) {
            for (int col = 0; col < Board.BOARD_SIZE; col++) {
                if (col != Board.MID_POINT || row > Board.MID_POINT) {
                    try {
                        board.putLetters(word, board.getSquare(row, col), Direction.DOWN);
                        fail("Exception expected");
                    } catch (ScrabbleException e) {
                        // expected
                    }
                }
            }
        }

        wordsmithControl.expectAndReturn(wordsmith.isValidWord(word), true);

        replay();
        board.putLetters(word, board.getSquare(Board.MID_POINT-word.length(), Board.MID_POINT), Direction.DOWN);
        verify();
    }

    public void testCannotAddSecondWordToAnywhereOtherThanAdjacentFirstWord() throws Exception {
        String word = "a";
        String madeWord = "aa";

        wordsmithControl.expectAndReturn(wordsmith.isValidWord(word), true);
        wordsmithControl.expectAndReturn(wordsmith.isValidWord(madeWord), true);

        replay();
        board.putLetters(word, board.getSquare(Board.MID_POINT, Board.MID_POINT), Direction.DOWN);

        for (int row = 0; row < Board.BOARD_SIZE; row++) {
            for (int col = 0; col < Board.BOARD_SIZE; col++) {
                if ((row != Board.MID_POINT-1 || col != Board.MID_POINT) &&
                    (row != Board.MID_POINT || (col != Board.MID_POINT-1 && col != Board.MID_POINT+1)) &&
                    (row != Board.MID_POINT+1 || col != Board.MID_POINT)) {
                    try {
                        board.putLetters(word, board.getSquare(row, col), Direction.DOWN);
                        fail("Exception expected");
                    } catch (ScrabbleException e) {

                    }
                }
            }
        }

        board.putLetters(word, board.getSquare(Board.MID_POINT-1, Board.MID_POINT), Direction.ACROSS);
        verify();
    }

}
