package com.jamescookie.scrabble;

import lombok.Getter;

/**
 * @author ukjamescook
 */
public class WordScore {
    @Getter
    private int score = 0;
    private int mod = 1;

    public int getModifier() {
        return mod;
    }

    public void setModifier(int mod) {
        if (mod > this.mod) {
            this.mod = mod;
        }
    }

    public void addToScore(int score) {
        this.score += score;
    }

    public int getFinalScore() {
        return score * mod;
    }
}
