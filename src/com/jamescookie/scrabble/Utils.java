package com.jamescookie.scrabble;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.Iterator;
import javax.swing.JList;

public class Utils {
    public static final String VERSION = "1.8";

    public static final char WILDCARD = '*';

    static final Set<String> TWO_LETTER_WORDS = new LinkedHashSet<String>();

    static {
        TWO_LETTER_WORDS.add("aa");
        TWO_LETTER_WORDS.add("ab");
        TWO_LETTER_WORDS.add("ad");
        TWO_LETTER_WORDS.add("ae");
        TWO_LETTER_WORDS.add("ag");
        TWO_LETTER_WORDS.add("ah");
        TWO_LETTER_WORDS.add("ai");
        TWO_LETTER_WORDS.add("al");
        TWO_LETTER_WORDS.add("am");
        TWO_LETTER_WORDS.add("an");
        TWO_LETTER_WORDS.add("ar");
        TWO_LETTER_WORDS.add("as");
        TWO_LETTER_WORDS.add("at");
        TWO_LETTER_WORDS.add("aw");
        TWO_LETTER_WORDS.add("ax");
        TWO_LETTER_WORDS.add("ay");
        TWO_LETTER_WORDS.add("ba");
        TWO_LETTER_WORDS.add("be");
        TWO_LETTER_WORDS.add("bi");
        TWO_LETTER_WORDS.add("bo");
        TWO_LETTER_WORDS.add("by");
        TWO_LETTER_WORDS.add("de");
        TWO_LETTER_WORDS.add("do");
        TWO_LETTER_WORDS.add("ed");
        TWO_LETTER_WORDS.add("ef");
        TWO_LETTER_WORDS.add("eh");
        TWO_LETTER_WORDS.add("el");
        TWO_LETTER_WORDS.add("em");
        TWO_LETTER_WORDS.add("en");
        TWO_LETTER_WORDS.add("er");
        TWO_LETTER_WORDS.add("es");
        TWO_LETTER_WORDS.add("et");
        TWO_LETTER_WORDS.add("ex");
        TWO_LETTER_WORDS.add("fa");
        TWO_LETTER_WORDS.add("go");
        TWO_LETTER_WORDS.add("ha");
        TWO_LETTER_WORDS.add("he");
        TWO_LETTER_WORDS.add("hi");
        TWO_LETTER_WORDS.add("hm");
        TWO_LETTER_WORDS.add("ho");
        TWO_LETTER_WORDS.add("id");
        TWO_LETTER_WORDS.add("if");
        TWO_LETTER_WORDS.add("in");
        TWO_LETTER_WORDS.add("is");
        TWO_LETTER_WORDS.add("it");
        TWO_LETTER_WORDS.add("jo");
        TWO_LETTER_WORDS.add("ka");
        TWO_LETTER_WORDS.add("la");
        TWO_LETTER_WORDS.add("li");
        TWO_LETTER_WORDS.add("lo");
        TWO_LETTER_WORDS.add("ma");
        TWO_LETTER_WORDS.add("me");
        TWO_LETTER_WORDS.add("mi");
        TWO_LETTER_WORDS.add("mm");
        TWO_LETTER_WORDS.add("mo");
        TWO_LETTER_WORDS.add("mu");
        TWO_LETTER_WORDS.add("my");
        TWO_LETTER_WORDS.add("na");
        TWO_LETTER_WORDS.add("ne");
        TWO_LETTER_WORDS.add("no");
        TWO_LETTER_WORDS.add("nu");
        TWO_LETTER_WORDS.add("od");
        TWO_LETTER_WORDS.add("oe");
        TWO_LETTER_WORDS.add("of");
        TWO_LETTER_WORDS.add("oh");
        TWO_LETTER_WORDS.add("om");
        TWO_LETTER_WORDS.add("on");
        TWO_LETTER_WORDS.add("op");
        TWO_LETTER_WORDS.add("or");
        TWO_LETTER_WORDS.add("os");
        TWO_LETTER_WORDS.add("ow");
        TWO_LETTER_WORDS.add("ox");
        TWO_LETTER_WORDS.add("oy");
        TWO_LETTER_WORDS.add("pa");
        TWO_LETTER_WORDS.add("pe");
        TWO_LETTER_WORDS.add("pi");
        TWO_LETTER_WORDS.add("re");
        TWO_LETTER_WORDS.add("sh");
        TWO_LETTER_WORDS.add("si");
        TWO_LETTER_WORDS.add("so");
        TWO_LETTER_WORDS.add("ta");
        TWO_LETTER_WORDS.add("ti");
        TWO_LETTER_WORDS.add("to");
        TWO_LETTER_WORDS.add("uh");
        TWO_LETTER_WORDS.add("um");
        TWO_LETTER_WORDS.add("un");
        TWO_LETTER_WORDS.add("up");
        TWO_LETTER_WORDS.add("us");
        TWO_LETTER_WORDS.add("ut");
        TWO_LETTER_WORDS.add("we");
        TWO_LETTER_WORDS.add("wo");
        TWO_LETTER_WORDS.add("xi");
        TWO_LETTER_WORDS.add("xu");
        TWO_LETTER_WORDS.add("ya");
        TWO_LETTER_WORDS.add("ye");
        TWO_LETTER_WORDS.add("yo");
    }

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


    public static String formatTwoLetterWords(String lineSeperator) {
        StringBuffer sb = new StringBuffer();
        char firstChar = 'a';
        String seperator = "";
        for (Iterator<String> iterator = TWO_LETTER_WORDS.iterator(); iterator.hasNext();) {
            String s = iterator.next();
            if (firstChar != s.charAt(0)) {
                seperator = lineSeperator;
            }
            firstChar = s.charAt(0);
            sb.append(seperator).append(s);
            seperator = ", ";
        }

        return sb.toString();
    }
}
