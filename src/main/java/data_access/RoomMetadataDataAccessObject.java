package data_access;

import java.util.Map;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.ReturnValue;
import software.amazon.awssdk.services.dynamodb.model.UpdateItemRequest;
import software.amazon.awssdk.services.dynamodb.model.UpdateItemResponse;
import use_case.commit.RoomMetadataDataAccessInterface;

public class RoomMetadataDataAccessObject implements RoomMetadataDataAccessInterface {

    private final DynamoDbClient client;
    private final String tableName = "RoomMetadata";

    public RoomMetadataDataAccessObject(DynamoDbClient client) {
        this.client = client;
    }

    @Override
    public int incrementAndGetLatestCommitId(String roomId) {
        final UpdateItemRequest update = UpdateItemRequest.builder()
                .tableName(tableName)
                .key(Map.of("roomId", AttributeValue.fromS(roomId)))
                .updateExpression("SET latestCommitId = if_not_exists(latestCommitId, :zero) + :inc")
                .expressionAttributeValues(Map.of(
                        ":zero", AttributeValue.fromN("0"),
                        ":inc", AttributeValue.fromN("1")
                ))
                .returnValues(ReturnValue.UPDATED_NEW)
                .build();

        final UpdateItemResponse response = client.updateItem(update);

        return Integer.parseInt(response.attributes().get("latestCommitId").n());
    }
}
