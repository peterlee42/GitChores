package use_case.profile;

import static org.mockito.Mockito.*;

import entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.logged_in.UserService;
import use_case.room.RoomDataAccessInterface;
import use_case.session.SessionDataAccessInterface;

class UpdateProfileInteractorTest {

    private UpdateProfileInteractor interactor;
    private UpdateProfileOutputBoundary presenter;
    private SessionDataAccessInterface sessionDataAccessObject;
    private RoomDataAccessInterface roomDataAccessInterface;
    private UserService userService;
    private User user;

    @BeforeEach
    void setUp() {
        presenter = mock(UpdateProfileOutputBoundary.class);
        sessionDataAccessObject = mock(SessionDataAccessInterface.class);
        roomDataAccessInterface = mock(RoomDataAccessInterface.class);
        userService = mock(UserService.class);
        user = new User("testUser", "test@example.com", "password", "1");

        when(userService.getUser()).thenReturn(user);

        interactor = new UpdateProfileInteractor(
                presenter,
                sessionDataAccessObject,
                roomDataAccessInterface,
                userService);
    }

    @Test
    void execute_shouldPrepareSuccessView() {
        interactor.execute();

        verify(presenter).prepareSuccessView(any(UpdateProfileOutputData.class));
    }

    @Test
    void updateProfile_shouldPrepareProfilePic() {
        UpdateProfileInputData inputData = new UpdateProfileInputData("path/to/photo.jpg");

        interactor.updateProfile(inputData);

        verify(presenter).prepareProfilePic(any(UpdateProfileOutputData.class));
    }

    @Test
    void logout_shouldClearSessionAndPrepareLoginView() {
        interactor.logout();

        verify(sessionDataAccessObject).clearCurrentToken();
        verify(presenter).prepareLoginView();
    }

    @Test
    void leaveRoom_shouldRemoveUserAndPrepareLeaveRoomView() {
        when(roomDataAccessInterface.getUserRoomId(user.getId())).thenReturn("roomId");

        interactor.leaveRoom();

        verify(roomDataAccessInterface).removeUserFromRoom("roomId", user.getId());
        verify(presenter).prepareLeaveRoomView();
    }
}
