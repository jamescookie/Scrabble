package com.jamescookie.swing;

import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class JTextFieldWithLabel extends JPanel {
    public static final int ABOVE = 1;
    public static final int BELOW = 2;
    public static final int LEFT = 3;
    public static final int RIGHT = 4;

    private final JTextField textfield = new JTextField();
    private final JLabel label = new JLabel();

    public JTextFieldWithLabel() {
        this(ABOVE, "JTextFieldWithLabel");
    }

    public JTextFieldWithLabel(int labelPosition, String labelText) {
        setLayout(new BorderLayout());
        label.setText(labelText);
        switch(labelPosition) {
            case BELOW:
                super.add(label, BorderLayout.SOUTH);
                break;
            case LEFT:
                super.add(label, BorderLayout.WEST);
                break;
            case RIGHT:
                super.add(label, BorderLayout.EAST);
                break;
            default:
                super.add(label, BorderLayout.NORTH);
                break;
        }
        super.add(textfield, BorderLayout.CENTER);
    }

    public JTextField getTextfield() {
        return textfield;
    }

    public JLabel getLabel() {
        return label;
    }

    public Component add(Component comp) {
        return comp;
    }

    public Component add(String name, Component comp) {
        return comp;
    }

    public Component add(Component comp, int index) {
        return comp;
    }

    public void add(Component comp, Object constraints) {
    }

    public void add(Component comp, Object constraints, int index) {
    }

}
