package com.jamescookie.scrabble.types;

import com.jamescookie.scrabble.Letter;
import com.jamescookie.scrabble.Wildcard;

import java.util.*;

import static com.jamescookie.scrabble.Utils.WILDCARD;

public abstract class Type {
    private final List<Letter> letters = new ArrayList<>();

    public List<Letter> getAllLetters() {
        return new ArrayList<>(letters);
    }

    protected void put(char c, int amount, int score) {
        for (int i = 0; i < amount; i++) {
            if (c == WILDCARD) {
                letters.add(new Wildcard(c));
            } else {
                letters.add(new Letter(c, score));
            }
        }
    }

}
