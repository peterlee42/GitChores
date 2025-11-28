package interface_adapter.room.create;

import interface_adapter.ViewManagerModel;
import interface_adapter.login.LoginViewModel;
import interface_adapter.room.join.JoinViewModel;
import interface_adapter.session.SessionState;
import interface_adapter.session.SessionViewModel;
import use_case.room.create.CreateRoomOutputBoundary;
import use_case.room.create.CreateRoomOutputData;

public class CreateRoomPresenter implements CreateRoomOutputBoundary {

    private final CreateRoomViewModel viewModel;
    private final ViewManagerModel viewManagerModel;
    private final SessionViewModel sessionViewModel;
    private final LoginViewModel loginViewModel;
    private final JoinViewModel joinViewModel;

    /**
     * Constructs a CreateRoomPresenter.
     *
     * @param viewModel        the create room view model
     * @param viewManagerModel the view manager model
     * @param sessionViewModel the session model
     * @param loginViewModel   the login view model
     * @param joinViewModel    the join view model
     */
    public CreateRoomPresenter(CreateRoomViewModel viewModel, ViewManagerModel viewManagerModel,
            SessionViewModel sessionViewModel, LoginViewModel loginViewModel, JoinViewModel joinViewModel) {
        this.viewModel = viewModel;
        this.viewManagerModel = viewManagerModel;
        this.sessionViewModel = sessionViewModel;
        this.loginViewModel = loginViewModel;
        this.joinViewModel = joinViewModel;
    }

    @Override
    public void presentSuccess(CreateRoomOutputData outputData) {
        final CreateRoomState state = viewModel.getState();
        state.setRoomId(outputData.getRoomId());
        state.setRoomName(outputData.getRoomName());
        state.setInviteCode(outputData.getInviteCode());
        state.setSuccess(true);
        state.setError(null);
        viewModel.setState(state);
        viewModel.firePropertyChange();

        // Update session with room ID
        final SessionState sessionState = sessionViewModel.getState();
        sessionState.setRoomId(outputData.getRoomId());
        sessionViewModel.setState(sessionState);
        sessionViewModel.firePropertyChange();

        // Navigate to dashboard
        viewManagerModel.setActiveViewName("main");
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
        // clear session state
        final SessionState sessionState = sessionViewModel.getState();
        sessionState.clear();
        sessionViewModel.firePropertyChange();

        viewManagerModel.setState(loginViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void switchToJoinView() {
        viewManagerModel.setState(joinViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }
}
