package entity;

/**
 * An entity that represents a room within the app.
 */
public class Room extends AbstractDomain {

    private String name;
    private String description;
    private final String ownerId;
    private final String inviteCode;

    /**
     * Creates a new room with the given non-empty name and description.
     *
     * @param id          the room ID
     * @param name        the room name
     * @param description the room description
     * @param ownerId     the user ID of the room owner
     * @param inviteCode  the room invite code — TODO: generation
     * @throws IllegalArgumentException if any of the parameters are null or empty
     */
    public Room(String id, String name, String description, String ownerId, String inviteCode) {
        super(id);

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Room name cannot be null or empty");
        }
        if (ownerId == null || ownerId.trim().isEmpty()) {
            throw new IllegalArgumentException("Owner ID cannot be null or empty");
        }
        if (inviteCode == null || inviteCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Room invite code cannot be null or empty");
        }

        this.name = name;
        this.description = description;
        this.ownerId = ownerId;
        this.inviteCode = inviteCode;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
