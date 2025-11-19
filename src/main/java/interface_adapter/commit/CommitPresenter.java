package interface_adapter.commit;

import use_case.commit.CommitOutputBoundary;
import use_case.commit.CommitResponseModel;

/**
 * The presenter for the commiting to database use case.
 */
public class CommitPresenter implements CommitOutputBoundary {

    private String viewMessage;

    @Override
    public void presentSuccess(CommitResponseModel responseModel) {
        final String time = responseModel.getTimestamp().toString();
        final String formattedTime = time.substring(0, 10) + ", Time " + time.substring(11, 19);

        this.viewMessage = String.format(
                "Commit #%d created: %s at %s",
                responseModel.getCommitId(),
                responseModel.getMessage(),
                formattedTime
        );
    }

    @Override
    public void presentFailure(String errorMessage) {
        this.viewMessage = "ERROR: " + errorMessage;
    }

    public String getViewMessage() {
        return viewMessage;
    }
}
