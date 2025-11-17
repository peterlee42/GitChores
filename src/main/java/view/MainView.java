package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class MainView extends JPanel implements ActionListener {
    private final String viewName = "main";

    private final CardLayout contentLayout = new CardLayout();
    private final JPanel contentPanel = new JPanel(contentLayout);

    private final JButton dashboardButton = new JButton("Dashboard");
    private final JButton consoleButton = new JButton("Console");
    private final JButton profileButton = new JButton("Profile");

    private final JPanel dashboardView;
    private final GitConsoleView consoleView;
    private final ProfileView profileView;

    @SuppressWarnings("checkstyle:MultipleStringLiterals")
    public MainView(GitConsoleView consolePanel, ProfileView profilePanel) {
        this.dashboardView = new JPanel();
        this.consoleView = consolePanel;
        this.profileView = profilePanel;

        setLayout(new BorderLayout());
        final JToolBar navBar = new JToolBar();
        navBar.setFloatable(false);

        navBar.add(dashboardButton);
        navBar.add(consoleButton);
        navBar.add(profileButton);

        add(navBar, BorderLayout.NORTH);

        contentPanel.add(dashboardView, "dashboard");
        contentPanel.add(consoleView, consoleView.getViewName());
        contentPanel.add(profileView, profileView.getViewName());

        add(contentPanel, BorderLayout.CENTER);

        dashboardButton.addActionListener(this);
        consoleButton.addActionListener(this);
        profileButton.addActionListener(this);

        contentLayout.show(contentPanel, "dashboard");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        final Object source = e.getSource();

        if (source == dashboardButton) {
            contentLayout.show(contentPanel, "dashboard");
        } else if (source == consoleButton) {
            contentLayout.show(contentPanel, consoleView.getViewName());
        } else if (source == profileButton) {
            contentLayout.show(contentPanel, profileView.getViewName());
        }
    }

    public String getViewName() {
        return viewName;
    }
}
