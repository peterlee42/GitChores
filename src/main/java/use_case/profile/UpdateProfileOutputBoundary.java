package use_case.profile;

/**
 * Output boundary for the profile update use case.
 */
public interface UpdateProfileOutputBoundary {

    /**
     * Called when the profile update succeeds.
     *
     * @param data output data with updated profile info
     */
    void prepareSuccessView(UpdateProfileOutputData data);

    /**
     * Called when the profile update fails.
     *
     * @param errorMessage message describing the error
     */
    void prepareFailView(String errorMessage);

    /**
     * Switwches to login view.
     */
    void prepareLoginView();

    /**
     * Switches user out of their room view.
     */
    void prepareLeaveRoomView();
}
