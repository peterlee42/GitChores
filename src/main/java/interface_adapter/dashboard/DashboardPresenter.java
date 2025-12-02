package interface_adapter.dashboard;

import use_case.dashboard.DashboardOutputBoundary;
import use_case.dashboard.DashboardOutputData;

public class DashboardPresenter implements DashboardOutputBoundary {
    private final DashboardViewModel dashboardViewModel;

    public DashboardPresenter(DashboardViewModel dashboardViewModel) {
        this.dashboardViewModel = dashboardViewModel;

    }

    @Override
    public void prepareSuccessView(DashboardOutputData dashboardOutputData) {
        final DashboardState dashboardState = dashboardViewModel.getState();

        dashboardState.setActivityData(dashboardOutputData.getActivityData());
        dashboardState.setCommitsMessages(dashboardOutputData.getCommitsMessages());
        dashboardState.setCurrentUsername(dashboardOutputData.getCurrentUsername());
        dashboardState.setRoomCode(dashboardOutputData.getRoomCode());
        dashboardState.setRoomName(dashboardOutputData.getRoomName());
        dashboardState.setRoomDescription(dashboardOutputData.getRoomDescription());
        dashboardViewModel.setState(dashboardState);
        dashboardViewModel.firePropertyChange();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        final DashboardState dashboardState = dashboardViewModel.getState();
        dashboardState.setErrorMessage(errorMessage);
        dashboardViewModel.setState(dashboardState);
        dashboardViewModel.firePropertyChange();
    }
}
