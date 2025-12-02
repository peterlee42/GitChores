package use_case.dashboard;

public interface DashboardInputBoundary {

    /**
     * Executes the login use case.
     * 
     * @param dashboardInputData the input data for the login use case
     */
    void execute(DashboardInputData dashboardInputData);

    /**
     * Switches the view to the Chore Creation View.
     */
    void switchToChoreCreationView();
}
