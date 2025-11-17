package data_access;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.Commit;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.QueryRequest;
import software.amazon.awssdk.services.dynamodb.model.QueryResponse;
import use_case.commit.CommitDataAccessInterface;

public class CommitDataAccessObject implements CommitDataAccessInterface {

    private final DynamoDbClient client;
    private final String tableName = "Commits";

    public CommitDataAccessObject(DynamoDbClient client) {
        this.client = client;
    }

    @Override
    public void saveCommit(Commit commit) {
        final Map<String, AttributeValue> newItem = new HashMap<>();
        newItem.put("roomId", AttributeValue.fromS(commit.getRoomId()));
        newItem.put("commitId", AttributeValue.fromN(String.valueOf(commit.getCommitId())));
        newItem.put("message", AttributeValue.fromS(commit.getMessage()));
        newItem.put("userId", AttributeValue.fromS(commit.getUserId()));
        newItem.put("timestamp", AttributeValue.fromS(commit.getTimestamp().toString()));

        final PutItemRequest putItemRequest = PutItemRequest.builder()
                .tableName(tableName)
                .item(newItem)
                .build();

        client.putItem(putItemRequest);
    }

    @Override
    public List<Commit> getCommitsForRoom(String roomId) {

        final QueryRequest queryRequest = QueryRequest.builder()
                .tableName(tableName)
                .keyConditionExpression("roomId = :roomId")
                .expressionAttributeValues(Map.of(":r", AttributeValue.fromS(roomId)))
                .scanIndexForward(true)
                .build();

        final QueryResponse response = client.query(queryRequest);

        // Convert response query into commits
        final List<Commit> commits = new ArrayList<>();
        for (Map<String, AttributeValue> item : response.items()) {
            commits.add(Commit.fromDynamo(item));
        }

        return commits;
    }

}
