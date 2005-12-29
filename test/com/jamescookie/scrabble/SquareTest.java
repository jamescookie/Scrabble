package com.jamescookie.scrabble;

/**
 * @author ukjamescook
 */
public class SquareTest extends Tester {

    public void testScoreForNormalSquare() throws Exception {
        Square sq = Square.getNormal(0,0);
        WordScore wordScore = sq.getScore(new Letter('a'), new WordScore());
        assertEquals(1, wordScore.getScore());
        assertEquals(1, wordScore.getModifier());
    }

    public void testScoreForDoubleSquare() throws Exception {
        Square sq = Square.getDoubleLetter(0,0);
        WordScore wordScore = sq.getScore(new Letter('a'), new WordScore());
        assertEquals(2, wordScore.getScore());
        assertEquals(1, wordScore.getModifier());
    }

    public void testScoreGetsAdded() throws Exception {
        Square sq = Square.getNormal(0,0);
        WordScore wordScore = new WordScore();
        wordScore.addToScore(1);
        wordScore = sq.getScore(new Letter('a'), wordScore);
        assertEquals(2, wordScore.getScore());
        assertEquals(1, wordScore.getModifier());
    }

    public void testScoreDoubleWord() throws Exception {
        Square sq = Square.getDoubleWord(0,0);
        WordScore wordScore = sq.getScore(new Letter('a'), new WordScore());
        assertEquals(2, wordScore.getModifier());
    }

    public void testScoreDoubleWordAfterTripleWord() throws Exception {
        Square sq = Square.getDoubleWord(0,0);
        WordScore wordScore = new WordScore();
        wordScore.setModifier(3);
        wordScore = sq.getScore(new Letter('a'), wordScore);
        assertEquals(3, wordScore.getModifier());
    }

    public void testScoreTripleWordAfterDoubleWord() throws Exception {
        Square sq = Square.getTripleWord(0,0);
        WordScore wordScore = new WordScore();
        wordScore.setModifier(2);
        wordScore = sq.getScore(new Letter('a'), wordScore);
        assertEquals(3, wordScore.getModifier());
    }

    public void testFinalScore() throws Exception {
        WordScore wordScore = new WordScore();
        wordScore.addToScore(5);
        assertEquals(5, wordScore.getFinalScore());
    }

    public void testFinalScoreWithAModifier() throws Exception {
        WordScore wordScore = new WordScore();
        wordScore.setModifier(2);
        wordScore.addToScore(50);
        assertEquals(100, wordScore.getFinalScore());
    }

    public void testFinalScoreWithNothingAdded() throws Exception {
        WordScore wordScore = new WordScore();
        assertEquals(0, wordScore.getFinalScore());
    }

    public void testGetScoreForNormalSquare() throws Exception {
        Square sq = Square.getNormal(0,0);
        sq.setLetter(new Letter('a'));
        WordScore wordScore = sq.getScore(new WordScore());
        assertEquals(1, wordScore.getScore());
        assertEquals(1, wordScore.getModifier());
    }

    public void testGetScoreForDoubleLetter() throws Exception {
        Square sq = Square.getDoubleLetter(0,0);
        sq.setLetter(new Letter('a'));
        WordScore wordScore = sq.getScore(new WordScore());
        assertEquals(1, wordScore.getScore());
        assertEquals(1, wordScore.getModifier());
    }

    public void testGetScoreForDoubleWord() throws Exception {
        Square sq = Square.getDoubleWord(0,0);
        sq.setLetter(new Letter('a'));
        WordScore wordScore = sq.getScore(new WordScore());
        assertEquals(1, wordScore.getScore());
        assertEquals(1, wordScore.getModifier());
    }

}
