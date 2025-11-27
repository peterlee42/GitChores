package use_case.signup;

public class SignupFailedException extends RuntimeException {
    public SignupFailedException(String message) {
        super(message);
    }

}
