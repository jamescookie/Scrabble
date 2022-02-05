package com.jamescookie.scrabble;

import com.jamescookie.scrabble.types.Bag;
import com.jamescookie.scrabble.types.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author ukjamescook
 */
public class BagTest {
    private Bag bag;

    @BeforeEach
    public void setup() {
        bag = Game.itsYourTurn().getBag();
    }

    @Test
    public void testGetLetter() throws Exception {
        char c = 'a';
        Letter l = bag.getLetter(c);
        assertEquals(c, l.getCharacter());
    }

    @Test
    public void testGetLetters() throws Exception {
        String word = "hello";
        List<Letter> letters = bag.getLetters(word);
        char[] chars = word.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            assertEquals(c, letters.get(i).getCharacter());
        }
    }

    @Test
    public void testGetLettersWithWildcard() throws Exception {
        String word = "hel" + Utils.WILDCARD + "lo";
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

    @Test
    public void testGetLettersDealsWithCapitals() throws Exception {
        String word = "HELLO";
        List<Letter> letters = bag.getLetters(word);
        char[] chars = word.toLowerCase().toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            assertEquals(c, letters.get(i).getCharacter());
        }
    }

    @Test
    public void testGetLettersReturnsEmptyList() throws Exception {
        List<Letter> letters = bag.getLetters(null);
        assertEquals(0, letters.size());
    }

}
