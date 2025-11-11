package app;

import java.awt.*;

import javax.swing.*;

import interface_adapter.git_console.GitConsoleController;
import interface_adapter.git_console.GitConsolePresenter;
import interface_adapter.git_console.GitConsoleViewModel;
import interface_adapter.join.JoinViewModel;
import use_case.git_console.GitConsoleInputBoundary;
import use_case.git_console.GitConsoleInteractor;
import use_case.git_console.GitConsoleOutputBoundary;
import view.GitConsoleView;
import view.JoinView;

/**
 * Class for building the app.
 */
@SuppressWarnings({"checkstyle:ClassDataAbstractionCoupling", "checkstyle:SuppressWarnings"})
public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();

    private JoinView joinView;
    private GitConsoleView gitConsoleView;
    private GitConsoleViewModel gitConsoleViewModel;

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
        joinView = new JoinView(joinViewModel);
        cardPanel.add(joinView, joinView.getViewName());
        return this;
    }

    /**
     * Placeholder.
     *
     * @return Placeholder.
     */
    public AppBuilder addGitConsoleView() {
        gitConsoleViewModel = new GitConsoleViewModel();
        gitConsoleView = new GitConsoleView(gitConsoleViewModel);
        cardPanel.add(gitConsoleView, gitConsoleView.getViewName());
        return this;
    }

    /**
     * Placeholder.
     *
     * @return Placeholder.
     */
    public AppBuilder addGitConsoleUseCase() {
        // To be implemented
        final GitConsoleOutputBoundary gitConsoleOutputBoundary = new GitConsolePresenter(gitConsoleViewModel);
        final GitConsoleInputBoundary gitConsoleInteractor = new GitConsoleInteractor(gitConsoleOutputBoundary);

        final GitConsoleController controller = new GitConsoleController(gitConsoleInteractor);
        gitConsoleView.setGitConsoleController(controller);
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
