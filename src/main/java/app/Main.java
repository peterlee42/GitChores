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
                // .addJoinView()
                // .addGitConsoleView()
                // .addGitConsoleUseCase()
                .addSignupView()
                .build();

        application.pack();
        application.setLocationRelativeTo(null);
        application.setVisible(true);
    }
}
