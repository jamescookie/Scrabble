package com.jamescookie.scrabble;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author ukjamescook
 */
public class WordTest {
    @Test
    public void testGenerate() throws Exception {
        Board board = new Board(null);
        Direction direction = Direction.ACROSS;
        int d = direction.getDirection();
        int row = 7;
        int col = 6;
        int length = 4;
        Word word = Word.generate(d+Word.EXPORT_SEPERATOR+
                row+Word.EXPORT_SEPERATOR+
                col+Word.EXPORT_SEPERATOR+
                length+Word.EXPORT_SEPERATOR, board);
        assertEquals(direction, word.getDirection());
        assertEquals(board.getSquare(row, col), word.getStartingPoint());
        assertEquals(length, word.getLength());
    }
}
