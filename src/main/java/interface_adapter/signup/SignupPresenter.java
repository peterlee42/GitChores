package interface_adapter.signup;

import interface_adapter.ViewManagerModel;
import interface_adapter.git_console.GitConsoleViewModel;
import interface_adapter.login.LoginViewModel;
import use_case.signup.SignupOutputBoundary;
import use_case.signup.SignupOutputData;

/**
 * The Presenter for the Signup Use Case.
 */
public class SignupPresenter implements SignupOutputBoundary {

    private final SignupViewModel signupViewModel;
    private final LoginViewModel loginViewModel;
    private final ViewManagerModel viewManagerModel;
    private final GitConsoleViewModel gitConsoleViewModel;

    public SignupPresenter(ViewManagerModel viewManagerModel, SignupViewModel signupViewModel,
            LoginViewModel loginViewModel, GitConsoleViewModel gitConsoleViewModel) {
        this.signupViewModel = signupViewModel;
        this.loginViewModel = loginViewModel;
        this.viewManagerModel = viewManagerModel;
        this.gitConsoleViewModel = gitConsoleViewModel;
    }

    @Override
    public void prepareSuccessView(SignupOutputData response) {
        final SignupState signupState = signupViewModel.getState();
        signupState.setUsername(response.getUsername());
        signupViewModel.firePropertyChange();

        // TODO: Make it switch to main view or join view. This is temporary to get it
        // started.
        viewManagerModel.setState(gitConsoleViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void prepareFailView(String error) {
        final SignupState signupState = signupViewModel.getState();
        signupState.setUsernameError(error);
        signupViewModel.firePropertyChange();
    }

    @Override
    public void switchToLoginView() {
        viewManagerModel.setState(loginViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }
}
