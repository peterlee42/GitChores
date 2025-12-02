package interface_adapter.dashboard;

import interface_adapter.ViewManagerModel;
import interface_adapter.chore_creation.ChoreCreationViewModel;
import use_case.dashboard.DashboardOutputBoundary;
import use_case.dashboard.DashboardOutputData;

public class DashboardPresenter implements DashboardOutputBoundary {
    private final DashboardViewModel dashboardViewModel;
    private final ViewManagerModel viewManagerModel;
    private final ChoreCreationViewModel choreCreationViewModel;

    public DashboardPresenter(DashboardViewModel dashboardViewModel, ViewManagerModel viewManagerModel,
            ChoreCreationViewModel choreCreationViewModel) {
        this.dashboardViewModel = dashboardViewModel;
        this.viewManagerModel = viewManagerModel;
        this.choreCreationViewModel = choreCreationViewModel;
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

    @Override
    public void presentChoreCreationView() {
        viewManagerModel.setState(choreCreationViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }
}
