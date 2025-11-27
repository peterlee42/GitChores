package interface_adapter.login;

import interface_adapter.ViewManagerModel;
import interface_adapter.logged_in.MainViewModel;
import interface_adapter.logged_in.TokenState;
import interface_adapter.signup.SignupViewModel;
import use_case.login.LoginOutputBoundary;
import use_case.login.LoginOutputData;

public class LoginPresenter implements LoginOutputBoundary {

    private final LoginViewModel loginViewModel;
    private final SignupViewModel signupViewModel;
    private final ViewManagerModel viewManagerModel;
    private final MainViewModel mainViewModel;

    public LoginPresenter(ViewManagerModel viewManagerModel,
            LoginViewModel loginViewModel, SignupViewModel signupViewModel, MainViewModel mainViewModel) {
        this.signupViewModel = signupViewModel;
        this.loginViewModel = loginViewModel;
        this.mainViewModel = mainViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(LoginOutputData response) {
        final LoginState loginState = loginViewModel.getState();
        loginState.setUsername(response.getUsername());
        loginViewModel.firePropertyChange();

        // clear state
        loginViewModel.setState(new LoginState());

        // add tokens to TokenState
        final TokenState tokenState = mainViewModel.getState();
        tokenState.setIdToken(response.getIdToken());
        tokenState.setAccessToken(response.getAccessToken());
        tokenState.setRefreshToken(response.getRefreshToken());
        mainViewModel.firePropertyChange();

        // switch view
        viewManagerModel.setState(mainViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void prepareFailView(String error) {
        final LoginState loginState = loginViewModel.getState();
        loginState.setLoginError(error);
        loginViewModel.firePropertyChange();
    }

    @Override
    public void switchToSignupView() {
        viewManagerModel.setState(signupViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }
}
