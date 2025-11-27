package entity;

/**
 * An entity that represents a user in our app.
 */
public class User extends AbstractDomain {
    private String username;
    private String email;

    /**
     * Creates a new user with the given non-empty id and non-empty username.
     * 
     * @param id       the user ID
     * @param username the user's username
     * @param email    the user's email
     * @throws IllegalArgumentException if any of the parameters are null or empty
     */
    public User(String id, String username, String email) {
        super(id);

        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }

        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
