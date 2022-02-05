package com.jamescookie.scrabble.utils;

import com.jamescookie.scrabble.Board;
import com.jamescookie.scrabble.Square;

public class TestUtils {

    public static String boardToString(Board board) {
        StringBuilder sb = new StringBuilder();
        for (Square[] row : board.getEntireBoard()) {
            sb.append(Board.getCharactersFromSquares(row, false)).append("\n");
        }
        return sb.toString();

    }
}
