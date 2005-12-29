package com.jamescookie.scrabble;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Arrays;
import javax.swing.JLabel;

public class Tray {
    private final String _letters;
    private final String _boardLetters;
    private final Filter _filter;
    private final Wordsmith wordsmith;
    private JLabel _info;
    private boolean _stop = false;

    public Tray(String letters, Filter filter, Wordsmith wordsmith) {
        _filter = filter;
        this.wordsmith = wordsmith;
        String boardLetters = filter.getMustContain();

        letters = letters.toLowerCase();
        if (boardLetters != null) {
            _boardLetters = boardLetters.toLowerCase();
            StringBuffer tmp = new StringBuffer();
            for (int i = 0; i < _boardLetters.length(); i++) {
                if (_boardLetters.charAt(i) != Utils.WILDCARD) {
                    tmp.append(_boardLetters.charAt(i));
                }
            }
            tmp.append(letters);
            _letters = tmp.toString();
        } else {
            _letters = letters;
            _boardLetters = null;
        }
    }

    public Tray(String letters, Filter filter, Wordsmith wordsmith, JLabel info) {
        this(letters, filter, wordsmith);
        _info = info;
    }

    /**
     * stops the processing at the next suitable point.
     */
    public void stop() {
        _stop = true;
    }

    /**
     * gets the possible words based on the letters given in the constructor.
     *
     * @return A Collection of possible words.
     */
    public Collection<String> getWords() {
        Collection<String> possibleWords;

        if (_boardLetters != null &&
                (_boardLetters.length() > 1 || _letters.indexOf(Utils.WILDCARD) != -1)) {
            possibleWords = getWordsByFiltering();
        } else {
            possibleWords = getWordsByCrunching();
        }


        return Utils.sortWords(possibleWords);
    }

    /**
     * gets the possible words by looking through all the words that are
     * produced by the filter and seeing if they can be constructed with
     * the letters we have.
     *
     * @return A Collection of possible words.
     */
    private Collection<String> getWordsByFiltering() {
        Collection<String> possibleWords = new ArrayList<String>();
        Collection<String> filteredWords = _filter.filter(wordsmith.getWords());

        for (String word : filteredWords) {
            if (Utils.canCreateWord(word, _letters)) {
                possibleWords.add(word);
            }
        }

        return possibleWords;
    }

    /**
     * gets the possible words by going through all combinations of letters and
     * seeing if they exist in the word list.
     *
     * @return A Collection of possible words.
     */
    private Collection<String> getWordsByCrunching() {
        Collection<String> possibleWords;
        if (_letters.indexOf(Utils.WILDCARD) != -1) {
            Set<String> tmp = new HashSet<String>();

            String[] letterCombinations = getLetterCombinations(_letters);

            for (final String newVar : letterCombinations) {
                tmp.addAll(run(newVar.toCharArray()));
                if (_stop) {
                    break;
                }
            }

            possibleWords = tmp;
        } else {
            possibleWords = run(_letters.toCharArray());
        }
        if (!_stop) {
            updateInfo("Found " + possibleWords.size() + " words");
        }

        return possibleWords;
    }

    /**
     * Gets all letter combinations when a string contains a wild card.
     *
     * @param letters The letters that may contain a wild card.
     * @return A String[] of every combination.
     */
    private static String[] getLetterCombinations(String letters) {
        char[] chars = letters.toCharArray();
        char c;
        List<String> letterCombinations = new ArrayList<String>();
        char[] newChars;
        String newString;

        for (int i = 0; i < chars.length; i++) {
            c = chars[i];
            if (c == Utils.WILDCARD) {
                for (char j = 'a'; j <= 'z'; j++) {
                    newChars = new char[chars.length];
                    chars[i] = j;
                    System.arraycopy(chars, 0, newChars, 0, chars.length);
                    newString = new String(newChars);
                    if (newString.indexOf(Utils.WILDCARD) != -1) {
                        letterCombinations.addAll(Arrays.asList(getLetterCombinations(newString)));
                    } else {
                        letterCombinations.add(newString);
                    }
                }
                break;
            }
        }

        return letterCombinations.toArray(new String[0]);
    }

    /**
     * this is the top level method that uses the others to produce a Collection
     * of valid words from an Array of letters.
     *
     * @param letters A char[] of letters.
     * @return A Collection of valid words.
     */
    private Collection<String> run(char[] letters) {
        Collection<String> possibleCombinations = new HashSet<String>();
        updateInfo("Processing: "+new String(letters));

        for (int i = 0; i < letters.length; i++) {
            possibleCombinations.addAll(combine(Utils.removeElement(letters, i), new String(new char[] {letters[i]})));
            if (_stop) {
                updateInfo("Stopped at " + possibleCombinations.size() + " combinations");
                break;
            } else {
                updateInfo("Currently found " + possibleCombinations.size() + " combinations");
            }
        }

        return wordsmith.findWords(possibleCombinations);
    }

    /**
     * a recursive method that adds each of the letters in the given array
     * to the String provided.
     *
     * @param letters A char[] of letters to append to the String.
     * @param currentLetters The String to append letters to.
     * @return A Collection of all the resulting 'words'.
     */
    private Collection<String> combine(char[] letters, String currentLetters) {
        Collection<String> words = new HashSet<String>();
        String newWord;

        for (int i = 0; i < letters.length; i++) {
            newWord = Utils.createWord(currentLetters.toCharArray(), letters[i]);
            words.add(newWord);
            if (letters.length > 1) {  // ensures that we only get words of >= 2 letters
                words.addAll(combine(Utils.removeElement(letters, i), newWord));
            }
        }

        return words;
    }

    /**
     * updates the label with what's going on.
     *
     * @param message The information to display.
     */
    private void updateInfo(String message) {
        if (_info != null) {
            _info.setText(message);
        }
    }

    /**
     * Inner class to allow testing of private methods.
     */
    static class Tester {
        private Tester() {
        }

        public static Collection<String> getWordsByCrunching(Tray tray) {
            return tray.getWordsByCrunching();
        }

        public static String[] getLetterCombinations(String letters) {
            return Tray.getLetterCombinations(letters);
        }
    }
}
