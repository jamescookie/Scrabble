package com.jamescookie.scrabble.utils;

import com.jamescookie.scrabble.Board;
import com.jamescookie.scrabble.Square;
import com.jamescookie.scrabble.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;

public class TestUtils {

    public static String boardToString(Board board) {
        StringBuilder sb = new StringBuilder();
        for (Square[] row : board.getEntireBoard()) {
            sb.append(Board.getCharactersFromSquares(row, false)).append("\n");
        }
        return sb.toString();
    }

    public static List<Square> getOccupiedSquares(Board board) {
        List<Square> squares = new ArrayList<>();

        for (Square[] row : board.getEntireBoard()) {
            for (Square square : row) {
                if (square.hasLetter()) {
                    squares.add(square);
                }
            }
        }

        return squares;
    }

    public static String getCharactersFromBoard(Board board) {
        return getOccupiedSquares(board)
                .stream()
                .map(square -> square.getLetter().isWildcard() ? Utils.WILDCARD : square.getCharacter())
                .collect(Collector.of(
                        StringBuilder::new,
                        StringBuilder::append,
                        StringBuilder::append,
                        StringBuilder::toString));
    }

}
