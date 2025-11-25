package data_access.cognito;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;

/**
 * Used to create CognitoIdentityProviderClient objects.
 */
public class IdentityProviderClientFactory {
    /**
     * Initialize a cognito identity provider client.
     * 
     * @return the initialized identity provider client
     */
    public static CognitoIdentityProviderClient createClient() {
        return CognitoIdentityProviderClient.builder()
                .region(Region.US_EAST_2)
                .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
                .build();
    }
}
