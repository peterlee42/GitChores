package interface_adapter.room.create;

import interface_adapter.SessionModel;
import interface_adapter.ViewManagerModel;
import use_case.room.create.CreateRoomOutputBoundary;
import use_case.room.create.CreateRoomOutputData;

public class CreateRoomPresenter implements CreateRoomOutputBoundary {

    private final CreateRoomViewModel viewModel;
    private final ViewManagerModel viewManagerModel;
    private final SessionModel sessionModel;

    /**
     * Constructs a CreateRoomPresenter.
     *
     * @param viewModel        the create room view model
     * @param viewManagerModel the view manager model
     * @param sessionModel     the session model
     */
    public CreateRoomPresenter(CreateRoomViewModel viewModel, ViewManagerModel viewManagerModel,
                               SessionModel sessionModel) {
        this.viewModel = viewModel;
        this.viewManagerModel = viewManagerModel;
        this.sessionModel = sessionModel;
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

        // Update session with new room ID
        sessionModel.setRoomId(outputData.getRoomId());

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
}
