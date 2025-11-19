package use_case.signup;

import data_access.congito.CognitoUserDataAccess;

/**
 * The interactor for the Signup Use Case.
 */
public class SignupInteractor implements SignupInputBoundary {
    private final SignupOutputBoundary signupPresenter;

    public SignupInteractor(SignupOutputBoundary signupPresenter) {
        this.signupPresenter = signupPresenter;
    }

    @Override
    public void execute(SignupInputData signupInputData) {
        // check password are the same
        // check if username taken
        // check email valid
        // create user in cognito
        // signupPresenter.prepareSuccessView();
    }

    @Override
    public void switchToLoginView() {
        signupPresenter.switchToLoginView();
    }
}
