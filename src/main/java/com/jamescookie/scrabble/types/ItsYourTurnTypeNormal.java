package com.jamescookie.scrabble.types;

import com.jamescookie.scrabble.Utils;

public class ItsYourTurnTypeNormal extends ItsYourTurnType {
    public ItsYourTurnTypeNormal() {
        super();
        put('e', 12, 1);
        put(Utils.WILDCARD, 2, 0);
    }
}
