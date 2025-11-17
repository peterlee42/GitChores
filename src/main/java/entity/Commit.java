package entity;

import java.time.LocalDateTime;
import java.util.Map;

import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

/**
 * An entity that represents a commit within the room. This signifies the
 * completion of a task and is accompanied by
 * a message.
 */
public class Commit {

    private final String roomId;
    private final int commitId;
    private final String message;
    private final String userId;
    private final LocalDateTime timestamp;

    /**
     * Used to create a new commit with the given details and a timestamp at the current moment.
     *
     * @param roomId   the room ID of the user who created the commit
     * @param commitId the ID of the commit
     * @param message  the commit message
     * @param userId   the ID of the user creating the commit
     * @throws IllegalArgumentException if any of the parameters are null or empty
     */
    public Commit(String roomId, int commitId, String message, String userId) {
        if (roomId == null || roomId.trim().isEmpty()) {
            throw new IllegalArgumentException("Room ID cannot be null or empty");
        }
        if (message == null || message.trim().isEmpty()) {
            throw new IllegalArgumentException("Message cannot be null or empty");
        }
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }
        this.commitId = commitId;
        this.roomId = roomId;
        this.message = message;
        this.userId = userId;
        this.timestamp = LocalDateTime.now();
    }

    /**
     * Constructor call variant, used to load a commit from DynamoDB.
     *
     * @param roomId   the room ID of the user who created the commit
     * @param commitId the ID of the commit
     * @param message  the commit message
     * @param userId   the ID of the user creating the commit
     * @param timestamp the timestamp the commit was created
     */
    public Commit(String roomId, int commitId, String message, String userId, LocalDateTime timestamp) {
        this.commitId = commitId;
        this.roomId = roomId;
        this.message = message;
        this.userId = userId;
        this.timestamp = timestamp;
    }

    /**
     * Converts a DynamoDB item map into an instance of a Commit entity.
     * @param item the DynamoDB attribute map representing a stored commit
     * @return the corresponding commit object
     */
    public static Commit fromDynamo(Map<String, AttributeValue> item) {
        return new Commit(
                item.get("roomId").s(),
                Integer.parseInt(item.get("commitId").n()),
                item.get("message").s(),
                item.get("userId").s(),
                LocalDateTime.parse(item.get("timestamp").s())
        );
    }

    public int getCommitId() {
        return commitId;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getMessage() {
        return message;
    }

    public String getUserId() {
        return userId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
