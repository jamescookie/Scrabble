package com.jamescookie.scrabble;

public class ItsYourTurnTypeWild extends ItsYourTurnType {
    public ItsYourTurnTypeWild() {
        super();
        put('e', 7, 1);
        put(Utils.WILDCARD, 6, 0);
    }
}
