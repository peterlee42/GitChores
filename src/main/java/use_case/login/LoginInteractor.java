package use_case.login;

import entity.Token;
import entity.User;
import use_case.exception.LoginFailedException;
import use_case.room.RoomDataAccessInterface;
import use_case.session.SessionDataAccessInterface;

/**
 * The interactor for the Signup Use Case.
 */
public class LoginInteractor implements LoginInputBoundary {
    private final LoginOutputBoundary loginPresenter;
    private final LoginDataAccessInterface userDataAccessObject;
    private final SessionDataAccessInterface sessionDataAccessObject;
    private final RoomDataAccessInterface roomDataAccessObject;

    public LoginInteractor(LoginOutputBoundary loginPresenter, LoginDataAccessInterface userDataAccessObject,
            SessionDataAccessInterface sessionDataAccessObject, RoomDataAccessInterface roomDataAccessObject) {
        this.loginPresenter = loginPresenter;
        this.userDataAccessObject = userDataAccessObject;
        this.sessionDataAccessObject = sessionDataAccessObject;
        this.roomDataAccessObject = roomDataAccessObject;
    }

    @Override
    public void execute(LoginInputData loginInputData) {
        final String username = loginInputData.getUsername();
        final String password = loginInputData.getPassword();

        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            loginPresenter.prepareFailView("Username and password cannot be empty.");
            return;
        } else {

            try {
                final Token token = userDataAccessObject.login(username, password);
                sessionDataAccessObject.setCurrentToken(token);

                final User user = userDataAccessObject.getCurrentUser(token);

                final boolean inRoom;
                if (roomDataAccessObject.getUserRoomId(user.getId()) != null) {
                    inRoom = true;
                } else {
                    inRoom = false;
                }
                final LoginOutputData output = new LoginOutputData(username, inRoom);
                loginPresenter.prepareSuccessView(output);
            } catch (LoginFailedException ex) {
                loginPresenter.prepareFailView(ex.getMessage());
            }
        }
    }

    @Override
    public void switchToSignupView() {
        loginPresenter.switchToSignupView();
    }
}
