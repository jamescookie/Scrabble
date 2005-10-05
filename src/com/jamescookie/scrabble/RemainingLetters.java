package com.jamescookie.scrabble;

import java.util.LinkedHashMap;

public class RemainingLetters {
    private static LinkedHashMap<Character, Integer> LETTER_OCCURANCES = new LinkedHashMap<Character, Integer>();

    static {
        LETTER_OCCURANCES.put('a', 7);
        LETTER_OCCURANCES.put('b', 2);
        LETTER_OCCURANCES.put('c', 3);
        LETTER_OCCURANCES.put('d', 4);
        LETTER_OCCURANCES.put('e', 11);
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
        LETTER_OCCURANCES.put(Utils.WILDCARD, 2);
    }

    public static String lettersLeft(String usedLetters) {
        LinkedHashMap<Character, Integer> localLetters = new LinkedHashMap<Character, Integer>(LETTER_OCCURANCES);
        String missing = "";
        String seperator = "\nThese extra LETTER_OCCURANCES could not be found: '";
        if (usedLetters != null) {
            char[] chars = usedLetters.toLowerCase().toCharArray();
            for (char c : chars) {
                if (localLetters.containsKey(c)) {
                    int score = localLetters.get(c);
                    if (score < 2) {
                        localLetters.remove(c);
                    } else {
                        localLetters.put(c, score - 1);
                    }
                } else {
                    missing += seperator + c + "'";
                    seperator = ", '";
                }
            }
        }
        String retValue = localLetters.toString();
        return "With the board LETTER_OCCURANCES '"+ usedLetters +"', the remaining LETTER_OCCURANCES are:\n" + retValue.substring(1, retValue.length() - 1) + missing;
    }

}
