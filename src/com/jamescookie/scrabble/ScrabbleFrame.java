package com.jamescookie.scrabble;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class ScrabbleFrame extends JFrame {
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
    private final JMenuBar jMenuBar1 = new JMenuBar();
    private final JMenu jMenu1 = new JMenu();
    private final JMenuItem jMenuItem1 = new JMenuItem();
    JMenuItem jMenuItem2 = new JMenuItem();

    //Construct the frame
    public ScrabbleFrame() {
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Component initialization
    private void jbInit() {
        JPanel contentPane = (JPanel) getContentPane();
        this.setJMenuBar(jMenuBar1);
        setSize(new Dimension(220, 320));
        setTitle("Scrabble - Version " + Utils.VERSION);
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
        jMenu1.setText("Extra");
        jMenuItem1.setText("Remaining letters");
        jMenuItem1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jMenuItem1_actionPerformed(e);
            }
        });
        jMenuItem2.setText("Show two letter words");
        jMenuItem2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jMenuItem2_actionPerformed(e);
            }
        });
        jScrollPane1.getViewport().add(jList1, null);
        jPanel7.add(selOperator, null);
        jPanel7.add(selLength, null);
        jPanel6.add(btnFindWords, null);
        jPanel6.add(btnStop, null);
        jPanel6.add(btnJustFilter, null);
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
        jMenuBar1.add(jMenu1);
        jMenu1.add(jMenuItem1);
        jMenu1.add(jMenuItem2);
    }

    //Overridden so we can exit when window is closed
    protected void processWindowEvent(WindowEvent e) {
        super.processWindowEvent(e);
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            System.exit(0);
        }
    }

    private void btnFindWords_actionPerformed() {
        jList1.setListData(new Object[] {});
        if (Utils.isValidText(txtLetters.getText())) {
            Filter f = getFilter();
            Tray t = new Tray(txtLetters.getText(), f, jLabel1);
            _thread = new WordProcessing(t, f, jList1);
            _thread.start();
        }
    }

    private void btnJustFilter_actionPerformed() {
        jList1.setListData(new Object[] {});
        if (txtMustContain.getText().length() > 0) {
            Utils.displayCollection(Utils.sortWords(getFilter().filter(WordLoader._words)), jList1);
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

    public void jMenuItem1_actionPerformed(ActionEvent e) {
        String usedLetters = JOptionPane.showInputDialog("Input all the letters currently on the board.\nUse ? for blanks.");
        if (usedLetters != null) {
            JOptionPane.showMessageDialog(
                    this,
                    new RemainingLetters().lettersLeft(usedLetters)
                );
        }
    }

    public void jMenuItem2_actionPerformed(ActionEvent e) {

    }
}
