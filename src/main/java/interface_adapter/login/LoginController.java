package interface_adapter.login;

import use_case.login.LoginInputBoundary;
import use_case.login.LoginInputData;

public class LoginController {
    private final LoginInputBoundary userLoginUseCaseInteractor;

    public LoginController(LoginInputBoundary userLoginUseCaseInteractor) {
        this.userLoginUseCaseInteractor = userLoginUseCaseInteractor;
    }

    /**
     * Executes the Signup Use Case.
     * 
     * @param username the username to sign up
     * @param password the password
     */
    public void execute(String username, String password) {
        final LoginInputData loginInputData = new LoginInputData(
                username, password);

        userLoginUseCaseInteractor.execute(loginInputData);
    }

    /**
     * Executes the "switch to SignupView" Use Case.
     */
    public void switchToSignupView() {
        userLoginUseCaseInteractor.switchToSignupView();
    }
}
