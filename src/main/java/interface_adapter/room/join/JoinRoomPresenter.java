package interface_adapter.room.join;

import interface_adapter.SessionModel;
import interface_adapter.ViewManagerModel;
import interface_adapter.join.JoinState;
import interface_adapter.join.JoinViewModel;
import use_case.room.join.JoinRoomOutputBoundary;
import use_case.room.join.JoinRoomOutputData;

/**
 * Presenter for joining a room.
 */
public class JoinRoomPresenter implements JoinRoomOutputBoundary {

    private final JoinViewModel viewModel;
    private final ViewManagerModel viewManagerModel;
    private final SessionModel sessionModel;

    /**
     * Constructs a JoinRoomPresenter.
     *
     * @param viewModel        the join view model
     * @param viewManagerModel the view manager model
     * @param sessionModel     the session model
     */
    public JoinRoomPresenter(JoinViewModel viewModel, ViewManagerModel viewManagerModel,
                             SessionModel sessionModel) {
        this.viewModel = viewModel;
        this.viewManagerModel = viewManagerModel;
        this.sessionModel = sessionModel;
    }

    @Override
    public void presentSuccess(JoinRoomOutputData outputData) {
        final JoinState state = viewModel.getState();
        state.setJoinError(null);
        state.setRoomCode("");
        viewModel.setState(state);
        viewModel.firePropertyChange();

        // Update session with room ID
        sessionModel.setRoomId(outputData.getRoomId());

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
}
