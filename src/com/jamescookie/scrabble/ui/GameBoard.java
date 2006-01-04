package com.jamescookie.scrabble.ui;

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
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.jamescookie.io.FileChooser;
import com.jamescookie.scrabble.Board;
import com.jamescookie.scrabble.Direction;
import com.jamescookie.scrabble.Possibility;
import com.jamescookie.scrabble.PossibilityGenerator;
import com.jamescookie.scrabble.ScrabbleException;
import com.jamescookie.scrabble.Square;
import com.jamescookie.scrabble.Utils;

public class GameBoard extends JFrame {
    private ScrabbleButton[][] scrabbleButtons = new ScrabbleButton[Board.BOARD_SIZE][Board.BOARD_SIZE];
    private final Board board;
    private final PossibilityGenerator possibilityGenerator;

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
    private final JTextField jTextField = new JTextField("10");
    private final JComboBox jComboBox = new JComboBox();
    private final JMenuBar jMenuBar1 = new JMenuBar();
    private final JMenu menuFile = new JMenu();
    private final JMenuItem menuFileSave = new JMenuItem();
    private final JMenuItem menuFileLoad = new JMenuItem();
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

    private void jbInit() throws Exception {
        jButtonStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                generateWords();
            }
        });

        setJMenuBar(jMenuBar1);
        setSize(new Dimension(500, 500));
        setTitle("Scrabble - Version " + Utils.VERSION);
        JPanel jPanelContent = (JPanel) getContentPane();

        // Set the attributes of the board to make sure the buttons are correctly aligned.
        flowLayout1.setHgap(0);
        flowLayout1.setVgap(0);
        jPanelSquares.setMaximumSize(new Dimension((24 * (Board.BOARD_SIZE)) + 2, (24 * (Board.BOARD_SIZE)) + 2));
        jPanelSquares.setMinimumSize(jPanelSquares.getMaximumSize());
        jPanelSquares.setPreferredSize(jPanelSquares.getMaximumSize());
        jPanelSquares.setBorder(BorderFactory.createLineBorder(Color.black));
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
        menuFileSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveBoard();
            }
        });
        menuFileLoad.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadBoard();
            }
        });

        // Misc setup
        jButtonStart.setText("Generate words");
        jLabelInfo.setText("Set up board");
        jComboBox.addActionListener(comboActionListener);

        // Add content to initial panels
        jPanelInfo.add(jLabelInfo, null);
        jPanelBoard.add(jPanelSquares, null);
        jPanelControls.add(jButtonStart, null);
        jPanelControls.add(jTextField, null);
        jPanelControlsAndDisplay.setLayout(borderLayout2);
        jPanelControlsAndDisplay.add(jPanelControls, BorderLayout.NORTH);
        jPanelControlsAndDisplay.add(jComboBox, BorderLayout.CENTER);

        // Add content to main panel
        jPanelContent.setLayout(borderLayout1);
        jPanelContent.add(jPanelInfo, BorderLayout.NORTH);
        jPanelContent.add(jPanelBoard, BorderLayout.CENTER);
        jPanelContent.add(jPanelControlsAndDisplay, BorderLayout.SOUTH);

        // Add menu
        jMenuBar1.add(menuFile);
        menuFile.add(menuFileSave);
        menuFile.add(menuFileLoad);
    }

    /**
     * overridden so we can exit when window is closed.
     * @param e the window evern
     */
    protected void processWindowEvent(WindowEvent e) {
        super.processWindowEvent(e);
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            System.exit(0);
        }
    }

    protected void makeMove(Square square) {
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
            Collection<Possibility> possibilities = possibilityGenerator.generate(letters, number);
            jButtonStart.setEnabled(true);
            jLabelInfo.setText("Words generated.");
            jComboBox.removeActionListener(comboActionListener);
            jComboBox.removeAllItems();
            for (Possibility possibility : possibilities) {
                jComboBox.addItem(possibility);
            }
            jComboBox.addActionListener(comboActionListener);
        }
    }

    private void showPossibility(Possibility possibility) {
        if (possibility != null) {
            Square startPoint = possibility.getStartPoint();
            scrabbleButtons[startPoint.getRow()][startPoint.getColumn()].highlight(possibility.getDirection());
        }
    }

    private void saveBoard() {
        FileChooser fileChooser = new FileChooser(FileChooser.SAVE, this, null);
        File file = fileChooser.chooseFile();
        BufferedWriter w = null;

        if (file != null) {
            try {
                if (file.exists()) {
                    file.delete();
                }
                w = new BufferedWriter(new FileWriter(file));
                board.export(w);
                w.flush();
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
        FileChooser fileChooser = new FileChooser(FileChooser.OPEN, this, null);
        File file = fileChooser.chooseFile();
        BufferedReader r = null;

        if (file != null) {
            try {
                FileInputStream fis = new FileInputStream(file);
                r = new BufferedReader(new InputStreamReader(fis));
                board.generate(r);
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
