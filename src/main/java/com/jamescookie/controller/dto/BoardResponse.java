package com.jamescookie.controller.dto;

import com.jamescookie.scrabble.Board;
import io.micronaut.core.annotation.Introspected;
import lombok.Data;

import java.util.Collection;

@Data
@Introspected
public class BoardResponse {
    private final String board;
    private Collection<PossibilityResponse> results;

    public BoardResponse(Board board) {
        this.board = board.asString();
    }
}
