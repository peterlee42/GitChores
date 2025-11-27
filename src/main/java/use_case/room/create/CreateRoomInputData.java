package use_case.room.create;

/**
 * Input data for room creation.
 */
public class CreateRoomInputData {
    private final String roomName;
    private final String description;
    private final String ownerId;

    /**
     * Constructs input data for creating a room.
     *
     * @param roomName    the name of the room
     * @param description the room description
     * @param ownerId     the ID of the user creating the room
     */
    public CreateRoomInputData(String roomName, String description, String ownerId) {
        this.roomName = roomName;
        this.description = description;
        this.ownerId = ownerId;
    }

    public String getRoomName() {
        return roomName;
    }

    public String getDescription() {
        return description;
    }

    public String getOwnerId() {
        return ownerId;
    }
}
