package use_case.room.join;

public interface JoinRoomOutputBoundary {

    /**
     * Presents the success result of joining a room.
     *
     * @param outputData the output data
     */
    void presentSuccess(JoinRoomOutputData outputData);

    /**
     * Presents the failure result of joining a room.
     *
     * @param errorMessage the error message
     */
    void presentFailure(String errorMessage);
}
