package interface_adapter.login;

import interface_adapter.ViewManagerModel;
import interface_adapter.logged_in.SessionState;
import interface_adapter.logged_in.SessionViewModel;
import interface_adapter.room.join.JoinViewModel;
import interface_adapter.signup.SignupViewModel;
import use_case.login.LoginOutputBoundary;
import use_case.login.LoginOutputData;

public class LoginPresenter implements LoginOutputBoundary {

    private final LoginViewModel loginViewModel;
    private final SignupViewModel signupViewModel;
    private final ViewManagerModel viewManagerModel;
    private final SessionViewModel sessionViewModel;
    private final JoinViewModel joinViewModel;

    public LoginPresenter(ViewManagerModel viewManagerModel,
            LoginViewModel loginViewModel, SignupViewModel signupViewModel, SessionViewModel sessionViewModel,
            JoinViewModel joinViewModel) {
        this.signupViewModel = signupViewModel;
        this.loginViewModel = loginViewModel;
        this.sessionViewModel = sessionViewModel;
        this.viewManagerModel = viewManagerModel;
        this.joinViewModel = joinViewModel;

    }

    @Override
    public void prepareSuccessView(LoginOutputData response) {
        final SessionState sessionState = sessionViewModel.getState();
        sessionState.setUsername(response.getUsername());
        sessionState.setUserId(response.getUserId());
        sessionState.setEmail(response.getEmail());

        sessionViewModel.firePropertyChange();

        // clear login state
        loginViewModel.setState(new LoginState());
        loginViewModel.firePropertyChange();

        final SessionState newSessionState = sessionViewModel.getState();
        System.out.println(newSessionState.toString());

        // TODO: switch to screen based on user status
        viewManagerModel.setState(joinViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void prepareFailView(String error) {
        final LoginState loginState = loginViewModel.getState();
        loginState.setLoginError(error);
        loginViewModel.setState(loginState);
        loginViewModel.firePropertyChange();
    }

    @Override
    public void switchToSignupView() {
        viewManagerModel.setState(signupViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }
}
