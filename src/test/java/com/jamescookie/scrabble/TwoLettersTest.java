package com.jamescookie.scrabble;

import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TwoLettersTest {

    @Test
    public void testTwoLettersInReverseOrder() {
        Set<String> twoLetterWords = new LinkedHashSet<>();
        twoLetterWords.add("ad");
        twoLetterWords.add("ba");
        Set<String> reversedTwoLetters = TwoLetters.reverseOrder(twoLetterWords);
        Object[] test = reversedTwoLetters.toArray();
        assertEquals(test[0], "ba");
        assertEquals(test[1], "ad");
    }

}
