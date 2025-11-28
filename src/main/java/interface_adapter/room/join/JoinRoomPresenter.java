package interface_adapter.room.join;

import interface_adapter.ViewManagerModel;
import interface_adapter.login.LoginViewModel;
import interface_adapter.room.create.CreateRoomViewModel;
import interface_adapter.session.SessionState;
import interface_adapter.session.SessionViewModel;
import use_case.room.join.JoinRoomOutputBoundary;
import use_case.room.join.JoinRoomOutputData;

/**
 * Presenter for joining a room.
 */
public class JoinRoomPresenter implements JoinRoomOutputBoundary {

    private final JoinViewModel viewModel;
    private final ViewManagerModel viewManagerModel;
    private final SessionViewModel sessionViewModel;
    private final LoginViewModel loginViewModel;
    private final CreateRoomViewModel createRoomViewModel;

    /**
     * Constructs a JoinRoomPresenter.
     *
     * @param viewModel           the join view model
     * @param viewManagerModel    the view manager model
     * @param sessionViewModel    the session model
     * @param loginViewModel      the login view model
     * @param createRoomViewModel the create room view model
     */
    public JoinRoomPresenter(JoinViewModel viewModel, ViewManagerModel viewManagerModel,
            SessionViewModel sessionViewModel, LoginViewModel loginViewModel, CreateRoomViewModel createRoomViewModel) {
        this.viewModel = viewModel;
        this.viewManagerModel = viewManagerModel;
        this.sessionViewModel = sessionViewModel;
        this.loginViewModel = loginViewModel;
        this.createRoomViewModel = createRoomViewModel;
    }

    @Override
    public void presentSuccess(JoinRoomOutputData outputData) {
        final JoinState state = viewModel.getState();
        state.setJoinError(null);
        state.setRoomCode("");
        viewModel.setState(state);
        viewModel.firePropertyChange();

        // Update session with room ID
        final SessionState sessionState = sessionViewModel.getState();
        sessionState.setRoomId(outputData.getRoomId());
        sessionViewModel.firePropertyChange();

        // Navigate to dashboard/main view
        viewManagerModel.setActiveViewName("main");
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
