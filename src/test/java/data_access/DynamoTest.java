package data_access;

import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;

/**
 * This is a test file you can use to check if the DynamoDb client is working,
 * we'll delete it at the end
 * It should print out the names of all the tables [roomMetadata, commits, etc].
 */

public class DynamoTest {
    public static void test(String[] args) {
        try {
            System.out.println("ENV KEY = " + System.getenv("AWS_ACCESS_KEY_ID"));
            System.out.println("ENV SECRET = " + System.getenv("AWS_SECRET_ACCESS_KEY"));

            final DynamoDbClient client = DynamoDbClient.builder()
                    .region(Region.US_EAST_2)
                    .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
                    .build();

            final var response = client.listTables();
            System.out.println("Connected successfully!");
            System.out.println("Tables: " + response.tableNames());
        } catch (DynamoDbException evt) {
            System.out.println("Error connecting to DynamoDB:");
            evt.printStackTrace();
        }
    }
}
