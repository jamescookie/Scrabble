package com.jamescookie.scrabble;

import java.util.Collection;
import javax.swing.JLabel;

import junit.framework.TestCase;

public class TrayTest extends TestCase {
    private final Filter filter = new Filter();

    protected void setUp() throws Exception {
        super.setUp();
    }

    public void testGetWordsByCrunching() throws Exception {
        Tray tray = new Tray("abc", filter, new JLabel());
        Collection words = Tray.Tester.getWordsByCrunching(tray);
        assertEquals("incorrect number of words returned", 3, words.size());
    }

    public void testGetWordsByCrunchingWithAWildCard() throws Exception {
        Tray tray = new Tray("w*", filter, new JLabel());
        Collection words = Tray.Tester.getWordsByCrunching(tray);
        assertEquals("incorrect number of words returned", 4, words.size());
    }

    public void testGetWordsByCrunchingWithTwoWildCards() throws Exception {
        Tray tray = new Tray("**", filter, new JLabel());
        Collection words = Tray.Tester.getWordsByCrunching(tray);
        assertTrue("incorrect words returned", words.containsAll(Utils.TWO_LETTER_WORDS));
        assertEquals("incorrect number of words returned", words.size(), Utils.TWO_LETTER_WORDS.size());
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
