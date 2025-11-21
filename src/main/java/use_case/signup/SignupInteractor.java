package use_case.signup;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 * The interactor for the Signup Use Case.
 */
public class SignupInteractor implements SignupInputBoundary {
    private final int minimumPasswordLength = 8;
    private final SignupOutputBoundary signupPresenter;
    private final SignupDataAccessInterface signupDataAccess;

    public SignupInteractor(SignupOutputBoundary signupPresenter, SignupDataAccessInterface signupDataAccess) {
        this.signupPresenter = signupPresenter;
        this.signupDataAccess = signupDataAccess;
    }

    @Override
    @SuppressWarnings("checkstyle:CyclomaticComplexityCheck")
    public void execute(SignupInputData signupInputData) {
        final String username = signupInputData.getUsername();
        final String email = signupInputData.getEmail();
        final String password = signupInputData.getPassword();
        final String confirmPassword = signupInputData.getConfirmPassword();

        if ("".equals(username) || "".equals(password) || "".equals(email) || "".equals(confirmPassword)) {
            signupPresenter.prepareFailView("All fields must be non-empty.");
        } else if (password.length() < minimumPasswordLength || !password.matches(".*[A-Z].*")
                || !password.matches(".*[a-z].*") || !password.matches(".*[0-9].*")
                || !password.matches(".*[!@#$%^&*()_+\\-=[\\]{};':\"\\\\|,.<>/?].*")) {
            signupPresenter.prepareFailView(
                    "Passwords must be \n"
                            + "At least 8 characters long, \n"
                            + "Contain at least one uppercase letter \n"
                            + "Contain at least one lowercase letter \n"
                            + "Contain at least one digit \n"
                            + "Contain at least one special character.");
        }
        // check password are the same
        else if (!password.equals(confirmPassword)) {
            signupPresenter.prepareFailView("Passwords do not match.");
        }
        // check email valid
        else if (!isValidEmail(email)) {
            signupPresenter.prepareFailView("Invalid email address.");
        }
        // check if username taken
        else if (signupDataAccess.usernameExists(username)) {
            signupPresenter.prepareFailView("Username is already taken.");
        } else {
            // create user in cognito
            signupDataAccess.createUser(username, email, password);
            signupPresenter.prepareSuccessView(new SignupOutputData(username));
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
