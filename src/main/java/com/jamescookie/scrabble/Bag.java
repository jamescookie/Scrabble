package com.jamescookie.scrabble;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author ukjamescook
 */
public class Bag {
    private final List<Letter> letters;

    private Bag(Type type) {
        this.letters = type.getAllLetters();
    }

    public static Bag getInstance() {
        return new Bag(new TypeNormal());
    }

    public static Bag getInstance(Type type) {
        return new Bag(type);
    }

    public List<Letter> lettersLeft() {
        return this.letters;
    }

    public Letter getLetter(char c) throws ScrabbleException {
        Letter letter = letters.stream().filter(l -> l.getCharacter() == c).findFirst().orElseThrow(() -> new ScrabbleException("No letters left in bag for: " + c));
        letters.remove(letter);
        return letter;
    }

    public List<Letter> getLetters(String word) throws ScrabbleException {
        ArrayList<Letter> letters = new ArrayList<>();
        Optional<Wildcard> wildcard = Optional.empty();

        if (word != null) {
            char[] chars = word.toLowerCase().toCharArray();
            for (char c : chars) {
                if (wildcard.isPresent()) {
                    letters.add(wildcard.get().setCharacter(c));
                    wildcard = Optional.empty();
                } else {
                    if (c == Utils.WILDCARD) {
                        wildcard = Optional.of((Wildcard) getLetter(c));
                    } else {
                        letters.add(getLetter(c));
                    }
                }
            }
        }

        return letters;
    }

    public void returnLetters(List<Letter> letters) {
        this.letters.addAll(letters);
    }
}
