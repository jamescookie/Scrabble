package com.jamescookie.scrabble;

public record Possibility(int score, String letters, Square startPoint, Direction direction) {
}
