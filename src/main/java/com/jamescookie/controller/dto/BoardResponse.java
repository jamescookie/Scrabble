package com.jamescookie.controller.dto;

import com.jamescookie.scrabble.Board;
import io.micronaut.core.annotation.Introspected;
import lombok.Data;

import java.util.Collection;

import static com.jamescookie.scrabble.Board.BOARD_SIZE;

@Data
@Introspected
public class BoardResponse {
    private final int size = BOARD_SIZE;
    private final String board;
    private Collection<PossibilityResponse> results;

    public BoardResponse(Board board) {
        this.board = board.asString();
    }
}
