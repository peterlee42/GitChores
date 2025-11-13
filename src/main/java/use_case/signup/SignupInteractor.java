package use_case.signup;

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
        // TODO: Implement signup logic here
    }

    @Override
    public void switchToLoginView() {
        signupPresenter.switchToLoginView();
    }
}
