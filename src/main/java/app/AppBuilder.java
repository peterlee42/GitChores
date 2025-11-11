package app;

import java.awt.*;

import javax.swing.*;

import interface_adapter.join.JoinViewModel;
import view.JoinView;
import view.ProfileView;

/**
 * Class for building the app.
 */
public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();

    private JoinView joinView;
    private ProfileView profileView;

    /**
     * Constructor for AppBuilder.
     */
    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    /**
     * Adds join view.
     *
     * @return AppBuilder
     */
    public AppBuilder addJoinView() {
        final JoinViewModel joinViewModel = new JoinViewModel();
        joinView = new JoinView(joinViewModel, cardPanel, cardLayout);
        cardPanel.add(joinView, joinView.getViewName());
        return this;
    }

    /**
     * Adds profile view.
     *
     * @return AppBuilder
     */
    public AppBuilder addProfileView() {
        profileView = new ProfileView(cardPanel, cardLayout);
        cardPanel.add(profileView, profileView.getViewName());
        return this;
    }

    /**
     * Builds the view.
     *
     * @return JFrame
     */
    public JFrame build() {
        final JFrame application = new JFrame("GitChores");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        application.add(cardPanel);

        return application;
    }
}
