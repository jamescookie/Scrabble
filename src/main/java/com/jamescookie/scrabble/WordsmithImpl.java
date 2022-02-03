package com.jamescookie.scrabble;

import jakarta.inject.Singleton;

import java.util.Set;
import java.util.Collection;
import java.util.HashSet;

@Singleton
public class WordsmithImpl implements Wordsmith {
    private final Set<String> words;

    public WordsmithImpl(WordLoader wordLoader) {
        words = wordLoader.getWords();
    }

    public boolean isValidWord(String word) {
        return words.contains(word);
    }

    /**
     * finds valid words from a Collection of random letters.
     *
     * @param possibleCombinations A Collection of random letters stuck together.
     * @return A Collection of valid words that were found.
     */
    public Collection<String> findWords(Collection<String> possibleCombinations) {
        Set<String> retValue = new HashSet<>();

        for (String word : possibleCombinations) {
            if (isValidWord(word)) {
                retValue.add(word);
            }
        }

        return retValue;
    }

    public Set<String> getWords() {
        return words;
    }

}
