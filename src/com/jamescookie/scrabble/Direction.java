package com.jamescookie.scrabble;

/**
 * @author ukjamescook
 */
public class Direction {
    public static final Direction DOWN = new Direction(1);
    public static final Direction ACROSS = new Direction(2);
    public static final Direction UP = new Direction(3);
    public static final Direction BACKWARDS = new Direction(4);
    public static final Direction FORWARDS = ACROSS;

    private final int direction;

    private Direction(int direction) {
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

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Direction direction1 = (Direction) o;

        //noinspection RedundantIfStatement
        if (direction != direction1.direction) return false;

        return true;
    }

    public int hashCode() {
        return direction;
    }


    public String toString() {
        String d = "";
        switch (direction) {
            case 1:
                d = "DOWN";
                break;
            case 2:
                d = "ACROSS";
                break;
            case 3:
                d = "UP";
                break;
            case 4:
                d = "BACKWARDS";
                break;
            default:
                d = "Unknown";
                break;

        }
        return "Direction{" +
                "direction="+d+
                '}';
    }
}
