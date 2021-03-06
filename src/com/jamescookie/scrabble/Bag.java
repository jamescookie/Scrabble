package com.jamescookie.scrabble;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ukjamescook
 */
public class Bag {
    private static final Map<Character, Letter> letters = new HashMap<Character, Letter>();

    static {
        for (char i = 'a'; i <= 'z'; i++) {
            letters.put(i, new Letter(i));
        }
    }

    private Bag() {}

    public static Letter getLetter(char c) {
        return letters.get(c);
    }

    public static List<Letter> getLetters(String word) {
        ArrayList<Letter> letters = new ArrayList<Letter>();
        boolean wildcardFound = false;

        if (word != null) {
            char[] chars = word.toLowerCase().toCharArray();
            for (char c : chars) {
                if (wildcardFound) {
                    wildcardFound = false;
                    letters.add(new Wildcard(c));
                } else {
                    if (c == Utils.WILDCARD) {
                        wildcardFound = true;
                    } else {
                        letters.add(Bag.getLetter(c));
                    }
                }
            }
        }

        return letters;
    }
}
