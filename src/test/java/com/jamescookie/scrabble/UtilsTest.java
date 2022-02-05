package com.jamescookie.scrabble;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author ukjamescook
 */
public class UtilsTest {

    @Test
    public void testIsAdjacent() {
        Square square = Square.getNormal(Board.MID_POINT, Board.MID_POINT);
        for (int row = 0; row < Board.BOARD_SIZE; row++) {
            for (int col = 0; col < Board.BOARD_SIZE; col++) {
                if ((row != Board.MID_POINT - 1 || col != Board.MID_POINT) &&
                        (row != Board.MID_POINT || (col != Board.MID_POINT - 1 && col != Board.MID_POINT + 1)) &&
                        (row != Board.MID_POINT + 1 || col != Board.MID_POINT)) {
                    assertFalse(Utils.isAdjacent(Square.getNormal(row, col), square));
                }
            }
        }

        assertTrue(Utils.isAdjacent(Square.getNormal(Board.MID_POINT - 1, Board.MID_POINT), square));
        assertTrue(Utils.isAdjacent(Square.getNormal(Board.MID_POINT, Board.MID_POINT - 1), square));
        assertTrue(Utils.isAdjacent(Square.getNormal(Board.MID_POINT, Board.MID_POINT + 1), square));
        assertTrue(Utils.isAdjacent(Square.getNormal(Board.MID_POINT + 1, Board.MID_POINT), square));
    }

    @Test
    public void testIsAdjacentAcross() {
        Square square = Square.getNormal(Board.MID_POINT, Board.MID_POINT);
        assertFalse(Utils.isAdjacentInDirection(Square.getNormal(Board.MID_POINT - 1, Board.MID_POINT), square, Direction.ACROSS));
        assertTrue(Utils.isAdjacentInDirection(Square.getNormal(Board.MID_POINT, Board.MID_POINT - 1), square, Direction.ACROSS));
        assertTrue(Utils.isAdjacentInDirection(Square.getNormal(Board.MID_POINT, Board.MID_POINT + 1), square, Direction.ACROSS));
        assertFalse(Utils.isAdjacentInDirection(Square.getNormal(Board.MID_POINT + 1, Board.MID_POINT), square, Direction.ACROSS));
    }

    @Test
    public void testIsAdjacentDown() {
        Square square = Square.getNormal(Board.MID_POINT, Board.MID_POINT);
        assertTrue(Utils.isAdjacentInDirection(Square.getNormal(Board.MID_POINT - 1, Board.MID_POINT), square, Direction.DOWN));
        assertFalse(Utils.isAdjacentInDirection(Square.getNormal(Board.MID_POINT, Board.MID_POINT - 1), square, Direction.DOWN));
        assertFalse(Utils.isAdjacentInDirection(Square.getNormal(Board.MID_POINT, Board.MID_POINT + 1), square, Direction.DOWN));
        assertTrue(Utils.isAdjacentInDirection(Square.getNormal(Board.MID_POINT + 1, Board.MID_POINT), square, Direction.DOWN));
    }

    @Test
    public void testIsImmediatelyBefore() {
        Square square = Square.getNormal(Board.MID_POINT, Board.MID_POINT);
        assertTrue(Utils.isImmediatelyBefore(Square.getNormal(Board.MID_POINT - 1, Board.MID_POINT), square));
        assertTrue(Utils.isImmediatelyBefore(Square.getNormal(Board.MID_POINT, Board.MID_POINT - 1), square));
    }

    @Test
    public void testIsImmediatelyAfter() {
        Square square = Square.getNormal(Board.MID_POINT, Board.MID_POINT);
        assertTrue(Utils.isImmediatelyAfter(Square.getNormal(Board.MID_POINT, Board.MID_POINT + 1), square));
        assertTrue(Utils.isImmediatelyAfter(Square.getNormal(Board.MID_POINT + 1, Board.MID_POINT), square));
    }

    @Test
    public void testIsBefore() {
        Square square = Square.getNormal(Board.MID_POINT, Board.MID_POINT);
        assertTrue(Utils.isBefore(Square.getNormal(Board.MID_POINT - 1, Board.MID_POINT), square));
        assertTrue(Utils.isBefore(Square.getNormal(Board.MID_POINT, Board.MID_POINT - 1), square));
    }

    @Test
    public void testIsAfter() {
        Square square = Square.getNormal(Board.MID_POINT, Board.MID_POINT);
        assertTrue(Utils.isAfter(Square.getNormal(Board.MID_POINT, Board.MID_POINT + 1), square));
        assertTrue(Utils.isAfter(Square.getNormal(Board.MID_POINT + 1, Board.MID_POINT), square));
    }

}
