package interface_adapter.dashboard;

import use_case.dashboard.DashboardInputBoundary;
import use_case.dashboard.DashboardInputData;

public class DashboardController {
    private final DashboardInputBoundary dashboardInteractor;

    public DashboardController(DashboardInputBoundary dashboardInteractor) {
        this.dashboardInteractor = dashboardInteractor;
    }

    /**
     * Executes the Dashboard Use Case.
     */
    public void execute() {
        final DashboardInputData inputData = new DashboardInputData();
        dashboardInteractor.execute(inputData);
    }

    /**
     * Switches the view to the Chore Creation View.
     */
    public void switchToChoreCreationView() {
        dashboardInteractor.switchToChoreCreationView();
    }
}
