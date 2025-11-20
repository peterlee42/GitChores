package data_access.cognito;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AttributeType;
import software.amazon.awssdk.services.cognitoidentityprovider.model.CognitoIdentityProviderException;
import software.amazon.awssdk.services.cognitoidentityprovider.model.SignUpRequest;
import use_case.signup.SignupDataAccessInterface;

public class UserDataAccessObject implements SignupDataAccessInterface {
    @Override
    public boolean usernameExists(String username) {
        // Implementation to check if username is taken in Cognito
        return false;
    }

    @Override
    public void createUser(String username, String email, String password) {
        // Implementation to create a new user in Cognito
    }

    /**
     * Calculate the secret hash for Cognito user pool client.
     * 
     * @param userPoolClientId     The client ID of the Cognito user pool.
     * @param userPoolClientSecret Secret key of the Cognito user pool.
     * @param userName             The username of the user.
     * @return The calculated secret hash.
     * @throws NoSuchAlgorithmException If the HMAC SHA256 algorithm is not
     *                                  available.
     * @throws InvalidKeyException      If the provided key is invalid.
     */
    public static String calculateSecretHash(String userPoolClientId, String userPoolClientSecret, String userName)
            throws NoSuchAlgorithmException, InvalidKeyException {

        final SecretKeySpec signingKey = new SecretKeySpec(
                userPoolClientSecret.getBytes(StandardCharsets.UTF_8),
                HMAC_SHA256_ALGORITHM);

        final Mac mac = Mac.getInstance(HMAC_SHA256_ALGORITHM);
        mac.init(signingKey);
        mac.update(userName.getBytes(StandardCharsets.UTF_8));
        final byte[] rawHmac = mac.doFinal(userPoolClientId.getBytes(StandardCharsets.UTF_8));
        return java.util.Base64.getEncoder().encodeToString(rawHmac);
    }
}
