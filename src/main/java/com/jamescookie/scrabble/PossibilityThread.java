package com.jamescookie.scrabble;

import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.HashSet;

/**
 * @author ukjamescook
 */
@RequiredArgsConstructor
public class PossibilityThread extends Thread {
    private final Collection<Possibility> possibilities = new HashSet<>();
    private final Collection<String> words;
    private final boolean[] rowsToTry;
    private final boolean [] colsToTry;
    private final Board board;
    private boolean stop = false;

    public void run() {
        Square square;
        Direction direction;
        for (String word : words) {
            for (int row = 0; row < Board.BOARD_SIZE; row++) {
                for (int col = 0; col < Board.BOARD_SIZE; col++) {
                    if (rowsToTry[row] || colsToTry[col]) {
                        try {
                            square = board.getSquare(row, col);
                        } catch (ScrabbleException e) {
                            // this would be catastrophic if this happens, but it won't!
                            continue;
                        }
                        direction = Direction.ACROSS;
                        try {
                            possibilities.add(new Possibility(
                                board.putLetters(word, square, direction),
                                word,
                                square,
                                direction
                            ));
                        } catch (ScrabbleException e) {
                            // do nothing...invalid word
                        }
                        direction = Direction.DOWN;
                        try {
                            possibilities.add(new Possibility(
                                board.putLetters(word, square, direction),
                                word,
                                square,
                                direction
                            ));
                        } catch (ScrabbleException e) {
                            // do nothing...invalid word
                        }
                    }
                    if (stop) {
                        break;
                    }
                }
                if (stop) {
                    break;
                }
            }
            if (stop) {
                break;
            } else {
                Thread.yield();
            }
        }
    }

    public void stopProcessing() {
        stop = true;
    }

    public Collection<Possibility> getPossibilities() {
        return possibilities;
    }
}
