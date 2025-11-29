package entity;

public class Token {
    private String tokenId;
    private String accessToken;
    private String refreshToken;

    public Token(String tokenId, String accessToken, String refreshToken) {
        this.tokenId = tokenId;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public String getTokenId() {
        return tokenId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
