package com.jamescookie.scrabble;

import java.awt.Color;

/**
 * @author ukjamescook
 */
public class Letter {
    private final char c;
    private int score;
    private Color backgroundColour;
    private Color textColour;

    public Letter(char c) {
        this.c = c;
        init(c);
    }

    public char getCharacter() {
        return c;
    }

    public boolean isWildcard() {
        return false;
    }

    public int getScore() {
        return score;
    }

    public Color getBackgroundColour() {
        return backgroundColour;
    }

    public Color getTextColour() {
        return textColour;
    }

    private void init(char c) {
        backgroundColour = new Color(251, 159, 26);
        textColour = Color.BLACK;
        switch (c) {
            case 'q': case 'z': case 'j':
                score = 10;
                break;
            case 'x':
                score = 8;
                break;
            case 'v': case 'k':
                score = 5;
                break;
            case 'm':case 'c':case 'p':case 'b':case 'w':case 'f':
                score = 4;
                break;
            case 'g':case 'y':case 'h':
                score = 3;
                break;
            case 'u':case 'd':case 'n':case 'l':
                score = 2;
                break;
            case 'a': case 'e': case 'i': case 'o': case 'r': case 's': case 't':
                score = 1;
                break;
            case Utils.WILDCARD:
                score = 0;
                break;
            default:
                throw new IllegalArgumentException("Unknown char - "+c);
        }
    }

    public String toString() {
        return Character.toString(getCharacter()).toUpperCase();
    }
}
