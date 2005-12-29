package com.jamescookie.scrabble;

/**
 * @author ukjamescook
 */
public class Letter {
    private final char c;
    private int score;

    public Letter(char c) {
        this.c = c;
        setScore(getScore(c));
    }

    public char getCharacter() {
        return c;
    }

    public int getScore() {
        return score;
    }

    private void setScore(int score) {
        this.score = score;
    }

    private static int getScore(char c) {
        int score = 0;
        switch (c) {
            case 'q': case 'z':
                score = 12;
                break;
            case 'j': case 'v': case 'x':
                score = 7;
                break;
            case 'k':
                score = 5;
                break;
            case 'b': case 'g': case 'p':
                score = 3;
                break;
            case 'c': case 'f': case 'm': case 'u': case 'w': case 'y':
                score = 2;
                break;
            case 'a': case 'd': case 'e': case 'h': case 'i': case 'l': case 'n': case 'o': case 'r': case 's': case 't':
                score = 1;
                break;
            case Utils.WILDCARD:
                score = 0;
                break;
            default:
                throw new IllegalArgumentException("Unknown char - "+c);
        }
        return score;
    }


    public String toString() {
        return "Letter{" +
                "char=" + c +
                ", score=" + score +
                '}';
    }
}
