package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JButton;
import javax.swing.border.Border;

public class ButtonBuilder {
    private String text;
    private Font font;
    private Color background;
    private Color foreground;
    private Border border;
    private Border focusBorder;
    private boolean isOpaque = true;

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
     * Adds focus border to button.
     * 
     * @param focusBorder when the button is focused, use this border
     * @return ButtonBuilder
     */
    public ButtonBuilder setFocusBorder(Border focusBorder) {
        this.focusBorder = focusBorder;
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
            button.setOpaque(isOpaque);
            button.setBackground(background);
        }
        if (foreground != null) {
            button.setForeground(foreground);
        }
        if (font != null) {
            button.setFont(font);
        }

        // default border will be empty border if none is provided
        button.setBorderPainted(true);
        if (border != null) {
            button.setBorder(border);
        } else {
            button.setBorder(ViewConstants.EMPTY_BORDER);
        }

        if (focusBorder != null) {
            // turn off the default focus painting
            button.setFocusPainted(false);

            // turn focus border on if we focus on it
            button.addFocusListener(new FocusAdapter() {
                public void focusGained(FocusEvent evt) {
                    button.setBorder(focusBorder);
                }

                public void focusLost(FocusEvent evt) {
                    if (border != null) {
                        button.setBorder(border);
                    } else {
                        button.setBorder(ViewConstants.EMPTY_BORDER);
                    }
                }
            });
        }
        return button;
    }
}
