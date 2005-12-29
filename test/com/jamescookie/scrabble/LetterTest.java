package com.jamescookie.scrabble;

/**
 * @author ukjamescook
 */
public class LetterTest extends Tester {
    public void testGetScore() throws Exception {
        assertEquals(1, new Letter('a').getScore());
        assertEquals(3, new Letter('b').getScore());
        assertEquals(2, new Letter('c').getScore());
        assertEquals(1, new Letter('d').getScore());
        assertEquals(1, new Letter('e').getScore());
        assertEquals(2, new Letter('f').getScore());
        assertEquals(3, new Letter('g').getScore());
        assertEquals(1, new Letter('h').getScore());
        assertEquals(1, new Letter('i').getScore());
        assertEquals(7, new Letter('j').getScore());
        assertEquals(5, new Letter('k').getScore());
        assertEquals(1, new Letter('l').getScore());
        assertEquals(2, new Letter('m').getScore());
        assertEquals(1, new Letter('n').getScore());
        assertEquals(1, new Letter('o').getScore());
        assertEquals(3, new Letter('p').getScore());
        assertEquals(12, new Letter('q').getScore());
        assertEquals(1, new Letter('r').getScore());
        assertEquals(1, new Letter('s').getScore());
        assertEquals(1, new Letter('t').getScore());
        assertEquals(2, new Letter('u').getScore());
        assertEquals(7, new Letter('v').getScore());
        assertEquals(2, new Letter('w').getScore());
        assertEquals(7, new Letter('x').getScore());
        assertEquals(2, new Letter('y').getScore());
        assertEquals(12, new Letter('z').getScore());
        assertEquals(0, new Letter(Utils.WILDCARD).getScore());
    }

    public void testLetterDoesNotAllowStrangeCharacters() throws Exception {
        try {
            new Letter(' ');
            fail("Exception expected");
        } catch (Exception e) {

        }
        try {
            new Letter('A');
            fail("Exception expected");
        } catch (Exception e) {

        }
        try {
            Character c = null;
            new Letter(c);
            fail("Exception expected");
        } catch (Exception e) {

        }
    }
}
