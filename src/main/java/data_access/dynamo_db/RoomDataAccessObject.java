package data_access.dynamo_db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.Room;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;
import use_case.room.RoomDataAccessInterface;

/**
 * The DAO for the Room DAI.
 */
public class RoomDataAccessObject implements RoomDataAccessInterface {
    private static final String ROOMS_TABLE = "Rooms";
    private static final String ROOM_MEMBERS_TABLE = "RoomMembers";
    private static final String INVITE_CODE_INDEX = "InviteCodeIndex";
    private static final String ROOM_ID = "roomId";
    private static final String USER_ID = "userId";
    private static final String DESCRIPTION = "description";

    private final DynamoDbClient client;

    public RoomDataAccessObject(DynamoDbClient client) {
        this.client = client;
    }

    @Override
    public void saveRoom(Room room) {
        final Map<String, AttributeValue> item = new HashMap<>();
        item.put(ROOM_ID, AttributeValue.fromS(room.getId()));
        item.put("name", AttributeValue.fromS(room.getName()));
        item.put("ownerId", AttributeValue.fromS(room.getOwnerId()));
        item.put("inviteCode", AttributeValue.fromS(room.getInviteCode()));

        if (room.getDescription() != null) {
            item.put(DESCRIPTION, AttributeValue.fromS(room.getDescription()));
        }

        final PutItemRequest putItemRequest = PutItemRequest.builder()
                .tableName(ROOMS_TABLE)
                .item(item)
                .build();

        client.putItem(putItemRequest);
    }

    @Override
    public Room getRoomById(String roomId) {
        final Map<String, AttributeValue> key = new HashMap<>();
        key.put(ROOM_ID, AttributeValue.fromS(roomId));

        final GetItemRequest getItemRequest = GetItemRequest.builder()
                .tableName(ROOMS_TABLE)
                .key(key)
                .build();

        final GetItemResponse response = client.getItem(getItemRequest);

        if (response.hasItem() && !response.item().isEmpty()) {
            return itemToRoom(response.item());
        }
        return null;
    }

    @Override
    public Room getRoomByInviteCode(String inviteCode) {
        final QueryRequest queryRequest = QueryRequest.builder()
                .tableName(ROOMS_TABLE)
                .indexName(INVITE_CODE_INDEX)
                .keyConditionExpression("inviteCode = :code")
                .expressionAttributeValues(Map.of(":code", AttributeValue.fromS(inviteCode)))
                .build();

        final QueryResponse response = client.query(queryRequest);

        if (!response.items().isEmpty()) {
            return itemToRoom(response.items().get(0));
        }
        return null;
    }

    @Override
    public void addUserToRoom(String roomId, String userId) {
        final Map<String, AttributeValue> item = new HashMap<>();
        item.put(ROOM_ID, AttributeValue.fromS(roomId));
        item.put(USER_ID, AttributeValue.fromS(userId));

        final PutItemRequest putItemRequest = PutItemRequest.builder()
                .tableName(ROOM_MEMBERS_TABLE)
                .item(item)
                .build();

        client.putItem(putItemRequest);
    }

    @Override
    public List<String> getRoomMembers(String roomId) {
        final QueryRequest queryRequest = QueryRequest.builder()
                .tableName(ROOM_MEMBERS_TABLE)
                .keyConditionExpression("roomId = :roomId")
                .expressionAttributeValues(Map.of(":roomId", AttributeValue.fromS(roomId)))
                .build();

        final QueryResponse response = client.query(queryRequest);

        final List<String> members = new ArrayList<>();
        for (Map<String, AttributeValue> item : response.items()) {
            members.add(item.get(USER_ID).s());
        }

        return members;
    }

    @Override
    public boolean isUserInRoom(String roomId, String userId) {
        final Map<String, AttributeValue> key = new HashMap<>();
        key.put(ROOM_ID, AttributeValue.fromS(roomId));
        key.put(USER_ID, AttributeValue.fromS(userId));

        final GetItemRequest getItemRequest = GetItemRequest.builder()
                .tableName(ROOM_MEMBERS_TABLE)
                .key(key)
                .build();

        final GetItemResponse response = client.getItem(getItemRequest);

        return response.hasItem() && !response.item().isEmpty();
    }

    private Room itemToRoom(Map<String, AttributeValue> item) {
        final String roomId = item.get(ROOM_ID).s();
        final String name = item.get("name").s();
        final String ownerId = item.get("ownerId").s();
        final String inviteCode = item.get("inviteCode").s();
        String desc = "";
        if (item.containsKey(DESCRIPTION)) {
            desc = item.get(DESCRIPTION).s();
        }
        final String description = desc;

        return new Room(roomId, name, description, ownerId, inviteCode);
    }
}
