package com.jamescookie.scrabble;

/**
 * @author ukjamescook
 */
public class WordTest extends Tester {
    public void testGenerate() throws Exception {
        Direction direction = Direction.ACROSS;
        int d = direction.getDirection();
        int row = 7;
        int col = 6;
        int length = 4;
        Word word = Word.generate(d+Word.EXPORT_SEPERATOR+
                row+Word.EXPORT_SEPERATOR+
                col+Word.EXPORT_SEPERATOR+
                length+Word.EXPORT_SEPERATOR);
        assertEquals(direction, word.getDirection());
//        assertEquals(Board.getSquare(row, col), word.getStartingPoint());
        assertEquals(length, word.getLength());
    }
}
