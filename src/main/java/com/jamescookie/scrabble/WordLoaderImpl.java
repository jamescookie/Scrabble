package com.jamescookie.scrabble;

import jakarta.inject.Singleton;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Singleton
public class WordLoaderImpl implements WordLoader {
    private final Set<String> words;

    public WordLoaderImpl() {
        words = loadWords();
    }

    public Set<String> getWords() {
        return words;
    }

    public static WordLoader getInstance() {
        return new WordLoaderImpl();
    }

    private Set<String> loadWords() {
        Set<String> retValue = new HashSet<String>();
        String thisLine;

        try {
            BufferedReader myInput = new BufferedReader(new InputStreamReader(Objects.requireNonNull(this.getClass().getResourceAsStream("/words/WORD.LST")), StandardCharsets.UTF_8));
            while ((thisLine = myInput.readLine()) != null) {
                retValue.add(thisLine.toLowerCase());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return retValue;
    }
}
