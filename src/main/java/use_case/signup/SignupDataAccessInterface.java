package use_case.signup;

public interface SignupDataAccessInterface {

    /**
     * Saves a new user's credentials to the data source.
     *
     * @param username The username of the new user.
     * @param email    The email of the new user.
     * @param password The password of the new user.
     */
    void createUser(String username, String email, String password);

}
