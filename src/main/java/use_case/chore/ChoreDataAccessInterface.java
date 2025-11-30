package use_case.chore;

import java.util.List;

import entity.Chore;

public interface ChoreDataAccessInterface {

    /**
     * Creates or updates a chore.
     *
     * @param chore the chore we want to save to the database
     */
    void saveChore(Chore chore);

    /**
     * Get a chore by its choreID.
     *
     * @param roomId the ID of the room
     * @param choreId the ID of the chore
     * @return the chore with choreID, or null if not found
     */
    Chore getChoreById(String roomId, String choreId);

    /**
     * Get all the chores for a room.
     *
     * @param roomId the ID of the room
     * @return a list of all the chores in a room
     */
    List<Chore> getChoresForRoom(String roomId);

    /**
     * Get all the chores assigned to a user.
     *
     * @param roomId the ID of the room
     * @param userId the ID of the user
     * @return a list of all the chores assigned to that user
     */
    List<Chore> getChoresAssignedToUser(String roomId, String userId);

    /**
     * Delete a chore.
     *
     * @param roomId the ID of the room
     * @param choreId the ID of the chore
     */
    void deleteChore(String roomId, String choreId);
}
