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

        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            loginPresenter.prepareFailView("Username and password cannot be empty.");
            return;
        } else {

            try {
                final User user = userDataAccessObject.login(username, password);

                final LoginOutputData output = new LoginOutputData(user.getUsername(), user.getId(),
                        user.getEmail());
                loginPresenter.prepareSuccessView(output);

            } catch (LoginFailedException ex) {
                loginPresenter.prepareFailView(ex.getMessage());
            }
        }
    }

    @Override
    public void switchToSignupView() {
        loginPresenter.switchToSignupView();
    }
}
