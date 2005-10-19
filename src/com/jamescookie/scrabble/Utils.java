package com.jamescookie.scrabble;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import javax.swing.JList;

public class Utils {
    public static final String VERSION = "2.0";

    public static final char WILDCARD = '*';

    private Utils() {
    }

    /**
     * checks to see if the given String is valid as a group of letters in Scrabble.
     *
     * @param s The String to check
     * @return <code>True</code> is the String is valid.
     */
    public static boolean isValidText(String s) {
        return (s != null) && (s.length() <= 7) && (s.length() > 1);
    }

    /**
     * removes the specified char from the array, reducing the array's size by 1.
     *
     * @param chars The char[] to remove an element from.
     * @param index The element to remove.
     * @return A char[] with the element removed.
     */
    public static char[] removeElement(char[] chars, int index) {
        char[] retValue = new char[chars.length - 1];
        int counter = 0;

        for (int i = 0; i < chars.length; i++) {
            if (i != index) {
                retValue[counter++] = chars[i];
            }
        }

        return retValue;
    }

    /**
     * combines the array with the char and produces a String.
     *
     * @param letters A char[].
     * @param letter A char.
     * @return The resulting String.
     */
    public static String createWord(char[] letters, char letter) {
        char[] newLetters = new char[letters.length + 1];
        System.arraycopy(letters, 0, newLetters, 0, letters.length);
        newLetters[newLetters.length - 1] = letter;
        return new String(newLetters);
    }

    /**
     * sorts a Collection of words based on their length.
     *
     * @param words A Collection of words to sort.
     * @return The sorted Collection.
     */
    public static Collection<String> sortWords(Collection<String> words) {
        List<String> sortedPossibleWords = new ArrayList<String>(words);
        Collections.sort(sortedPossibleWords, new LengthComparator());
        return sortedPossibleWords;
    }

    /**
     * displays the Collection in the given list.
     *
     * @param c The Collection to display.
     * @param list The JList object to display the Collection in.
     */
    public static void displayCollection(Collection<String> c, JList list) {
        if (c.size() > 0) {
            list.setListData(new Vector<String>(c));
        } else {
            list.setListData(new Object[] {"<none>"});
        }
    }

    /**
     * determines if the given word can be constructed from the given letters.
     *
     * @param word The word to try and form.
     * @param letters The letters with which to form the word.
     * @return True if the word can be made from the letters.
     */
    public static boolean canCreateWord(String word, String letters) {
        boolean retValue = true;

        if (word.length() > letters.length()) {
            retValue = false;
        } else {
            char[] wordLetters = word.toCharArray();
            char[] potentialLetters = letters.toCharArray();
            boolean letterFound;

            for (final char letter : wordLetters) {
                letterFound = false;
                for (int j = 0; j < potentialLetters.length; j++) {
                    if (potentialLetters[j] == letter) {
                        potentialLetters[j] = ' ';
                        letterFound = true;
                        break;
                    }
                }

                if (!letterFound) {
                    for (int j = 0; j < potentialLetters.length; j++) {
                        if (potentialLetters[j] == WILDCARD) {
                            potentialLetters[j] = ' ';
                            letterFound = true;
                            break;
                        }
                    }
                    if (!letterFound) {
                        retValue = false;
                        break;
                    }
                }
            }
        }

        return retValue;
    }

}
