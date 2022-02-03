package com.jamescookie.scrabble;

public class TypeNormal extends Type {
    public TypeNormal() {
        put('a', 9, 1);
        put('b', 2, 3);
        put('c', 2, 3);
        put('d', 4, 2);
        put('e', 12, 1);
        put('f', 2, 4);
        put('g', 3, 2);
        put('h', 2, 4);
        put('i', 9, 1);
        put('j', 1, 8);
        put('k', 1, 5);
        put('l', 4, 1);
        put('m', 2, 3);
        put('n', 6, 1);
        put('o', 8, 1);
        put('p', 2, 3);
        put('q', 1, 10);
        put('r', 6, 1);
        put('s', 4, 1);
        put('t', 6, 1);
        put('u', 4, 1);
        put('v', 2, 4);
        put('w', 2, 4);
        put('x', 1, 8);
        put('y', 2, 4);
        put('z', 1, 10);
        put(Utils.WILDCARD, 2, 0);
    }
}
