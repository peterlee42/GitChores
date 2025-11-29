package data_access.dynamo_db;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.Chore;
import entity.ChoreStatus;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DeleteItemRequest;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.GetItemResponse;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.QueryRequest;
import software.amazon.awssdk.services.dynamodb.model.QueryResponse;
import use_case.chore.ChoreDataAccessInterface;

public class ChoreDataAccessObject implements ChoreDataAccessInterface {

    private static final String TABLE_NAME = "Chores";
    private static final String ROOM_ID = "roomId";
    private static final String CHORE_ID = "choreId";

    private final DynamoDbClient client;

    public ChoreDataAccessObject(DynamoDbClient client) {
        this.client = client;
    }

    @SuppressWarnings("checkstyle:MultipleStringLiterals")
    @Override
    public void saveChore(Chore chore) {
        final Map<String, AttributeValue> item = new HashMap<>();

        item.put(ROOM_ID, AttributeValue.fromS(chore.getRoomId()));
        item.put(CHORE_ID, AttributeValue.fromS(chore.getId()));

        item.put("creatingUserId", AttributeValue.fromS(chore.getCreatingUserId()));

        if (chore.getAssignedUserId() != null && !chore.getAssignedUserId().trim().isEmpty()) {
            item.put("assignedUserId", AttributeValue.fromS(chore.getAssignedUserId()));
        }

        item.put("title", AttributeValue.fromS(chore.getTitle()));

        if (chore.getDescription() != null && !chore.getDescription().trim().isEmpty()) {
            item.put("description", AttributeValue.fromS(chore.getDescription()));
        }

        item.put("dueDate", AttributeValue.fromS(chore.getDueDate().toString()));
        item.put("status", AttributeValue.fromS(chore.getStatus().name()));
        item.put("needsReview", AttributeValue.fromBool(chore.getNeedsReview()));

        if (chore.getCreatedAt() != null) {
            item.put("createdAt", AttributeValue.fromS(chore.getCreatedAt().toString()));
        }
        if (chore.getUpdatedAt() != null) {
            item.put("updatedAt", AttributeValue.fromS(chore.getUpdatedAt().toString()));
        }

        final PutItemRequest request = PutItemRequest.builder()
                .tableName(TABLE_NAME)
                .item(item)
                .build();

        client.putItem(request);
    }

    @Override
    public Chore getChoreById(String roomId, String choreId) {
        final Map<String, AttributeValue> key = new HashMap<>();
        key.put(ROOM_ID, AttributeValue.fromS(roomId));
        key.put(CHORE_ID, AttributeValue.fromS(choreId));

        final GetItemRequest request = GetItemRequest.builder()
                .tableName(TABLE_NAME)
                .key(key)
                .build();

        final GetItemResponse response = client.getItem(request);

        if (!response.hasItem() || response.item().isEmpty()) {
            return null;
        }

        return itemToChore(response.item());
    }

    @Override
    public List<Chore> getChoresForRoom(String roomId) {
        final QueryRequest request = QueryRequest.builder()
                .tableName(TABLE_NAME)
                .keyConditionExpression(ROOM_ID + " = :roomId")
                .expressionAttributeValues(Map.of(
                        ":roomId", AttributeValue.fromS(roomId)))
                .build();

        final QueryResponse response = client.query(request);

        final List<Chore> chores = new ArrayList<>();
        for (Map<String, AttributeValue> item : response.items()) {
            chores.add(itemToChore(item));
        }
        return chores;
    }

    @Override
    public List<Chore> getChoresAssignedToUser(String roomId, String userId) {
        final List<Chore> all = getChoresForRoom(roomId);
        final List<Chore> filtered = new ArrayList<>();

        for (Chore chore : all) {
            if (userId.equals(chore.getAssignedUserId())) {
                filtered.add(chore);
            }
        }
        return filtered;
    }

    @Override
    public void deleteChore(String roomId, String choreId) {
        final Map<String, AttributeValue> key = new HashMap<>();
        key.put(ROOM_ID, AttributeValue.fromS(roomId));
        key.put(CHORE_ID, AttributeValue.fromS(choreId));

        final DeleteItemRequest request = DeleteItemRequest.builder()
                .tableName(TABLE_NAME)
                .key(key)
                .build();

        client.deleteItem(request);
    }

    private Chore itemToChore(Map<String, AttributeValue> item) {
        final String roomId = item.get(ROOM_ID).s();
        final String choreId = item.get(CHORE_ID).s();
        final String creatingUserId = item.get("creatingUserId").s();
        final String assignedUserId;
        if (item.containsKey("assignedUserId")) {
            assignedUserId = item.get("assignedUserId").s();
        } else {
            assignedUserId = null;
        }
        final String title = item.get("title").s();
        final String description;
        if (item.containsKey("description")) {
            description = item.get("description").s();
        } else {
            description = null;
        }

        final LocalDateTime dueDate = LocalDateTime.parse(item.get("dueDate").s());
        final ChoreStatus status = ChoreStatus.valueOf(item.get("status").s());
        final boolean needsReview = item.containsKey("needsReview") && item.get("needsReview").bool();

        return new Chore(
                choreId,
                roomId,
                assignedUserId,
                creatingUserId,
                title,
                description,
                dueDate,
                status,
                needsReview);
    }
}
