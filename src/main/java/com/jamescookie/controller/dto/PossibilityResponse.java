package com.jamescookie.controller.dto;

import com.jamescookie.scrabble.Possibility;
import io.micronaut.core.annotation.ReflectiveAccess;
import io.micronaut.serde.annotation.Serdeable;

@Serdeable
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
                possibility.score(),
                possibility.letters(),
                possibility.startPoint().getCol(),
                possibility.startPoint().getRow(),
                possibility.direction().toString()
        );
    }
}
