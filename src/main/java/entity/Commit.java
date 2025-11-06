package entity;

import java.time.LocalDateTime;

/**
 * An entity that represents a commit within the room. This signifies the completion of a task and is accompanied by
 * a message.
 */
public class Commit {

    private String commitId;
    private String message;
    private String userId;
    private LocalDateTime timestamp;

    /**
     * Creates a new commit with the given details.
     * @param commitId the ID of the commit - unsure how generated for now, can modify to be created in this class
     * @param message the commit message
     * @param userId the ID of the user creating the commit
     * @throws IllegalArgumentException if any of the parameters are empty
     */
    public Commit(String commitId, String message, String userId) {
        if (message == null || message.trim().isEmpty()) {
            throw new IllegalArgumentException("Message cannot be empty");
        }
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }
        if (commitId == null || commitId.trim().isEmpty()) {
            throw new IllegalArgumentException("Commit ID cannot be null or empty");
        }
        this.commitId = commitId;
        this.message = message;
        this.userId = userId;
        this.timestamp = LocalDateTime.now();
    }

    public String getCommitId() {return commitId;}

    public String getMessage() {return message;}

    public String getUserId() {return userId;}

    public LocalDateTime getTimestamp() {return timestamp;}
}
