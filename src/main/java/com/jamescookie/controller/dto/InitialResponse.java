package com.jamescookie.controller.dto;

import com.jamescookie.scrabble.*;
import io.micronaut.core.annotation.Introspected;
import lombok.Data;

import java.util.List;
import java.util.Map;
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
    private final Map<Character, Integer> letters;

    public InitialResponse(Board board) {
        List<Letter> letters = board.getBag().lettersLeft();
        this.remaining = letters.stream().map(Letter::getCharacter).collect(Collectors.toList());
        this.squares = new String[BOARD_SIZE][BOARD_SIZE];
        this.letters = letters.stream().collect(Collectors.toMap(Letter::getCharacter, Letter::getScore, (l1, l2)-> l1));
        Square[][] entireBoard = board.getEntireBoard();
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                this.squares[i][j] = entireBoard[i][j].asClass();
            }
        }
    }
}
