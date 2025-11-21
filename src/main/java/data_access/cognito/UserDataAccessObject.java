package data_access.cognito;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import io.github.cdimascio.dotenv.Dotenv;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AttributeType;
import software.amazon.awssdk.services.cognitoidentityprovider.model.CognitoIdentityProviderException;
import software.amazon.awssdk.services.cognitoidentityprovider.model.ListUsersRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.ListUsersResponse;
import software.amazon.awssdk.services.cognitoidentityprovider.model.SignUpRequest;
import use_case.signup.SignupDataAccessInterface;

public class UserDataAccessObject implements SignupDataAccessInterface {
    private static final String HMAC_SHA256_ALGORITHM = "HmacSHA256";

    private final CognitoIdentityProviderClient identityProviderClient = IdentityProviderClientFactory
            .createClient();

    private final Dotenv dotenv = Dotenv.load();

    @SuppressWarnings("checkstyle:ReturnCount")
    @Override
    public boolean usernameExists(String username) {
        // Implementation to check if username is taken in Cognito
        // try {
        // final String usernameFilter = "username = \"" + username + "\"";
        // final ListUsersRequest request = ListUsersRequest.builder()
        // .userPoolId(dotenv.get("COGNITO_USER_POOL_CLIENT_ID"))
        // .filter(usernameFilter)
        // .build();
        // final ListUsersResponse response = identityProviderClient.listUsers(request);
        // return !response.users().isEmpty();

        // } catch (CognitoIdentityProviderException ex) {
        // return false;
        // }
        return false;
    }

    @Override
    public void createUser(String username, String email, String password) {
        System.out.println("Creating user in Cognito: " + username);
        final AttributeType attributeType = AttributeType.builder()
                .name("email")
                .value(email)
                .build();
        final List<AttributeType> attributes = new ArrayList<>();
        attributes.add(attributeType);
        try {
            // calculate hash
            final String secretVal = calculateSecretHash(
                    dotenv.get("COGNITO_USER_POOL_CLIENT_ID"),
                    dotenv.get("COGNITO_USER_POOL_CLIENT_SECRET"),
                    username);

            // request sign up
            final SignUpRequest signUpRequest = SignUpRequest.builder()
                    .clientId(dotenv.get("COGNITO_USER_POOL_CLIENT_ID"))
                    .userAttributes(attributes).username(username)
                    .password(password)
                    .secretHash(secretVal)
                    .build();

            identityProviderClient.signUp(signUpRequest);

        } catch (CognitoIdentityProviderException ex) {
            System.err.println(ex.awsErrorDetails().errorMessage());
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        } catch (InvalidKeyException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Calculate the secret hash for Cognito user pool client.
     * 
     * @param userPoolClientId     The client ID of the Cognito user pool.
     * @param userPoolClientSecret Secret key of the Cognito user pool.
     * @param username             The username of the user.
     * @return The calculated secret hash.
     * @throws NoSuchAlgorithmException If the HMAC SHA256 algorithm is not
     *                                  available.
     * @throws InvalidKeyException      If the provided key is invalid.
     */
    public static String calculateSecretHash(String userPoolClientId, String userPoolClientSecret, String username)
            throws NoSuchAlgorithmException, InvalidKeyException {

        final SecretKeySpec signingKey = new SecretKeySpec(
                userPoolClientSecret.getBytes(StandardCharsets.UTF_8),
                HMAC_SHA256_ALGORITHM);

        final Mac mac = Mac.getInstance(HMAC_SHA256_ALGORITHM);
        mac.init(signingKey);
        mac.update(username.getBytes(StandardCharsets.UTF_8));
        final byte[] rawHmac = mac.doFinal(userPoolClientId.getBytes(StandardCharsets.UTF_8));
        return java.util.Base64.getEncoder().encodeToString(rawHmac);
    }
}
