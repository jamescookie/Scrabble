package com.jamescookie.controller.dto;

import com.jamescookie.scrabble.Board;
import com.jamescookie.scrabble.RemainingLetters;
import com.jamescookie.scrabble.Type;
import com.jamescookie.scrabble.TypeNormal;
import io.micronaut.core.annotation.Introspected;
import lombok.Data;

import java.util.Collection;

@Data
@Introspected
public class BoardResponse {
    private final String board;
    private final char[] remaining;
    private Collection<PossibilityResponse> results;

    public BoardResponse(Board board) {
        this.board = board.asString();
        this.remaining = RemainingLetters.lettersLeft(board.getCharactersFromBoard().toCharArray(), new TypeNormal());
    }
}
