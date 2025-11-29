package data_access;

import entity.Token;
import use_case.session.SessionDataAccessInterface;

public class SessionDataAccessObject implements SessionDataAccessInterface {
    private Token currentToken;

    @Override
    public Token getCurrentToken() {
        return currentToken;
    }

    @Override
    public void setCurrentToken(Token token) {
        this.currentToken = token;
    }

    @Override
    public void clearCurrentToken() {
        this.currentToken = null;
    }
}
