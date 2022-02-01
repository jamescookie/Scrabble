package com.jamescookie.scrabble;

public class TypeNormal extends Type {
    public TypeNormal() {
        super();
        LETTER_OCCURANCES.put('e', 12);
        LETTER_OCCURANCES.put(Utils.WILDCARD, 2);
    }
}
