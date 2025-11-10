package entity;

/**
 * An entity that represents a user in our app.
 */
public class User {
    private final String name;
    private final String passwordHash;

    /**
     * Creates a new user with the given non-empty id and non-empty username.
     * 
     * @param name         the username
     * @param passwordHash the password hash
     * @throws IllegalArgumentException if the password or name are empty
     */
    public User(String name, String passwordHash) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (passwordHash == null || passwordHash.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        this.name = name;
        this.passwordHash = passwordHash;
    }

    public String getName() {
        return name;
    }

    public String getPasswordHash() {
        return passwordHash;
    }
}
