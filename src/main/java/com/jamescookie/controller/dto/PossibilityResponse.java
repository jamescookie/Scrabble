package com.jamescookie.controller.dto;

import com.jamescookie.scrabble.Possibility;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.ReflectiveAccess;

@Introspected
@ReflectiveAccess
public record PossibilityResponse(
        int score,
        String letters,
        int x,
        int y,
        String direction
) {
    public static PossibilityResponse from(Possibility possibility) {
        return new PossibilityResponse(
                possibility.getScore(),
                possibility.getLetters(),
                possibility.getStartPoint().getCol(),
                possibility.getStartPoint().getRow(),
                possibility.getDirection().toString()
        );
    }
}
