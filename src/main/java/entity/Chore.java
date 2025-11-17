package entity;

import java.time.LocalDateTime;

/**
 * An entity that represents a chore within a room.
 */
public class Chore extends AbstractDomain {
    private final String roomId;
    private final String creatingUserId;
    private String assignedUserId;
    private String title;
    private String description;
    private LocalDateTime dueDate;
    private ChoreStatus status;
    private boolean needsReview;

    /**
     * Creates new chore with the given details.
     *
     * @param id     the Chore ID
     * @param roomId      the roomId of the Chore
     * @param assignedUserId    the id of the assigned user
     * @param creatingUserId    the id of the user creating the Chore
     * @param title the Chore title
     * @param description   the Chore description
     * @param dueDate   the Chore's dueDate
     * @param status    the Chore's Status
     * @param needsReview the review state of the Chore
     * @throws IllegalArgumentException if required fields are null or empty
     */
    @SuppressWarnings("checkstyle:ParameterNumber")
    public Chore(String id, String roomId, String assignedUserId, String creatingUserId,
                 String title, String description, LocalDateTime dueDate, ChoreStatus status, boolean needsReview) {
        super(id);

        if (roomId == null || roomId.trim().isEmpty()) {
            throw new IllegalArgumentException("Room ID cannot be empty.");
        }
        if (creatingUserId == null || creatingUserId.trim().isEmpty()) {
            throw new IllegalArgumentException("Creating user ID cannot be empty.");
        }
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Chore title cannot be empty.");
        }
        if (dueDate == null) {
            throw new IllegalArgumentException("Due date cannot be null.");
        }
        if (status == null) {
            throw new IllegalArgumentException("Chore status cannot be null.");
        }

        this.roomId = roomId;
        this.assignedUserId = assignedUserId;
        this.creatingUserId = creatingUserId;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.status = status;
        this.needsReview = needsReview;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getAssignedUserId() {
        return assignedUserId;
    }

    public String getCreatingUserId() {
        return creatingUserId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public ChoreStatus getStatus() {
        return status;
    }

    public boolean getNeedsReview() {
        return needsReview;
    }

    public void setAssignedUserId(String assignedUserId) {
        this.assignedUserId = assignedUserId;
    }

    /**
     * Sets the title for this chore.
     *
     * @param title the new title (must not be null)
     * @throws IllegalArgumentException if title is null
     */
    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets the due date for this chore.
     *
     * @param dueDate the new due date (must not be null)
     * @throws IllegalArgumentException if dueDate is null
     */
    public void setDueDate(LocalDateTime dueDate) {
        if (dueDate == null) {
            throw new IllegalArgumentException("Due date cannot be null");
        }
        this.dueDate = dueDate;
    }

    /**
     * Sets the status for this chore.
     *
     * @param status the new status (must not be null)
     * @throws IllegalArgumentException if status is null
     */
    public void setStatus(ChoreStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }
        this.status = status;
    }

    public void setNeedsReview(boolean needsReview) {
        this.needsReview = needsReview;
    }
}
