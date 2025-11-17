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
                // .addJoinView()
                // .addGitConsoleView()
                // .addGitConsoleUseCase()
                .addSignupView()
                // .addLoginView()
                .addProfileView()
                .build();

        application.setMinimumSize(application.getMinimumSize());

        application.pack();
        application.setLocationRelativeTo(null);
        application.setVisible(true);
    }
}
