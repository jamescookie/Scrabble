package com.jamescookie.scrabble.ui;

import com.jamescookie.scrabble.TwoLetters;
import com.jamescookie.scrabble.Type;
import com.jamescookie.scrabble.TypeNormal;
import com.jamescookie.scrabble.TypeWild;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import java.awt.AWTEvent;
import java.awt.MenuBar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

abstract class ScrabbleSuperFrame extends JFrame {
    private final JMenuBar jMenuBar1 = new JMenuBar();
    private final JMenu menuExtra = new JMenu();
    private final JMenuItem menuItemRemaining = new JMenuItem();
    private final JMenuItem menuItem2Letters = new JMenuItem();
    private final JMenu menuType = new JMenu();
    private final JRadioButtonMenuItem menuItemTypeNormal = new JRadioButtonMenuItem();
    private final JRadioButtonMenuItem menuItemTypeWild = new JRadioButtonMenuItem();
    private final JMenuItem menuItem2LettersOppositeOrder = new JMenuItem();
    private final ButtonGroup btngrpType = new ButtonGroup();

    //Construct the frame
    public ScrabbleSuperFrame() {
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Component initialization
    private void jbInit() {
        super.setJMenuBar(jMenuBar1);
        menuExtra.setText("Extra");
        menuItemRemaining.setText("Remaining letters");
        menuItemRemaining.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                remainingLetters();
            }
        });
        menuItem2Letters.setText("Show two letter words");
        menuItem2Letters.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showTwoLetterWords();
            }
        });
        menuItem2LettersOppositeOrder.setText("Show ordered on second letter");
        menuItem2LettersOppositeOrder.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showTwoLetterWordsOppositeOrder();
            }
        });
        menuType.setText("Type");
        menuItemTypeNormal.setText("Normal");
        menuItemTypeWild.setText("Wild");
        btngrpType.add(menuItemTypeNormal);
        btngrpType.add(menuItemTypeWild);
        menuItemTypeNormal.setSelected(true);

        jMenuBar1.add(menuExtra);
        jMenuBar1.add(menuType);
        menuExtra.add(menuItemRemaining);
        menuExtra.add(menuItem2Letters);
        menuExtra.add(menuItem2LettersOppositeOrder);
        menuType.add(menuItemTypeNormal);
        menuType.add(menuItemTypeWild);
    }

    public void setMenuBar(MenuBar mb) {
        // does nothing as we don't want our children removing the menu.
    }

    public void setJMenuBar(JMenuBar menubar) {
        // does nothing as we don't want our children removing the menu.
    }

    protected void addMenu(JMenuItem menu, int position) {
        jMenuBar1.add(menu, position);
    }

    protected void addToExtraMenu(JMenuItem menu) {
        menuExtra.add(menu);
    }

    protected com.jamescookie.scrabble.Type getScrabbleType() {
        return menuItemTypeNormal.isSelected() ? new TypeNormal() : new TypeWild();
    }

    protected abstract void remainingLetters();

    private void showTwoLetterWords() {
        JOptionPane.showMessageDialog(this, TwoLetters.formatTwoLetterWords("\n"));
    }

    private void showTwoLetterWordsOppositeOrder() {
        JOptionPane.showMessageDialog(this, TwoLetters.formatTwoLetterWordsInOppositeOrder("\n"));
    }
}
