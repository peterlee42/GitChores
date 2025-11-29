package use_case.login;

import entity.Token;

public interface LoginDataAccessInterface {
    /**
     * Gets access token from cognito.
     *
     * @param username The username of the new user.
     * @param password The password of the new user.
     * @return Token object containing token information.
     */
    Token login(String username, String password);
}
