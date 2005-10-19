package com.jamescookie.scrabble;

import java.util.Map;

public class RemainingLetters {
    public static String lettersLeft(String usedLetters, Type type) {
        Map<Character, Integer> localLetters = type.getLetterOccurances();
        String missing = "";
        String seperator = "\nThese extra letters could not be found: '";
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
        return "With the used letters '"+ usedLetters +"', the remaining letters are:\n" + retValue.substring(1, retValue.length() - 1) + missing;
    }
}
