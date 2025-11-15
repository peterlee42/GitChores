package view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.border.Border;

public class ButtonBuilder {
    private String text;
    private Font font;
    private Color background;
    private Color foreground;
    private Border border;
    private boolean isOpaque = true;
    private boolean isBorderPainted;

    /**
     * Adds text to button.
     * 
     * @param text Text to add
     * @return ButtonBuilder
     */
    public ButtonBuilder setText(String text) {
        this.text = text;
        return this;
    }

    /**
     * Adds background color to button.
     * 
     * @param color color to add
     * @return ButtonBuilder
     */
    public ButtonBuilder setBackground(Color color) {
        this.background = color;
        return this;
    }

    /**
     * Adds foreground color to button.
     * 
     * @param color color to add
     * @return ButtonBuilder
     */
    public ButtonBuilder setForeground(Color color) {
        this.foreground = color;
        return this;
    }

    /**
     * Adds border to button.
     * 
     * @param border border to add
     * @return ButtonBuilder
     */
    public ButtonBuilder setBorder(Border border) {
        this.border = border;
        return this;
    }

    /**
     * Adds font to button.
     * 
     * @param font font to add
     * @return ButtonBuilder
     */
    public ButtonBuilder setFont(Font font) {
        this.font = font;
        return this;
    }

    /**
     * Builds the button.
     *
     * @return JButton
     */
    public JButton build() {
        final JButton button = new JButton(text);
        if (text != null) {
            button.setText(text);
        }
        if (background != null) {
            button.setBackground(background);
        }
        if (foreground != null) {
            button.setForeground(foreground);
        }
        if (border != null) {
            button.setBorder(border);
        }
        if (font != null) {
            button.setFont(font);
        }
        button.setOpaque(isOpaque);
        button.setBorderPainted(isBorderPainted);
        return button;
    }
}
