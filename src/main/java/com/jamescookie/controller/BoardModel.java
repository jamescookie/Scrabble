package com.jamescookie.controller;

import com.jamescookie.scrabble.Square;

import io.micronaut.core.annotation.Introspected;

@Introspected
public class BoardModel {
    private final Square[][] squares;

    public BoardModel(Square[][] squares) {
        this.squares = squares;
    }

    public Square[][] getSquares() {
        return squares;
    }
}
