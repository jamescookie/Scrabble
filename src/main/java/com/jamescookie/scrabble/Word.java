package com.jamescookie.scrabble;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author ukjamescook
 */
@RequiredArgsConstructor
@Getter
@ToString
public class Word {
    private final Direction direction;
    private final List<Square> squares;
    private List<Letter> letters;
    static final String EXPORT_SEPARATOR = "#";

    public List<Letter> getLetters() {
        if (letters == null) {
            letters = new ArrayList<>();
            for (Square square : squares) {
                letters.add(square.getLetter());
            }
        }
        return letters;
    }

    public String getWord() {
        List<Letter> letters = getLetters();
        StringBuilder chars = new StringBuilder();
        for (Letter letter : letters) {
            chars.append(letter.getCharacter());
        }
        return chars.toString();
    }

    public int getLength() {
        return squares.size();
    }

    public List<Square> getSquares() {
        return Collections.unmodifiableList(squares);
    }

    public Square getStartingPoint() {
        return squares.getFirst();
    }

    public String export() {
        Square startingPoint = getStartingPoint();
        return getDirection().export() + EXPORT_SEPARATOR +
                startingPoint.getRow() + EXPORT_SEPARATOR +
                startingPoint.getCol() + EXPORT_SEPARATOR +
                squares.size();
    }

    public static Word generate(String representation, Board board) throws ScrabbleException {
        StringTokenizer st = new StringTokenizer(representation, EXPORT_SEPARATOR);
        Direction d = Direction.generate(st.nextToken());
        int row = Integer.parseInt(st.nextToken());
        int col = Integer.parseInt(st.nextToken());
        int size = Integer.parseInt(st.nextToken());
        Square square = board.getSquare(row, col);
        List<Square> squares = new ArrayList<>();
        squares.add(square);
        for (int i = 1; i < size; i++) {
            square = board.findNextSquare(square, d);
            squares.add(square);
        }
        return new Word(d, squares);
    }
}
