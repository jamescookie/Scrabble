package com.jamescookie.controller.dto;

import com.jamescookie.scrabble.*;
import io.micronaut.core.annotation.Introspected;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.jamescookie.scrabble.Board.BOARD_SIZE;
import static com.jamescookie.scrabble.TwoLetters.TWO_LETTER_WORDS;
import static com.jamescookie.scrabble.Utils.WILDCARD;

import io.micronaut.core.annotation.ReflectiveAccess;

@Introspected
@ReflectiveAccess
public record InitialResponse(
        int size,
        char wildcard,
        String[] twoLetterWords,
        List<Character> remaining,
        String[][] squares,
        Map<Character, Integer> letters
) {
    public InitialResponse(Board board) {
        this(
                BOARD_SIZE,
                WILDCARD,
                TWO_LETTER_WORDS.toArray(new String[] {}),
                board.getBag().lettersLeft().stream().map(Letter::getCharacter).toList(),
                initializeSquares(board),
                board.getBag().lettersLeft().stream()
                        .collect(Collectors.toMap(Letter::getCharacter, Letter::getScore, (l1, l2) -> l1))
        );
    }

    private static String[][] initializeSquares(Board board) {
        String[][] squares = new String[BOARD_SIZE][BOARD_SIZE];
        Square[][] entireBoard = board.getEntireBoard();
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                squares[i][j] = entireBoard[i][j].asClass();
            }
        }
        return squares;
    }
}
