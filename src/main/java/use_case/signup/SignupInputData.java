package use_case.signup;

/**
 * The input data for signing up.
 */
public class SignupInputData {
    private final String username;
    private final String email;
    private final String password;
    private final String confirmPassword;

    public SignupInputData(String username, String email, String password, String confirmPassword) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    String getUsername() {
        return username;
    }

    String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }
}
