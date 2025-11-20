package use_case.room.create;

public class CreateRoomInputData {
    private final String roomName;
    private final String description;
    private final String ownerId;

    public CreateRoomInputData(String roomName, String description, String ownerId) {
        this.roomName = roomName;
        this.description = description;
        this.ownerId = ownerId;
    }

    public String getRoomName() { return roomName; }

    public String getDescription() { return description; }

    public String getOwnerId() { return ownerId; }
}
