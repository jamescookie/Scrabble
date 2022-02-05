package com.jamescookie.controller.dto;

import com.jamescookie.scrabble.*;
import io.micronaut.core.annotation.Introspected;
import lombok.Data;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Introspected
public class BoardResponse {
    private final String board;
    private final List<Character> remaining;
    private Collection<PossibilityResponse> results;

    public BoardResponse(Board board) {
        this.board = board.exportBoard();
        this.remaining = board.getBag().lettersLeft().stream().map(Letter::getCharacter).collect(Collectors.toList());
    }
}
