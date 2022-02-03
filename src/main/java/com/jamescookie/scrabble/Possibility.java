package com.jamescookie.scrabble;

import lombok.Data;

/**
 * @author ukjamescook
 */
@Data
public class Possibility {
    private final int score;
    private final String letters;
    private final Square startPoint;
    private final Direction direction;
}
