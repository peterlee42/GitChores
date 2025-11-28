package use_case.login;

/**
 * Output Data for the Login Use Case.
 */
public class LoginOutputData {

    private final String username;
    private final String userId;
    private final String email;

    public LoginOutputData(String username, String userId, String email) {
        this.username = username;
        this.userId = userId;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getUserId() {
        return userId;
    }
}
