package com.jamescookie.controller.dto;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record AddRequest(String board, PossibilityResponse add) {
}
