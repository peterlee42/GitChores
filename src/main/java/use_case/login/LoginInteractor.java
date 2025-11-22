package use_case.login;

/**
 * The interactor for the Signup Use Case.
 */
public class LoginInteractor implements LoginInputBoundary {
    private final LoginOutputBoundary loginPresenter;

    public LoginInteractor(LoginOutputBoundary loginPresenter) {
        this.loginPresenter = loginPresenter;
    }

    @Override
    public void execute(LoginInputData loginInputData) {
        // TODO: Implement login logic here
    }

    @Override
    public void switchToSignupView() {
        loginPresenter.switchToSignupView();
    }
}
