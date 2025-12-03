package entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class TokenTest {

    @Test
    void testConstructorAndGetters() {
        final Token token = new Token("123", "access", "refresh");

        assertEquals("123", token.getTokenId());
        assertEquals("access", token.getAccessToken());
        assertEquals("refresh", token.getRefreshToken());
    }
}
