package com.jamescookie.scrabble;

import java.util.LinkedHashSet;
import java.util.Set;

import junit.framework.TestCase;

public class TwoLettersTest extends TestCase {

    public void testTwoLettersInReverseOrder() throws Exception {
        Set<String> twoLetterWords = new LinkedHashSet<String>();
        twoLetterWords.add("ad");
        twoLetterWords.add("ba");
        Set<String> reversedTwoLetters = TwoLetters.reverseOrder(twoLetterWords);
        Object[] test = reversedTwoLetters.toArray();
        assertEquals(test[0], "ba");
        assertEquals(test[1], "ad");
    }

}
