package use_case.room;

import java.util.List;

import entity.Room;

public interface RoomDataAccessInterface {
    /**
     * Saves a room to the data store.
     *
     * @param room the room to save
     */
    void saveRoom(Room room);

    /**
     * Retrieves a room by its ID.
     *
     * @param roomId the room ID
     * @return the room, or null if not found
     */
    Room getRoomById(String roomId);

    /**
     * Retrieves a room by its invite code.
     *
     * @param inviteCode the invite code
     * @return the room, or null if not found
     */
    Room getRoomByInviteCode(String inviteCode);

    /**
     * Adds a user to a room.
     *
     * @param roomId the room ID
     * @param userId the user ID to add
     */
    void addUserToRoom(String roomId, String userId);

    /**
     * Gets all members of a room.
     *
     * @param roomId the room ID
     * @return list of user IDs in the room
     */
    List<String> getRoomMembers(String roomId);

    /**
     * Checks if a user is a member of a room.
     *
     * @param roomId the room ID
     * @param userId the user ID
     * @return true if the user is a member, false otherwise
     */
    boolean isUserInRoom(String roomId, String userId);
}
