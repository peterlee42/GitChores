package data_access.cognito;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AttributeType;
import software.amazon.awssdk.services.cognitoidentityprovider.model.CognitoIdentityProviderException;
import software.amazon.awssdk.services.cognitoidentityprovider.model.SignUpRequest;
import use_case.signup.SignupDataAccessInterface;

public class UserDataAccessObject implements SignupDataAccessInterface {
    private static final CognitoIdentityProviderClient IDENTITY_PROVIDER_CLIENT = IdentityProviderClientFactory
            .createClient();
    private static final String HMAC_SHA256_ALGORITHM = "HmacSHA256";

    @Override
    public boolean usernameExists(String username) {
        // Implementation to check if username is taken in Cognito
        return false;
    }

    @Override
    public void createUser(String username, String email, String password) {
        final AttributeType attributeType = AttributeType.builder()
                .name("email")
                .value(email)
                .build();

        userAttributes.add(attributeType);
        try {

            SignUpRequest signUpRequest = SignUpRequest.builder()
                    .clientId(System.getenv("COGNITO_USER_POOL_CLIENT_ID"))
                    .username(username)
                    .password(password)
                    .userAttributes(userAttributes)
                    .secretHash(calculateSecretHash(
                            System.getenv("COGNITO_USER_POOL_CLIENT_ID"),
                            System.getenv("COGNITO_USER_POOL_CLIENT_SECRET"),
                            username))
                    .build();

            IdentityProviderClientFactory.createClient().signUp(signUpRequest);
        } catch (CognitoIdentityProviderException err) {
            System.err.println(err.awsErrorDetails().errorMessage());
            throw err;
        } catch (NoSuchAlgorithmException | InvalidKeyException err) {
            System.err.println("Error calculating secret hash: " + err.getMessage());
            throw new RuntimeException(err);
        }
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
