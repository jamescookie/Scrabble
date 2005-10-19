package com.jamescookie.scrabble;

public class TypeWild extends Type {
    public TypeWild() {
        super();
        LETTER_OCCURANCES.put('e', 7);
        LETTER_OCCURANCES.put(Utils.WILDCARD, 6);
    }
}
