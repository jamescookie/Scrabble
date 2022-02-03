package com.jamescookie.scrabble;

/**
 * @author ukjamescook
 */
public class Wildcard extends Letter {
    private char c;

    public Wildcard(char c) {
        super(Utils.WILDCARD, 0);
        this.c = c;
    }

    public char getCharacter() {
        return c;
    }

    public Wildcard setCharacter(char c) {
        this.c = c;
        return this;
    }

    public boolean isWildcard() {
        return true;
    }
}
