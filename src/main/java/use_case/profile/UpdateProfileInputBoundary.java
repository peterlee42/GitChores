package use_case.profile;

/**
 * Input boundary for the profile update use case.
 */
public interface UpdateProfileInputBoundary {

    /**
     * Executes a profile update.
     *
     * @param data input data containing new profile information
     */
    void updateProfile(UpdateProfileInputData data);

    /**
     * Logs out the current user.
     */
    void logout();

    /**
     * Allows the user to leave the current room.
     */
    void leaveRoom();
}
