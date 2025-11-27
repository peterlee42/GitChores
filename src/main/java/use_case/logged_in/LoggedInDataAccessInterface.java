package use_case.logged_in;

import entity.Token;
import entity.User;

public interface LoggedInDataAccessInterface {
    /**
     * Gets user information from token.
     * 
     * @param token The token of the logged in user.
     * @return The user information.
     */
    User getUser(Token token);
}
