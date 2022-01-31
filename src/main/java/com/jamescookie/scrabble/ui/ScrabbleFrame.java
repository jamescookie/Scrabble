package com.jamescookie.scrabble.ui;

import com.jamescookie.scrabble.*;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class ScrabbleFrame extends ScrabbleSuperFrame {
    private final Wordsmith wordsmith = new WordsmithImpl(WordLoaderImpl.getInstance());
    private WordProcessing _thread;

    private final Border borderInset5 = BorderFactory.createEmptyBorder(5, 5, 5, 5);
    private final BorderLayout borderLayout1 = new BorderLayout();
    private final BorderLayout borderLayout2 = new BorderLayout();
    private final BorderLayout borderLayout3 = new BorderLayout();
    private final BorderLayout borderLayout4 = new BorderLayout();
    private final BorderLayout borderLayout5 = new BorderLayout();
    private final BorderLayout borderLayout6 = new BorderLayout();
    private final JPanel jPanel1 = new JPanel();
    private final JPanel jPanel2 = new JPanel();
    private final JPanel jPanel3 = new JPanel();
    private final JPanel jPanel4 = new JPanel();
    private final JPanel jPanel5 = new JPanel();
    private final JPanel jPanel6 = new JPanel();
    private final JPanel jPanel7 = new JPanel();
    private final JTextField txtLetters = new JTextField();
    private final JTextField txtMustContain = new JTextField();
    private final JButton btnFindWords = new JButton();
    private final JButton btnStop = new JButton();
    private final JButton btnJustFilter = new JButton();
    private final JScrollPane jScrollPane1 = new JScrollPane();
    private final JList jList1 = new JList();
    private final JComboBox selLength = new JComboBox();
    private final JComboBox selOperator = new JComboBox();
    private final JLabel jLabel1 = new JLabel();

    //Construct the frame
    public ScrabbleFrame() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Component initialization
    private void jbInit() {
        JPanel contentPane = (JPanel) getContentPane();
        setSize(new Dimension(220, 620));
        setTitle(Utils.getTitle(""));
        selLength.addItem("Any");
        for (int i = 2; i < 10; i++) {
            selLength.addItem(String.valueOf(i));
        }
        selOperator.addItem(Operator.EQUALS);
        selOperator.addItem(Operator.LESS_THAN);
        selOperator.addItem(Operator.GREATER_THAN);
        btnFindWords.setText("Go");
        btnFindWords.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    btnFindWords_actionPerformed();
                }
            }
        );
        btnStop.setText("Stop");
        btnStop.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    btnStop_actionPerformed();
                }
            }
        );
        btnJustFilter.setText("Filter");
        btnJustFilter.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    btnJustFilter_actionPerformed();
                }
            }
        );
        jLabel1.setText("Idle");
        jList1.setFont(new Font("Courier New", 0, 11));
        contentPane.setLayout(borderLayout1);
        jPanel1.setLayout(borderLayout3);
        jPanel2.setBorder(borderInset5);
        jPanel2.setLayout(borderLayout2);
        jPanel3.setBorder(borderInset5);
        jPanel3.setLayout(borderLayout4);
        jPanel4.setLayout(borderLayout5);
        jPanel4.setBorder(borderInset5);
        jPanel5.setLayout(borderLayout6);
        jPanel5.setBorder(borderInset5);
        txtMustContain.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                captureEnter(e);
            }
        });
        txtLetters.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                captureEnter(e);
            }
        });
        jScrollPane1.getViewport().add(jList1);
        jPanel7.add(selOperator);
        jPanel7.add(selLength);
        jPanel6.add(btnFindWords);
        jPanel6.add(btnStop);
        jPanel6.add(btnJustFilter);
        jPanel5.add(txtMustContain,  BorderLayout.NORTH);
        jPanel5.add(jPanel7,  BorderLayout.SOUTH);
        jPanel4.add(txtLetters, BorderLayout.CENTER);
        jPanel4.add(jPanel6,  BorderLayout.SOUTH);
        jPanel3.add(jScrollPane1, BorderLayout.CENTER);
        jPanel3.add(jLabel1, BorderLayout.NORTH);
        jPanel2.add(jPanel5, BorderLayout.NORTH);
        jPanel2.add(jPanel4, BorderLayout.CENTER);
        jPanel1.add(jPanel2, BorderLayout.NORTH);
        jPanel1.add(jPanel3, BorderLayout.CENTER);
        contentPane.add(jPanel1, BorderLayout.CENTER);
    }

    private void btnFindWords_actionPerformed() {
        jList1.setListData(new Object[] {});
        if (Utils.isValidText(txtLetters.getText())) {
            Filter f = getFilter();
            Tray t = new Tray(txtLetters.getText(), f, wordsmith, jLabel1);
            _thread = new WordProcessing(t, f, jList1);
            _thread.start();
        }
    }

    private void btnJustFilter_actionPerformed() {
        jList1.setListData(new Object[] {});
        if (txtMustContain.getText().length() > 0) {
            Utils.displayCollection(Utils.sortWords(getFilter().filter(wordsmith.getWords())), jList1);
        }
    }

    private void btnStop_actionPerformed() {
        if (_thread != null) {
            _thread.stopProcessing();
            _thread = null;
        }
    }

    private Filter getFilter() {
        Filter f = new Filter();
        if (!"Any".equals(selLength.getSelectedItem().toString())) {
            f.setLength(Integer.parseInt(selLength.getSelectedItem().toString()));
        }
        f.setOperator((Operator) selOperator.getSelectedItem());
        f.setMustContain(txtMustContain.getText().toLowerCase());
        return f;
    }

    protected void remainingLetters() {
        String usedLetters = JOptionPane.showInputDialog("Input all the letters currently being used.\nUse "+Utils.WILDCARD+" for blanks.");
        com.jamescookie.scrabble.Type type = getScrabbleType();
        if (usedLetters != null) {
            JOptionPane.showMessageDialog(
                    this,
                    RemainingLetters.lettersLeft(usedLetters, type)
                );
        }
    }

    private void captureEnter(KeyEvent e) {
        if (KeyEvent.VK_ENTER == e.getKeyChar()) {
            btnFindWords_actionPerformed();
        }
    }
}
