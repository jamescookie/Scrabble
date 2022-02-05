package com.jamescookie.scrabble.types;

import com.jamescookie.scrabble.Utils;

public class ItsYourTurnTypeWild extends ItsYourTurnType {
    public ItsYourTurnTypeWild() {
        super();
        put('e', 7, 1);
        put(Utils.WILDCARD, 6, 0);
    }
}
