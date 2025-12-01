package use_case.login;

import entity.Token;
import entity.User;
import use_case.exception.TokenExpiredException;

public interface LoginDataAccessInterface {
    /**
     * Gets access token from cognito.
     *
     * @param username The username of the new user.
     * @param password The password of the new user.
     * @return Token object containing token information.
     */
    Token login(String username, String password);

    /**
     * Retrieves the currently logged-in user's information.
     *
     * @param token The authentication token of the current user.
     * @return The User object representing the current user.
     * @throws TokenExpiredException if the provided token has expired.
     */
    User getCurrentUser(Token token);

}
