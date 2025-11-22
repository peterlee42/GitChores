package app;

import javax.swing.JFrame;

/**
 * Main class for the GitChores.
 */
public class Main {
    /**
     * Main class constructor.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        final AppBuilder appBuilder = new AppBuilder();
        final JFrame application = appBuilder
                .addViewManager()
                .addLoginView()
                // .addSignupView()
                // .addJoinView()
                .addDashboardView()
                .addGitConsoleView()
                .addGitConsoleUseCase()
                // .addLoginView()
                .addProfileView()
                .addMainView()
                .addChoreCreationView()
                .build();

        application.setMinimumSize(application.getMinimumSize());

        application.pack();
        application.setLocationRelativeTo(null);
        application.setVisible(true);
    }
}
