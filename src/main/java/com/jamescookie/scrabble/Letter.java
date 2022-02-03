package com.jamescookie.scrabble;

import lombok.RequiredArgsConstructor;

/**
 * @author ukjamescook
 */
@RequiredArgsConstructor
public class Letter {
    private final char c;
    private final int score;

    public char getCharacter() {
        return c;
    }

    public boolean isWildcard() {
        return false;
    }

    public int getScore() {
        return score;
    }

    public String toString() {
        return Character.toString(getCharacter()).toUpperCase();
    }
}
