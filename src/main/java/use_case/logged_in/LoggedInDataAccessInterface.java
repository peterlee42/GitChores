package use_case.logged_in;

import entity.Token;
import entity.User;
import use_case.exception.TokenExpiredException;

public interface LoggedInDataAccessInterface {
    /**
     * Retrieves the currently logged-in user's information.
     *
     * @param token The authentication token of the current user.
     * @return The User object representing the current user.
     * @throws TokenExpiredException if the provided token has expired.
     */
    User getCurrentUser(Token token);

    /**
     * Updates the currently logged-in user's information.
     *
     * @param user  The User object containing updated user information.
     * @param token The authentication token of the current user.
     * @throws TokenExpiredException if the provided token has expired.
     */
    void updateCurrentUser(User user, Token token);

    /**
     * Refreshes the authentication token.
     * 
     * @param refreshToken The current refresh token
     * @return new Token
     */
    Token refreshToken(String refreshToken);
}
