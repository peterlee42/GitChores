package interface_adapter.login;

/**
 * The state for the Login View Model.
 */
public class LoginState {
    private String currentUsername;
    private String password;
    private String loginError;
    private boolean isLoggedIn;

    public String getCurrentUsername() {
        return currentUsername;
    }

    public String getPassword() {
        return password;
    }

    public String getLoginError() {
        return loginError;
    }

    public Boolean getIsLoggedIn() {
        return isLoggedIn;
    }

    public void setCurrentUsername(String username) {
        this.currentUsername = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLoginError(String loginError) {
        this.loginError = loginError;
    }

    public void setIsLoggedIn(Boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }
}
