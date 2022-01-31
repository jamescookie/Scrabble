package com.jamescookie.scrabble;

import java.util.Collection;
import java.util.HashSet;

public class WordsmithTest extends Tester {

    public void testGetWordsByCrunchingWithTwoWildCards() throws Exception {
        Wordsmith wordsmith = new WordsmithImpl(WordLoaderImpl.getInstance());
        HashSet<String> possibleCombinations = new HashSet<String>();

        for (char i = 'a'; i <= 'z'; i++) {
            for (char j = 'a'; j <= 'z'; j++) {
                possibleCombinations.add("" + i + j);
                possibleCombinations.add("" + j + i);
            }
        }
        Collection<String> words = wordsmith.findWords(possibleCombinations);
        assertTrue("incorrect words returned", words.containsAll(TwoLetters.TWO_LETTER_WORDS));
        assertEquals("incorrect number of words returned", words.size(), TwoLetters.TWO_LETTER_WORDS.size());
    }

}
