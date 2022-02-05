package com.jamescookie.scrabble;

import com.jamescookie.scrabble.types.Game;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author ukjamescook
 */
public class WordTest {
    @Test
    public void testGenerate() throws Exception {
        Board board = new Board(null, Game.itsYourTurn());
        Direction direction = Direction.ACROSS;
        int d = direction.getDirection();
        int row = 7;
        int col = 6;
        int length = 4;
        Word word = Word.generate(d+Word.EXPORT_SEPARATOR +
                row+Word.EXPORT_SEPARATOR +
                col+Word.EXPORT_SEPARATOR +
                length+Word.EXPORT_SEPARATOR, board);
        assertEquals(direction, word.getDirection());
        assertEquals(board.getSquare(row, col), word.getStartingPoint());
        assertEquals(length, word.getLength());
    }
}
