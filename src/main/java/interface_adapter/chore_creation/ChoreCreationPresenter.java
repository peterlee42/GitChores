package interface_adapter.chore_creation;

import interface_adapter.ViewManagerModel;
import interface_adapter.signup.SignupViewModel;
import use_case.chore_creation.ChoreCreationOutputBoundary;
import use_case.chore_creation.ChoreCreationOutputData;

public class ChoreCreationPresenter implements ChoreCreationOutputBoundary {

    private final ChoreCreationViewModel viewModel;
    private final ViewManagerModel viewManagerModel;
    private final SignupViewModel signupViewModel;

    public ChoreCreationPresenter(ChoreCreationViewModel viewModel,
                                  ViewManagerModel viewManagerModel,
                                  SignupViewModel signupViewModel) {
        this.viewModel = viewModel;
        this.viewManagerModel = viewManagerModel;
        this.signupViewModel = signupViewModel;
    }

    @Override
    public void prepareSuccessView(ChoreCreationOutputData outputData) {
        // Clear any errors
        final ChoreCreationState state = new ChoreCreationState();
        viewModel.setState(state);
        viewModel.firePropertyChange();

        // Navigate to main/dashboard view
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
    public void switchToSignupView() {
        viewManagerModel.setState(signupViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }
}
