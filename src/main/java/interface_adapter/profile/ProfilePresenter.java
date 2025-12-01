package interface_adapter.profile;

import interface_adapter.ViewManagerModel;
import interface_adapter.login.LoginViewModel;
import interface_adapter.room.join.JoinViewModel;
import use_case.profile.UpdateProfileOutputBoundary;
import use_case.profile.UpdateProfileOutputData;

public class ProfilePresenter implements UpdateProfileOutputBoundary {
    private final ViewManagerModel viewManagerModel;
    private final ProfileViewModel profileViewModel;
    private final LoginViewModel loginViewModel;
    private final JoinViewModel joinViewModel;

    public ProfilePresenter(ViewManagerModel viewManagerModel, ProfileViewModel profileViewModel,
            LoginViewModel loginViewModel, JoinViewModel joinViewModel) {
        this.profileViewModel = profileViewModel;
        this.loginViewModel = loginViewModel;
        this.joinViewModel = joinViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(UpdateProfileOutputData data) {
        final ProfileState profileState = profileViewModel.getState();
        profileState.setEmail(data.getEmail());
        profileState.setProfilePhotoPath(data.getProfilePhotoPath());
        profileState.setErrorMessage(null);
        profileViewModel.firePropertyChange();
    }

    @Override
    public void prepareFailView(String error) {
        final ProfileState profileState = profileViewModel.getState();
        profileState.setErrorMessage(error);
        profileViewModel.firePropertyChange();
    }

    @Override
    public void prepareLoginView() {
        viewManagerModel.setState(loginViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void prepareLeaveRoomView() {
        viewManagerModel.setState(joinViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }

}
