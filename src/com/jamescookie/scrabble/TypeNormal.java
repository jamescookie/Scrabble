package com.jamescookie.scrabble;

public class TypeNormal extends Type {
    public TypeNormal() {
        super();
        LETTER_OCCURANCES.put('e', 11);
        LETTER_OCCURANCES.put(Utils.WILDCARD, 2);
    }
}
