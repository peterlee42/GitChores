package use_case.room.create;

public interface CreateRoomOutputBoundary {
    /**
     * Presents the success result of room creation.
     *
     * @param outputData the output data
     */
    void presentSuccess(CreateRoomOutputData outputData);

    /**
     * Presents the failure result of room creation.
     *
     * @param errorMessage the error message
     */
    void presentFailure(String errorMessage);

    /**
     * Executes the switch to login view use case.
     */
    void switchToLoginView();

    /**
     * Executes the switch to join view use case.
     */
    void switchToJoinView();
}
