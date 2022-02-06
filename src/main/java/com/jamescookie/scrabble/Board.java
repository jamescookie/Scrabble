package com.jamescookie.scrabble;

import com.jamescookie.scrabble.types.Bag;
import com.jamescookie.scrabble.types.Game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;

/**
 * @author ukjamescook
 */
public class Board {
    public static final int BOARD_SIZE = 15;
    public static final int MID_POINT = 7;
    private static final int BONUS = 40;

    private final Wordsmith wordsmith;
    private final Square[][] squares;
    private final Bag bag;
    private List<Word> words = new ArrayList<>();
    private List<Square> squaresAddedThisTurn;
    private List<Word> wordsAddedThisTurn;
    private boolean dryRun = false;

    public Board(Wordsmith wordsmith, Game game) {
        this.wordsmith = wordsmith;
        bag = game.getBag();
        squares = game.getSquares();
        clearLetters();
    }

    public void clear() {
        clearLetters();
        words = new ArrayList<>();
    }

    public void setDryRun(boolean dryRun) {
        this.dryRun = dryRun;
        bag.setDryRun(dryRun);
    }

    public Bag getBag() {
        return bag;
    }

    List<Word> getWords() {
        return words;
    }

    public Square[][] getEntireBoard() {
        return squares;
    }

    public int putLetters(String letters, Square startPoint, Direction direction) throws ScrabbleException {
        List<Letter> bagLetters = bag.getLetters(letters);
        try {
            return putLetters(bagLetters, startPoint, direction);
        } catch (ScrabbleException e) {
            if (!dryRun) {
                bag.returnLetters(bagLetters);
            }
            throw e;
        }
    }

    private int putLetters(List<Letter> letters, Square startPoint, Direction direction) throws ScrabbleException {
        int score;
        if (letters.size() == 1) {
            direction = null;
        }
        List<Square> squares = getSquares(direction, startPoint, letters.size());
        if (words.size() == 0) {
            score = putFirstWordDown(startPoint, direction, letters, squares);
        } else {
            if (isNewWordGoingToTouchExistingWord(squares, words)) {
                score = checkAbleThenPutNewWordDown(startPoint, direction, letters);
            } else {
                throw new ScrabbleException(letters + " starting from " + startPoint + " does not touch any other word.");
            }
        }
        if (letters.size() == 7) {
            score += BONUS;
        }
        return score;
    }

    public Square getSquare(int row, int column) throws ScrabbleException {
        if (withinBoard(row, column)) {
            throw new ScrabbleException("Square invalid: row - " + row + ", col - " + column);
        }
        return squares[row][column];
    }

    public Square findNextSquare(Square square, Direction direction) throws ScrabbleException {
        int row = square.getRow();
        int column = square.getCol();

        if (Direction.DOWN.equals(direction)) {
            ++row;
        } else if (Direction.UP.equals(direction)) {
            --row;
        } else if (Direction.ACROSS.equals(direction)) {
            ++column;
        } else if (Direction.BACKWARDS.equals(direction)) {
            --column;
        }

        return getSquare(row, column);
    }

    public String exportBoard() {
        StringBuilder sb = new StringBuilder();
        for (Square[] row : squares) {
            sb.append(getCharactersFromSquares(row, true)).append(System.lineSeparator());
        }
        for (Word word : words) {
            sb.append(word.export()).append(System.lineSeparator());
        }
        return sb.toString();
    }

    public void importBoard(String s) throws IOException, ScrabbleException {
        BufferedReader r = new BufferedReader(new StringReader(s));
        for (int i = 0; i < BOARD_SIZE; i++) {
            char[] rowRepresentation = r.readLine().toCharArray();
            for (int j = 0, pos = 0; j < BOARD_SIZE; j++, pos++) {
                char c = rowRepresentation[pos];
                if (c == ' ') {
                    getSquare(i, j).clearLetter();
                } else if (c == Utils.WILDCARD) {
                    getSquare(i, j).changeLetter(((Wildcard) bag.getLetter(c)).setCharacter(rowRepresentation[++pos]));
                } else {
                    getSquare(i, j).changeLetter(bag.getLetter(c));
                }
            }
        }
        words = new ArrayList<>();
        String line = r.readLine();
        while (line != null && line.length() > 0) {
            words.add(Word.generate(line, this));
            line = r.readLine();
        }
    }

    public Map<String, String[]> getRowsAndColumns() {
        String[] rows = new String[BOARD_SIZE];
        String[] cols = new String[BOARD_SIZE];
        Map<String, String[]> boardLetters = Map.of(
                "rows", rows,
                "cols", cols
        );
        Square[][] columns = new Square[BOARD_SIZE][BOARD_SIZE];

        for (int i = 0; i < BOARD_SIZE; i++) {
            Square[] row = squares[i];
            rows[i] = getCharactersFromSquares(row, false).trim();
            for (int j = 0; j < BOARD_SIZE; j++) {
                columns[j][i] = row[j];
            }
        }

        for (int i = 0; i < BOARD_SIZE; i++) {
            cols[i] = getCharactersFromSquares(columns[i], false).trim();
        }

        return boardLetters;
    }

    public static String getCharactersFromSquares(Square[] squares, boolean showWildcards) {
        StringBuilder sb = new StringBuilder();
        for (Square square : squares) {
            if (square.hasLetter()) {
                if (showWildcards && square.getLetter().isWildcard()) {
                    sb.append(Utils.WILDCARD);
                }
                sb.append(square.getCharacter());
            } else {
                sb.append(' ');
            }
        }
        return sb.toString();
    }

    private static boolean isNewWordGoingToTouchExistingWord(List<Square> squares, List<Word> words) {
        boolean touching = false;
        List<Square> wordSquares;
        for (Word word : words) {
            wordSquares = word.getSquares();
            for (Square wordSquare : wordSquares) {
                for (Square square : squares) {
                    if (Utils.isAdjacent(square, wordSquare)) {
                        touching = true;
                        break;
                    }
                }
                if (touching) break;
            }
            if (touching) break;
        }
        return touching;
    }

    private void clearLetters() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0, pos = 0; j < BOARD_SIZE; j++, pos++) {
                try {
                    getSquare(i, j).clearLetter();
                } catch (ScrabbleException e) {
                    //not going to happen
                }
            }
        }
    }

    private List<Square> getSquares(Direction direction, Square startPoint, int wordLength) throws ScrabbleException {
        List<Square> squares = new ArrayList<>();
        int row = startPoint.getRow();
        int column = startPoint.getCol();
        if (Direction.ACROSS.equals(direction)) {
            int j = column + wordLength;
            if (j > BOARD_SIZE) {
                throw new ScrabbleException("Too many squares!");
            }
            for (int i = column; i < j; i++) {
                squares.add(getSquare(row, i));
            }
        } else {
            int j = row + wordLength;
            if (j > BOARD_SIZE) {
                throw new ScrabbleException("Too many squares!");
            }
            for (int i = row; i < j; i++) {
                squares.add(getSquare(i, column));
            }
        }
        return squares;
    }

    private int putFirstWordDown(Square startPoint, Direction direction, List<Letter> letters, List<Square> squares) throws ScrabbleException {
        int score;
        int row = startPoint.getRow();
        int column = startPoint.getCol();
        wordsAddedThisTurn = new ArrayList<>();
        if (Direction.ACROSS.equals(direction)) {
            if (row == MID_POINT &&
                    (column <= MID_POINT && (column + letters.size() > MID_POINT))) {
                score = addWord(letters, direction, squares, false);
            } else {
                throw new ScrabbleException(startPoint + " is not a valid starting point");
            }
        } else {
            if (column == MID_POINT &&
                    (row <= MID_POINT && (row + letters.size() > MID_POINT))) {
                score = addWord(letters, direction, getSquares(direction, startPoint, letters.size()), false);
            } else {
                throw new ScrabbleException(startPoint + " is not a valid starting point");
            }
        }
        return score;
    }

    private int checkAbleThenPutNewWordDown(Square startPoint, Direction direction, List<Letter> letters) throws ScrabbleException {
        Set<Word> adjacentWords = new HashSet<>();
        Map<Square, Letter> squaresMap = new LinkedHashMap<>();

        checkNotStartingOnAnotherWord(startPoint);
        findSquaresAndAdjacentWords(startPoint, letters, direction, squaresMap, adjacentWords);

        return putNewWordDown(startPoint, direction, letters, adjacentWords, squaresMap);
    }

    private synchronized int putNewWordDown(Square startPoint, Direction direction, List<Letter> letters,
                                            Set<Word> adjacentWords, Map<Square, Letter> squaresMap) throws ScrabbleException {
        int score = 0;
        List<Word> replacedWords = new ArrayList<>();
        boolean add = false;

        squaresAddedThisTurn = new ArrayList<>();
        wordsAddedThisTurn = new ArrayList<>();

        Board.DoubleWordCreation doubleAdd = checkForDoubleWordCreation(squaresMap, direction, replacedWords);
        squaresMap = doubleAdd.getMap();
        score += doubleAdd.getScore();
        direction = doubleAdd.getDirection();

        replacedWords.forEach(adjacentWords::remove);

        for (Word word : adjacentWords) {
            if (direction == null) {
                if (squaresMap.size() > 2) {
                    add = true;
                } else {
                    Letter letter = letters.get(0);
                    int tmpScore = createNewWordIfSquaresAreAdjacent(word, startPoint, Direction.getAdjacent(word.getDirection()), letter, replacedWords);
                    if (tmpScore == 0) {
                        score += addLetterToWord(letter, startPoint, word);
                    } else {
                        score += tmpScore;
                    }
                }
            } else {
                add = true;
                score += applyNewWordToExistingWord(word, squaresMap, direction, startPoint, replacedWords);
            }
        }

        ArrayList<Square> squares = getSquaresFromMap(squaresMap);
        if (replacedWords.size() > 0) {
            int i = 0;
            for (Word word : replacedWords) {
                if (i++ == 0) {
                    score += replaceWord(word, getLettersFromMap(squaresMap), direction, squares);
                } else {
                    if (!dryRun) {
                        words.remove(word);
                    }
                }
            }
        } else {
            if (add) {
                score += addWord(getLettersFromMap(squaresMap), direction, squares, true);
            }
        }
        return score;
    }

    private int applyNewWordToExistingWord(Word existingWord, Map<Square, Letter> newWord, Direction direction, Square startPoint, List<Word> replacedWords) throws ScrabbleException {
        int score = 0;
        Direction wordDirection = existingWord.getDirection();

        if (wordDirection == null) {
            replacedWords.add(existingWord);
        } else if (direction.equals(wordDirection)) {
            if (wordDirection.equals(Direction.ACROSS)) {
                if (startPoint.getRow() != existingWord.getStartingPoint().getRow()) {
                    score = putNewWordAlongsideExistingWord(newWord, direction, existingWord, replacedWords);
                } else {
                    replacedWords.add(existingWord);
                }
            } else {
                if (startPoint.getCol() != existingWord.getStartingPoint().getCol()) {
                    score = putNewWordAlongsideExistingWord(newWord, direction, existingWord, replacedWords);
                } else {
                    replacedWords.add(existingWord);
                }
            }
        } else {
            score = crossWords(newWord, direction, existingWord);
        }
        return score;
    }

    private DoubleWordCreation checkForDoubleWordCreation(Map<Square, Letter> newWord, Direction direction, List<Word> replacedWords) throws ScrabbleException {
        int score = 0;
        if (direction == null) {
            Map<Square, Letter> wordAcross = findAdditionalLettersAtEitherEndOfNewWordAndFindReplacedWords(newWord, Direction.ACROSS, replacedWords);
            Map<Square, Letter> wordDown = findAdditionalLettersAtEitherEndOfNewWordAndFindReplacedWords(newWord, Direction.DOWN, replacedWords);
            if (wordAcross.size() > 1) {
                newWord = wordAcross;
                direction = Direction.ACROSS;
                if (wordDown.size() > 1) {
                    score = addWord(getLettersFromMap(wordDown), Direction.DOWN, getSquaresFromMap(wordDown), true);
                }
            } else if (wordDown.size() > 1) {
                newWord = wordDown;
                direction = Direction.DOWN;
            }
        } else {
            newWord = findAdditionalLettersAtEitherEndOfNewWordAndFindReplacedWords(newWord, direction, replacedWords);
        }
        return new DoubleWordCreation(newWord, score, direction);
    }

    private Map<Square, Letter> findAdditionalLettersAtEitherEndOfNewWordAndFindReplacedWords(Map<Square, Letter> newWord, Direction direction, List<Word> replacedWords) {
        ArrayList<Square> squares = getSquaresFromMap(newWord);
        Square firstSquare = squares.get(0);
        Square lastSquare = squares.get(squares.size() - 1);
        List<Square> priorSquares = findAdditionalLetters(firstSquare, Direction.getOpposite(direction));
        List<Square> followingSquares = findAdditionalLetters(lastSquare, direction);
        Map<Square, Letter> newMap = new LinkedHashMap<>();

        if (priorSquares.size() > 0) {
            for (Word word : words) {
                if (priorSquares.containsAll(word.getSquares())) {
                    replacedWords.add(word);
                    break;
                }
            }
            newMap = new LinkedHashMap<>();
            for (ListIterator<Square> iterator = priorSquares.listIterator(priorSquares.size()); iterator.hasPrevious(); ) {
                Square square = iterator.previous();
                newMap.put(square, square.getLetter());
            }
        }

        newMap.putAll(newWord);

        if (followingSquares.size() > 0) {
            for (Word word : words) {
                if (followingSquares.containsAll(word.getSquares())) {
                    replacedWords.add(word);
                    break;
                }
            }
            for (Square square : followingSquares) {
                newMap.put(square, square.getLetter());
            }
        }

        return newMap;
    }

    private void findSquaresAndAdjacentWords(Square startPoint, List<Letter> letters, Direction direction, Map<Square, Letter> squaresMap, Set<Word> adjacentWords) throws ScrabbleException {
        List<Square> wordSquares;
        Square square = startPoint;
        boolean found;
        for (int i = 0; i < letters.size(); i++) {
            Letter letter = letters.get(i);
            found = false;
            if (i != 0) {
                square = findNextSquare(square, direction);
            }
            if (square.hasLetter()) {
                squaresMap.put(square, square.getLetter());
                --i;
                for (Word word : words) {
                    wordSquares = word.getSquares();
                    for (Square wordSquare : wordSquares) {
                        if (square.equals(wordSquare)) {
                            adjacentWords.add(word);
                            found = true;
                            break;
                        }
                    }
                    if (found) break;
                }
            } else {
                squaresMap.put(square, letter);
                for (Word word : words) {
                    wordSquares = word.getSquares();
                    for (Square wordSquare : wordSquares) {
                        if (Utils.isAdjacent(square, wordSquare)) {
                            adjacentWords.add(word);
                            break;
                        }
                    }
                }
            }
        }
    }

    private int putNewWordAlongsideExistingWord(Map<Square, Letter> newWord, Direction direction, Word existingWord, List<Word> replacedWords) throws ScrabbleException {
        int score = 0;
        ArrayList<Square> squares = getSquaresFromMap(newWord);
        Direction adjacentDirection = Direction.getAdjacent(direction);

        for (Square square : squares) {
            score += createNewWordIfSquaresAreAdjacent(existingWord, square, adjacentDirection, newWord.get(square), replacedWords);
        }
        return score;
    }

    private int createNewWordIfSquaresAreAdjacent(Word existingWord, Square square, Direction adjacentDirection, Letter letter, List<Word> replacedWords) throws ScrabbleException {
        int score = 0;

        for (Square wordSquare : existingWord.getSquares()) {
            if (Utils.isAdjacentInDirection(wordSquare, square, adjacentDirection)) {
                Map<Square, Letter> map = new LinkedHashMap<>();
                map.put(square, letter);
                map = findAdditionalLettersAtEitherEndOfNewWordAndFindReplacedWords(map, adjacentDirection, replacedWords);
                ArrayList<Square> squares = getSquaresFromMap(map);

                score = addWord(getLettersFromMap(map), adjacentDirection, squares, true);
                break;
            }
        }
        return score;
    }

    private int crossWords(Map<Square, Letter> newWord, Direction direction, Word existingWord) throws ScrabbleException {
        int score = 0;
        ArrayList<Square> squares = getSquaresFromMap(newWord);
        Direction adjacentDirection = Direction.getAdjacent(direction);
        List<Square> wordSquares = existingWord.getSquares();

        for (Square square : squares) {
            Letter letter = newWord.get(square);
            if (!wordSquares.contains(square)) {
                for (Square wordSquare : wordSquares) {
                    if (Utils.isAdjacentInDirection(wordSquare, square, adjacentDirection)) {
                        Map<Square, Letter> map = new LinkedHashMap<>();
                        ArrayList<Word> replacedWords = new ArrayList<>();
                        map.put(square, letter);
                        map = findAdditionalLettersAtEitherEndOfNewWordAndFindReplacedWords(map, adjacentDirection, replacedWords);

                        if (replacedWords.size() > 0) {
                            score = replaceWord(replacedWords.get(0), getLettersFromMap(map), adjacentDirection, getSquaresFromMap(map));
                            if (replacedWords.size() > 1) {
                                if (!dryRun) {
                                    words.remove(replacedWords.get(1));
                                }
                            }
                        }

                        break;
                    }
                }
            }
        }
        return score;
    }

    private int addLetterToWord(Letter letter, Square square, Word word) throws ScrabbleException {
        Direction direction;
        List<Letter> newLetters = new ArrayList<>(word.getLetters());
        ArrayList<Square> newSquares = new ArrayList<>(word.getSquares());
        Square startPoint = word.getStartingPoint();

        if (word.getDirection() == null) {
            direction = determineDirection(startPoint, square);
        } else {
            direction = word.getDirection();
        }
        if (Utils.isImmediatelyBefore(square, startPoint)) {
            newLetters.add(0, letter);
            newSquares.add(0, square);
        } else {
            newLetters.add(letter);
            newSquares.add(square);
        }
        return replaceWord(word, newLetters, direction, newSquares);
    }

    static Direction determineDirection(Square square1, Square square2) {
        Direction direction = null;
        if (square1.getRow() == square2.getRow()) {
            direction = Direction.ACROSS;
        } else if (square1.getCol() == square2.getCol()) {
            direction = Direction.DOWN;
        }
        return direction;
    }

    private List<Square> findAdditionalLetters(Square square, Direction direction) {
        List<Square> newSquares = new ArrayList<>();
        try {
            do {
                square = findNextSquare(square, direction);
                if (square.hasLetter()) {
                    newSquares.add(square);
                }
            } while (square.hasLetter());
        } catch (ScrabbleException e) {
            // Off board, so no more letters possible
        }
        return newSquares;
    }

    private static void checkNotStartingOnAnotherWord(Square startPoint) throws ScrabbleException {
        if (startPoint.hasLetter()) {
            throw new ScrabbleException(startPoint + " is not a valid starting point");
        }
    }

    private int addWord(List<Letter> letters, Direction direction, List<Square> squares, boolean record) throws ScrabbleException {
        WordScore wordScore = checkAndScoreWord(squares, letters, record);
        if (wordScore == null) return 0;
        addWordToCollection(direction, squares, record);
        return wordScore.getFinalScore();
    }

    private int replaceWord(Word word, List<Letter> letters, Direction direction, List<Square> squares) throws ScrabbleException {
        WordScore wordScore = checkAndScoreWord(squares, letters, true);
        if (wordScore == null) return 0;
        if (!dryRun) {
            words.remove(word);
        }
        addWordToCollection(direction, squares, true);
        return wordScore.getFinalScore();
    }

    private void addWordToCollection(Direction direction, List<Square> squares, boolean record) {
        Word word = new Word(direction, squares);
        if (record) {
            wordsAddedThisTurn.add(word);
        }
        if (!dryRun) {
            words.add(word);
        }
    }

    private WordScore checkAndScoreWord(List<Square> squares, List<Letter> letters, boolean record) throws ScrabbleException {
        WordScore wordScore = null;
        if (!wordAlreadyExists(squares)) {
            checkValid(letters);
            wordScore = setLettersInSquares(letters, squares, record);
        }
        return wordScore;
    }

    private WordScore setLettersInSquares(List<Letter> letters, List<Square> squares, boolean record) throws ScrabbleException {
        WordScore wordScore = new WordScore();
        for (int i = 0; i < letters.size(); i++) {
            Square square = squares.get(i);
            Letter letter = letters.get(i);
            if (square.hasLetter()) {
                if (record && squaresAddedThisTurn.contains(square)) {
                    wordScore = square.getScore(letter, wordScore);
                } else {
                    wordScore = square.getScore(wordScore);
                }
            } else {
                if (!dryRun) {
                    square.setLetter(letter);
                }
                wordScore = square.getScore(letter, wordScore);
                if (record) {
                    squaresAddedThisTurn.add(square);
                }
            }
        }
        return wordScore;
    }

    private static List<Letter> getLettersFromMap(Map<Square, Letter> newWord) {
        return new ArrayList<>(newWord.values());
    }

    private static ArrayList<Square> getSquaresFromMap(Map<Square, Letter> squaresMap) {
        return new ArrayList<>(squaresMap.keySet());
    }

    private void checkValid(List<Letter> letters) throws ScrabbleException {
        if (!wordsmith.isValidWord(getCharactersFromLetters(letters))) {
            throw new ScrabbleException(letters + " does not make a valid word.");
        }
    }

    private boolean wordAlreadyExists(List<Square> squares) {
        boolean found = false;
        for (Word word : words) {
            found = wordOccupiesSquares(word, squares);
            if (found) break;
        }
        if (dryRun && !found) {
            for (Word word : wordsAddedThisTurn) {
                found = wordOccupiesSquares(word, squares);
                if (found) break;
            }
        }
        return found;
    }

    private static boolean wordOccupiesSquares(Word word, List<Square> squares) {
        boolean found = false;
        List<Square> wordSquares = word.getSquares();
        if (wordSquares.size() == squares.size() && wordSquares.containsAll(squares)) {
            found = true;
        }
        return found;
    }

    private static String getCharactersFromLetters(List<Letter> letters) {
        StringBuilder chars = new StringBuilder();
        for (Letter letter : letters) {
            chars.append(letter.getCharacter());
        }
        return chars.toString();
    }

    private static boolean withinBoard(int row, int column) {
        return row >= BOARD_SIZE || row < 0 || column >= BOARD_SIZE || column < 0;
    }

    private static class DoubleWordCreation {
        private final Map<Square, Letter> map;
        private final int score;
        private final Direction direction;

        public DoubleWordCreation(Map<Square, Letter> map, int score, Direction direction) {
            this.map = map;
            this.score = score;
            this.direction = direction;
        }

        public Map<Square, Letter> getMap() {
            return map;
        }

        public int getScore() {
            return score;
        }

        public Direction getDirection() {
            return direction;
        }
    }
}
