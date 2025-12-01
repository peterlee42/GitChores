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
                .addLoginView()
                .addSignupView()
                .addCreateRoomView()
                .addJoinView()
                .addDashboardView()
                .addGitConsoleView()
                .addProfileView()
                .addMainView()
                .addSignupUseCase()
                .addLoginUseCase()
                .addGitConsoleUseCase()
                .addRoomUseCases()
                .addProfileUseCase()
                .build();

        application.setFocusable(true);
        application.requestFocus();

        application.pack();
        application.setLocationRelativeTo(null);
        application.setVisible(true);
    }
}
