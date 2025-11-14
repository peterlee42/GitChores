package view;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.border.Border;

public class CustomButton extends JButton {
    CustomButton(String text, Color backgroundColor, Color foregroundColor, Border border) {
        this.setText(text);
        this.setBackground(backgroundColor);
        this.setForeground(foregroundColor);
        this.setBorder(border);
        this.setOpaque(true);
        this.setBorderPainted(false);
    }
}
