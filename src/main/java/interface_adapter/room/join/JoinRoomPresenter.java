package interface_adapter.room.join;

import interface_adapter.ViewManagerModel;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.login.LoginViewModel;
import interface_adapter.room.create.CreateRoomViewModel;
import use_case.room.join.JoinRoomOutputBoundary;
import use_case.room.join.JoinRoomOutputData;

/**
 * Presenter for joining a room.
 */
public class JoinRoomPresenter implements JoinRoomOutputBoundary {

    private final JoinViewModel viewModel;
    private final ViewManagerModel viewManagerModel;
    private final LoginViewModel loginViewModel;
    private final CreateRoomViewModel createRoomViewModel;
    private final LoggedInViewModel mainViewModel;

    /**
     * Constructs a JoinRoomPresenter.
     *
     * @param viewModel           the join view model
     * @param viewManagerModel    the view manager model
     * @param loginViewModel      the login view model
     * @param createRoomViewModel the create room view model
     * @param mainViewModel       the main view model
     */
    public JoinRoomPresenter(JoinViewModel viewModel, ViewManagerModel viewManagerModel,
            LoginViewModel loginViewModel, CreateRoomViewModel createRoomViewModel, LoggedInViewModel mainViewModel) {
        this.viewModel = viewModel;
        this.viewManagerModel = viewManagerModel;
        this.loginViewModel = loginViewModel;
        this.createRoomViewModel = createRoomViewModel;
        this.mainViewModel = mainViewModel;
    }

    @Override
    public void presentSuccess(JoinRoomOutputData outputData) {
        viewModel.setState(new JoinState());
        viewModel.firePropertyChange();

        // Navigate to dashboard/main view
        viewManagerModel.setActiveViewName(mainViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void presentFailure(String errorMessage) {
        final JoinState state = viewModel.getState();
        state.setJoinError(errorMessage);
        viewModel.setState(state);
        viewModel.firePropertyChange();
    }

    @Override
    public void switchToLoginView() {
        viewManagerModel.setState(loginViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void switchToCreateView() {
        viewManagerModel.setState(createRoomViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }
}
