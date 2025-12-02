package use_case.room.create;

import entity.Room;
import entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import use_case.exception.CreateRoomFailedException;
import use_case.logged_in.UserService;
import use_case.session.SessionDataAccessInterface;
import use_case.room.RoomDataAccessInterface;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateRoomInteractorTest {

    @Mock
    private RoomDataAccessInterface roomDataAccess;

    @Mock
    private SessionDataAccessInterface sessionDataAccess;

    @Mock
    private CreateRoomOutputBoundary createRoomPresenter;

    @Mock
    private UserService userService;

    // ---------- 1. Success path ----------
    @Test
    void execute_success_savesRoomAndPresentsSuccess() {
        // Arrange
        final String roomName = "My Room";
        final String description = "A place";
        final CreateRoomInputData input = new CreateRoomInputData(roomName, description);

        final User user = mock(User.class);
        when(userService.getUser()).thenReturn(user);
        when(user.getId()).thenReturn("user-1");

        // Ensure uniqueness: getRoomByInviteCode returns null for any candidate
        when(roomDataAccess.getRoomByInviteCode(anyString())).thenReturn(null);

        CreateRoomInteractor interactor = new CreateRoomInteractor(
                roomDataAccess, sessionDataAccess, createRoomPresenter, userService
        );

        // Act
        interactor.execute(input);

        // Assert: room saved and user added
        ArgumentCaptor<Room> roomCaptor = ArgumentCaptor.forClass(Room.class);
        verify(roomDataAccess, times(1)).saveRoom(roomCaptor.capture());
        Room saved = roomCaptor.getValue();
        assertEquals(roomName, saved.getName());
        assertEquals(description, saved.getDescription());
        assertEquals("user-1", saved.getOwnerId());
        assertNotNull(saved.getInviteCode());

        verify(roomDataAccess, times(1)).addUserToRoom(saved.getId(), "user-1");

        // Assert presenter called with success
        ArgumentCaptor<CreateRoomOutputData> outCaptor = ArgumentCaptor.forClass(CreateRoomOutputData.class);
        verify(createRoomPresenter, times(1)).presentSuccess(outCaptor.capture());
        CreateRoomOutputData out = outCaptor.getValue();
        assertEquals(roomName, out.getRoomName());
        assertEquals(description, out.getRoomDescription());
        assertEquals(saved.getInviteCode(), out.getInviteCode());

        verify(createRoomPresenter, never()).presentFailure(anyString());
    }

    // ---------- 2. user null ----------
    @Test
    void execute_userNull_presentsNotLoggedIn() {
        when(userService.getUser()).thenReturn(null);

        CreateRoomInteractor interactor = new CreateRoomInteractor(
                roomDataAccess, sessionDataAccess, createRoomPresenter, userService
        );

        interactor.execute(new CreateRoomInputData("n", "d"));

        verify(createRoomPresenter, times(1)).presentFailure("User not logged in");
        verify(roomDataAccess, never()).saveRoom(any());
    }

    // ---------- 3. user already in a room ----------
    @Test
    void execute_userAlreadyInRoom_presentsAlreadyInRoom() {
        final User user = mock(User.class);
        when(userService.getUser()).thenReturn(user);
        when(user.getId()).thenReturn("user-2");

        when(roomDataAccess.getUserRoomId("user-2")).thenReturn("existing-room");

        CreateRoomInteractor interactor = new CreateRoomInteractor(
                roomDataAccess, sessionDataAccess, createRoomPresenter, userService
        );

        interactor.execute(new CreateRoomInputData("rname", "desc"));

        verify(createRoomPresenter, times(1)).presentFailure("You are already in a room. Leave your current room first.");
        verify(roomDataAccess, never()).saveRoom(any());
    }

    // ---------- 4. empty room name ----------
    @Test
    void execute_emptyName_presentsValidation() {
        final User user = mock(User.class);
        when(userService.getUser()).thenReturn(user);
        when(user.getId()).thenReturn("user-3");

        CreateRoomInteractor interactor = new CreateRoomInteractor(
                roomDataAccess, sessionDataAccess, createRoomPresenter, userService
        );

        interactor.execute(new CreateRoomInputData("   ", "desc"));

        verify(createRoomPresenter, times(1)).presentFailure("Room name cannot be empty");
        verify(roomDataAccess, never()).saveRoom(any());
    }

    // ---------- 5. empty owner id ----------
    @Test
    void execute_emptyOwnerId_presentsValidation() {
        final User user = mock(User.class);
        when(userService.getUser()).thenReturn(user);
        when(user.getId()).thenReturn("   ");

        CreateRoomInteractor interactor = new CreateRoomInteractor(
                roomDataAccess, sessionDataAccess, createRoomPresenter, userService
        );

        interactor.execute(new CreateRoomInputData("name", "desc"));

        verify(createRoomPresenter, times(1)).presentFailure("Owner ID cannot be empty");
        verify(roomDataAccess, never()).saveRoom(any());
    }

    // ---------- 6. DAO failure path ----------
    @Test
    void execute_daoThrows_presentsFailure() {
        final User user = mock(User.class);
        when(userService.getUser()).thenReturn(user);
        when(user.getId()).thenReturn("user-4");

        when(roomDataAccess.getRoomByInviteCode(anyString())).thenReturn(null);
        doThrow(new CreateRoomFailedException("db down")).when(roomDataAccess).saveRoom(any());

        CreateRoomInteractor interactor = new CreateRoomInteractor(
                roomDataAccess, sessionDataAccess, createRoomPresenter, userService
        );

        interactor.execute(new CreateRoomInputData("name", "desc"));

        verify(createRoomPresenter, times(1)).presentFailure("Failed to create room: db down");
        verify(roomDataAccess, never()).addUserToRoom(anyString(), anyString());
    }
}
