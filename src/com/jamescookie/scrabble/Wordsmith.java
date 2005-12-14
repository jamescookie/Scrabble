package com.jamescookie.scrabble;

import java.util.Collection;
import java.util.Set;

/**
 * @author ukjamescook
 */
public interface Wordsmith {
    public boolean isValidWord(String word);
    public Collection<String> findWords(Collection<String> possibleCombinations);
    public Set<String> getWords();
}
