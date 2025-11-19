package use_case.commit;

import java.util.List;

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

    /**
     * Retrieves the list of all chores pending review for a given room.
     * @param roomId The ID of the room we want to obtain information for.
     * @return list of chores pending approval
     */
    List<String> getPendingReviews(String roomId);

    /**
     * Adds a chore to the review list for the given room, and returns if the operation was successful.
     * @param roomId The ID of the room we want to obtain information for.
     * @param choreName The name of the chore.
     * @return True if a new review request was added, false if it failed or the name already existed.
     */
    boolean addPendingReview(String roomId, String choreName);

    /**
     * Adds a chore to the review list for the given room.
     * @param roomId The ID of the room we want to obtain information for.
     * @param choreName The name of the chore.
     * @return True if the chore was removed from review list, false if it failed or name already existed.
     */
    boolean removePendingReview(String roomId, String choreName);
}
