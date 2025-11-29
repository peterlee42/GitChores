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
     */
    public void execute(String roomName, String description) {
        final CreateRoomInputData inputData = new CreateRoomInputData(roomName, description);
        interactor.execute(inputData);
    }

    /**
     * Executes the "switch to joinView" Use Case.
     */
    public void switchToJoinView() {
        interactor.switchToJoinView();
    }

    /**
     * Executes the "switch to loginView" Use Case.
     */
    public void switchToLoginView() {
        interactor.switchToLoginView();
    }
}
