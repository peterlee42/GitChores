package use_case.session;

import entity.Token;

public interface SessionDataAccessInterface {

    /**
     * Saves the current token.
     * 
     * @param token current token
     */
    void setCurrentToken(Token token);

    /**
     * Retrieves the current token.
     * 
     * @return current token
     */
    Token getCurrentToken();

    /**
     * Clears the current token.
     */
    void clearCurrentToken();
}
