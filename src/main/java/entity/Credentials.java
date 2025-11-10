package entity;

/**
 * Class representing a user's credentials.
 */
public class Credentials {
    private final String userId;
    private final String email;
    private final String passwordHash;

    /**
     * Creates new credentials with the given non-empty username and non-empty
     * password hash.
     *
     * @param userId       the user ID
     * @param email        email
     * @param passwordHash the password hash
     * @throws IllegalArgumentException if the password or name are empty
     */
    public Credentials(String userId, String email, String passwordHash) {
        if (userId == null || userId.isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be empty");
        }
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        if (passwordHash == null || passwordHash.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }

        this.userId = userId;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }
}
