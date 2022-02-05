package com.jamescookie.scrabble;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WordGeneratingThreadTest {
    @Test
    public void testRemoveLetters() {
        assertEquals("abc", WordGeneratingThread.Tester.removeLetters("", "abc"));
        assertEquals("abc", WordGeneratingThread.Tester.removeLetters("b", "abbc"));
        assertEquals("aac", WordGeneratingThread.Tester.removeLetters("b*b", "ababc"));
    }

    @Test
    public void testAddWildcardBackIn() {
        assertEquals("abc", WordGeneratingThread.Tester.addWildcardBackIn("abc", "a", "bc"));
        assertEquals("aaa*a", WordGeneratingThread.Tester.addWildcardBackIn("aaaa", "a*", "aa"));
        assertEquals("a*bc", WordGeneratingThread.Tester.addWildcardBackIn("abc", "a*c", ""));
        assertEquals("*ab*c", WordGeneratingThread.Tester.addWildcardBackIn("abc", "**b", ""));
    }

    @Test
    public void testFindEntries1() {
        boolean[] entries = WordGeneratingThread.Tester.findEntries(new String[]{"", "", "has", "not", ""}, "a");
        boolean[] expected = new boolean[]{false, false, true, false, false};
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], entries[i]);
        }
    }

    @Test
    public void testFindEntries2() {
        boolean[] entries = WordGeneratingThread.Tester.findEntries(new String[]{"a a", "", "a"}, "a");
        boolean[] expected = new boolean[]{true, false, true};
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], entries[i]);
        }
    }

    @Test
    public void testFindEntries3() {
        boolean[] entries = WordGeneratingThread.Tester.findEntries(new String[]{"a a a", "", "a"}, "a a");
        boolean[] expected = new boolean[]{true, false, false};
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], entries[i]);
        }
    }
}
