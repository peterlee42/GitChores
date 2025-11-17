package view;

import javax.swing.*;

public class DashboardView extends JPanel {
    private final String viewName = "dashboard";

    public DashboardView() {
        final JLabel placeholderLabel = new JLabel("Dashboard - Coming Soon");
        placeholderLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(placeholderLabel);
    }

    public String getViewName() {
        return viewName;
    }
}
