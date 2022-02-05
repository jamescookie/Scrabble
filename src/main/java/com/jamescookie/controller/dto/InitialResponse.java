package com.jamescookie.controller.dto;

import com.jamescookie.scrabble.*;
import io.micronaut.core.annotation.Introspected;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

import static com.jamescookie.scrabble.Board.BOARD_SIZE;
import static com.jamescookie.scrabble.TwoLetters.TWO_LETTER_WORDS;
import static com.jamescookie.scrabble.Utils.WILDCARD;

@Data
@Introspected
public class InitialResponse {
    private final int size = BOARD_SIZE;
    private final char wildcard = WILDCARD;
    private final String[] twoLetterWords = TWO_LETTER_WORDS.toArray(new String[] {});
    private final List<Character> remaining;
    private final String[][] squares;

    public InitialResponse(Board board) {
        this.remaining = board.getBag().lettersLeft().stream().map(Letter::getCharacter).collect(Collectors.toList());
        this.squares = new String[BOARD_SIZE][BOARD_SIZE];
        Square[][] entireBoard = board.getEntireBoard();
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                this.squares[i][j] = entireBoard[i][j].asClass();
            }
        }
    }
}
