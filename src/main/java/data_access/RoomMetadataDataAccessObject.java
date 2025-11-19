package data_access;

import java.util.List;
import java.util.Map;

import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.ReturnValue;
import software.amazon.awssdk.services.dynamodb.model.UpdateItemRequest;
import software.amazon.awssdk.services.dynamodb.model.UpdateItemResponse;
import use_case.commit.RoomMetadataDataAccessInterface;

/**
 * The DAO for the Room Metadata table.
 */
public class RoomMetadataDataAccessObject implements RoomMetadataDataAccessInterface {

    private static final String TABLE_NAME = "RoomMetadata";
    private static final String ROOM_ID = "roomId";
    private final DynamoDbClient client;

    public RoomMetadataDataAccessObject(DynamoDbClient client) {
        this.client = client;
    }

    @Override
    public int incrementAndGetLatestCommitId(String roomId) {
        final UpdateItemRequest update = UpdateItemRequest.builder()
                .tableName(TABLE_NAME)
                .key(Map.of(ROOM_ID, AttributeValue.fromS(roomId)))
                .updateExpression("SET latestCommitId = if_not_exists(latestCommitId, :zero) + :inc")
                .expressionAttributeValues(Map.of(
                        ":zero", AttributeValue.fromN("0"),
                        ":inc", AttributeValue.fromN("1")))
                .returnValues(ReturnValue.UPDATED_NEW)
                .build();

        final UpdateItemResponse response = client.updateItem(update);

        return Integer.parseInt(response.attributes().get("latestCommitId").n());
    }

    @SuppressWarnings("checkstyle:ReturnCount")
    @Override
    public List<String> getPendingReviews(String roomId) {
        final Map<String, AttributeValue> key = Map.of(ROOM_ID, AttributeValue.fromS(roomId));
        final Map<String, AttributeValue> item = client.getItem(request -> request.tableName(TABLE_NAME).key(key))
                .item();

        if (item == null || !item.containsKey("pendingReviews")) {
            return List.of();
        }

        return item.get("pendingReviews").ss();
    }

    @SuppressWarnings("checkstyle:ReturnCount")
    @Override
    public boolean addPendingReview(String roomId, String choreName) {
        try {
            final List<String> pendingReviews = getPendingReviews(roomId);
            if (pendingReviews.contains(choreName)) {
                return false;
            }

            final UpdateItemRequest update = UpdateItemRequest.builder()
                    .tableName(TABLE_NAME)
                    .key(Map.of(ROOM_ID, AttributeValue.fromS(roomId)))
                    .updateExpression("ADD pendingReviews :choreSet")
                    .expressionAttributeValues(Map.of(":choreSet", AttributeValue.fromSs(List.of(choreName))))
                    .build();

            client.updateItem(update);
            return true;
        } catch (DynamoDbException | SdkClientException ex) {
            System.err.println("AWS error during addPendingReview: " + ex.getMessage());
            return false;
        }
    }

    @SuppressWarnings("checkstyle:ReturnCount")
    @Override
    public boolean removePendingReview(String roomId, String choreName) {
        try {
            final List<String> currentReviews = getPendingReviews(roomId);

            // Nothing to remove
            if (!currentReviews.contains(choreName)) {
                return false;
            }
            final UpdateItemRequest update = UpdateItemRequest.builder()
                    .tableName(TABLE_NAME)
                    .key(Map.of(ROOM_ID, AttributeValue.fromS(roomId)))
                    .updateExpression("DELETE pendingReviews :choreSet")
                    .expressionAttributeValues(Map.of(":choreSet", AttributeValue.fromSs(List.of(choreName))))
                    .build();

            client.updateItem(update);
            return true;
        } catch (DynamoDbException | SdkClientException ex) {
            System.err.println("AWS error during removePendingReview: " + ex.getMessage());
            return false;
        }
    }
}
