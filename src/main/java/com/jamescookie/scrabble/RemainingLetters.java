package com.jamescookie.scrabble;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class RemainingLetters {
    public static char[] lettersLeft(char[] usedLetters, Type type) {
        Map<Character, Integer> localLetters = type.getLetterOccurances();
        if (usedLetters != null) {
            for (char c : usedLetters) {
                if (localLetters.containsKey(c)) {
                    int currentAmount = localLetters.get(c);
                    if (currentAmount < 2) {
                        localLetters.remove(c);
                    } else {
                        localLetters.put(c, currentAmount - 1);
                    }
                }
            }
        }
        return localLetters.entrySet()
                .stream()
                .map(es -> es.getKey().toString().repeat(es.getValue()))
                .collect(Collectors.joining())
                .toCharArray();
    }
}
