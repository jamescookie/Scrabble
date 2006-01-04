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
        switch (c) {
            case 'q': case 'z':
                backgroundColour = new Color(0, 117, 0);
                textColour = Color.WHITE;
                score = 12;
                break;
            case 'j': case 'v': case 'x':
                backgroundColour = new Color(148, 46, 0);
                textColour = Color.WHITE;
                score = 7;
                break;
            case 'k':
                backgroundColour = new Color(242, 95, 0);
                textColour = Color.WHITE;
                score = 5;
                break;
            case 'b': case 'g': case 'p':
                backgroundColour = new Color(245, 188, 134);
                textColour = Color.BLACK;
                score = 3;
                break;
            case 'c': case 'f': case 'm': case 'u': case 'w': case 'y':
                backgroundColour = new Color(242, 242, 142);
                textColour = Color.BLACK;
                score = 2;
                break;
            case 'a': case 'd': case 'e': case 'h': case 'i': case 'l': case 'n': case 'o': case 'r': case 's': case 't':
                backgroundColour = Color.WHITE;
                textColour = Color.BLACK;
                score = 1;
                break;
            case Utils.WILDCARD:
                backgroundColour = Color.BLACK;
                textColour = Color.WHITE;
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
