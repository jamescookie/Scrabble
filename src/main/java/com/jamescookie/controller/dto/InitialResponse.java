package com.jamescookie.controller.dto;

import com.jamescookie.scrabble.Board;
import com.jamescookie.scrabble.Square;
import io.micronaut.core.annotation.Introspected;
import lombok.Data;

import static com.jamescookie.scrabble.Board.BOARD_SIZE;

@Data
@Introspected
public class InitialResponse {
    private final int size = BOARD_SIZE;
    private final String board;
    private final String[][] squares;

    public InitialResponse(Board board) {
        this.board = board.asString();
        this.squares = new String[BOARD_SIZE][BOARD_SIZE];
        Square[][] entireBoard = board.getEntireBoard();
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                this.squares[i][j] = entireBoard[i][j].asClass();
            }
        }
    }
}
