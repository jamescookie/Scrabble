package com.jamescookie.scrabble;

/**
 * @author ukjamescook
 */
public class Square {
    private final int letterMod;
    private final int wordMod;
    private final int row;
    private final int col;

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

    private Square(int letterMod, int wordMod, int row, int col) {
        this.letterMod = letterMod;
        this.wordMod = wordMod;
        this.row = row;
        this.col = col;
    }

    public int getLetterModifier() {
        return letterMod;
    }

    public int getWordModifier() {
        return wordMod;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return col;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Square square = (Square) o;

        if (letterMod != square.letterMod) return false;
        if (wordMod != square.wordMod) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = letterMod;
        result = 29 * result + wordMod;
        return result;
    }


    public String toString() {
        return "Square{" +
                "letterMod=" + letterMod +
                ", wordMod=" + wordMod +
                ", row=" + row +
                ", col=" + col +
                '}';
    }
}
