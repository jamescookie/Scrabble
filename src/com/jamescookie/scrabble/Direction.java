package com.jamescookie.scrabble;

/**
 * @author ukjamescook
 */
public class Direction {
    public static final Direction DOWN = new Direction(1);
    public static final Direction ACROSS = new Direction(2);

    private final int direction;

    private Direction(int direction) {
        this.direction = direction;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Direction direction1 = (Direction) o;

        if (direction != direction1.direction) return false;

        return true;
    }

    public int hashCode() {
        return direction;
    }
}
