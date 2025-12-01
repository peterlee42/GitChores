package app;

import java.awt.CardLayout;
import java.util.function.Consumer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import interface_adapter.ViewManagerModel;
import interface_adapter.profile.ProfileController;
import use_case.profile.UpdateProfileInputBoundary;
import use_case.profile.UpdateProfileInputData;
import view.ProfileView;

/**
 * Standalone launcher for testing ProfileView without going through
 * Login/Join/Main. Used only for manual local testing.
 */
public final class ProfileViewTestMain {

    private ProfileViewTestMain() {
        // utility class; no instances
    }

    /**
     * Entry point for the ProfileView test application.
     *
     * @param args command-line arguments (unused)
     */
    @SuppressWarnings("checkstyle:UncommentedMain")
    public static void main(final String[] args) {
        SwingUtilities.invokeLater(ProfileViewTestMain::createAndShowProfileWindow);
    }

    /**
     * Builds and displays a simple frame that contains only the Profile view.
     */
    private static void createAndShowProfileWindow() {
        // Simple CardLayout panel; only one card (Profile)
        final JPanel cardPanel = new JPanel(new CardLayout());
        final CardLayout cardLayout = (CardLayout) cardPanel.getLayout();

        final UpdateProfileInputBoundary stubInteractor =
                (UpdateProfileInputData data) -> {

                };

        final ProfileController profileController =
                new ProfileController(stubInteractor);

        final Consumer<String> navigator = name -> {
            cardLayout.show(cardPanel, name);
        };

        final ViewManagerModel viewManagerModel = null;

        final ProfileView profileView =
                new ProfileView(viewManagerModel,
                        "join",
                        navigator,
                        profileController);

        // Optional: set some test user info
        profileView.setUserInfo("Test User", "test@example.com");

        cardPanel.add(profileView, profileView.getViewName());

        final JFrame frame = new JFrame("ProfileView Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(cardPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        cardLayout.show(cardPanel, profileView.getViewName());
    }
}
