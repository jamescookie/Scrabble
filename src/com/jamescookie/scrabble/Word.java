package com.jamescookie.scrabble;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author ukjamescook
 */
public class Word {
    private final Direction direction;
    private final List<Square> squares;
    private List<Letter> letters;
    static final String EXPORT_SEPERATOR = "#";

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

    public int getLength() {
        return squares.size();
    }

    public List<Square> getSquares() {
        return Collections.unmodifiableList(squares);
    }

    public Square getStartingPoint() {
        return squares.get(0);
    }

    public String export() {
        Square startingPoint = getStartingPoint();
        return getDirection().export() + EXPORT_SEPERATOR +
                startingPoint.getRow() + EXPORT_SEPERATOR +
                startingPoint.getColumn() + EXPORT_SEPERATOR +
                squares.size();
    }

    public static Word generate(String representation) throws ScrabbleException {
        StringTokenizer st = new StringTokenizer(representation, EXPORT_SEPERATOR);
        Direction d = Direction.generate(st.nextToken());
        int row = Integer.parseInt(st.nextToken());
        int col = Integer.parseInt(st.nextToken());
        int size = Integer.parseInt(st.nextToken());
        Square square = Board.getSquare(row, col);
        List<Square> squares = new ArrayList<Square>();
        squares.add(square);
        for (int i = 1; i < size; i++) {
            square = Board.findNextSquare(square, d);
            squares.add(square);
        }
        return new Word(d, squares);
    }

    public String toString() {
        return "Word{" +
                getWord() +
                '}';
    }
}
