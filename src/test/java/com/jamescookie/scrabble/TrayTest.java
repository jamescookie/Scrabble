package com.jamescookie.scrabble;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TrayTest {
    @Mock
    private Wordsmith wordsmith;
    private final Filter filter = new Filter();

    @Test
    public void testGetWordsByCrunching() {
        ArrayList<String> returnedWords = new ArrayList<>();
        HashSet<String> possibleCombinations = new HashSet<>();
        possibleCombinations.add("ab");
        possibleCombinations.add("ac");
        possibleCombinations.add("ba");
        possibleCombinations.add("bc");
        possibleCombinations.add("ca");
        possibleCombinations.add("cb");
        possibleCombinations.add("abc");
        possibleCombinations.add("acb");
        possibleCombinations.add("bac");
        possibleCombinations.add("bca");
        possibleCombinations.add("cba");
        possibleCombinations.add("cab");
        when(wordsmith.findWords(possibleCombinations)).thenReturn(returnedWords);
        Tray tray = new Tray("abc", filter, wordsmith);
        Collection<String> words = tray.getWordsByCrunching();
        assertEquals(returnedWords, words, "incorrect number of words returned");
    }

    @Test
    public void testGetWordsByCrunchingWithAWildCard() {
        ArrayList<String> returnedWords;
        HashSet<String> possibleCombinations;
        String character = "w";

        for (char i = 'a'; i <= 'z'; i++) {
            possibleCombinations = new HashSet<>();
            returnedWords = new ArrayList<>();
            possibleCombinations.add(character + i);
            possibleCombinations.add(i + character);
            if (i % 5 == 0) {
                returnedWords.add(character + i);
                when(wordsmith.findWords(possibleCombinations)).thenReturn(returnedWords);
            } else {
                when(wordsmith.findWords(possibleCombinations)).thenReturn(returnedWords);
            }
        }
        Tray tray = new Tray(character + Utils.WILDCARD, filter, wordsmith);
        Collection<String> words = tray.getWordsByCrunching();
        assertEquals(5, words.size(), "incorrect number of words returned");
    }

    @Test
    public void testGetWordsByCrunchingWithTwoWildCards() {
        ArrayList<String> returnedWords = new ArrayList<>();
        HashSet<String> possibleCombinations;

        for (char i = 'a'; i <= 'z'; i++) {
            for (char j = 'a'; j <= 'z'; j++) {
                possibleCombinations = new HashSet<>();
                possibleCombinations.add("" + i + j);
                possibleCombinations.add("" + j + i);
                when(wordsmith.findWords(possibleCombinations)).thenReturn(returnedWords);
            }
        }
        Tray tray = new Tray("**", filter, wordsmith);
        Collection<String> words = tray.getWordsByCrunching();
        assertEquals(0, words.size(), "incorrect number of words returned");
    }

    @Test
    public void testGetLetterCombinationsWithOneWildCard() {
        assertEquals(26,
                Tray.getLetterCombinations(String.valueOf(Utils.WILDCARD)).length,
                "incorrect number of combinations returned");
    }

    @Test
    public void testGetLetterCombinationsWithTwoWildCards() {
        assertEquals((26 * 26),
                Tray.getLetterCombinations(String.valueOf(Utils.WILDCARD) + Utils.WILDCARD).length,
                "incorrect number of combinations returned");
    }

    @Test
    public void testGetLetterCombinationsWithThreeWildCards() {
        assertEquals((26 * 26 * 26),
                Tray.getLetterCombinations(String.valueOf(Utils.WILDCARD) + Utils.WILDCARD + Utils.WILDCARD).length,
                "incorrect number of combinations returned");
    }

}
