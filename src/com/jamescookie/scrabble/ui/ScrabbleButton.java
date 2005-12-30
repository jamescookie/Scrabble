package com.jamescookie.scrabble.ui;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.BorderFactory;

import com.jamescookie.scrabble.Square;

public class ScrabbleButton extends JButton {
    private static final Color DOUBLE_WORD_SQUARE = new Color(102, 204, 102);
    private static final Color DOUBLE_LETTER_SQUARE = new Color(153, 204, 255);
    private static final Color TRIPLE_WORD_SQUARE = new Color(0, 102, 0);
    private static final Color TRIPLE_LETTER_SQUARE = new Color(51, 102, 204);

    private GameBoard myParent;
    private Square square;
    private Dimension buttonSize = new Dimension(24, 24);

    public ScrabbleButton(Square square, GameBoard parent) {
        myParent = parent;
        this.square = square;

        setMaximumSize(buttonSize);
        setMinimumSize(buttonSize);
        setPreferredSize(buttonSize);
        setBorder(BorderFactory.createLineBorder(Color.black));
        addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                scrabbleButtonClicked();
            }

        });
        setFocusPainted(false);
        updateButtonGraphics();
    }

    public void paint(Graphics g) {
        if (square.hasLetter()) {
            setText(("" + square.getLetter().getCharacter()).toUpperCase());
        }
        super.paint(g);
    }

    private void scrabbleButtonClicked() {
        myParent.makeMove(square);
    }

    private void updateButtonGraphics() {
        if (square.equivalentMods(Square.getDoubleWord(0,0))) {
            setBackground(DOUBLE_WORD_SQUARE);
        } else if (square.equivalentMods(Square.getDoubleLetter(0,0))) {
            setBackground(DOUBLE_LETTER_SQUARE);
        } else if (square.equivalentMods(Square.getTripleWord(0,0))) {
            setBackground(TRIPLE_WORD_SQUARE);
        } else if (square.equivalentMods(Square.getTripleLetter(0,0))) {
            setBackground(TRIPLE_LETTER_SQUARE);
        }
    }

}
