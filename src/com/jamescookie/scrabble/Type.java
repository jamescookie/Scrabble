package com.jamescookie.scrabble;

import java.util.Map;
import java.util.TreeMap;

public abstract class Type {
    final Map<Character, Integer> LETTER_OCCURANCES = new TreeMap<Character, Integer>();

    Type() {
        LETTER_OCCURANCES.put('a', 7);
        LETTER_OCCURANCES.put('b', 2);
        LETTER_OCCURANCES.put('c', 3);
        LETTER_OCCURANCES.put('d', 4);
        LETTER_OCCURANCES.put('f', 3);
        LETTER_OCCURANCES.put('g', 2);
        LETTER_OCCURANCES.put('h', 6);
        LETTER_OCCURANCES.put('i', 7);
        LETTER_OCCURANCES.put('j', 1);
        LETTER_OCCURANCES.put('k', 1);
        LETTER_OCCURANCES.put('l', 4);
        LETTER_OCCURANCES.put('m', 2);
        LETTER_OCCURANCES.put('n', 6);
        LETTER_OCCURANCES.put('o', 6);
        LETTER_OCCURANCES.put('p', 2);
        LETTER_OCCURANCES.put('q', 1);
        LETTER_OCCURANCES.put('r', 6);
        LETTER_OCCURANCES.put('s', 6);
        LETTER_OCCURANCES.put('t', 8);
        LETTER_OCCURANCES.put('u', 3);
        LETTER_OCCURANCES.put('v', 1);
        LETTER_OCCURANCES.put('w', 2);
        LETTER_OCCURANCES.put('x', 1);
        LETTER_OCCURANCES.put('y', 2);
        LETTER_OCCURANCES.put('z', 1);
    }

    public Map<Character, Integer> getLetterOccurances() {
        return LETTER_OCCURANCES;
    }

}
