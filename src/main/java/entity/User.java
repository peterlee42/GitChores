package entity;

/**
 * An entity that represents a user in our app.
 */
public class User extends Domain {
    private String firstName;
    private String lastName;
    private final Credentials credentials;

    /**
     * Creates a new user with the given non-empty id and non-empty username.
     * 
     * @param id          the user ID
     * @param firstName   the user's first name
     * @param lastName    the user's last name
     * @param credentials the user's credentials
     * @throws IllegalArgumentException if any of the parameters are null or empty
     */
    public User(String id, String firstName, String lastName, Credentials credentials) {
        super(id);

        if (credentials == null) {
            throw new IllegalArgumentException("Credentials cannot be null");
        }
        if (firstName == null || firstName.isEmpty()) {
            throw new IllegalArgumentException("First name cannot be empty");
        }
        if (lastName == null || lastName.isEmpty()) {
            throw new IllegalArgumentException("Last name cannot be empty");
        }

        this.credentials = credentials;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
