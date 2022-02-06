package com.jamescookie.scrabble.types;

import com.jamescookie.scrabble.Letter;
import com.jamescookie.scrabble.ScrabbleException;
import com.jamescookie.scrabble.Utils;
import com.jamescookie.scrabble.Wildcard;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author ukjamescook
 */
public class Bag {
    private final List<Letter> letters;
    boolean dryRun = false;

    Bag(Type type) {
        this.letters = new CopyOnWriteArrayList<>(type.getAllLetters());
    }

    public List<Letter> lettersLeft() {
        return this.letters;
    }

    public void setDryRun(boolean b) {
        dryRun = b;
    }

    public Letter getLetter(char c) throws ScrabbleException {
        Letter letter;
        if (c == Utils.WILDCARD) {
            letter = getWildcard(c);
        } else {
            letter = letters.stream().filter(l -> l.getCharacter() == c).findFirst().orElseThrow(() -> new ScrabbleException("No letters left in bag for: " + c));
        }
        if (!dryRun) letters.remove(letter);
        return letter;
    }

    private Wildcard getWildcard(char c) throws ScrabbleException {
        return letters.stream()
                .filter(Letter::isWildcard)
                .findFirst()
                .map(l -> ((Wildcard) l).setCharacter(c))
                .orElseThrow(() -> new ScrabbleException("No wildcard letters left in bag"));
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
        letters.stream()
                .filter(Letter::isWildcard)
                .forEach(l -> ((Wildcard) l).setCharacter(Utils.WILDCARD));
        this.letters.addAll(letters);
    }
}
