package com.jamescookie.scrabble;

import java.util.List;
import java.util.Collections;
import java.util.ArrayList;

/**
 * @author ukjamescook
 */
public class Word {
    private Direction direction;
    private List<Square> squares;
    private List<Letter> letters;

    public Word(Direction direction, List<Square> squares) {
        this.direction = direction;
        this.squares = squares;
    }

    public List<Letter> getLetters() {
        if (letters == null) {
            letters = new ArrayList<Letter>();
            for (Square square : squares) {
                letters.add(square.getLetter());
            }
        }
        return letters;
    }

    public String getWord() {
        List<Letter> letters = getLetters();
        StringBuffer chars = new StringBuffer();
        for (Letter letter : letters) {
            chars.append(letter.getCharacter());
        }
        return chars.toString();
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getLength() {
        return squares.size();
    }

    public List<Square> getSquares() {
        return Collections.unmodifiableList(squares);
    }

    public void addSquare(Square square) {
        letters = null;
        if (Utils.isAdjacent(getStartingPoint(), square)) {
            squares.add(0, square);
        } else {
            squares.add(square);
        }
    }

    public Square getStartingPoint() {
        return squares.get(0);
    }


    public String toString() {
        return "Word{" +
                getWord() +
                '}';
    }
}
