package use_case.signup;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import use_case.exception.SignupFailedException;

/**
 * The interactor for the Signup Use Case.
 */
public class SignupInteractor implements SignupInputBoundary {
    private final SignupOutputBoundary signupPresenter;
    private final SignupDataAccessInterface signupDataAccess;

    public SignupInteractor(SignupOutputBoundary signupPresenter, SignupDataAccessInterface signupDataAccess) {
        this.signupPresenter = signupPresenter;
        this.signupDataAccess = signupDataAccess;
    }

    @SuppressWarnings("checkstyle:CyclomaticComplexityCheck")
    @Override
    public void execute(SignupInputData signupInputData) {
        final String username = signupInputData.getUsername();
        final String email = signupInputData.getEmail();
        final String password = signupInputData.getPassword();
        final String confirmPassword = signupInputData.getConfirmPassword();

        if (username.isEmpty() || password.isEmpty() || email.isEmpty() || confirmPassword.isEmpty()) {
            signupPresenter.prepareFailView("All fields must be non-empty.");
        } else if (!password.equals(confirmPassword)) {
            signupPresenter.prepareFailView("Passwords do not match.");
        } else if (!isValidEmail(email)) {
            signupPresenter.prepareFailView("Invalid email address.");
        } else {

            try {
                signupDataAccess.createUser(username, email, password);
                signupPresenter.prepareSuccessView(new SignupOutputData(username));

            } catch (SignupFailedException ex) {
                signupPresenter.prepareFailView(ex.getMessage());
            }
        }
    }

    @Override
    public void switchToLoginView() {
        signupPresenter.switchToLoginView();
    }

    /**
     * Check if email is valid.
     * 
     * @param email the email address to check
     * @return true if the email is valid, false otherwise
     */
    public static boolean isValidEmail(String email) {
        boolean result = true;
        try {
            final InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }
}
