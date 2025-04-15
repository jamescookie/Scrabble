package com.jamescookie.controller.dto;

import com.jamescookie.scrabble.Board;
import com.jamescookie.scrabble.Letter;
import io.micronaut.core.annotation.ReflectiveAccess;
import io.micronaut.serde.annotation.Serdeable;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Serdeable
@ReflectiveAccess
public record BoardResponse(
        String board,
        List<Character> remaining,
        Collection<PossibilityResponse> results
) {
    public BoardResponse(Board board, Collection<PossibilityResponse> results) {
        this(
                board.exportBoard(),
                board.getBag().lettersLeft().stream().map(Letter::getCharacter).collect(Collectors.toList()),
                results
        );
    }
}