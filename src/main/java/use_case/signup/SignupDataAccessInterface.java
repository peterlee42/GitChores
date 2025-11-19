package use_case.signup;

public interface SignupDataAccessInterface {
    /**
     * Checks if a username already exists in the data source.
     *
     * @param username The username to check.
     * @return true if the username exists, false otherwise.
     */
    boolean usernameExists(String username);

    /**
     * Saves a new user's credentials to the data source.
     *
     * @param username The username of the new user.
     * @param email    The email of the new user.
     * @param password The password of the new user.
     */
    void saveUser(String username, String email, String password);
}
