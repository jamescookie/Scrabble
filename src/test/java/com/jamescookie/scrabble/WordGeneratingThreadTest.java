package com.jamescookie.scrabble;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WordGeneratingThreadTest {
    @Test
    public void testRemoveLetters() {
        assertEquals("abc", WordGeneratingThread.removeLetters("", "abc"));
        assertEquals("abc", WordGeneratingThread.removeLetters("b", "abbc"));
        assertEquals("aac", WordGeneratingThread.removeLetters("b*b", "ababc"));
    }

    @Test
    public void testAddWildcardBackIn() {
        assertEquals("abc", WordGeneratingThread.addWildcardBackIn("abc", "a", "bc"));
        assertEquals("aaa*a", WordGeneratingThread.addWildcardBackIn("aaaa", "a*", "aa"));
        assertEquals("a*bc", WordGeneratingThread.addWildcardBackIn("abc", "a*c", ""));
        assertEquals("*ab*c", WordGeneratingThread.addWildcardBackIn("abc", "**b", ""));
    }

    @Test
    public void testFindEntries1() {
        boolean[] entries = WordGeneratingThread.findEntries(new String[]{"", "", "has", "not", ""}, "a");
        boolean[] expected = new boolean[]{false, false, true, false, false};
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], entries[i]);
        }
    }

    @Test
    public void testFindEntries2() {
        boolean[] entries = WordGeneratingThread.findEntries(new String[]{"a a", "", "a"}, "a");
        boolean[] expected = new boolean[]{true, false, true};
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], entries[i]);
        }
    }

    @Test
    public void testFindEntries3() {
        boolean[] entries = WordGeneratingThread.findEntries(new String[]{"a a a", "", "a"}, "a a");
        boolean[] expected = new boolean[]{true, false, false};
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], entries[i]);
        }
    }
}
