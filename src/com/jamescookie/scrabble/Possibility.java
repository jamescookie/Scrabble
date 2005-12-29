package com.jamescookie.scrabble;

/**
 * @author ukjamescook
 */
public class Possibility {
    private final int score;
    private final String letters;
    private final Square startPoint;
    private final Direction direction;

    public Possibility(int score, String letters, Square startPoint, Direction direction) {
        this.score = score;
        this.letters = letters;
        this.startPoint = startPoint;
        this.direction = direction;
    }

    public int getScore() {
        return score;
    }

    public String getLetters() {
        return letters;
    }

    public Square getStartPoint() {
        return startPoint;
    }

    public Direction getDirection() {
        return direction;
    }


    public String toString() {
        return "Possibility{" +
                "score=" + score +
                ", letters='" + letters + '\'' +
                ", startPoint=" + startPoint +
                ", direction=" + direction +
                '}';
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Possibility that = (Possibility) o;

        if (score != that.score) return false;
        if (!direction.equals(that.direction)) return false;
        if (!letters.equals(that.letters)) return false;
        //noinspection RedundantIfStatement
        if (!startPoint.equals(that.startPoint)) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = score;
        result = 29 * result + letters.hashCode();
        result = 29 * result + startPoint.hashCode();
        result = 29 * result + direction.hashCode();
        return result;
    }
}
