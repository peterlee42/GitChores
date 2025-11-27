package use_case.room.join;

public interface JoinRoomInputBoundary {

    /**
     * Executes the room joining use case.
     *
     * @param inputData the input data for joining a room
     */
    void execute(JoinRoomInputData inputData);
}
