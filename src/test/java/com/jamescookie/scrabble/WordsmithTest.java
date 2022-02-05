package com.jamescookie.scrabble;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WordsmithTest {

    @Test
    public void testGetWordsByCrunchingWithTwoWildCards() {
        Wordsmith wordsmith = new WordsmithImpl(WordLoaderImpl.getInstance());
        HashSet<String> possibleCombinations = new HashSet<>();

        for (char i = 'a'; i <= 'z'; i++) {
            for (char j = 'a'; j <= 'z'; j++) {
                possibleCombinations.add("" + i + j);
                possibleCombinations.add("" + j + i);
            }
        }
        Collection<String> words = wordsmith.findWords(possibleCombinations);
        assertTrue(words.containsAll(TwoLetters.TWO_LETTER_WORDS), "incorrect words returned");
        assertEquals(words.size(), TwoLetters.TWO_LETTER_WORDS.size(), "incorrect number of words returned");
    }

}
