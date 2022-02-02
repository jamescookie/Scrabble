package com.jamescookie.controller.dto;

import com.jamescookie.scrabble.Board;
import com.jamescookie.scrabble.RemainingLetters;
import com.jamescookie.scrabble.Square;
import com.jamescookie.scrabble.TypeNormal;
import io.micronaut.core.annotation.Introspected;
import lombok.Data;

import static com.jamescookie.scrabble.Board.BOARD_SIZE;
import static com.jamescookie.scrabble.TwoLetters.TWO_LETTER_WORDS;
import static com.jamescookie.scrabble.Utils.WILDCARD;

@Data
@Introspected
public class InitialResponse {
    private final int size = BOARD_SIZE;
    private final char wildcard = WILDCARD;
    private final String[] twoLetterWords = TWO_LETTER_WORDS.toArray(new String[] {});
    private final char[] remaining;
    private final String[][] squares;

    public InitialResponse(Board board) {
        this.remaining = RemainingLetters.lettersLeft(board.getCharactersFromBoard().toCharArray(), new TypeNormal());
        this.squares = new String[BOARD_SIZE][BOARD_SIZE];
        Square[][] entireBoard = board.getEntireBoard();
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                this.squares[i][j] = entireBoard[i][j].asClass();
            }
        }
    }
}
