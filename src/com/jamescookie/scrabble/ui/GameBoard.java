package com.jamescookie.scrabble.ui;

import com.jamescookie.io.FileChooser;
import com.jamescookie.scrabble.Board;
import com.jamescookie.scrabble.Direction;
import com.jamescookie.scrabble.Possibility;
import com.jamescookie.scrabble.PossibilityGenerator;
import com.jamescookie.scrabble.RemainingLetters;
import com.jamescookie.scrabble.ResultExpecter;
import com.jamescookie.scrabble.ScrabbleException;
import com.jamescookie.scrabble.Square;
import com.jamescookie.scrabble.Utils;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;

class GameBoard extends ScrabbleSuperFrame implements ResultExpecter {
    private final ScrabbleButton[][] scrabbleButtons = new ScrabbleButton[Board.BOARD_SIZE][Board.BOARD_SIZE];
    private final Board board;
    private final PossibilityGenerator possibilityGenerator;
    private File currentDirectory = null;
    private File currentFile = null;

    private final FlowLayout flowLayout1 = new FlowLayout();
    private final BorderLayout borderLayout1 = new BorderLayout();
    private final BorderLayout borderLayout2 = new BorderLayout();
    private final JPanel jPanelSquares = new JPanel();
    private final JPanel jPanelBoard = new JPanel();
    private final JPanel jPanelInfo = new JPanel();
    private final JPanel jPanelControls = new JPanel();
    private final JPanel jPanelControlsAndDisplay = new JPanel();
    private final JLabel jLabelInfo = new JLabel();
    private final JButton jButtonStart = new JButton();
    private final JButton jButtonStop = new JButton();
    private final JButton jButtonInsert = new JButton();
    private final JButton jButtonClear = new JButton();
    private final JTextField jTextField = new JTextField("100");
    private final JComboBox jComboBox = new JComboBox();
    private final JMenu menuFile = new JMenu();
    private final JMenuItem menuFileSave = new JMenuItem();
    private final JMenuItem menuFileSaveAs = new JMenuItem();
    private final JMenuItem menuFileLoad = new JMenuItem();
    private final JMenuItem menuItemShowWordVariations = new JMenuItem();
    private final JMenuItem menuItemOldStyle = new JMenuItem();
    private final ActionListener comboActionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showPossibility((Possibility) jComboBox.getSelectedItem());
            }
        };

    public GameBoard(PossibilityGenerator possibilityGenerator) {
        this.possibilityGenerator = possibilityGenerator;
        board = possibilityGenerator.getBoard();
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void resultsAreReady() {
        Collection<Possibility> possibilities = possibilityGenerator.getResults();
        jButtonStart.setEnabled(true);
        jLabelInfo.setText("Words generated");
        jComboBox.removeActionListener(comboActionListener);
        jComboBox.removeAllItems();
        for (Possibility possibility : possibilities) {
            jComboBox.addItem(possibility);
        }
        jComboBox.addActionListener(comboActionListener);
    }

    //Overridden so we can exit when window is closed
    protected void processWindowEvent(WindowEvent e) {
        super.processWindowEvent(e);
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            System.exit(0);
        }
    }

    private void jbInit() throws Exception {
        // Action Listeners
        jButtonStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                generateWords();
            }
        });
        jButtonStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                stopGenerating();
            }
        });
        jButtonInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                insertSelectedPossibility((Possibility) jComboBox.getSelectedItem());
            }
        });
        jButtonClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clear();
            }
        });
        menuItemShowWordVariations.setText("Show selected word variations");
        menuItemShowWordVariations.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showWordVariations();
            }
        });
        menuItemOldStyle.setText("Open 'Old Style' board");
        menuItemOldStyle.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openOldStyle();
            }
        });

        setSize(new Dimension(500, 500));
        setTitle(Utils.getTitle(""));
        JPanel jPanelContent = (JPanel) getContentPane();

        // Set the attributes of the board to make sure the buttons are correctly aligned.
        flowLayout1.setHgap(0);
        flowLayout1.setVgap(0);
        jPanelSquares.setMaximumSize(new Dimension((24 * (Board.BOARD_SIZE)) + 2, (24 * (Board.BOARD_SIZE)) + 2));
        jPanelSquares.setMinimumSize(jPanelSquares.getMaximumSize());
        jPanelSquares.setPreferredSize(jPanelSquares.getMaximumSize());
        jPanelSquares.setBorder(BorderFactory.createLineBorder(Color.white));
        jPanelSquares.setLayout(flowLayout1);

        // Add the buttons to the board
        ScrabbleButton button;
        for (int i = 0; i < Board.BOARD_SIZE; i++) {
            for (int j = 0; j < Board.BOARD_SIZE; j++) {
                button = new ScrabbleButton(Board.getSquare(i, j), this);
                jPanelSquares.add(button, null);
                scrabbleButtons[i][j] = button;
            }
        }

        // Menu setup
        menuFile.setText("File");
        menuFileLoad.setText("Load");
        menuFileSave.setText("Save");
        menuFileSaveAs.setText("Save As");
        menuFileSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveBoard();
            }
        });
        menuFileSaveAs.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveBoardAs();
            }
        });
        menuFileLoad.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadBoard();
            }
        });

        // Misc setup
        jButtonStart.setText("Generate words");
        jButtonStop.setText("Stop");
        jButtonInsert.setText("Insert word");
        jButtonClear.setText("Clear board");
        jLabelInfo.setText("Set up board");
        jTextField.setPreferredSize(new Dimension(50, 20));
        jComboBox.addActionListener(comboActionListener);

        // Add content to initial panels
        jPanelInfo.add(jLabelInfo, null);
        jPanelBoard.add(jPanelSquares, null);
        jPanelControls.add(jButtonStart, null);
        jPanelControls.add(jTextField, null);
        jPanelControls.add(jButtonStop, null);
        jPanelControls.add(jButtonInsert, null);
        jPanelControls.add(jButtonClear, null);
        jPanelControlsAndDisplay.setLayout(borderLayout2);
        jPanelControlsAndDisplay.add(jPanelControls, BorderLayout.NORTH);
        jPanelControlsAndDisplay.add(jComboBox, BorderLayout.CENTER);

        // Add content to main panel
        jPanelContent.setLayout(borderLayout1);
        jPanelContent.add(jPanelInfo, BorderLayout.NORTH);
        jPanelContent.add(jPanelBoard, BorderLayout.CENTER);
        jPanelContent.add(jPanelControlsAndDisplay, BorderLayout.SOUTH);

        // Add menu
        addMenu(menuFile, 0);
        menuFile.add(menuFileSave);
        menuFile.add(menuFileSaveAs);
        menuFile.add(menuFileLoad);
        addToExtraMenu(menuItemShowWordVariations);
        addToExtraMenu(menuItemOldStyle);
    }

    protected void remainingLetters() {
        JOptionPane.showMessageDialog(
                this,
                RemainingLetters.lettersLeft(Board.getCharactersFromBoard(), getScrabbleType())
            );
    }

    void makeMove(Square square) {
        String letters = JOptionPane.showInputDialog(this,
                            "Enter the letters to put down.\n" +
                            "Insert '"+ Utils.WILDCARD+ "' before the letter where a wildcard is used.",
                            "Enter letters",
                            JOptionPane.QUESTION_MESSAGE);
        if (letters != null) {
            Direction[] selectionValues = new Direction[] {Direction.ACROSS, Direction.DOWN};
            Direction direction = (Direction) JOptionPane.showInputDialog(this,
                                "Which direction do these letter go?",
                                "Enter direction",
                                JOptionPane.QUESTION_MESSAGE,
                                null, selectionValues, null);
            if (direction != null) {
                enterLetters(letters, square, direction);
            }
        }
    }

    private void enterLetters(String letters, Square square, Direction direction) {
        try {
            int score = board.putLetters(letters, square, direction);
            jLabelInfo.setText(letters + " scores " + score);
        } catch (ScrabbleException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "An error occured", JOptionPane.ERROR_MESSAGE);
        }
        repaint();
    }

    private void generateWords() {
        String letters = JOptionPane.showInputDialog(this,
                            "Enter the letters you have in your tray.",
                            "Enter letters",
                            JOptionPane.QUESTION_MESSAGE);
        if (letters != null) {
            jLabelInfo.setText("Generating words, please wait.");
            jButtonStart.setEnabled(false);
            repaint();
            int number = 10;
            String numberText = jTextField.getText();
            if (numberText != null && numberText.length() > 0) {
                number = Integer.parseInt(numberText);
            }
            possibilityGenerator.generate(letters, number, this);
        }
    }

    private void stopGenerating() {
        possibilityGenerator.stop();
        jLabelInfo.setText("Generating stopped.");
        jButtonStart.setEnabled(true);
    }

    private void insertSelectedPossibility(Possibility possibility) {
        if (possibility != null) {
            try {
                String letters = possibility.getLetters();
                board.putLetters(letters, possibility.getStartPoint(), possibility.getDirection());
                jLabelInfo.setText("Letters '" + letters + "' entered.");
                repaint();
            } catch (ScrabbleException e) {
                JOptionPane.showMessageDialog(
                    this,
                    "Error (" + e + ") inserting possibility (" +
                    possibility + "): " +
                    e.getMessage(),
                    "Insert Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void clear() {
        board.clear();
        jComboBox.removeAllItems();
        setTitle(Utils.getTitle(""));
        jLabelInfo.setText("Board cleared.");
        repaint();
    }

    private void showWordVariations() {
        Possibility possibility = (Possibility) jComboBox.getSelectedItem();
        if (possibility != null) {
            String letters = possibility.getLetters().replaceAll("\\"+Utils.WILDCARD, "");
            JOptionPane.showMessageDialog(
                this,
                "Words that contain '" + letters + "' (with a maximum of 2 extra letters) are:\n" +
                Utils.sortWords(possibilityGenerator.findVariations(letters)),
                "Variations",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void openOldStyle() {
        Scrabble.openOldStyle();
    }

    private void showPossibility(Possibility possibility) {
        if (possibility != null) {
            Square startPoint = possibility.getStartPoint();
            scrabbleButtons[startPoint.getRow()][startPoint.getColumn()].highlight(possibility.getDirection());
        }
    }

    private void saveBoardAs() {
        FileChooser fileChooser = new FileChooser(FileChooser.SAVE, this, currentDirectory);
        File file = fileChooser.chooseFile();
        saveBoard(file);
    }

    private void saveBoard() {
        saveBoard(currentFile);
    }

    private void saveBoard(File file) {
        BufferedWriter w = null;

        if (file != null) {
            try {
                if (file.exists()) {
                    file.delete();
                }
                w = new BufferedWriter(new FileWriter(file));
                board.export(w);
                w.flush();
                jLabelInfo.setText("File saved - " + file.getName());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(
                    this,
                    "Error (" + e + ") writing file (" +
                    file.getAbsolutePath() + "): " +
                    e.getMessage(),
                    "Write Error",
                    JOptionPane.ERROR_MESSAGE);
            } finally {
                if (w != null) {
                    try {
                        w.close();
                    } catch (IOException e) {
                        //not a lot else we can do here
                    }
                }
            }
        }
    }

    private void loadBoard() {
        FileChooser fileChooser = new FileChooser(FileChooser.OPEN, this, currentDirectory);
        File file = fileChooser.chooseFile();
        BufferedReader r = null;

        if (file != null) {
            try {
                currentFile = file;
                currentDirectory = file.getParentFile();
                jComboBox.removeAllItems();
                FileInputStream fis = new FileInputStream(file);
                r = new BufferedReader(new InputStreamReader(fis));
                board.generate(r);
                setTitle(Utils.getTitle("- " + file.getName()));
                jLabelInfo.setText("File loaded - " + file.getName());
                repaint();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(
                    this,
                    "Error (" + e + ") reading file (" +
                    file.getAbsolutePath() + "): " +
                    e.getMessage(),
                    "Read Error",
                    JOptionPane.ERROR_MESSAGE);
            } catch (ScrabbleException e) {
                JOptionPane.showMessageDialog(
                    this,
                    "Error (" + e + ") rebuilding board:" +
                    e.getMessage(),
                    "Build Error",
                    JOptionPane.ERROR_MESSAGE);
            } finally {
                if (r != null) {
                    try {
                        r.close();
                    } catch (IOException e) {
                        //not a lot else we can do here
                    }
                }
            }
        }
    }
}
