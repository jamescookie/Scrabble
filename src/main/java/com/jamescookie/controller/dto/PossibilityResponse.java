package com.jamescookie.controller.dto;

import com.jamescookie.scrabble.Possibility;
import io.micronaut.core.annotation.Introspected;
import lombok.Data;

@Data
@Introspected
public class PossibilityResponse {
    private final int score;
    private final String letters;
    private final int x;
    private final int y;
    private final String direction;

    public static PossibilityResponse from(Possibility possibility) {
        return new PossibilityResponse(possibility.getScore(), possibility.getLetters(), possibility.getStartPoint().getCol(), possibility.getStartPoint().getRow(), possibility.getDirection().toString());
    }
}
