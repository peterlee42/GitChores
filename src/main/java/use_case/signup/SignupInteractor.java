package use_case.signup;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import software.amazon.awssdk.services.cognitoidentityprovider.model.CognitoIdentityProviderException;

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
        }
        // check password are the same
        else if (!password.equals(confirmPassword)) {
            signupPresenter.prepareFailView("Passwords do not match.");
        }
        // check email valid
        else if (!isValidEmail(email)) {
            signupPresenter.prepareFailView("Invalid email address.");
        } else {
            try {
                signupDataAccess.createUser(username, email, password);
                signupPresenter.prepareSuccessView(new SignupOutputData(username));
            } catch (CognitoIdentityProviderException ex) {
                final String errorMessage;
                switch (ex.awsErrorDetails().errorCode()) {
                    case "UsernameExistsException":
                        errorMessage = "User already exists.";
                        break;
                    case "InvalidPasswordException":
                    case "InvalidParameterException":
                        errorMessage = "Passwords must contain: \n"
                                + "At least 8 characters long, \n"
                                + "At least one uppercase letter \n"
                                + "At least one lowercase letter \n"
                                + "At least one digit \n"
                                + "At least one special character.";
                        break;
                    default:
                        errorMessage = "An error occurred during signup.";
                }
                System.out.println("Error during signup: " + ex.awsErrorDetails().errorMessage());
                signupPresenter.prepareFailView(errorMessage);
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
