package com.jamescookie.scrabble;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.*;

/**
 * @author ukjamescook
 */
public class Board {
    public static final int BOARD_SIZE = 15;
    public static final int MID_POINT = 7;
    private static final int BONUS = 40;

    private final Wordsmith wordsmith;
    private static final Square[][] squares = new Square[][] {
            {Square.getDoubleLetter(0, 0),  Square.getNormal(0, 1),         Square.getNormal(0, 2),         Square.getTripleWord(0, 3),         Square.getNormal(0, 4),         Square.getNormal(0, 5),         Square.getNormal(0, 6),         Square.getDoubleLetter(0, 7),   Square.getNormal(0, 8),         Square.getNormal(0, 9),         Square.getNormal(0, 10),         Square.getTripleWord(0, 11),     Square.getNormal(0, 12),         Square.getNormal(0, 13),         Square.getDoubleLetter(0, 14)},
            {Square.getNormal(1, 0),        Square.getNormal(1, 1),         Square.getDoubleWord(1, 2),     Square.getNormal(1, 3),             Square.getNormal(1, 4),         Square.getDoubleLetter(1, 5),   Square.getNormal(1, 6),         Square.getNormal(1, 7),         Square.getNormal(1, 8),         Square.getTripleLetter(1, 9),   Square.getNormal(1, 10),         Square.getNormal(1, 11),         Square.getDoubleWord(1, 12),     Square.getNormal(1, 13),         Square.getNormal(1, 14)},
            {Square.getNormal(2, 0),        Square.getDoubleWord(2, 1),     Square.getNormal(2, 2),         Square.getNormal(2, 3),             Square.getNormal(2, 4),         Square.getNormal(2, 5),         Square.getNormal(2, 6),         Square.getNormal(2, 7),         Square.getNormal(2, 8),         Square.getNormal(2, 9),         Square.getNormal(2, 10),         Square.getNormal(2, 11),         Square.getNormal(2, 12),         Square.getDoubleWord(2, 13),     Square.getNormal(2, 14)},
            {Square.getTripleWord(3, 0),    Square.getNormal(3, 1),         Square.getNormal(3, 2),         Square.getDoubleLetter(3, 3),       Square.getNormal(3, 4),         Square.getNormal(3, 5),         Square.getTripleLetter(3, 6),   Square.getNormal(3, 7),         Square.getDoubleLetter(3, 8),   Square.getNormal(3, 9),         Square.getNormal(3, 10),         Square.getDoubleLetter(3, 11),   Square.getNormal(3, 12),         Square.getNormal(3, 13),         Square.getTripleWord(3, 14)},
            {Square.getNormal(4, 0),        Square.getNormal(4, 1),         Square.getNormal(4, 2),         Square.getNormal(4, 3),             Square.getNormal(4, 4),         Square.getDoubleWord(4, 5),     Square.getNormal(4, 6),         Square.getNormal(4, 7),         Square.getNormal(4, 8),         Square.getDoubleWord(4, 9),     Square.getNormal(4, 10),         Square.getNormal(4, 11),         Square.getNormal(4, 12),         Square.getNormal(4, 13),         Square.getNormal(4, 14)},
            {Square.getNormal(5, 0),        Square.getTripleLetter(5, 1),   Square.getNormal(5, 2),         Square.getNormal(5, 3),             Square.getDoubleWord(5, 4),     Square.getNormal(5, 5),         Square.getNormal(5, 6),         Square.getNormal(5, 7),         Square.getNormal(5, 8),         Square.getNormal(5, 9),         Square.getDoubleWord(5, 10),     Square.getNormal(5, 11),         Square.getNormal(5, 12),         Square.getDoubleLetter(5, 13),   Square.getNormal(5, 14)},
            {Square.getNormal(6, 0),        Square.getNormal(6, 1),         Square.getNormal(6, 2),         Square.getDoubleLetter(6, 3),       Square.getNormal(6, 4),         Square.getNormal(6, 5),         Square.getDoubleLetter(6, 6),   Square.getNormal(6, 7),         Square.getDoubleLetter(6, 8),   Square.getNormal(6, 9),         Square.getNormal(6, 10),         Square.getTripleLetter(6, 11),   Square.getNormal(6, 12),         Square.getNormal(6, 13),         Square.getNormal(6, 14)},
            {Square.getDoubleLetter(7, 0),  Square.getNormal(7, 1),         Square.getNormal(7, 2),         Square.getNormal(7, 3),             Square.getNormal(7, 4),         Square.getNormal(7, 5),         Square.getNormal(7, 6),         Square.getDoubleWord(7, 7),     Square.getNormal(7, 8),         Square.getNormal(7, 9),         Square.getNormal(7, 10),         Square.getNormal(7, 11),         Square.getNormal(7, 12),         Square.getNormal(7, 13),         Square.getDoubleLetter(7, 14)},
            {Square.getNormal(8, 0),        Square.getNormal(8, 1),         Square.getNormal(8, 2),         Square.getTripleLetter(8, 3),       Square.getNormal(8, 4),         Square.getNormal(8, 5),         Square.getDoubleLetter(8, 6),   Square.getNormal(8, 7),         Square.getDoubleLetter(8, 8),   Square.getNormal(8, 9),         Square.getNormal(8, 10),         Square.getDoubleLetter(8, 11),   Square.getNormal(8, 12),         Square.getNormal(8, 13),         Square.getNormal(8, 14)},
            {Square.getNormal(9, 0),        Square.getDoubleLetter(9, 1),   Square.getNormal(9, 2),         Square.getNormal(9, 3),             Square.getDoubleWord(9, 4),     Square.getNormal(9, 5),         Square.getNormal(9, 6),         Square.getNormal(9, 7),         Square.getNormal(9, 8),         Square.getNormal(9, 9),         Square.getDoubleWord(9, 10),     Square.getNormal(9, 11),         Square.getNormal(9, 12),         Square.getTripleLetter(9, 13),   Square.getNormal(9, 14)},
            {Square.getNormal(10, 0),       Square.getNormal(10, 1),        Square.getNormal(10, 2),         Square.getNormal(10, 3),             Square.getNormal(10, 4),         Square.getDoubleWord(10, 5),     Square.getNormal(10, 6),         Square.getNormal(10, 7),         Square.getNormal(10, 8),         Square.getDoubleWord(10, 9),     Square.getNormal(10, 10),         Square.getNormal(10, 11),         Square.getNormal(10, 12),         Square.getNormal(10, 13),         Square.getNormal(10, 14)},
            {Square.getTripleWord(11, 0),   Square.getNormal(11, 1),        Square.getNormal(11, 2),         Square.getDoubleLetter(11, 3),       Square.getNormal(11, 4),         Square.getNormal(11, 5),         Square.getDoubleLetter(11, 6),   Square.getNormal(11, 7),         Square.getTripleLetter(11, 8),   Square.getNormal(11, 9),         Square.getNormal(11, 10),         Square.getDoubleLetter(11, 11),   Square.getNormal(11, 12),         Square.getNormal(11, 13),         Square.getTripleWord(11, 14)},
            {Square.getNormal(12, 0),       Square.getDoubleWord(12, 1),    Square.getNormal(12, 2),         Square.getNormal(12, 3),             Square.getNormal(12, 4),         Square.getNormal(12, 5),         Square.getNormal(12, 6),         Square.getNormal(12, 7),         Square.getNormal(12, 8),         Square.getNormal(12, 9),         Square.getNormal(12, 10),         Square.getNormal(12, 11),         Square.getNormal(12, 12),         Square.getDoubleWord(12, 13),     Square.getNormal(12, 14)},
            {Square.getNormal(13, 0),       Square.getNormal(13, 1),        Square.getDoubleWord(13, 2),     Square.getNormal(13, 3),             Square.getNormal(13, 4),         Square.getTripleLetter(13, 5),   Square.getNormal(13, 6),         Square.getNormal(13, 7),         Square.getNormal(13, 8),         Square.getDoubleLetter(13, 9),   Square.getNormal(13, 10),         Square.getNormal(13, 11),         Square.getDoubleWord(13, 12),     Square.getNormal(13, 13),         Square.getNormal(13, 14)},
            {Square.getDoubleLetter(14, 0), Square.getNormal(14, 1),        Square.getNormal(14, 2),         Square.getTripleWord(14, 3),         Square.getNormal(14, 4),         Square.getNormal(14, 5),         Square.getNormal(14, 6),         Square.getDoubleLetter(14, 7),   Square.getNormal(14, 8),         Square.getNormal(14, 9),         Square.getNormal(14, 10),         Square.getTripleWord(14, 11),     Square.getNormal(14, 12),         Square.getNormal(14, 13),         Square.getDoubleLetter(14, 14)},
    };
    private List<Word> words = new ArrayList<Word>();
    private List<Square> squaresAddedThisTurn;
    private List<Word> wordsAddedThisTurn;
    private boolean testing = false;

    public Board(Wordsmith wordsmith) {
        this.wordsmith = wordsmith;
        clearLetters();
    }

    public int putLetters(String letters, Square startPoint, Direction direction) throws ScrabbleException {
        return putLetters(Bag.getLetters(letters), startPoint, direction);
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
                throw new ScrabbleException(letters + " starting from "+startPoint+ " does not touch any other word.");
            }
        }
        if (letters.size() == 7) {
            score += BONUS;
        }
        return score;
    }

    public void setTesting(boolean testing) {
        this.testing = testing;
    }

    public static Square getSquare(int row, int column) throws ScrabbleException {
        if (withinBoard(row, column)) {
            throw new ScrabbleException("Sqaure invalid: row - "+row + ", col - "+column);
        }
        return squares[row][column];
    }

    public static Square findNextSquare(Square square, Direction direction) throws ScrabbleException {
        int row = square.getRow();
        int column = square.getColumn();

        if (Direction.DOWN.equals(direction)) {
            ++row;
        } else if (Direction.UP.equals(direction)) {
            --row;
        } else if (Direction.FORWARDS.equals(direction)) {
            ++column;
        } else if (Direction.BACKWARDS.equals(direction)) {
            --column;
        }

        return getSquare(row, column);
    }

    public void export(BufferedWriter w) throws IOException {
        for (Square[] row : squares) {
            w.write(getCharactersFromSquares(row, true));
            w.newLine();
        }
        for (Word word : words) {
            w.write(word.export());
            w.newLine();
        }
    }

    public void generate(BufferedReader r) throws IOException, ScrabbleException {
        for (int i = 0; i < BOARD_SIZE; i++) {
            char[] rowReprestation = r.readLine().toCharArray();
            for (int j = 0, pos = 0; j < BOARD_SIZE; j++, pos++) {
                char c = rowReprestation[pos];
                if (c == ' ') {
                    getSquare(i, j).clearLetter();
                } else if (c == Utils.WILDCARD) {
                    getSquare(i, j).changeLetter(new Wildcard(rowReprestation[++pos]));
                } else {
                    getSquare(i, j).changeLetter(Bag.getLetter(c));
                }
            }
        }
        words = new ArrayList<Word>();
        String line = r.readLine();
        while (line != null && line.length() > 0) {
            words.add(Word.generate(line));
            line = r.readLine();
        }
    }

    public void clear() {
        clearLetters();
        words = new ArrayList<Word>();
    }

    public static String[][] getBoardLetters() {
        String[] rows = new String[BOARD_SIZE];
        String[] cols = new String[BOARD_SIZE];
        String[][] boardLetters = new String[][] {rows, cols};
        //noinspection unchecked
        ArrayList<Square>[] columns = new ArrayList[BOARD_SIZE];

        for (int i = 0; i < columns.length; i++) {
            columns[i] = new ArrayList<Square>();
        }

        for (int i = 0; i < squares.length; i++) {
            Square[] row = squares[i];
            rows[i] = getCharactersFromSquares(row, false).trim();
            for (int j = 0; j < row.length; j++) {
                columns[j].add(row[j]);
            }
        }

        for (int i = 0; i < columns.length; i++) {
            cols[i] = getCharactersFromSquares(columns[i].toArray(new Square[BOARD_SIZE]), false).trim();
        }

        return boardLetters;
    }

    public static String getCharactersFromBoard() {
        StringBuffer sb = new StringBuffer();
        for (Square[] row : squares) {
            for (Square square : row) {
                if (square.hasLetter()) {
                    if (square.getLetter().isWildcard()) {
                        sb.append(Utils.WILDCARD);
                    } else {
                        sb.append(square.getCharacter());
                    }
                }
            }
        }
        return sb.toString();
    }

    List<Word> getWords() {
        return words;
    }

    static String getBoard() {
        StringBuffer sb = new StringBuffer();
        for (Square[] row : squares) {
            sb.append(getCharactersFromSquares(row, false)).append("\n");
        }
        return sb.toString();
    }

    private static void clearLetters() {
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

    private static String getCharactersFromSquares(Square[] squares, boolean showWildcards) {
        StringBuffer sb = new StringBuffer();
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

    private static List<Square> getSquares(Direction direction, Square startPoint, int wordLength) throws ScrabbleException {
        List<Square> squares = new ArrayList<Square>();
        int row = startPoint.getRow();
        int column = startPoint.getColumn();
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
        int column = startPoint.getColumn();
        if (Direction.ACROSS.equals(direction)) {
            if (row == MID_POINT &&
                    (column <= MID_POINT && (column + letters.size() >= MID_POINT))) {
                score = addWord(letters, direction, squares, false);
            } else {
                throw new ScrabbleException(startPoint + " is not a valid starting point");
            }
        } else {
            if (column == MID_POINT &&
                    (row <= MID_POINT && (row + letters.size() >= MID_POINT))) {
                score = addWord(letters, direction, getSquares(direction, startPoint, letters.size()), false);
            } else {
                throw new ScrabbleException(startPoint + " is not a valid starting point");
            }
        }
        return score;
    }

    private int checkAbleThenPutNewWordDown(Square startPoint, Direction direction, List<Letter> letters) throws ScrabbleException {
        Set<Word> adjacentWords = new HashSet<Word>();
        Map<Square, Letter> squaresMap = new LinkedHashMap<Square, Letter>();

        checkNotStartingOnAnotherWord(startPoint);
        findSquaresAndAdjacentWords(startPoint, letters, direction, squaresMap, adjacentWords);

        return putNewWordDown(startPoint, direction, letters, adjacentWords, squaresMap);
    }

    private synchronized int putNewWordDown(Square startPoint, Direction direction, List<Letter> letters,
                               Set<Word> adjacentWords, Map<Square, Letter> squaresMap) throws ScrabbleException {
        int score = 0;
        List<Word> replacedWords = new ArrayList<Word>();
        boolean add = false;

        squaresAddedThisTurn = new ArrayList<Square>();
        wordsAddedThisTurn = new ArrayList<Word>();

        Board.DoubleWordCreation doubleAdd = checkForDoubleWordCreation(squaresMap, direction, replacedWords);
        squaresMap = doubleAdd.getMap();
        score += doubleAdd.getScore();
        direction = doubleAdd.getDirection();

        adjacentWords.removeAll(replacedWords);

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
                    if (!testing) {
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
                if (startPoint.getColumn() != existingWord.getStartingPoint().getColumn()) {
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
            Map<Square, Letter> wordAcross = findAdditonalLettersAtEitherEndOfNewWordAndFindReplacedWords(newWord, Direction.ACROSS, replacedWords);
            Map<Square, Letter> wordDown = findAdditonalLettersAtEitherEndOfNewWordAndFindReplacedWords(newWord, Direction.DOWN, replacedWords);
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
            newWord = findAdditonalLettersAtEitherEndOfNewWordAndFindReplacedWords(newWord, direction, replacedWords);
        }
        return new DoubleWordCreation(newWord, score, direction);
    }

    private Map<Square, Letter> findAdditonalLettersAtEitherEndOfNewWordAndFindReplacedWords(Map<Square, Letter> newWord, Direction direction, List<Word> replacedWords) {
        ArrayList<Square> squares = getSquaresFromMap(newWord);
        Square firstSquare = squares.get(0);
        Square lastSquare = squares.get(squares.size() - 1);
        List<Square> priorSquares = findAdditionalLetters(firstSquare, Direction.getOpposite(direction));
        List<Square> followingSquares = findAdditionalLetters(lastSquare, direction);
        Map<Square, Letter> newMap = new LinkedHashMap<Square, Letter>();

        if (priorSquares.size() > 0) {
            for (Word word : words) {
                if (priorSquares.containsAll(word.getSquares())) {
                    replacedWords.add(word);
                    break;
                }
            }
            newMap = new LinkedHashMap<Square, Letter>();
            for (ListIterator<Square> iterator = priorSquares.listIterator(priorSquares.size()); iterator.hasPrevious();) {
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
                Map<Square, Letter> map = new LinkedHashMap<Square, Letter>();
                map.put(square, letter);
                map = findAdditonalLettersAtEitherEndOfNewWordAndFindReplacedWords(map, adjacentDirection, replacedWords);
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
                        Map<Square, Letter> map = new LinkedHashMap<Square, Letter>();
                        ArrayList<Word> replacedWords = new ArrayList<Word>();
                        map.put(square, letter);
                        map = findAdditonalLettersAtEitherEndOfNewWordAndFindReplacedWords(map, adjacentDirection, replacedWords);

                        if (replacedWords.size() > 0) {
                            score = replaceWord(replacedWords.get(0), getLettersFromMap(map), adjacentDirection, getSquaresFromMap(map));
                            if (replacedWords.size() > 1) {
                                if (!testing) {
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
        List<Letter> newLetters = new ArrayList<Letter>(word.getLetters());
        ArrayList<Square> newSquares = new ArrayList<Square>(word.getSquares());
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

    private static Direction determineDirection(Square square1, Square square2) {
        Direction direction = null;
        if (square1.getRow() == square2.getRow()) {
            direction = Direction.ACROSS;
        } else if (square1.getColumn() == square2.getColumn()) {
            direction = Direction.DOWN;
        }
        return direction;
    }

    private static List<Square> findAdditionalLetters(Square square, Direction direction) {
        List<Square> newSquares = new ArrayList<Square>();
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
        if (!testing) {
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
        if (!testing) {
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
                if (!testing) {
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
        return new ArrayList<Letter>(newWord.values());
    }

    private static ArrayList<Square> getSquaresFromMap(Map<Square, Letter> squaresMap) {
        return new ArrayList<Square>(squaresMap.keySet());
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
        if (testing && !found) {
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
        StringBuffer chars = new StringBuffer();
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

    /**
     * Inner class to allow testing of private methods.
     */
    static class Tester {
        private Tester() {
        }

        public static List<Square> getSquares(Direction direction, Square startPoint, int wordLength) throws ScrabbleException {
            return Board.getSquares(direction, startPoint, wordLength);
        }

        public static boolean isNewWordGoingToTouchExistingWord(List<Square> squares, List<Word> words) {
            return Board.isNewWordGoingToTouchExistingWord(squares, words);
        }

        public static Square findNextSquare(Square square, Direction direction) throws ScrabbleException {
            return Board.findNextSquare(square, direction);
        }

        public static Direction determineDirection(Square square1, Square square2) {
            return Board.determineDirection(square1, square2);
        }

        public static String getCharactersFromSquares(Square[] squares, boolean showWildcards) {
            return Board.getCharactersFromSquares(squares, showWildcards);
        }
    }
}
