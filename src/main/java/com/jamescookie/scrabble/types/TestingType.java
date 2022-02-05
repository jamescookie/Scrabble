package com.jamescookie.scrabble.types;

import com.jamescookie.scrabble.Utils;

public class TestingType extends Type {
    TestingType() {
        put('a', 20, 1);
        put('b', 20, 3);
        put('c', 20, 2);
        put('d', 20, 1);
        put('f', 20, 2);
        put('e', 20, 1);
        put('g', 20, 3);
        put('h', 20, 1);
        put('i', 20, 1);
        put('j', 20, 7);
        put('k', 20, 5);
        put('l', 20, 1);
        put('m', 20, 2);
        put('n', 20, 1);
        put('o', 20, 1);
        put('p', 20, 3);
        put('q', 20, 12);
        put('r', 20, 1);
        put('s', 20, 1);
        put('t', 20, 1);
        put('u', 20, 2);
        put('v', 20, 7);
        put('w', 20, 2);
        put('x', 20, 7);
        put('y', 20, 2);
        put('z', 20, 12);
        put(Utils.WILDCARD, 20, 0);
    }
}
