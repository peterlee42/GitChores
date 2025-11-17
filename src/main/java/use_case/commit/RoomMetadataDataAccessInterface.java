package use_case.commit;

/**
 * DAO interface for the Metadata table, which stores the latest commitID for each room.
 */
public interface RoomMetadataDataAccessInterface {

    /**
     * Retrieves the next commit ID for the specific room, so we can create a commit with that ID.
     * @param roomId The ID of the room we want to obtain information for.
     * @return An integer value representing the next free commit number.
     */
    int incrementAndGetLatestCommitId(String roomId);
}
