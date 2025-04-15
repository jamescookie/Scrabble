package com.jamescookie.controller.dto;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record PossibilityRequest(String board, String letters, int secondsToWait, int numberOfPossibilities) {
}
