package use_case.login;

import entity.User;

/**
 * The interactor for the Signup Use Case.
 */
public class LoginInteractor implements LoginInputBoundary {
    private final LoginOutputBoundary loginPresenter;
    private final LoginDataAccessInterface userDataAccessObject;

    public LoginInteractor(LoginOutputBoundary loginPresenter, LoginDataAccessInterface userDataAccessObject) {
        this.loginPresenter = loginPresenter;
        this.userDataAccessObject = userDataAccessObject;
    }

    @Override
    public void execute(LoginInputData loginInputData) {
        final String username = loginInputData.getUsername();
        final String password = loginInputData.getPassword();

        try {
            final User user = userDataAccessObject.login(username, password);

            final LoginOutputData output = new LoginOutputData(user.getUsername());
            loginPresenter.prepareSuccessView(output);

        } catch (LoginFailedException ex) {
            loginPresenter.prepareFailView(ex.getMessage());
        }
    }

    @Override
    public void switchToSignupView() {
        loginPresenter.switchToSignupView();
    }
}
