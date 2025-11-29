package use_case.login;

import entity.Token;
import use_case.exception.LoginFailedException;
import use_case.session.SessionDataAccessInterface;

/**
 * The interactor for the Signup Use Case.
 */
public class LoginInteractor implements LoginInputBoundary {
    private final LoginOutputBoundary loginPresenter;
    private final LoginDataAccessInterface userDataAccessObject;
    private final SessionDataAccessInterface sessionDataAccessObject;

    public LoginInteractor(LoginOutputBoundary loginPresenter, LoginDataAccessInterface userDataAccessObject,
            SessionDataAccessInterface sessionDataAccessObject) {
        this.loginPresenter = loginPresenter;
        this.userDataAccessObject = userDataAccessObject;
        this.sessionDataAccessObject = sessionDataAccessObject;
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
                final Token token = userDataAccessObject.login(username, password);
                sessionDataAccessObject.setCurrentToken(token);

                // TODO: Determine if the user is in a room
                // For now, we assume the user is not in a room
                // We need to look up in DynamoDB to check this information
                final LoginOutputData output = new LoginOutputData(username, false);
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
