package com.jamescookie.scrabble.ui;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import com.jamescookie.scrabble.Board;
import com.jamescookie.scrabble.PossibilityGenerator;
import com.jamescookie.scrabble.WordLoaderImpl;
import com.jamescookie.scrabble.WordsmithImpl;

public class Scrabble {
    private final boolean packFrame = false;

    //Construct the application
    private Scrabble() {
        JFrame frame;
        Object[] options = {"Old Style",
                    "Funky New Board"};
        int option = JOptionPane.showOptionDialog(
                null,
                "Which do you want to launch?",
                "Choose application",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                null);

        if (option == 0) {
            frame = new ScrabbleFrame();
        } else {
            WordsmithImpl wordsmith = new WordsmithImpl(WordLoaderImpl.getInstance());
            Board board = new Board(wordsmith);
            PossibilityGenerator generator = new PossibilityGenerator(wordsmith, board);
            frame = new GameBoard(generator);
        }

        //Validate frames that have preset sizes
        //Pack frames that have useful preferred size info, e.g. from their layout
        if (packFrame) {
            frame.pack();
        } else {
            frame.validate();
        }
        //Center the window
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = frame.getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        frame.setLocation((screenSize.width - frameSize.width) / 2,
                          (screenSize.height - frameSize.height) / 2);
        frame.setVisible(true);
    }

    //Main method
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        new Scrabble();
    }
}
