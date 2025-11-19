package use_case.commit;

public interface CommitInputBoundary {

    /**
     * Attempt to save the commit to the database and present a success/failure message.
     * @param requestModel an object containing the needed info for the database (userId, roomId, message)
     */
    void execute(CommitRequestModel requestModel);
}
