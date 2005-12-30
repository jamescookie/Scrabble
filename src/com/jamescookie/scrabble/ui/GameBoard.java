package com.jamescookie.scrabble.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JOptionPane;

import com.jamescookie.scrabble.Board;
import com.jamescookie.scrabble.Square;
import com.jamescookie.scrabble.PossibilityGenerator;
import com.jamescookie.scrabble.Direction;
import com.jamescookie.scrabble.ScrabbleException;

public class GameBoard extends JFrame {
    private ScrabbleButton[][] _othelloButtons = new ScrabbleButton[Board.BOARD_SIZE][Board.BOARD_SIZE];
    private final Board board;
    private final PossibilityGenerator possibilityGenerator;


    FlowLayout flowLayout1 = new FlowLayout();
    BorderLayout borderLayout1 = new BorderLayout();
    JPanel jPanelContent = new JPanel();
    JPanel jPanelSquares = new JPanel();
    JPanel jPanelBoard = new JPanel();
    JPanel jPanelInfo = new JPanel();
    JPanel jPanelInitialButtons = new JPanel();
    JLabel jLabelInfo = new JLabel();
    JButton jButtonStart = new JButton();

    public GameBoard(PossibilityGenerator possibilityGenerator) {
        this.possibilityGenerator = possibilityGenerator;
        this.board = possibilityGenerator.getBoard();
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        jButtonStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                generateWords(e);
            }
        });

        this.setSize(new Dimension(500, 500));
        this.setTitle("Othello");
        jPanelContent = (JPanel) this.getContentPane();

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
                button = new ScrabbleButton(board.getSquare(i, j), this);
                jPanelSquares.add(button, null);
                _othelloButtons[i][j] = button;
            }
        }

        // Misc setup
        jButtonStart.setText("Generate words");
        jLabelInfo.setText("Set up board");

        // Add content to initial panels
        jPanelInfo.add(jLabelInfo, null);
        jPanelBoard.add(jPanelSquares, null);
        jPanelInitialButtons.add(jButtonStart, null);

        // Add content to main panel
        jPanelContent.setLayout(borderLayout1);
        jPanelContent.add(jPanelInfo, BorderLayout.NORTH);
        jPanelContent.add(jPanelBoard, BorderLayout.CENTER);
        jPanelContent.add(jPanelInitialButtons, BorderLayout.SOUTH);

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
        String path = JOptionPane.showInputDialog(this,
                            "Enter the letters to put down.",
                            "Enter letters",
                            JOptionPane.INFORMATION_MESSAGE);
        try {
            board.putLetters(path, square, Direction.ACROSS);
        } catch (ScrabbleException e) {
            e.printStackTrace();
        }
        repaint();
    }

    private void generateWords(ActionEvent e) {
        possibilityGenerator.generate("abc");
    }
}
