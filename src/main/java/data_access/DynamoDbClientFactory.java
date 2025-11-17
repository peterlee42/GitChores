package data_access;

import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

/**
 * Used to create DynamoDbClient objects.
 */
public class DynamoDbClientFactory {
    /**
     * Initialize a client.
     * @return the initialized DynamoDb Client.
     */
    public static DynamoDbClient createClient() {
        return DynamoDbClient.builder()
                .region(Region.US_EAST_2)
                .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
                .build();
    }
}
