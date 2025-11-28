package use_case.room.join;

public interface JoinRoomInputBoundary {

    /**
     * Executes the room joining use case.
     *
     * @param inputData the input data for joining a room
     */
    void execute(JoinRoomInputData inputData);

    /**
     * Executes the switch to login view use case.
     */
    void switchToLoginView();

    /**
     * Executes the switch to create view use case.
     */
    void switchToCreateView();
}
