package com.jamescookie.scrabble;

import static com.jamescookie.scrabble.Board.MID_POINT;

/**
 * @author ukjamescook
 */
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

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return col;
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

    public Letter getLetter() {
        return letter;
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

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Square square = (Square) o;

        if (!equivalentMods(square)) return false;
        if (col != square.col) return false;
        //noinspection RedundantIfStatement
        if (row != square.row) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = letterMod;
        result = 29 * result + wordMod;
        result = 29 * result + row;
        result = 29 * result + col;
        return result;
    }

    public String toString() {
        return "Square [" +
                "row=" + (row + 1) +
                ", col=" + (col + 1) +
                "]" +
                (hasLetter() ? " contains " + letter : "");
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
