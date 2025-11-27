package entity;

public class Token {
    private final String idToken;
    private final String accessToken;
    private final String refreshToken;

    public Token(String idToken, String accessToken, String refreshToken) {
        this.idToken = idToken;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
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
}
