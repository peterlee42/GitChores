package interface_adapter.chore_creation;

import interface_adapter.ViewManagerModel;
import interface_adapter.logged_in.LoggedInViewModel;
import use_case.chore_creation.ChoreCreationOutputBoundary;
import use_case.chore_creation.ChoreCreationOutputData;

public class ChoreCreationPresenter implements ChoreCreationOutputBoundary {

    private final ChoreCreationViewModel viewModel;
    private final ViewManagerModel viewManagerModel;
    private final LoggedInViewModel loggedInViewModel;

    public ChoreCreationPresenter(ChoreCreationViewModel viewModel,
            ViewManagerModel viewManagerModel, LoggedInViewModel loggedInViewModel) {
        this.viewModel = viewModel;
        this.viewManagerModel = viewManagerModel;
        this.loggedInViewModel = loggedInViewModel;
    }

    @Override
    public void prepareSuccessView(ChoreCreationOutputData outputData) {
        final ChoreCreationState state = new ChoreCreationState();
        viewModel.setState(state);
        viewModel.firePropertyChange();
        viewManagerModel.setState(loggedInViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        final ChoreCreationState state = viewModel.getState();
        state.setChoreError(errorMessage);
        viewModel.setState(state);
        viewModel.firePropertyChange();
    }

    @Override
    public void switchToDashboardView() {
        viewManagerModel.setState(loggedInViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }
}
