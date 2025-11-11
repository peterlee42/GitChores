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
        AppBuilder appBuilder = new AppBuilder();
        JFrame application = appBuilder
                .addJoinView()
                .addGitConsoleView()
                .addGitConsoleUseCase()
                .build();
        application.setMinimumSize(new java.awt.Dimension(400, 300));
        // ^ ABOVE LINE IS SUBJECT TO CHANGE

        application.pack();
        application.setLocationRelativeTo(null);
        application.setVisible(true);
    }
}
