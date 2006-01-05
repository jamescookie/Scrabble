package com.jamescookie.scrabble.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicButtonUI;

import com.jamescookie.scrabble.Direction;
import com.jamescookie.scrabble.Letter;
import com.jamescookie.scrabble.Square;

class ScrabbleButton extends JButton {
    private static final Color DOUBLE_WORD_SQUARE = new Color(102, 204, 102);
    private static final Color DOUBLE_LETTER_SQUARE = new Color(153, 204, 255);
    private static final Color TRIPLE_WORD_SQUARE = new Color(0, 102, 0);
    private static final Color TRIPLE_LETTER_SQUARE = new Color(51, 102, 204);

    private static final int SIZE = 24;

    private final GameBoard myParent;
    private final Square square;
    private final Dimension buttonSize = new Dimension(SIZE, SIZE);
    private final MyBasicButtonUI bbui = new MyBasicButtonUI();

    public ScrabbleButton(Square square, GameBoard parent) {
        myParent = parent;
        this.square = square;

        setMaximumSize(buttonSize);
        setMinimumSize(buttonSize);
        setPreferredSize(buttonSize);
        setBorder(BorderFactory.createLineBorder(Color.black));
        setFont(getFont().deriveFont(Font.BOLD, 12.0f));
        addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                scrabbleButtonClicked();
            }

        });
        setFocusPainted(false);
        updateButtonGraphics();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (square.hasLetter()) {
            setText(square.getLetter().toString());
        } else {
            setText("");
        }
    }

    protected void paintChildren(Graphics g) {
        super.paintChildren(g);
        if (square.hasLetter()) {
            Letter l = square.getLetter();
            g.setColor(l.getBackgroundColour());
            g.fillOval(2, 2, SIZE - 4, SIZE - 4);
            setForeground(l.getTextColour());
            bbui.myPaintText(g, this, getTextRectangle(), getText());
        }
    }

    public void highlight(Direction direction) {
        Graphics g = getGraphics();
        g.setColor(Color.WHITE);
        if (Direction.ACROSS.equals(direction)) {
            g.drawLine(1, SIZE / 2, SIZE - 2, SIZE / 2);
            g.drawLine(SIZE - 2, SIZE / 2, SIZE / 2, 1);
            g.drawLine(SIZE - 2, SIZE / 2, SIZE / 2, SIZE - 2);
        } else if (Direction.DOWN.equals(direction)) {
            g.drawLine(SIZE / 2, 1, SIZE / 2, SIZE - 2);
            g.drawLine(SIZE / 2, SIZE - 2, SIZE - 2, SIZE / 2);
            g.drawLine(SIZE / 2, SIZE - 2, 1, SIZE / 2);
        }
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

    private static class MyBasicButtonUI extends BasicButtonUI {
        public void myPaintText(Graphics g, JComponent c, Rectangle textRect, String text) {
            paintText(g, c, textRect, text);
        }
    }

    /*
     * Returns the bounding rectangle for the component text.
     * This is lifted from AbstractButton (I should use that, but it has private access)
     */
    private Rectangle getTextRectangle() {
        String text = getText();
        Icon icon = (isEnabled()) ? getIcon() : getDisabledIcon();

        if ((icon == null) && (text == null)) {
            return null;
        }

        Rectangle paintIconR = new Rectangle();
        Rectangle paintTextR = new Rectangle();
        Rectangle paintViewR = new Rectangle();
        Insets paintViewInsets = new Insets(0, 0, 0, 0);

        paintViewInsets = getInsets(paintViewInsets);
        paintViewR.x = paintViewInsets.left;
        paintViewR.y = paintViewInsets.top;
        paintViewR.width = getWidth() - (paintViewInsets.left + paintViewInsets.right);
        paintViewR.height = getHeight() - (paintViewInsets.top + paintViewInsets.bottom);

        Graphics g = getGraphics();
        if (g == null) {
            return null;
        }
        SwingUtilities.layoutCompoundLabel(
                this,
                g.getFontMetrics(),
                text,
                icon,
                getVerticalAlignment(),
                getHorizontalAlignment(),
                getVerticalTextPosition(),
                getHorizontalTextPosition(),
                paintViewR,
                paintIconR,
                paintTextR,
                0);

        return paintTextR;
    }
}
