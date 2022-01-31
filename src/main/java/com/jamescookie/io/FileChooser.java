package com.jamescookie.io;

import java.awt.Component;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * A dialog needed to pick the relevent type of files. Uses
 * {@link javax.swing.JFileChooser JFileChooser} and sets it up accordingly.
 * <p>
 * Copyright (c) 2001
 * <p>
 * @author James Cook
 * @version 1.0
 */
public class FileChooser {
    public static final int SAVE = 1;
    public static final int SAVE_AS = 2;
    public static final int OPEN = 3;

    private static final String TXT = "txt";
    private static final String EXTENSION = "."+TXT;

    private JFileChooser chooser;
    private static Component frame;
    private int option;

    public FileChooser(int option, Component frame, File currentDir) {
        MyFileFilter xmlFilter = new MyFileFilter(TXT, "TXT Files");
        xmlFilter.setExtensionListInDescription(true);

        chooser = new JFileChooser();
        FileChooser.frame = frame;
        this.option = option;

        if (this.option == OPEN) {
            chooser.setDialogType(JFileChooser.OPEN_DIALOG);
        } else {
            chooser.setDialogType(JFileChooser.SAVE_DIALOG);
        }

        chooser.setAcceptAllFileFilterUsed(false);
        chooser.addChoosableFileFilter(xmlFilter);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        chooser.setAccessory(null);
        chooser.setFileView(null);
        chooser.setMultiSelectionEnabled(false);
        chooser.setCurrentDirectory(currentDir);
    }

    public File chooseFile() {
        File theFile = null;
        int result;
        int retval = chooser.showDialog(frame, null);

        if (retval == JFileChooser.APPROVE_OPTION) {
            theFile = chooser.getSelectedFile();
            if (option == OPEN) {
                if (!theFile.exists()) {
                    JOptionPane.showMessageDialog(frame,
                            "That file does not exist. No action taken.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    theFile = null;
                }
            } else {
                if (!theFile.toString().toLowerCase().endsWith(EXTENSION.toLowerCase())) {
                    theFile = new File(theFile.toString() + EXTENSION);
                }

                if (theFile.exists()) {
                    result = JOptionPane.showConfirmDialog(frame,
                                    "Do you want to replace the existing " +
                                    theFile.getName() + "?");
                    if (result != JOptionPane.YES_OPTION) {
                        theFile = null;
                    }
                }
            }
        } else if (retval == JFileChooser.ERROR_OPTION) {
            JOptionPane.showMessageDialog(frame,
                    "An error occured. No file was chosen.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        return theFile;
    }

}
