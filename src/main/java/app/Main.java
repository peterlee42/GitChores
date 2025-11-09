package app;

import javax.swing.JFrame;

public class Main {
    /**
     * Main class for java app.
     *
     * @param args command-line arguments
     */

    public static void main(String[] args) {
        final AppBuilder appBuilder = new AppBuilder();
        final JFrame application = appBuilder.addJoinView().build();

        application.pack();
        application.setLocationRelativeTo(null);
        application.setVisible(true);
    }
}
