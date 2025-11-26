package use_case.login;

import software.amazon.awssdk.services.cognitoidentityprovider.model.AuthenticationResultType;
import software.amazon.awssdk.services.cognitoidentityprovider.model.CognitoIdentityProviderException;
import software.amazon.awssdk.services.cognitoidentityprovider.model.NotAuthorizedException;
import software.amazon.awssdk.services.cognitoidentityprovider.model.UserNotFoundException;

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
            // Get tokens from Cognito
            System.err.println("Attempting to log in user: " + username);
            final AuthenticationResultType result = userDataAccessObject.login(username, password);

            System.out.println("Login successful for user: " + username + ". Token: " + result.idToken());

            final LoginOutputData output = new LoginOutputData(username);
            loginPresenter.prepareSuccessView(output);

        } catch (NotAuthorizedException ex) {
            loginPresenter.prepareFailView("Incorrect username or password.");
        } catch (UserNotFoundException ex) {
            loginPresenter.prepareFailView("Account does not exist.");
        } catch (CognitoIdentityProviderException ex) {
            loginPresenter.prepareFailView("Login failed. Please try again.");
        }
    }

    @Override
    public void switchToSignupView() {
        loginPresenter.switchToSignupView();
    }
}
