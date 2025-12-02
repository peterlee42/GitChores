package use_case.chore_creation;

public interface ChoreCreationOutputBoundary {

    /**
     * Prepares the success view after a chore is successfully created.
     *
     * @param outputData the data to present
     */
    void prepareSuccessView(ChoreCreationOutputData outputData);

    /**
     * Prepares the fail view when chore creation fails.
     *
     * @param errorMessage the error message to display
     */
    void prepareFailView(String errorMessage);

    /**
     * Switches the view back to the dashboard.
     */
    void switchToDashboardView();
}
