package com.jamescookie.scrabble;

import java.util.Arrays;

/**
 * @author ukjamescook
 */
public enum Direction {
    DOWN(1),
    ACROSS(2),
    UP(3),
    BACKWARDS(4);

    private final int direction;

    Direction(int direction) {
        this.direction = direction;
    }

    public static Direction getAdjacent(Direction d) {
        Direction retValue = null;
        if (ACROSS.equals(d)) {
            retValue = DOWN;
        } else if (DOWN.equals(d)) {
            retValue = ACROSS;
        }
        return retValue;
    }

    public static Direction getOpposite(Direction d) {
        Direction retValue = null;
        if (ACROSS.equals(d)) {
            retValue = BACKWARDS;
        } else if (DOWN.equals(d)) {
            retValue = UP;
        }
        return retValue;
    }

    public int getDirection() {
        return direction;
    }

    public String export() {
        return Integer.toString(direction);
    }

    public static Direction generate(String representation) {
        return Arrays.stream(Direction.values())
                .filter(d -> d.getDirection() == Integer.parseInt(representation))
                .findFirst()
                .orElseThrow();
    }

    public static Direction from(String representation) {
        return "DOWN".equalsIgnoreCase(representation) ? DOWN : ACROSS;
    }

    public String toString() {
        return this.name();
    }
}
