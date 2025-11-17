package use_case.commit;

import java.time.LocalDateTime;

/**
 * This object outlines the format of the response model object to display information about a created commit.
 */
public class CommitResponseModel {

    private final int commitId;
    private final String message;
    private final LocalDateTime timestamp;
    private String viewMessage;

    public CommitResponseModel(int commitId, String message, LocalDateTime timestamp) {
        this.commitId = commitId;
        this.message = message;
        this.timestamp = timestamp;
    }

    public int getCommitId() {
        return commitId;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setViewMessage(String viewMessage) {
        this.viewMessage = viewMessage;
    }

    public String getViewMessage() {
        return viewMessage;
    }
}
