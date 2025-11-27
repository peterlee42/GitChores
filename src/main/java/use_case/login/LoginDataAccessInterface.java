package use_case.login;

import entity.User;

public interface LoginDataAccessInterface {
    /**
     * Gets access token from cognito.
     *
     * @param username The username of the new user.
     * @param password The password of the new user.
     * @return User object containing user information.
     */
    User login(String username, String password);
}
