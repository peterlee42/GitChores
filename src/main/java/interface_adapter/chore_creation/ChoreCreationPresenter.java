package interface_adapter.chore_creation;

import interface_adapter.ViewManagerModel;
import use_case.chore_creation.ChoreCreationOutputBoundary;
import use_case.chore_creation.ChoreCreationOutputData;

public class ChoreCreationPresenter implements ChoreCreationOutputBoundary {

    private final ChoreCreationViewModel viewModel;
    private final ViewManagerModel viewManagerModel;

    public ChoreCreationPresenter(ChoreCreationViewModel viewModel,
                                  ViewManagerModel viewManagerModel) {
        this.viewModel = viewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(ChoreCreationOutputData outputData) {
        final ChoreCreationState state = new ChoreCreationState();
        viewModel.setState(state);
        viewModel.firePropertyChange();
        viewManagerModel.setState("main");
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
        viewManagerModel.setState("main");
        viewManagerModel.firePropertyChange();
    }
}
