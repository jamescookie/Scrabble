package com.jamescookie.scrabble;

import com.jamescookie.scrabble.types.Bag;
import com.jamescookie.scrabble.types.Game;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author ukjamescook
 */
public class LetterTest {
    @Test
    public void testGetScore() throws Exception {
        Bag bag = Game.itsYourTurn().getBag();
        assertEquals(1, bag.getLetter('a').getScore());
        assertEquals(3, bag.getLetter('b').getScore());
        assertEquals(2, bag.getLetter('c').getScore());
        assertEquals(1, bag.getLetter('d').getScore());
        assertEquals(1, bag.getLetter('e').getScore());
        assertEquals(2, bag.getLetter('f').getScore());
        assertEquals(3, bag.getLetter('g').getScore());
        assertEquals(1, bag.getLetter('h').getScore());
        assertEquals(1, bag.getLetter('i').getScore());
        assertEquals(7, bag.getLetter('j').getScore());
        assertEquals(5, bag.getLetter('k').getScore());
        assertEquals(1, bag.getLetter('l').getScore());
        assertEquals(2, bag.getLetter('m').getScore());
        assertEquals(1, bag.getLetter('n').getScore());
        assertEquals(1, bag.getLetter('o').getScore());
        assertEquals(3, bag.getLetter('p').getScore());
        assertEquals(12, bag.getLetter('q').getScore());
        assertEquals(1, bag.getLetter('r').getScore());
        assertEquals(1, bag.getLetter('s').getScore());
        assertEquals(1, bag.getLetter('t').getScore());
        assertEquals(2, bag.getLetter('u').getScore());
        assertEquals(7, bag.getLetter('v').getScore());
        assertEquals(2, bag.getLetter('w').getScore());
        assertEquals(7, bag.getLetter('x').getScore());
        assertEquals(2, bag.getLetter('y').getScore());
        assertEquals(12, bag.getLetter('z').getScore());
        assertEquals(0, bag.getLetter(Utils.WILDCARD).getScore());
    }

    @Test
    public void testLetterDoesNotAllowStrangeCharacters() {
        Bag bag = Game.itsYourTurn().getBag();
        assertThrows(ScrabbleException.class, () ->
                bag.getLetter(' '));
        assertThrows(ScrabbleException.class, () ->
                bag.getLetter('A'));
    }
}
