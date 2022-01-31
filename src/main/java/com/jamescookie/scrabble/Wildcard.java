package com.jamescookie.scrabble;

/**
 * @author ukjamescook
 */
public class Wildcard extends Letter {
    private final char c;

    public Wildcard(char c) {
        super(Utils.WILDCARD);
        this.c = c;
    }

    public char getCharacter() {
        return c;
    }

    public boolean isWildcard() {
        return true;
    }
}
