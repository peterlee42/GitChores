package interface_adapter.logged_in;

public class TokenState {
    private String idToken;
    private String accessToken;
    private String refreshToken;
    private String tokenError;

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void setTokenError(String tokenError) {
        this.tokenError = tokenError;
    }

    public String getIdToken() {
        return idToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getTokenError() {
        return tokenError;
    }
}
