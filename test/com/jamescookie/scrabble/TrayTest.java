package com.jamescookie.scrabble;

import java.util.Collection;
import java.util.HashSet;
import java.util.ArrayList;

import org.easymock.MockControl;

public class TrayTest extends Tester {
    private final Filter filter = new Filter();
    private MockControl wordsmithControl;
    private Wordsmith wordsmith;

    protected void setUp() throws Exception {
        super.setUp();
        wordsmithControl = createControl(Wordsmith.class);
        wordsmith = (Wordsmith) wordsmithControl.getMock();
    }

    public void testGetWordsByCrunching() throws Exception {
        ArrayList<String> returnedWords = new ArrayList<String>();
        HashSet<String> possibleCombinations = new HashSet<String>();
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
        wordsmithControl.expectAndReturn(wordsmith.findWords(possibleCombinations), returnedWords);
        Tray tray = new Tray("abc", filter, wordsmith);
        replay();
        Collection words = Tray.Tester.getWordsByCrunching(tray);
        verify();
        assertEquals("incorrect number of words returned", returnedWords, words);
    }

    public void testGetWordsByCrunchingWithAWildCard() throws Exception {
        ArrayList<String> returnedWords;
        HashSet<String> possibleCombinations;
        String character = "w";

        for (char i = 'a'; i <= 'z'; i++) {
            possibleCombinations = new HashSet<String>();
            returnedWords = new ArrayList<String>();
            possibleCombinations.add(character + i);
            possibleCombinations.add(i + character);
            if (i % 5 == 0) {
                returnedWords.add(character + i);
                wordsmithControl.expectAndReturn(wordsmith.findWords(possibleCombinations), returnedWords);
            } else {
                wordsmithControl.expectAndReturn(wordsmith.findWords(possibleCombinations), returnedWords);
            }
        }
        Tray tray = new Tray(character + Utils.WILDCARD, filter, wordsmith);
        replay();
        Collection words = Tray.Tester.getWordsByCrunching(tray);
        verify();
        assertEquals("incorrect number of words returned", 5, words.size());
    }

    public void testGetWordsByCrunchingWithTwoWildCards() throws Exception {
        ArrayList<String> returnedWords = new ArrayList<String>();
        HashSet<String> possibleCombinations;

        for (char i = 'a'; i <= 'z'; i++) {
            for (char j = 'a'; j <= 'z'; j++) {
                possibleCombinations = new HashSet<String>();
                possibleCombinations.add("" + i + j);
                possibleCombinations.add("" + j + i);
                wordsmithControl.expectAndReturn(wordsmith.findWords(possibleCombinations), returnedWords);
            }
        }
        Tray tray = new Tray("**", filter, wordsmith);
        replay();
        Collection words = Tray.Tester.getWordsByCrunching(tray);
        verify();
        assertEquals("incorrect number of words returned", 0, words.size());
    }

    public void testGetLetterCombinationsWithOneWildCard() throws Exception {
        assertEquals("incorrect number of combinations returned", 26,
                Tray.Tester.getLetterCombinations(String.valueOf(Utils.WILDCARD)).length);
    }

    public void testGetLetterCombinationsWithTwoWildCards() throws Exception {
        assertEquals("incorrect number of combinations returned", (26*26),
                Tray.Tester.getLetterCombinations(String.valueOf(Utils.WILDCARD)+String.valueOf(Utils.WILDCARD)).length);
    }

    public void testGetLetterCombinationsWithThreeWildCards() throws Exception {
        assertEquals("incorrect number of combinations returned", (26*26*26),
                Tray.Tester.getLetterCombinations(String.valueOf(Utils.WILDCARD)+String.valueOf(Utils.WILDCARD)+String.valueOf(Utils.WILDCARD)).length);
    }

}
