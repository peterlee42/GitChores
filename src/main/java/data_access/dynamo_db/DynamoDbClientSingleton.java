package data_access.dynamo_db;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

public final class DynamoDbClientSingleton {

    private static final DynamoDbClient INSTANCE = DynamoDbClientFactory.createClient();

    private DynamoDbClientSingleton() {
    }

    /**
     * Get the singleton instance of DynamoDbClient.
     * 
     * @return the singleton instance of DynamoDbClient
     */
    public static DynamoDbClient getInstance() {
        return INSTANCE;
    }
}
