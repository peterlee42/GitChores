package use_case.commit;

import java.util.List;

import entity.Commit;

/**
 * DAO interface for the GitConsole Use Case, in particular the commit functionality.
 */
public interface CommitDataAccessInterface {

    /**
     * Retrieves the next commit ID for the specific room, so we can create a commit with that ID.
     * @param commit The Commit we want to save to our database.
     */
    void saveCommit(Commit commit);

    /**
     * Retrieves a list of commits for the room (TBD if all or just recent ones).
     * @param roomId The ID of the room we want to obtain information for.
     * @return A list of the commits
     */
    List<Commit> getCommitsForRoom(String roomId);
}
