package app;

import java.awt.*;

import javax.swing.*;

import interface_adapter.ViewManagerModel;
import interface_adapter.git_console.GitConsoleController;
import interface_adapter.git_console.GitConsolePresenter;
import interface_adapter.git_console.GitConsoleViewModel;
import interface_adapter.join.JoinViewModel;
import interface_adapter.login.LoginViewModel;
import interface_adapter.signup.SignupViewModel;
import interface_adapter.chore_creation.ChoreCreationViewModel;
import use_case.git_console.GitConsoleInputBoundary;
import use_case.git_console.GitConsoleInteractor;
import use_case.git_console.GitConsoleOutputBoundary;
import view.Constants;
import view.GitConsoleView;
import view.JoinView;
import view.LoginView;
import view.ProfileView;
import view.SignupView;
import view.ViewManager;
import view.ChoreCreationView;

/**
 * Class for building the app.
 */
@SuppressWarnings({ "checkstyle:ClassDataAbstractionCoupling", "checkstyle:SuppressWarnings" })
public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();

    private JoinView joinView;
    private SignupView signupView;
    private LoginView loginView;
    private GitConsoleView gitConsoleView;
    private GitConsoleViewModel gitConsoleViewModel;
    private ChoreCreationView choreCreationView;

    private ViewManagerModel viewManagerModel;

    /**
     * Constructor for AppBuilder.
     */
    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    /**
     * Wires the ViewManager engine so views can switch via a shared model.
     *
     * @return AppBuilder
     */
    public AppBuilder addViewManager() {
        viewManagerModel = new ViewManagerModel();
        new ViewManager(cardPanel, cardLayout, viewManagerModel);
        return this;
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
     * Adds Sign Up View.
     *
     * @return AppBuilder
     */
    public AppBuilder addSignupView() {
        final SignupViewModel signupViewModel = new SignupViewModel();
        signupView = new SignupView(signupViewModel);
        cardPanel.add(signupView, signupView.getViewName());

        return this;
    }

    /**
     * Adds Login View.
     *
     * @return AppBuilder
     */
    public AppBuilder addLoginView() {
        final LoginViewModel loginViewModel = new LoginViewModel();
        loginView = new LoginView(loginViewModel);
        cardPanel.add(loginView, loginView.getViewName());

        return this;
    }

    /**
     * Adds Git Console view.
     *
     * @return AppBuilder
     */
    public AppBuilder addGitConsoleView() {
        gitConsoleViewModel = new GitConsoleViewModel();
        gitConsoleView = new GitConsoleView(gitConsoleViewModel);
        cardPanel.add(gitConsoleView, gitConsoleView.getViewName());
        return this;
    }

    /**
     * Adds Git Console use case.
     *
     * @return AppBuilder
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
     * Adds Chore Creation View.
     *
     * @return AppBuilder
     */
    public AppBuilder addChoreCreationView() {
        final ChoreCreationViewModel choreCreationViewModel = new ChoreCreationViewModel();
        choreCreationView = new ChoreCreationView(choreCreationViewModel);
        cardPanel.add(choreCreationView, choreCreationView.getViewName());

        return this;
    }

    /**
     * Adds Profile view (your screen). Does not modify teammates' views.
     *
     * @return AppBuilder
     */
    public AppBuilder addProfileView() {
        // Prefer going back to Signup; else Join; else default name
        final String backTarget;
        if (signupView != null) {
            backTarget = signupView.getViewName();
        } else if (joinView != null) {
            backTarget = joinView.getViewName();
        } else {
            backTarget = Constants.JOIN_VIEW_NAME;
        }

        // Navigation callback: always show the card; also drive CA engine if wired
        final java.util.function.Consumer<String> navigator = (String name) -> {
            if (viewManagerModel != null) {
                viewManagerModel.setActiveViewName(name);
            }
            cardLayout.show(cardPanel, name);
        };

        final ProfileView profileView = new ProfileView(viewManagerModel, backTarget, navigator);
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

        // Start on a sensible screen if the ViewManager is wired.
        if (viewManagerModel != null) {
            if (signupView != null) {
                viewManagerModel.setActiveViewName(signupView.getViewName());
            } else if (joinView != null) {
                viewManagerModel.setActiveViewName(joinView.getViewName());
            } else if (gitConsoleView != null) {
                viewManagerModel.setActiveViewName(gitConsoleView.getViewName());
            }
        }

        return application;
    }
}
