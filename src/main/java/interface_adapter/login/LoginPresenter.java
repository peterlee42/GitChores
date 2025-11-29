package interface_adapter.login;

import interface_adapter.ViewManagerModel;
import interface_adapter.room.join.JoinViewModel;
import interface_adapter.signup.SignupViewModel;
import use_case.login.LoginOutputBoundary;
import use_case.login.LoginOutputData;

public class LoginPresenter implements LoginOutputBoundary {

    private final LoginViewModel loginViewModel;
    private final SignupViewModel signupViewModel;
    private final ViewManagerModel viewManagerModel;
    private final JoinViewModel joinViewModel;

    public LoginPresenter(ViewManagerModel viewManagerModel,
            LoginViewModel loginViewModel, SignupViewModel signupViewModel,
            JoinViewModel joinViewModel) {
        this.signupViewModel = signupViewModel;
        this.loginViewModel = loginViewModel;
        this.viewManagerModel = viewManagerModel;
        this.joinViewModel = joinViewModel;

    }

    @Override
    public void prepareSuccessView(LoginOutputData response) {

        // clear login state
        loginViewModel.setState(new LoginState());
        loginViewModel.firePropertyChange();

        if (!response.isInRoom()) {
            viewManagerModel.setState(joinViewModel.getViewName());
            viewManagerModel.firePropertyChange();
        } else {
            viewManagerModel.setState("main");
            viewManagerModel.firePropertyChange();
        }
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
