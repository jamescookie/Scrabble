package com.jamescookie.scrabble;

import com.jamescookie.scrabble.types.Bag;
import com.jamescookie.scrabble.types.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author ukjamescook
 */
public class SquareTest {
    private Bag bag;

    @BeforeEach
    public void setup() {
        bag = Game.itsYourTurn().getBag();
    }

    @Test
    public void testScoreForNormalSquare() throws Exception {
        Square sq = Square.getNormal(0, 0);
        WordScore wordScore = sq.getScore(bag.getLetter('a'), new WordScore());
        assertEquals(1, wordScore.getScore());
        assertEquals(1, wordScore.getModifier());
    }

    @Test
    public void testScoreForDoubleSquare() throws Exception {
        Square sq = Square.getDoubleLetter(0, 0);
        WordScore wordScore = sq.getScore(bag.getLetter('a'), new WordScore());
        assertEquals(2, wordScore.getScore());
        assertEquals(1, wordScore.getModifier());
    }

    @Test
    public void testScoreGetsAdded() throws Exception {
        Square sq = Square.getNormal(0, 0);
        WordScore wordScore = new WordScore();
        wordScore.addToScore(1);
        wordScore = sq.getScore(bag.getLetter('a'), wordScore);
        assertEquals(2, wordScore.getScore());
        assertEquals(1, wordScore.getModifier());
    }

    @Test
    public void testScoreDoubleWord() throws Exception {
        Square sq = Square.getDoubleWord(0, 0);
        WordScore wordScore = sq.getScore(bag.getLetter('a'), new WordScore());
        assertEquals(2, wordScore.getModifier());
    }

    @Test
    public void testScoreDoubleWordAfterTripleWord() throws Exception {
        Square sq = Square.getDoubleWord(0, 0);
        WordScore wordScore = new WordScore();
        wordScore.setModifier(3);
        wordScore = sq.getScore(bag.getLetter('a'), wordScore);
        assertEquals(3, wordScore.getModifier());
    }

    @Test
    public void testScoreTripleWordAfterDoubleWord() throws Exception {
        Square sq = Square.getTripleWord(0, 0);
        WordScore wordScore = new WordScore();
        wordScore.setModifier(2);
        wordScore = sq.getScore(bag.getLetter('a'), wordScore);
        assertEquals(3, wordScore.getModifier());
    }

    @Test
    public void testFinalScore() {
        WordScore wordScore = new WordScore();
        wordScore.addToScore(5);
        assertEquals(5, wordScore.getFinalScore());
    }

    @Test
    public void testFinalScoreWithAModifier() {
        WordScore wordScore = new WordScore();
        wordScore.setModifier(2);
        wordScore.addToScore(50);
        assertEquals(100, wordScore.getFinalScore());
    }

    @Test
    public void testFinalScoreWithNothingAdded() {
        WordScore wordScore = new WordScore();
        assertEquals(0, wordScore.getFinalScore());
    }

    @Test
    public void testGetScoreForNormalSquare() throws Exception {
        Square sq = Square.getNormal(0, 0);
        sq.setLetter(bag.getLetter('a'));
        WordScore wordScore = sq.getScore(new WordScore());
        assertEquals(1, wordScore.getScore());
        assertEquals(1, wordScore.getModifier());
    }

    @Test
    public void testGetScoreForDoubleLetter() throws Exception {
        Square sq = Square.getDoubleLetter(0, 0);
        sq.setLetter(bag.getLetter('a'));
        WordScore wordScore = sq.getScore(new WordScore());
        assertEquals(1, wordScore.getScore());
        assertEquals(1, wordScore.getModifier());
    }

    @Test
    public void testGetScoreForDoubleWord() throws Exception {
        Square sq = Square.getDoubleWord(0, 0);
        sq.setLetter(bag.getLetter('a'));
        WordScore wordScore = sq.getScore(new WordScore());
        assertEquals(1, wordScore.getScore());
        assertEquals(1, wordScore.getModifier());
    }

}
