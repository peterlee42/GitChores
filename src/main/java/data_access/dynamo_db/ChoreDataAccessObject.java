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

    private static final String CREATING_USER_ID = "creatingUserId";
    private static final String ASSIGNED_USER_ID = "assignedUserId";
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String DUE_DATE = "dueDate";
    private static final String STATUS = "status";
    private static final String NEEDS_REVIEW = "needsReview";
    private static final String CREATED_AT = "createdAt";
    private static final String UPDATED_AT = "updatedAt";

    private final DynamoDbClient client;

    public ChoreDataAccessObject(DynamoDbClient client) {
        this.client = client;
    }

    @Override
    public void saveChore(Chore chore) {
        final Map<String, AttributeValue> item = new HashMap<>();

        // set partition and sort key
        item.put(ROOM_ID, AttributeValue.fromS(chore.getRoomId()));
        item.put(CHORE_ID, AttributeValue.fromS(chore.getId()));
        item.put(CREATING_USER_ID, AttributeValue.fromS(chore.getCreatingUserId()));

        if (chore.getAssignedUserId() != null && !chore.getAssignedUserId().trim().isEmpty()) {
            item.put(ASSIGNED_USER_ID, AttributeValue.fromS(chore.getAssignedUserId()));
        }

        item.put(TITLE, AttributeValue.fromS(chore.getTitle()));

        if (chore.getDescription() != null && !chore.getDescription().trim().isEmpty()) {
            item.put(DESCRIPTION, AttributeValue.fromS(chore.getDescription()));
        }

        item.put(DUE_DATE, AttributeValue.fromS(chore.getDueDate().toString()));
        item.put(STATUS, AttributeValue.fromS(chore.getStatus().name()));
        item.put(NEEDS_REVIEW, AttributeValue.fromBool(chore.getNeedsReview()));

        if (chore.getCreatedAt() != null) {
            item.put(CREATED_AT, AttributeValue.fromS(chore.getCreatedAt().toString()));
        }
        if (chore.getUpdatedAt() != null) {
            item.put(UPDATED_AT, AttributeValue.fromS(chore.getUpdatedAt().toString()));
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
                .expressionAttributeValues(Map.of(":roomId", AttributeValue.fromS(roomId)))
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
        final String creatingUserId = item.get(CREATING_USER_ID).s();

        final String assignedUserId;
        if (item.containsKey(ASSIGNED_USER_ID)) {
            assignedUserId = item.get(ASSIGNED_USER_ID).s();
        } else {
            assignedUserId = null;
        }

        final String title = item.get(TITLE).s();

        final String description;
        if (item.containsKey(DESCRIPTION)) {
            description = item.get(DESCRIPTION).s();
        } else {
            description = null;
        }

        final LocalDateTime dueDate = LocalDateTime.parse(item.get(DUE_DATE).s());
        final ChoreStatus status = ChoreStatus.valueOf(item.get(STATUS).s());
        final boolean needsReview = item.containsKey(NEEDS_REVIEW) && item.get(NEEDS_REVIEW).bool();

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
