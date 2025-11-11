package entity;

/**
 * Class representing a user's credentials.
 */
public class Credentials {
    private final String username;
    private final String email;
    private final String passwordHash;

    /**
     * Creates new credentials with the given non-empty username and non-empty
     * password hash.
     *
     * @param username     the username
     * @param email        email
     * @param passwordHash the password hash
     * @throws IllegalArgumentException if the password or name are empty
     */
    public Credentials(String username, String email, String passwordHash) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        if (passwordHash == null || passwordHash.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }

        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }
}
