package com.jamescookie.scrabble;

public class WordGeneratingThreadTest extends Tester {
    public void testRemoveLetters() throws Exception {
        assertEquals("abc", WordGeneratingThread.Tester.removeLetters("", "abc"));
        assertEquals("abc", WordGeneratingThread.Tester.removeLetters("b", "abbc"));
        assertEquals("aac", WordGeneratingThread.Tester.removeLetters("b*b", "ababc"));
    }

    public void testAddWildcardBackIn() throws Exception {
        assertEquals("abc", WordGeneratingThread.Tester.addWildcardBackIn("abc", "a", "bc"));
        assertEquals("aaa*a", WordGeneratingThread.Tester.addWildcardBackIn("aaaa", "a*", "aa"));
        assertEquals("a*bc", WordGeneratingThread.Tester.addWildcardBackIn("abc", "a*c", ""));
        assertEquals("*ab*c", WordGeneratingThread.Tester.addWildcardBackIn("abc", "**b", ""));
    }

    public void testFindEntries1() throws Exception {
        boolean[] entries = WordGeneratingThread.Tester.findEntries(new String[] {"", "", "has", "not", ""}, "a");
        boolean[] expected = new boolean[]{false, false, true, false, false};
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], entries[i]);
        }
    }

    public void testFindEntries2() throws Exception {
        boolean[] entries = WordGeneratingThread.Tester.findEntries(new String[] {"a a", "", "a"}, "a");
        boolean[] expected = new boolean[]{true, false, true};
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], entries[i]);
        }
    }

    public void testFindEntries3() throws Exception {
        boolean[] entries = WordGeneratingThread.Tester.findEntries(new String[] {"a a a", "", "a"}, "a a");
        boolean[] expected = new boolean[]{true, false, false};
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], entries[i]);
        }
    }
}
