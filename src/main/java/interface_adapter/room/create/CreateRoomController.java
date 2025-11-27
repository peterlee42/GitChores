package interface_adapter.room.create;

import use_case.room.create.CreateRoomInputBoundary;
import use_case.room.create.CreateRoomInputData;

public class CreateRoomController {

    private final CreateRoomInputBoundary interactor;

    /**
     * Constructs a CreateRoomController.
     *
     * @param interactor the room creation interactor
     */
    public CreateRoomController(CreateRoomInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Executes the room creation use case.
     *
     * @param roomName    the room name
     * @param description the room description
     * @param ownerId     the owner user ID
     */
    public void execute(String roomName, String description, String ownerId) {
        final CreateRoomInputData inputData = new CreateRoomInputData(roomName, description, ownerId);
        interactor.execute(inputData);
    }
}
