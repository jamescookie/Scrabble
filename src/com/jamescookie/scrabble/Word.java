package com.jamescookie.scrabble;

/**
 * @author ukjamescook
 */
public class Word {
    private String word;
    private Square startPoint;
    private final Direction direction;

    public Word(String word, Square startPoint, Direction direction) {
        this.word = word;
        this.startPoint = startPoint;
        this.direction = direction;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Square getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(Square startPoint) {
        this.startPoint = startPoint;
    }

    public Direction getDirection() {
        return direction;
    }
}
