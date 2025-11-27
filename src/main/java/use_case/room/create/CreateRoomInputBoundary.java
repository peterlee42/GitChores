package use_case.room.create;

public interface CreateRoomInputBoundary {

    /**
     * Executes the room creation use case.
     *
     * @param inputData the input data for room creation
     */
    void execute(CreateRoomInputData inputData);
}
