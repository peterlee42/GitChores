package data_access.cognito;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import entity.User;
import io.github.cdimascio.dotenv.Dotenv;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AttributeType;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AuthFlowType;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AuthenticationResultType;
import software.amazon.awssdk.services.cognitoidentityprovider.model.CognitoIdentityProviderException;
import software.amazon.awssdk.services.cognitoidentityprovider.model.GetUserRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.GetUserResponse;
import software.amazon.awssdk.services.cognitoidentityprovider.model.InitiateAuthRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.InitiateAuthResponse;
import software.amazon.awssdk.services.cognitoidentityprovider.model.SignUpRequest;
import use_case.logged_in.LoggedInDataAccessInterface;
import use_case.login.LoginDataAccessInterface;
import use_case.signup.SignupDataAccessInterface;

public class UserDataAccessObject
        implements SignupDataAccessInterface, LoginDataAccessInterface, LoggedInDataAccessInterface {
    private static final String HMAC_SHA256_ALGORITHM = "HmacSHA256";
    private final Dotenv dotenv = Dotenv.load();
    private final String clientId = dotenv.get("COGNITO_USER_POOL_CLIENT_ID");
    private final String clientSecret = dotenv.get("COGNITO_USER_POOL_CLIENT_SECRET");

    private final CognitoIdentityProviderClient identityProviderClient = IdentityProviderClientFactory
            .createClient();

    @Override
    public User login(String username, String password) {
        final Map<String, String> authParameters = new LinkedHashMap<String, String>();
        authParameters.put("USERNAME", username);
        authParameters.put("PASSWORD", password);

        try {
            // calculate hash
            final String secretVal = calculateSecretHash(
                    clientId,
                    clientSecret,
                    username);
            authParameters.put("SECRET_HASH", secretVal);
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        } catch (InvalidKeyException ex) {
            ex.printStackTrace();
        }

        try {

            final InitiateAuthRequest authRequest = InitiateAuthRequest.builder()
                    .clientId(clientId)
                    .authParameters(authParameters)
                    .authFlow(AuthFlowType.USER_PASSWORD_AUTH)
                    .build();

            final InitiateAuthResponse authResponse = identityProviderClient.initiateAuth(authRequest);
            final AuthenticationResultType resultType = authResponse.authenticationResult();

            // request user information from Cognito
            final GetUserRequest request = GetUserRequest.builder()
                    .accessToken(resultType.accessToken())
                    .build();
            final GetUserResponse response = identityProviderClient.getUser(request);

            // Get userId and email
            String userId = null;
            String email = null;

            for (AttributeType attr : response.userAttributes()) {
                if ("sub".equals(attr.name())) {
                    userId = attr.value();
                } else if ("email".equals(attr.name())) {
                    email = attr.value();
                }
            }

            // return User entity
            final User user = new User(userId, response.username(), email);
            return user;
        } catch (CognitoIdentityProviderException ex) {
            System.err.println(ex.awsErrorDetails().errorMessage());
            throw ex;
        }
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
                    clientId,
                    clientSecret,
                    username);

            // request sign up
            final SignUpRequest signUpRequest = SignUpRequest.builder()
                    .clientId(clientId)
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
