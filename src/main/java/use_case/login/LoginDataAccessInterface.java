package use_case.login;

import software.amazon.awssdk.services.cognitoidentityprovider.model.AuthenticationResultType;

public interface LoginDataAccessInterface {
    /**
     * Gets access token from cognito.
     *
     * @param username The username of the new user.
     * @param password The password of the new user.
     * @return AuthenticationResultType containing tokens
     */
    AuthenticationResultType login(String username, String password);
}
