package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;
import javax.swing.border.Border;

import interface_adapter.logged_in.SessionState;
import interface_adapter.logged_in.SessionViewModel;

public class MainView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "main";

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

    private final SessionViewModel sessionViewModel;

    private JButton activeButton;

    public MainView(DashboardView dashboardPanel, GitConsoleView consolePanel,
            ProfileView profilePanel, SessionViewModel sessionViewModel) {
        this.dashboardView = dashboardPanel;
        this.consoleView = consolePanel;
        this.profileView = profilePanel;
        this.sessionViewModel = sessionViewModel;
        this.sessionViewModel.addPropertyChangeListener(this);

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

        contentLayout.show(contentPanel, "dashboard");
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

        if (source == dashboardButton) {
            // TODO: Use ViewModel constant
            contentLayout.show(contentPanel, "dashboard");
            setActiveTab(dashboardButton);
        } else if (source == consoleButton) {
            contentLayout.show(contentPanel, consoleView.getViewName());
            setActiveTab(consoleButton);
        } else if (source == profileButton) {
            contentLayout.show(contentPanel, profileView.getViewName());
            setActiveTab(profileButton);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final SessionState state = (SessionState) evt.getNewValue();
        if (state.getSessionError() != null) {
            JOptionPane.showMessageDialog(this, "Error with the current session.");
        }
    }

    public String getViewName() {
        return viewName;
    }
}
