package use_case.chore;

import java.util.List;

import entity.Chore;

public interface ChoreDataAccessInterface {
    /**
     * Placeholder.
     * @param chore placeholder
     */
    void saveChore(Chore chore);

    /**
     * Placeholder.
     * @param roomId placeholder
     * @param choreId placeholder
     * @return Placeholder
     */
    Chore getChoreById(String roomId, String choreId);

    /**
     * Placeholder.
     * @param roomId placeholder
     * @return placeholder
     */
    List<Chore> getChoresForRoom(String roomId);

    /**
     * Placeholder.
     * @param roomId placeholder
     * @param userId placeholder
     * @return placeholder
     */
    List<Chore> getChoresAssignedToUser(String roomId, String userId);

    /**
     * Placeholder.
     * @param roomId placeholder
     * @param choreId placeholder
     */
    void deleteChore(String roomId, String choreId);
}
