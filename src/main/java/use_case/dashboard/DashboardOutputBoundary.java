package use_case.dashboard;

public interface DashboardOutputBoundary {
    /**
     * Prepares the success view for the Dashboard Use Case.
     * 
     * @param dashboardOutputData the output data
     */
    void prepareSuccessView(DashboardOutputData dashboardOutputData);

    /**
     * Prepares the failure view for the Dashboard Use Case.
     * 
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);
}
