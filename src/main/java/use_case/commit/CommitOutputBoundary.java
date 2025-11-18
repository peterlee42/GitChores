package use_case.commit;

/**
 * The output boundary for the commit use case.
 */
public interface CommitOutputBoundary {

    /**
     * Presents a success message of the commit being saved successfully.
     * @param responseModel the information of the commit to be presented to the user (message, timestamp, commitID)
     */
    void presentSuccess(CommitResponseModel responseModel);

    /**
     * Presents a failure message when the commit is not saved successfully.
     * @param message an error message.
     */
    void presentFailure(String message);
}
