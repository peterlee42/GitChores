package interface_adapter.logged_in;

public class LoggedInState {
    private String username;
    private String id;
    private String email;
    private String loggedInError;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLoggedInError(String loggedInError) {
        this.loggedInError = loggedInError;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }

    public String getLoggedInError() {
        return loggedInError;
    }
}
