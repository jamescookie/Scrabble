package com.jamescookie.scrabble;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import static com.jamescookie.scrabble.Board.MID_POINT;

/**
 * @author ukjamescook
 */
@Getter
@ToString
@EqualsAndHashCode
public class Square {
    private final int letterMod;
    private final int wordMod;
    private final int row;
    private final int col;
    private Letter letter;

    public static Square getNormal(int row, int col) {
        return new Square(1, 1, row, col);
    }

    public static Square getTripleWord(int row, int col) {
        return new Square(1, 3, row, col);
    }

    public static Square getDoubleWord(int row, int col) {
        return new Square(1, 2, row, col);
    }

    public static Square getTripleLetter(int row, int col) {
        return new Square(3, 1, row, col);
    }

    public static Square getDoubleLetter(int row, int col) {
        return new Square(2, 1, row, col);
    }

    public static Square from(Square square) {
        return new Square(square.letterMod, square.wordMod, square.row, square.col);
    }

    private Square(int letterMod, int wordMod, int row, int col) {
        this.letterMod = letterMod;
        this.wordMod = wordMod;
        this.row = row;
        this.col = col;
    }

    public void setLetter(Letter letter) throws ScrabbleException {
        if (hasLetter()) {
            throw new ScrabbleException("Cannot set letter "+letter +" in "+this);
        }
        changeLetter(letter);
    }

    public void changeLetter(Letter letter) {
        this.letter = letter;
    }

    public void clearLetter() {
        letter = null;
    }

    public WordScore getScore(Letter letter, WordScore wordScore) {
        wordScore.addToScore(letterMod * letter.getScore());
        wordScore.setModifier(wordMod);
        return wordScore;
    }

    public WordScore getScore(WordScore wordScore) {
        wordScore.addToScore(letter.getScore());
        return wordScore;
    }

    public char getCharacter() {
        return letter.getCharacter();
    }

    public boolean hasLetter() {
        return letter != null;
    }

    public boolean equivalentMods(Square square) {
        if (letterMod != square.letterMod) return false;
        //noinspection RedundantIfStatement
        if (wordMod != square.wordMod) return false;

        return true;
    }

    public String asClass() {
        if (row == MID_POINT && col == MID_POINT) {
            return "START DW";
        }
        if (letterMod == 2) {
            return "DL";
        }
        if (letterMod == 3) {
            return "TL";
        }
        if (wordMod == 2) {
            return "DW";
        }
        if (wordMod == 3) {
            return "TW";
        }
        return "";
    }

}
