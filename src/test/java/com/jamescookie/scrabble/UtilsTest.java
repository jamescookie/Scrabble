package com.jamescookie.scrabble;

/**
 * @author ukjamescook
 */
public class UtilsTest extends Tester {

    public void testIsAdjacent() throws Exception {
        replay();
        Square square = Square.getNormal(Board.MID_POINT, Board.MID_POINT);
        for (int row = 0; row < Board.BOARD_SIZE; row++) {
            for (int col = 0; col < Board.BOARD_SIZE; col++) {
                if ((row != Board.MID_POINT-1 || col != Board.MID_POINT) &&
                    (row != Board.MID_POINT || (col != Board.MID_POINT-1 && col != Board.MID_POINT+1)) &&
                    (row != Board.MID_POINT+1 || col != Board.MID_POINT)) {
                    assertFalse(Utils.isAdjacent(Square.getNormal(row, col), square));
                }
            }
        }

        assertTrue(Utils.isAdjacent(Square.getNormal(Board.MID_POINT-1, Board.MID_POINT), square));
        assertTrue(Utils.isAdjacent(Square.getNormal(Board.MID_POINT, Board.MID_POINT-1), square));
        assertTrue(Utils.isAdjacent(Square.getNormal(Board.MID_POINT, Board.MID_POINT+1), square));
        assertTrue(Utils.isAdjacent(Square.getNormal(Board.MID_POINT+1, Board.MID_POINT), square));
        verify();
    }

    public void testIsAdjacentAcross() throws Exception {
        Square square = Square.getNormal(Board.MID_POINT, Board.MID_POINT);
        replay();
        assertFalse(Utils.isAdjacentInDirection(Square.getNormal(Board.MID_POINT-1, Board.MID_POINT), square, Direction.ACROSS));
        assertTrue(Utils.isAdjacentInDirection(Square.getNormal(Board.MID_POINT, Board.MID_POINT-1), square, Direction.ACROSS));
        assertTrue(Utils.isAdjacentInDirection(Square.getNormal(Board.MID_POINT, Board.MID_POINT+1), square, Direction.ACROSS));
        assertFalse(Utils.isAdjacentInDirection(Square.getNormal(Board.MID_POINT+1, Board.MID_POINT), square, Direction.ACROSS));
        verify();
    }

    public void testIsAdjacentDown() throws Exception {
        Square square = Square.getNormal(Board.MID_POINT, Board.MID_POINT);
        replay();
        assertTrue(Utils.isAdjacentInDirection(Square.getNormal(Board.MID_POINT-1, Board.MID_POINT), square, Direction.DOWN));
        assertFalse(Utils.isAdjacentInDirection(Square.getNormal(Board.MID_POINT, Board.MID_POINT-1), square, Direction.DOWN));
        assertFalse(Utils.isAdjacentInDirection(Square.getNormal(Board.MID_POINT, Board.MID_POINT+1), square, Direction.DOWN));
        assertTrue(Utils.isAdjacentInDirection(Square.getNormal(Board.MID_POINT+1, Board.MID_POINT), square, Direction.DOWN));
        verify();
    }

    public void testIsImmediatelyBefore() throws Exception {
        Square square = Square.getNormal(Board.MID_POINT, Board.MID_POINT);
        assertTrue(Utils.isImmediatelyBefore(Square.getNormal(Board.MID_POINT-1, Board.MID_POINT), square));
        assertTrue(Utils.isImmediatelyBefore(Square.getNormal(Board.MID_POINT, Board.MID_POINT-1), square));
    }

    public void testIsImmediatelyAfter() throws Exception {
        Square square = Square.getNormal(Board.MID_POINT, Board.MID_POINT);
        assertTrue(Utils.isImmediatelyAfter(Square.getNormal(Board.MID_POINT, Board.MID_POINT+1), square));
        assertTrue(Utils.isImmediatelyAfter(Square.getNormal(Board.MID_POINT+1, Board.MID_POINT), square));
    }

    public void testIsBefore() throws Exception {
        Square square = Square.getNormal(Board.MID_POINT, Board.MID_POINT);
        assertTrue(Utils.isBefore(Square.getNormal(Board.MID_POINT-1, Board.MID_POINT), square));
        assertTrue(Utils.isBefore(Square.getNormal(Board.MID_POINT, Board.MID_POINT-1), square));
    }

    public void testIsAfter() throws Exception {
        Square square = Square.getNormal(Board.MID_POINT, Board.MID_POINT);
        assertTrue(Utils.isAfter(Square.getNormal(Board.MID_POINT, Board.MID_POINT+1), square));
        assertTrue(Utils.isAfter(Square.getNormal(Board.MID_POINT+1, Board.MID_POINT), square));
    }

}
