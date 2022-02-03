package com.jamescookie.scrabble;

import java.util.Collection;
import java.util.Set;

/**
 * @author ukjamescook
 */
public interface Wordsmith {
    boolean isValidWord(String word);
    Collection<String> findWords(Collection<String> possibleCombinations);
    Set<String> getWords();
}
