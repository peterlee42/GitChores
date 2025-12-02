package data_access.cognito;

import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;

public final class IdentityProviderClientSingleton {
    private static final CognitoIdentityProviderClient INSTANCE = IdentityProviderClientFactory.createClient();

    private IdentityProviderClientSingleton() {
    }

    /**
     * Get the singleton instance of CognitoIdentityProviderClient.
     * 
     * @return the singleton instance of CognitoIdentityProviderClient
     */
    public static CognitoIdentityProviderClient getInstance() {
        return INSTANCE;
    }
}
