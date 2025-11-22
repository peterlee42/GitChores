package interface_adapter.login;

/**
 * The state for the Login View Model.
 */
public class LoginState {
    private String username;
    private String password;
    private String loginError;
    private boolean isLoggedIn;

    public String getUsername() {
        return username;
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

    public void setUsername(String username) {
        this.username = username;
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
