package use_case.room.create;

public interface CreateRoomInputBoundary {

    /**
     * Executes the room creation use case.
     *
     * @param inputData the input data for room creation
     */
    void execute(CreateRoomInputData inputData);

    /**
     * Executes the switch to login view use case.
     */
    void switchToLoginView();

    /**
     * Executes the switch to join view use case.
     */
    void switchToJoinView();
}
