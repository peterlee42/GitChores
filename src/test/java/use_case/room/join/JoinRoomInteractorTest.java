package use_case.room.join;

import entity.Room;
import entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import use_case.exception.JoinRoomFailedException;
import use_case.logged_in.UserService;
import use_case.session.SessionDataAccessInterface;
import use_case.room.RoomDataAccessInterface;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JoinRoomInteractorTest {

    @Mock
    private RoomDataAccessInterface roomDataAccess;

    @Mock
    private SessionDataAccessInterface sessionDataAccess;

    @Mock
    private JoinRoomOutputBoundary joinRoomPresenter;

    @Mock
    private UserService userService;

    // ---------- 1. Success path ----------
    @Test
    void execute_success_addsUserAndPresentsSuccess() {
        final User user = mock(User.class);
        when(userService.getUser()).thenReturn(user);
        when(user.getId()).thenReturn("user-1");

        final Room room = new Room("r1", "RoomName", "desc", "owner-1", "111111");
        when(roomDataAccess.getRoomByInviteCode("111111")).thenReturn(room);
        when(roomDataAccess.getUserRoomId("user-1")).thenReturn(null);
        when(roomDataAccess.isUserInRoom("r1", "user-1")).thenReturn(false);

        JoinRoomInteractor interactor = new JoinRoomInteractor(
                roomDataAccess, sessionDataAccess, joinRoomPresenter, userService
        );

        interactor.execute(new JoinRoomInputData("111111"));

        verify(roomDataAccess, times(1)).addUserToRoom("r1", "user-1");

        ArgumentCaptor<JoinRoomOutputData> outCaptor = ArgumentCaptor.forClass(JoinRoomOutputData.class);
        verify(joinRoomPresenter, times(1)).presentSuccess(outCaptor.capture());
        JoinRoomOutputData out = outCaptor.getValue();
        assertEquals("RoomName", out.getRoomName());
        assertTrue(out.isSuccess());
    }

    // ---------- 2. user null ----------
    @Test
    void execute_userNull_presentsNotLoggedIn() {
        when(userService.getUser()).thenReturn(null);

        JoinRoomInteractor interactor = new JoinRoomInteractor(
                roomDataAccess, sessionDataAccess, joinRoomPresenter, userService
        );

        interactor.execute(new JoinRoomInputData("code"));

        verify(joinRoomPresenter, times(1)).presentFailure("User not logged in");
    }

    // ---------- 3. empty invite code ----------
    @Test
    void execute_emptyInviteCode_presentsValidation() {
        final User user = mock(User.class);
        when(userService.getUser()).thenReturn(user);

        JoinRoomInteractor interactor = new JoinRoomInteractor(
                roomDataAccess, sessionDataAccess, joinRoomPresenter, userService
        );

        interactor.execute(new JoinRoomInputData("   "));

        verify(joinRoomPresenter, times(1)).presentFailure("Invite code cannot be empty");
    }

    // ---------- 4. user already in a room ----------
    @Test
    void execute_userAlreadyInRoom_presentsAlreadyInRoom() {
        final User user = mock(User.class);
        when(userService.getUser()).thenReturn(user);
        when(user.getId()).thenReturn("u3");

        when(roomDataAccess.getUserRoomId("u3")).thenReturn("some-room");

        JoinRoomInteractor interactor = new JoinRoomInteractor(
                roomDataAccess, sessionDataAccess, joinRoomPresenter, userService
        );

        interactor.execute(new JoinRoomInputData("111111"));

        verify(joinRoomPresenter, times(1)).presentFailure("You are already in a room. Leave your current room first.");
    }

    // ---------- 5. empty user id ----------
    @Test
    void execute_emptyUserId_presentsValidation() {
        final User user = mock(User.class);
        when(userService.getUser()).thenReturn(user);
        when(user.getId()).thenReturn("   ");

        JoinRoomInteractor interactor = new JoinRoomInteractor(
                roomDataAccess, sessionDataAccess, joinRoomPresenter, userService
        );

        interactor.execute(new JoinRoomInputData("111111"));

        verify(joinRoomPresenter, times(1)).presentFailure("User ID cannot be empty");
    }

    // ---------- 6. invalid invite code ----------
    @Test
    void execute_invalidInviteCode_presentsInvalid() {
        final User user = mock(User.class);
        when(userService.getUser()).thenReturn(user);
        when(user.getId()).thenReturn("u4");

        when(roomDataAccess.getRoomByInviteCode("999999")).thenReturn(null);

        JoinRoomInteractor interactor = new JoinRoomInteractor(
                roomDataAccess, sessionDataAccess, joinRoomPresenter, userService
        );

        interactor.execute(new JoinRoomInputData("999999"));

        verify(joinRoomPresenter, times(1)).presentFailure("Invalid invite code");
    }

    // ---------- 7. already member ----------
    @Test
    void execute_alreadyMember_presentsAlreadyMember() {
        final User user = mock(User.class);
        when(userService.getUser()).thenReturn(user);
        when(user.getId()).thenReturn("u5");

        final Room room = new Room("r5", "Name", "d", "owner", "222222");
        when(roomDataAccess.getRoomByInviteCode("222222")).thenReturn(room);
        when(roomDataAccess.getUserRoomId("u5")).thenReturn(null);
        when(roomDataAccess.isUserInRoom("r5", "u5")).thenReturn(true);

        JoinRoomInteractor interactor = new JoinRoomInteractor(
                roomDataAccess, sessionDataAccess, joinRoomPresenter, userService
        );

        interactor.execute(new JoinRoomInputData("222222"));

        verify(joinRoomPresenter, times(1)).presentFailure("You are already a member of this room");
    }

    // ---------- 8. DAO failure path ----------
    @Test
    void execute_daoThrows_presentsFailure() {
        final User user = mock(User.class);
        when(userService.getUser()).thenReturn(user);
        when(user.getId()).thenReturn("u6");

        final Room room = new Room("r6", "Name6", "d", "owner6", "333333");
        when(roomDataAccess.getRoomByInviteCode("333333")).thenReturn(room);
        when(roomDataAccess.getUserRoomId("u6")).thenReturn(null);
        when(roomDataAccess.isUserInRoom("r6", "u6")).thenReturn(false);

        doThrow(new JoinRoomFailedException("db down")).when(roomDataAccess).addUserToRoom("r6", "u6");

        JoinRoomInteractor interactor = new JoinRoomInteractor(
                roomDataAccess, sessionDataAccess, joinRoomPresenter, userService
        );

        interactor.execute(new JoinRoomInputData("333333"));

        verify(joinRoomPresenter, times(1)).presentFailure("Failed to join create room: db down");
    }
}
