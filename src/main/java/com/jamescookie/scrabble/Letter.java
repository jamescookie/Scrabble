package com.jamescookie.scrabble;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author ukjamescook
 */
@RequiredArgsConstructor
public class Letter {
    private final char c;
    @Getter
    private final int score;

    public char getCharacter() {
        return c;
    }

    public boolean isWildcard() {
        return false;
    }

    public String toString() {
        return Character.toString(getCharacter()).toUpperCase();
    }
}
