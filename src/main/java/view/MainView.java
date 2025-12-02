package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;
import javax.swing.border.Border;

import interface_adapter.dashboard.DashboardController;
import interface_adapter.logged_in.LoggedInState;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.profile.ProfileController;

public class MainView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "logged_in";

    private final int navBarHeight = 32;
    private final int navButtonBorder = 8;

    private final CardLayout contentLayout = new CardLayout();
    private final JPanel contentPanel = new JPanel(contentLayout);

    private final JButton dashboardButton = new JButton("Dashboard");
    private final JButton consoleButton = new JButton("Console");
    private final JButton profileButton = new JButton("Profile");

    private final DashboardView dashboardView;
    private final GitConsoleView consoleView;
    private final ProfileView profileView;

    private final LoggedInViewModel loggedInViewModel;

    private DashboardController dashboardController;
    private ProfileController profileController;

    private JButton activeButton;

    public MainView(LoggedInViewModel loggedInViewModel, DashboardView dashboardPanel, GitConsoleView consolePanel,
            ProfileView profilePanel) {
        this.loggedInViewModel = loggedInViewModel;
        this.dashboardView = dashboardPanel;
        this.consoleView = consolePanel;
        this.profileView = profilePanel;
        setLayout(new BorderLayout());
        final JToolBar navBar = createNavBar();

        final ButtonGroup group = new ButtonGroup();
        group.add(dashboardButton);
        group.add(consoleButton);
        group.add(profileButton);

        dashboardButton.setSelected(true);

        styleNavButton(dashboardButton);
        styleNavButton(consoleButton);
        styleNavButton(profileButton);

        navBar.add(dashboardButton);
        navBar.add(consoleButton);
        navBar.add(profileButton);

        add(navBar, BorderLayout.NORTH);

        contentPanel.add(dashboardView, dashboardView.getViewName());
        contentPanel.add(consoleView, consoleView.getViewName());
        contentPanel.add(profileView, profileView.getViewName());

        add(contentPanel, BorderLayout.CENTER);

        dashboardButton.addActionListener(this);
        consoleButton.addActionListener(this);
        profileButton.addActionListener(this);

        contentLayout.show(contentPanel, dashboardView.getViewName());
        setActiveTab(dashboardButton);
    }

    private JToolBar createNavBar() {
        final JToolBar navBar = new JToolBar();

        navBar.setFloatable(false);
        navBar.setRollover(true);
        navBar.setBackground(ViewColors.ORANGE);
        navBar.setOpaque(true);
        navBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));

        final Dimension navSize = navBar.getPreferredSize();
        navBar.setPreferredSize(new Dimension(navSize.width, navBarHeight));

        return navBar;
    }

    private void styleNavButton(JButton button) {
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        button.setForeground(Color.WHITE);
        button.setBorderPainted(true);
        button.setBorder(
                BorderFactory.createEmptyBorder(navButtonBorder, 2 * navButtonBorder,
                        navButtonBorder, 2 * navButtonBorder));
    }

    private void setActiveTab(JButton button) {
        activeButton = button;

        final JButton[] buttons = { dashboardButton, consoleButton, profileButton };
        final Border padding = BorderFactory.createEmptyBorder(navButtonBorder,
                2 * navButtonBorder,
                navButtonBorder, 2 * navButtonBorder);

        for (JButton b : buttons) {
            final boolean isActive = b == activeButton;

            final Font baseFont = ViewConstants.LABEL_FONT;
            final Font derivedFont;
            if (isActive) {
                derivedFont = baseFont.deriveFont(Font.BOLD);
            } else {
                derivedFont = baseFont.deriveFont(Font.PLAIN);
            }
            b.setFont(derivedFont);

            if (isActive) {
                final Border underline = BorderFactory.createMatteBorder(0, 0, 3, 0, Color.BLACK);
                b.setBorder(BorderFactory.createCompoundBorder(underline, padding));
            } else {
                b.setBorder(padding);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        final Object source = e.getSource();

        final LoggedInState state = loggedInViewModel.getState();

        if (source == dashboardButton) {
            contentLayout.show(contentPanel, dashboardView.getViewName());
            state.setActiveTab(dashboardView.getViewName());
            loggedInViewModel.setState(state);
            loggedInViewModel.firePropertyChange();
        } else if (source == consoleButton) {
            contentLayout.show(contentPanel, consoleView.getViewName());
            state.setActiveTab(consoleView.getViewName());

            loggedInViewModel.setState(state);
            loggedInViewModel.firePropertyChange();
        } else if (source == profileButton) {
            contentLayout.show(contentPanel, profileView.getViewName());
            state.setActiveTab(profileView.getViewName());

            loggedInViewModel.setState(state);
            loggedInViewModel.firePropertyChange();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final LoggedInState state = loggedInViewModel.getState();
        if (state.getErrorMessage() != null) {
            JOptionPane.showMessageDialog(this, state.getErrorMessage());
        }
        final String activeTab = state.getActiveTab();
        if (activeTab.equals(dashboardView.getViewName())) {
            setActiveTab(dashboardButton);
            contentLayout.show(contentPanel, dashboardView.getViewName());
            if (dashboardController != null) {
                dashboardController.execute();
            }
        } else if (activeTab.equals(consoleView.getViewName())) {
            contentLayout.show(contentPanel, consoleView.getViewName());
            setActiveTab(consoleButton);
        } else if (activeTab.equals(profileView.getViewName())) {
            setActiveTab(profileButton);
            contentLayout.show(contentPanel, profileView.getViewName());
            if (profileController != null) {
                profileController.execute();
            }
        }
    }

    public void setDashboardController(DashboardController controller) {
        this.dashboardController = controller;
    }

    public void setProfileController(ProfileController profileController) {
        this.profileController = profileController;
    }

    public String getViewName() {
        return viewName;
    }
}
