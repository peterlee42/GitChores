package use_case.logged_in;

import entity.Token;
import entity.User;
import use_case.exception.TokenExpiredException;
import use_case.exception.TokenFailedException;
import use_case.session.SessionDataAccessInterface;

public class UserService {
    private final LoggedInDataAccessInterface loggedInDataAccess;
    private final SessionDataAccessInterface sessionDataAccess;

    public UserService(
            LoggedInDataAccessInterface loggedInDataAccess,
            SessionDataAccessInterface sessionDataAccess) {
        this.loggedInDataAccess = loggedInDataAccess;
        this.sessionDataAccess = sessionDataAccess;
    }

    /**
     * Get the current logged-in user, handling token expiration and refresh.
     * 
     * @return the current User or null if not logged in
     */
    public User getUser() {
        final Token token = sessionDataAccess.getCurrentToken();
        if (token == null) {
            return null;
        }

        try {
            final User user = loggedInDataAccess.getCurrentUser(token);
            return user;
        } catch (TokenExpiredException expiredException) {
            try {
                final Token newToken = loggedInDataAccess.refreshToken(token.getRefreshToken());
                sessionDataAccess.setCurrentToken(newToken);
                return loggedInDataAccess.getCurrentUser(newToken);
            } catch (TokenFailedException failedException) {
                sessionDataAccess.clearCurrentToken();
                return null;
            }
        } catch (TokenFailedException ex) {
            sessionDataAccess.clearCurrentToken();
            return null;
        }
    }
}
