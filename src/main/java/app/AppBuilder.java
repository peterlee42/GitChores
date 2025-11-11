package app;

import interface_adapter.git_console.GitConsoleController;
import interface_adapter.git_console.GitConsolePresenter;
import interface_adapter.git_console.GitConsoleViewModel;
import use_case.git_console.GitConsoleInputBoundary;
import use_case.git_console.GitConsoleInteractor;
import use_case.git_console.GitConsoleOutputBoundary;
import view.GitConsoleView;
import view.JoinView;
import view.ViewManager;
import interface_adapter.ViewManagerModel;
import interface_adapter.join.JoinViewModel;

import javax.swing.*;
import java.awt.*;

public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();

    private JoinView joinView;
    private GitConsoleView gitConsoleView;
    private GitConsoleViewModel gitConsoleViewModel;

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    public AppBuilder addJoinView() {
        JoinViewModel joinViewModel = new JoinViewModel();
        joinView = new JoinView(joinViewModel);
        cardPanel.add(joinView, joinView.getViewName());
        return this;
    }

    public AppBuilder addGitConsoleView() {
        gitConsoleViewModel = new GitConsoleViewModel();
        gitConsoleView = new GitConsoleView(gitConsoleViewModel);
        cardPanel.add(gitConsoleView, gitConsoleView.getViewName());
        return this;
    }

    public AppBuilder addGitConsoleUseCase() {
        // To be implemented
        final GitConsoleOutputBoundary gitConsoleOutputBoundary = new GitConsolePresenter(gitConsoleViewModel);
        final GitConsoleInputBoundary gitConsoleInteractor = new GitConsoleInteractor(gitConsoleOutputBoundary);

        GitConsoleController controller = new GitConsoleController(gitConsoleInteractor);
        gitConsoleView.setGitConsoleController(controller);
        return this;
    }

    public JFrame build() {
        final JFrame application = new JFrame("GitChores");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        application.add(cardPanel);

        // TODO: firepropertychange.
        // viewManagerModel.setState(joinView.getViewName());
        // viewManagerModel.firePropertyChange();

        return application;
    }
}
