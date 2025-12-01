package view;

import java.beans.PropertyChangeListener;

import javax.swing.*;

import interface_adapter.logged_in.LoggedInState;

public class DashboardView extends JPanel implements PropertyChangeListener {
    private final String viewName = "dashboard";

    public DashboardView() {
        final JLabel placeholderLabel = new JLabel("Dashboard - Coming Soon");
        placeholderLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(placeholderLabel);
    }

    @Override
    public void propertyChange(java.beans.PropertyChangeEvent evt) {
        final LoggedInState state = (LoggedInState) evt.getNewValue();
        if (state.getErrorMessage() != null) {
            JOptionPane.showMessageDialog(this, state.getErrorMessage());
        }
    }

    public String getViewName() {
        return viewName;
    }
}
