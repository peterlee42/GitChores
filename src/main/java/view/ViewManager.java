package view;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;

/**
 * Manages switching between different views in the application.
 */
public class ViewManager implements PropertyChangeListener {
    private final CardLayout cardLayout;
    private final JPanel views;

    /**
     * Constructs a ViewManager with the given views panel, card layout, and view
     * manager model.
     * 
     * @param views      the panel containing all views
     * @param cardLayout the CardLayout managing the views
     */
    public ViewManager(JPanel views, CardLayout cardLayout) {
        this.views = views;
        this.cardLayout = cardLayout;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            final String viewModelName = (String) evt.getNewValue();
            cardLayout.show(views, viewModelName);
        }
    }
}
