package com.jamescookie.scrabble;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/**
 * @author ukjamescook
 */
public class Bag {
    private static Map<Character, Letter> letters = new HashMap<Character, Letter>();

    static {
        for (char i = 'a'; i <= 'z'; i++) {
            letters.put(i, new Letter(i));
        }
    }

    public Letter getLetter(char c) {
        return letters.get(c);
    }

    public List<Letter> getLetters(String word) {
        ArrayList<Letter> letters = new ArrayList<Letter>();

        if (word != null) {
            char[] chars = word.toLowerCase().toCharArray();
            for (char c : chars) {
                letters.add(getLetter(c));
            }
        }

        return letters;
    }
}
