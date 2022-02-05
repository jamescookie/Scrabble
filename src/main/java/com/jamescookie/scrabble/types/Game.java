package com.jamescookie.scrabble.types;

import com.jamescookie.scrabble.Square;

import static com.jamescookie.scrabble.Board.BOARD_SIZE;

public class Game {
    private static final Square[][] ITS_YOUR_TURN_LAYOUT = new Square[][] {
            {Square.getDoubleLetter(0, 0),  Square.getNormal(0, 1),         Square.getNormal(0, 2),         Square.getTripleWord(0, 3),         Square.getNormal(0, 4),         Square.getNormal(0, 5),         Square.getNormal(0, 6),         Square.getDoubleLetter(0, 7),   Square.getNormal(0, 8),         Square.getNormal(0, 9),         Square.getNormal(0, 10),         Square.getTripleWord(0, 11),     Square.getNormal(0, 12),         Square.getNormal(0, 13),         Square.getDoubleLetter(0, 14)},
            {Square.getNormal(1, 0),        Square.getNormal(1, 1),         Square.getDoubleWord(1, 2),     Square.getNormal(1, 3),             Square.getNormal(1, 4),         Square.getDoubleLetter(1, 5),   Square.getNormal(1, 6),         Square.getNormal(1, 7),         Square.getNormal(1, 8),         Square.getTripleLetter(1, 9),   Square.getNormal(1, 10),         Square.getNormal(1, 11),         Square.getDoubleWord(1, 12),     Square.getNormal(1, 13),         Square.getNormal(1, 14)},
            {Square.getNormal(2, 0),        Square.getDoubleWord(2, 1),     Square.getNormal(2, 2),         Square.getNormal(2, 3),             Square.getNormal(2, 4),         Square.getNormal(2, 5),         Square.getNormal(2, 6),         Square.getNormal(2, 7),         Square.getNormal(2, 8),         Square.getNormal(2, 9),         Square.getNormal(2, 10),         Square.getNormal(2, 11),         Square.getNormal(2, 12),         Square.getDoubleWord(2, 13),     Square.getNormal(2, 14)},
            {Square.getTripleWord(3, 0),    Square.getNormal(3, 1),         Square.getNormal(3, 2),         Square.getDoubleLetter(3, 3),       Square.getNormal(3, 4),         Square.getNormal(3, 5),         Square.getTripleLetter(3, 6),   Square.getNormal(3, 7),         Square.getDoubleLetter(3, 8),   Square.getNormal(3, 9),         Square.getNormal(3, 10),         Square.getDoubleLetter(3, 11),   Square.getNormal(3, 12),         Square.getNormal(3, 13),         Square.getTripleWord(3, 14)},
            {Square.getNormal(4, 0),        Square.getNormal(4, 1),         Square.getNormal(4, 2),         Square.getNormal(4, 3),             Square.getNormal(4, 4),         Square.getDoubleWord(4, 5),     Square.getNormal(4, 6),         Square.getNormal(4, 7),         Square.getNormal(4, 8),         Square.getDoubleWord(4, 9),     Square.getNormal(4, 10),         Square.getNormal(4, 11),         Square.getNormal(4, 12),         Square.getNormal(4, 13),         Square.getNormal(4, 14)},
            {Square.getNormal(5, 0),        Square.getTripleLetter(5, 1),   Square.getNormal(5, 2),         Square.getNormal(5, 3),             Square.getDoubleWord(5, 4),     Square.getNormal(5, 5),         Square.getNormal(5, 6),         Square.getNormal(5, 7),         Square.getNormal(5, 8),         Square.getNormal(5, 9),         Square.getDoubleWord(5, 10),     Square.getNormal(5, 11),         Square.getNormal(5, 12),         Square.getDoubleLetter(5, 13),   Square.getNormal(5, 14)},
            {Square.getNormal(6, 0),        Square.getNormal(6, 1),         Square.getNormal(6, 2),         Square.getDoubleLetter(6, 3),       Square.getNormal(6, 4),         Square.getNormal(6, 5),         Square.getDoubleLetter(6, 6),   Square.getNormal(6, 7),         Square.getDoubleLetter(6, 8),   Square.getNormal(6, 9),         Square.getNormal(6, 10),         Square.getTripleLetter(6, 11),   Square.getNormal(6, 12),         Square.getNormal(6, 13),         Square.getNormal(6, 14)},
            {Square.getDoubleLetter(7, 0),  Square.getNormal(7, 1),         Square.getNormal(7, 2),         Square.getNormal(7, 3),             Square.getNormal(7, 4),         Square.getNormal(7, 5),         Square.getNormal(7, 6),         Square.getDoubleWord(7, 7),     Square.getNormal(7, 8),         Square.getNormal(7, 9),         Square.getNormal(7, 10),         Square.getNormal(7, 11),         Square.getNormal(7, 12),         Square.getNormal(7, 13),         Square.getDoubleLetter(7, 14)},
            {Square.getNormal(8, 0),        Square.getNormal(8, 1),         Square.getNormal(8, 2),         Square.getTripleLetter(8, 3),       Square.getNormal(8, 4),         Square.getNormal(8, 5),         Square.getDoubleLetter(8, 6),   Square.getNormal(8, 7),         Square.getDoubleLetter(8, 8),   Square.getNormal(8, 9),         Square.getNormal(8, 10),         Square.getDoubleLetter(8, 11),   Square.getNormal(8, 12),         Square.getNormal(8, 13),         Square.getNormal(8, 14)},
            {Square.getNormal(9, 0),        Square.getDoubleLetter(9, 1),   Square.getNormal(9, 2),         Square.getNormal(9, 3),             Square.getDoubleWord(9, 4),     Square.getNormal(9, 5),         Square.getNormal(9, 6),         Square.getNormal(9, 7),         Square.getNormal(9, 8),         Square.getNormal(9, 9),         Square.getDoubleWord(9, 10),     Square.getNormal(9, 11),         Square.getNormal(9, 12),         Square.getTripleLetter(9, 13),   Square.getNormal(9, 14)},
            {Square.getNormal(10, 0),       Square.getNormal(10, 1),        Square.getNormal(10, 2),        Square.getNormal(10, 3),            Square.getNormal(10, 4),        Square.getDoubleWord(10, 5),    Square.getNormal(10, 6),        Square.getNormal(10, 7),        Square.getNormal(10, 8),        Square.getDoubleWord(10, 9),    Square.getNormal(10, 10),        Square.getNormal(10, 11),        Square.getNormal(10, 12),        Square.getNormal(10, 13),        Square.getNormal(10, 14)},
            {Square.getTripleWord(11, 0),   Square.getNormal(11, 1),        Square.getNormal(11, 2),        Square.getDoubleLetter(11, 3),      Square.getNormal(11, 4),        Square.getNormal(11, 5),        Square.getDoubleLetter(11, 6),  Square.getNormal(11, 7),        Square.getTripleLetter(11, 8),  Square.getNormal(11, 9),        Square.getNormal(11, 10),        Square.getDoubleLetter(11, 11),  Square.getNormal(11, 12),        Square.getNormal(11, 13),        Square.getTripleWord(11, 14)},
            {Square.getNormal(12, 0),       Square.getDoubleWord(12, 1),    Square.getNormal(12, 2),        Square.getNormal(12, 3),            Square.getNormal(12, 4),        Square.getNormal(12, 5),        Square.getNormal(12, 6),        Square.getNormal(12, 7),        Square.getNormal(12, 8),        Square.getNormal(12, 9),        Square.getNormal(12, 10),        Square.getNormal(12, 11),        Square.getNormal(12, 12),        Square.getDoubleWord(12, 13),    Square.getNormal(12, 14)},
            {Square.getNormal(13, 0),       Square.getNormal(13, 1),        Square.getDoubleWord(13, 2),    Square.getNormal(13, 3),            Square.getNormal(13, 4),        Square.getTripleLetter(13, 5),  Square.getNormal(13, 6),        Square.getNormal(13, 7),        Square.getNormal(13, 8),        Square.getDoubleLetter(13, 9),  Square.getNormal(13, 10),        Square.getNormal(13, 11),        Square.getDoubleWord(13, 12),    Square.getNormal(13, 13),        Square.getNormal(13, 14)},
            {Square.getDoubleLetter(14, 0), Square.getNormal(14, 1),        Square.getNormal(14, 2),        Square.getTripleWord(14, 3),        Square.getNormal(14, 4),        Square.getNormal(14, 5),        Square.getNormal(14, 6),        Square.getDoubleLetter(14, 7),  Square.getNormal(14, 8),        Square.getNormal(14, 9),        Square.getNormal(14, 10),        Square.getTripleWord(14, 11),    Square.getNormal(14, 12),        Square.getNormal(14, 13),        Square.getDoubleLetter(14, 14)},
    };
    private static final Square[][] TRADITIONAL_LAYOUT = new Square[][] {
            {Square.getTripleWord(0, 0),    Square.getNormal(0, 1),         Square.getNormal(0, 2),         Square.getDoubleLetter(0, 3),       Square.getNormal(0, 4),         Square.getNormal(0, 5),         Square.getNormal(0, 6),         Square.getTripleWord(0, 7),     Square.getNormal(0, 8),         Square.getNormal(0, 9),         Square.getNormal(0, 10),         Square.getDoubleLetter(0, 11),   Square.getNormal(0, 12),         Square.getNormal(0, 13),         Square.getTripleWord(0, 14)},
            {Square.getNormal(1, 0),        Square.getDoubleWord(1, 1),     Square.getNormal(1, 2),         Square.getNormal(1, 3),             Square.getNormal(1, 4),         Square.getTripleLetter(1, 5),   Square.getNormal(1, 6),         Square.getNormal(1, 7),         Square.getNormal(1, 8),         Square.getTripleLetter(1, 9),   Square.getNormal(1, 10),         Square.getNormal(1, 11),         Square.getNormal(1, 12),         Square.getDoubleWord(1, 13),     Square.getNormal(1, 14)},
            {Square.getNormal(2, 0),        Square.getNormal(2, 1),         Square.getDoubleWord(2, 2),     Square.getNormal(2, 3),             Square.getNormal(2, 4),         Square.getNormal(2, 5),         Square.getDoubleLetter(2, 6),   Square.getNormal(2, 7),         Square.getDoubleLetter(2, 8),   Square.getNormal(2, 9),         Square.getNormal(2, 10),         Square.getNormal(2, 11),         Square.getDoubleWord(2, 12),     Square.getNormal(2, 13),         Square.getNormal(2, 14)},
            {Square.getDoubleLetter(3, 0),  Square.getNormal(3, 1),         Square.getNormal(3, 2),         Square.getDoubleWord(3, 3),         Square.getNormal(3, 4),         Square.getNormal(3, 5),         Square.getNormal(3, 6),         Square.getDoubleLetter(3, 7),   Square.getNormal(3, 8),         Square.getNormal(3, 9),         Square.getNormal(3, 10),         Square.getDoubleWord(3, 11),     Square.getNormal(3, 12),         Square.getNormal(3, 13),         Square.getDoubleLetter(3, 14)},
            {Square.getNormal(4, 0),        Square.getNormal(4, 1),         Square.getNormal(4, 2),         Square.getNormal(4, 3),             Square.getDoubleWord(4, 4),     Square.getNormal(4, 5),         Square.getNormal(4, 6),         Square.getNormal(4, 7),         Square.getNormal(4, 8),         Square.getNormal(4, 9),         Square.getDoubleWord(4, 10),     Square.getNormal(4, 11),         Square.getNormal(4, 12),         Square.getNormal(4, 13),         Square.getNormal(4, 14)},
            {Square.getNormal(5, 0),        Square.getTripleLetter(5, 1),   Square.getNormal(5, 2),         Square.getNormal(5, 3),             Square.getNormal(5, 4),         Square.getTripleLetter(5, 5),   Square.getNormal(5, 6),         Square.getNormal(5, 7),         Square.getNormal(5, 8),         Square.getTripleLetter(5, 9),   Square.getNormal(5, 10),         Square.getNormal(5, 11),         Square.getNormal(5, 12),         Square.getTripleLetter(5, 13),   Square.getNormal(5, 14)},
            {Square.getNormal(6, 0),        Square.getNormal(6, 1),         Square.getDoubleLetter(6, 2),   Square.getNormal(6, 3),             Square.getNormal(6, 4),         Square.getNormal(6, 5),         Square.getDoubleLetter(6, 6),   Square.getNormal(6, 7),         Square.getDoubleLetter(6, 8),   Square.getNormal(6, 9),         Square.getNormal(6, 10),         Square.getNormal(6, 11),         Square.getDoubleLetter(6, 12),   Square.getNormal(6, 13),         Square.getNormal(6, 14)},
            {Square.getTripleWord(7, 0),    Square.getNormal(7, 1),         Square.getNormal(7, 2),         Square.getDoubleLetter(7, 3),       Square.getNormal(7, 4),         Square.getNormal(7, 5),         Square.getNormal(7, 6),         Square.getDoubleWord(7, 7),     Square.getNormal(7, 8),         Square.getNormal(7, 9),         Square.getNormal(7, 10),         Square.getDoubleLetter(7, 11),   Square.getNormal(7, 12),         Square.getNormal(7, 13),         Square.getTripleWord(7, 14)},
            {Square.getNormal(8, 0),        Square.getNormal(8, 1),         Square.getDoubleLetter(8, 2),   Square.getNormal(8, 3),             Square.getNormal(8, 4),         Square.getNormal(8, 5),         Square.getDoubleLetter(8, 6),   Square.getNormal(8, 7),         Square.getDoubleLetter(8, 8),   Square.getNormal(8, 9),         Square.getNormal(8, 10),         Square.getNormal(8, 11),         Square.getDoubleLetter(8, 12),   Square.getNormal(8, 13),         Square.getNormal(8, 14)},
            {Square.getNormal(9, 0),        Square.getTripleLetter(9, 1),   Square.getNormal(9, 2),         Square.getNormal(9, 3),             Square.getNormal(9, 4),         Square.getTripleLetter(9, 5),   Square.getNormal(9, 6),         Square.getNormal(9, 7),         Square.getNormal(9, 8),         Square.getTripleLetter(9, 9),   Square.getNormal(9, 10),         Square.getNormal(9, 11),         Square.getNormal(9, 12),         Square.getTripleLetter(9, 13),   Square.getNormal(9, 14)},
            {Square.getNormal(10, 0),       Square.getNormal(10, 1),        Square.getNormal(10, 2),        Square.getNormal(10, 3),            Square.getDoubleWord(10, 4),    Square.getNormal(10, 5),        Square.getNormal(10, 6),        Square.getNormal(10, 7),        Square.getNormal(10, 8),        Square.getNormal(10, 9),        Square.getDoubleWord(10, 10),    Square.getNormal(10, 11),        Square.getNormal(10, 12),        Square.getNormal(10, 13),        Square.getNormal(10, 14)},
            {Square.getDoubleLetter(11, 0), Square.getNormal(11, 1),        Square.getNormal(11, 2),        Square.getDoubleWord(11, 3),        Square.getNormal(11, 4),        Square.getNormal(11, 5),        Square.getNormal(11, 6),        Square.getDoubleLetter(11, 7),  Square.getNormal(11, 8),        Square.getNormal(11, 9),        Square.getNormal(11, 10),        Square.getDoubleWord(11, 11),    Square.getNormal(11, 12),        Square.getNormal(11, 13),        Square.getDoubleLetter(11, 14)},
            {Square.getNormal(12, 0),       Square.getNormal(12, 1),        Square.getDoubleWord(12, 2),    Square.getNormal(12, 3),            Square.getNormal(12, 4),        Square.getNormal(12, 5),        Square.getDoubleLetter(12, 6),  Square.getNormal(12, 7),        Square.getDoubleLetter(12, 8),  Square.getNormal(12, 9),        Square.getNormal(12, 10),        Square.getNormal(12, 11),        Square.getDoubleWord(12, 12),    Square.getNormal(12, 13),        Square.getNormal(12, 14)},
            {Square.getNormal(13, 0),       Square.getDoubleWord(13, 1),    Square.getNormal(13, 2),        Square.getNormal(13, 3),            Square.getNormal(13, 4),        Square.getTripleLetter(13, 5),  Square.getNormal(13, 6),        Square.getNormal(13, 7),        Square.getNormal(13, 8),        Square.getTripleLetter(13, 9),  Square.getNormal(13, 10),        Square.getNormal(13, 11),        Square.getNormal(13, 12),        Square.getDoubleWord(13, 13),    Square.getNormal(13, 14)},
            {Square.getTripleWord(14, 0),   Square.getNormal(14, 1),        Square.getNormal(14, 2),        Square.getDoubleLetter(14, 3),      Square.getNormal(14, 4),        Square.getNormal(14, 5),        Square.getNormal(14, 6),        Square.getTripleWord(14, 7),    Square.getNormal(14, 8),        Square.getNormal(14, 9),        Square.getNormal(14, 10),        Square.getDoubleLetter(14, 11),  Square.getNormal(14, 12),        Square.getNormal(14, 13),        Square.getTripleWord(14, 14)},
    };

    private final Bag bag;
    private final Square[][] squares;

    private Game(Bag bag, Square[][] squares) {
        this.bag = bag;
        this.squares = new Square[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                this.squares[i][j] = Square.from(squares[i][j]);
            }
        }
    }

    public static Game normal() {
        return new Game(new Bag(new TypeNormal()), TRADITIONAL_LAYOUT);
    }

    public static Game itsYourTurn() {
        return new Game(new Bag(new ItsYourTurnTypeNormal()), ITS_YOUR_TURN_LAYOUT);
    }

    public static Game itsYourTurnWild() {
        return new Game(new Bag(new ItsYourTurnTypeWild()), ITS_YOUR_TURN_LAYOUT);
    }

    public static Game testing() {
        return new Game(new Bag(new TestingType()), ITS_YOUR_TURN_LAYOUT);
    }

    public Bag getBag() {
        return bag;
    }

    public Square[][] getSquares() {
        return squares;
    }
}
