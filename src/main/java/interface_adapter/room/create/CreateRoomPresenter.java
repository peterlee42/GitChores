package interface_adapter.room.create;

import interface_adapter.ViewManagerModel;
import interface_adapter.login.LoginViewModel;
import interface_adapter.main.MainViewModel;
import interface_adapter.room.join.JoinViewModel;
import use_case.room.create.CreateRoomOutputBoundary;
import use_case.room.create.CreateRoomOutputData;

public class CreateRoomPresenter implements CreateRoomOutputBoundary {

    private final CreateRoomViewModel viewModel;
    private final ViewManagerModel viewManagerModel;
    private final LoginViewModel loginViewModel;
    private final JoinViewModel joinViewModel;
    private final MainViewModel mainViewModel;

    /**
     * Constructs a CreateRoomPresenter.
     *
     * @param viewModel        the create room view model
     * @param viewManagerModel the view manager model
     * @param loginViewModel   the login view model
     * @param joinViewModel    the join view model
     * @param mainViewModel    the main view model
     */
    public CreateRoomPresenter(CreateRoomViewModel viewModel, ViewManagerModel viewManagerModel,
            LoginViewModel loginViewModel, JoinViewModel joinViewModel, MainViewModel mainViewModel) {
        this.viewModel = viewModel;
        this.viewManagerModel = viewManagerModel;
        this.loginViewModel = loginViewModel;
        this.joinViewModel = joinViewModel;
        this.mainViewModel = mainViewModel;
    }

    @Override
    public void presentSuccess(CreateRoomOutputData outputData) {
        final CreateRoomState state = viewModel.getState();
        state.setRoomName(outputData.getRoomName());
        state.setInviteCode(outputData.getInviteCode());
        state.setSuccess(true);
        state.setError(null);
        viewModel.setState(state);
        viewModel.firePropertyChange();

        // Navigate to dashboard
        viewManagerModel.setActiveViewName(mainViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void presentFailure(String errorMessage) {
        final CreateRoomState state = viewModel.getState();
        state.setSuccess(false);
        state.setError(errorMessage);
        viewModel.setState(state);
        viewModel.firePropertyChange();
    }

    @Override
    public void switchToLoginView() {
        viewManagerModel.setState(loginViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void switchToJoinView() {
        viewManagerModel.setState(joinViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }
}
