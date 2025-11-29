package use_case.room.create;

/**
 * Input data for room creation.
 */
public class CreateRoomInputData {
    private final String roomName;
    private final String description;

    /**
     * Constructs input data for creating a room.
     *
     * @param roomName    the name of the room
     * @param description the room description
     */
    public CreateRoomInputData(String roomName, String description) {
        this.roomName = roomName;
        this.description = description;
    }

    public String getRoomName() {
        return roomName;
    }

    public String getDescription() {
        return description;
    }
}
