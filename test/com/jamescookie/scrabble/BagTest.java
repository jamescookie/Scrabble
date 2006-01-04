package com.jamescookie.scrabble;

import java.util.List;

/**
 * @author ukjamescook
 */
public class BagTest extends Tester {
    private Bag bag;

    protected void setUp() throws Exception {
        super.setUp();
        bag = new Bag();
    }

    public void testGetLetter() throws Exception {
        char c = 'a';
        Letter l = bag.getLetter(c);
        assertEquals(c, l.getCharacter());
    }

    public void testGetLetters() throws Exception {
        String word = "hello";
        List<Letter> letters = bag.getLetters(word);
        char[] chars = word.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            assertEquals(c, letters.get(i).getCharacter());
        }
    }

    public void testGetLettersWithWildcard() throws Exception {
        String word = "hel"+Utils.WILDCARD+"lo";
        List<Letter> letters = bag.getLetters(word);
        char[] chars = word.toCharArray();
        for (int i = 0, j = 0; i < chars.length; i++, j++) {
            char c = chars[i];
            if (c == Utils.WILDCARD) {
                c = chars[++i];
                assertEquals(0, letters.get(j).getScore());
            }
            assertEquals(c, letters.get(j).getCharacter());
        }
    }

    public void testGetLettersDealsWithCapitols() throws Exception {
        String word = "HELLO";
        List<Letter> letters = bag.getLetters(word);
        char[] chars = word.toLowerCase().toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            assertEquals(c, letters.get(i).getCharacter());
        }
    }

    public void testGetLettersReturnsEmptyList() throws Exception {
        List<Letter> letters = bag.getLetters(null);
        assertEquals(0, letters.size());
    }

}
