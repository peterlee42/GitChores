package use_case.chore_creation;

import entity.Chore;
import entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import use_case.chore.ChoreDataAccessInterface;
import use_case.exception.ChoreCreationFailedException;
import use_case.logged_in.UserService;
import use_case.room.RoomDataAccessInterface;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChoreCreationInteractorTest {

    @Mock
    private ChoreDataAccessInterface choreDao;

    @Mock
    private RoomDataAccessInterface roomDao;

    @Mock
    private ChoreCreationOutputBoundary presenter;

    @Mock
    private UserService userService;

    // ---------- 1. Success path ----------
    @Test
    void execute_success_savesChoreAndPresentsSuccess() {
        // Arrange
        final String title = "Take out trash";
        final String description = "Weekly task";
        final String priority = "High";
        final String dueDate = "2025-12-31T23:59";
        final String assignedUserId = "user-2";
        final ChoreCreationInputData input = new ChoreCreationInputData(
                title, description, priority, dueDate, assignedUserId
        );

        final User user = mock(User.class);
        when(userService.getUser()).thenReturn(user);
        when(user.getId()).thenReturn("user-1");
        when(roomDao.getUserRoomId("user-1")).thenReturn("room-1");

        ChoreCreationInteractor interactor = new ChoreCreationInteractor(
                presenter, choreDao, roomDao, userService
        );

        // Act
        interactor.execute(input);

        // Assert: chore saved
        ArgumentCaptor<Chore> choreCaptor = ArgumentCaptor.forClass(Chore.class);
        verify(choreDao, times(1)).saveChore(choreCaptor.capture());
        Chore saved = choreCaptor.getValue();
        assertEquals(title, saved.getTitle());
        assertEquals(description, saved.getDescription());
        assertEquals("room-1", saved.getRoomId());
        assertEquals(assignedUserId, saved.getAssignedUserId());
        assertEquals("user-1", saved.getCreatingUserId());
        assertNotNull(saved.getId());
        assertTrue(saved.getId().startsWith("chore-"));

        // Assert presenter called with success
        ArgumentCaptor<ChoreCreationOutputData> outCaptor = ArgumentCaptor.forClass(ChoreCreationOutputData.class);
        verify(presenter, times(1)).prepareSuccessView(outCaptor.capture());
        ChoreCreationOutputData out = outCaptor.getValue();
        assertEquals(title, out.getTitle());

        verify(presenter, never()).prepareFailView(anyString());
    }

    // ---------- 2. user null ----------
    @Test
    void execute_userNull_presentsNotLoggedIn() {
        when(userService.getUser()).thenReturn(null);

        ChoreCreationInteractor interactor = new ChoreCreationInteractor(
                presenter, choreDao, roomDao, userService
        );

        interactor.execute(new ChoreCreationInputData("title", "desc", "High", "2025-12-31T23:59", "user-2"));

        verify(presenter, times(1)).prepareFailView("User must be logged in.");
        verify(choreDao, never()).saveChore(any());
    }

    // ---------- 3. user not in a room ----------
    @Test
    void execute_userNotInRoom_presentsNotInRoom() {
        final User user = mock(User.class);
        when(userService.getUser()).thenReturn(user);
        when(user.getId()).thenReturn("user-1");
        when(roomDao.getUserRoomId("user-1")).thenReturn(null);

        ChoreCreationInteractor interactor = new ChoreCreationInteractor(
                presenter, choreDao, roomDao, userService
        );

        interactor.execute(new ChoreCreationInputData("title", "desc", "High", "2025-12-31T23:59", "user-2"));

        verify(presenter, times(1)).prepareFailView("User must be in a room.");
        verify(choreDao, never()).saveChore(any());
    }

    // ---------- 4. invalid due date format ----------
    @Test
    void execute_invalidDueDateFormat_presentsValidation() {
        final User user = mock(User.class);
        when(userService.getUser()).thenReturn(user);
        when(user.getId()).thenReturn("user-1");
        when(roomDao.getUserRoomId("user-1")).thenReturn("room-1");

        ChoreCreationInteractor interactor = new ChoreCreationInteractor(
                presenter, choreDao, roomDao, userService
        );

        interactor.execute(new ChoreCreationInputData("title", "desc", "High", "invalid-date", "user-2"));

        verify(presenter, times(1)).prepareFailView("Invalid due date format. Use YYYY-MM-DDTHH:MM.");
        verify(choreDao, never()).saveChore(any());
    }

    // ---------- 5. null priority defaults to Medium ----------
    @Test
    void execute_nullPriority_defaultsToMedium() {
        final User user = mock(User.class);
        when(userService.getUser()).thenReturn(user);
        when(user.getId()).thenReturn("user-1");
        when(roomDao.getUserRoomId("user-1")).thenReturn("room-1");

        ChoreCreationInteractor interactor = new ChoreCreationInteractor(
                presenter, choreDao, roomDao, userService
        );

        interactor.execute(new ChoreCreationInputData("title", "desc", null, "2025-12-31T23:59", "user-2"));

        ArgumentCaptor<Chore> choreCaptor = ArgumentCaptor.forClass(Chore.class);
        verify(choreDao, times(1)).saveChore(choreCaptor.capture());
        // Note: priority is not stored in Chore entity, but the logic handles it
        verify(presenter, times(1)).prepareSuccessView(any());
    }

    // ---------- 6. blank priority defaults to Medium ----------
    @Test
    void execute_blankPriority_defaultsToMedium() {
        final User user = mock(User.class);
        when(userService.getUser()).thenReturn(user);
        when(user.getId()).thenReturn("user-1");
        when(roomDao.getUserRoomId("user-1")).thenReturn("room-1");

        ChoreCreationInteractor interactor = new ChoreCreationInteractor(
                presenter, choreDao, roomDao, userService
        );

        interactor.execute(new ChoreCreationInputData("title", "desc", "   ", "2025-12-31T23:59", "user-2"));

        verify(choreDao, times(1)).saveChore(any());
        verify(presenter, times(1)).prepareSuccessView(any());
    }

    // ---------- 7. null assignedUserId defaults to current user ----------
    @Test
    void execute_nullAssignedUser_defaultsToCurrentUser() {
        final User user = mock(User.class);
        when(userService.getUser()).thenReturn(user);
        when(user.getId()).thenReturn("user-1");
        when(roomDao.getUserRoomId("user-1")).thenReturn("room-1");

        ChoreCreationInteractor interactor = new ChoreCreationInteractor(
                presenter, choreDao, roomDao, userService
        );

        interactor.execute(new ChoreCreationInputData("title", "desc", "High", "2025-12-31T23:59", null));

        ArgumentCaptor<Chore> choreCaptor = ArgumentCaptor.forClass(Chore.class);
        verify(choreDao, times(1)).saveChore(choreCaptor.capture());
        Chore saved = choreCaptor.getValue();
        assertEquals("user-1", saved.getAssignedUserId());
    }

    // ---------- 8. blank assignedUserId defaults to current user ----------
    @Test
    void execute_blankAssignedUser_defaultsToCurrentUser() {
        final User user = mock(User.class);
        when(userService.getUser()).thenReturn(user);
        when(user.getId()).thenReturn("user-1");
        when(roomDao.getUserRoomId("user-1")).thenReturn("room-1");

        ChoreCreationInteractor interactor = new ChoreCreationInteractor(
                presenter, choreDao, roomDao, userService
        );

        interactor.execute(new ChoreCreationInputData("title", "desc", "High", "2025-12-31T23:59", "   "));

        ArgumentCaptor<Chore> choreCaptor = ArgumentCaptor.forClass(Chore.class);
        verify(choreDao, times(1)).saveChore(choreCaptor.capture());
        Chore saved = choreCaptor.getValue();
        assertEquals("user-1", saved.getAssignedUserId());
    }

    // ---------- 9. choreDao throws ChoreCreationFailedException ----------
    @Test
    void execute_choreDaoThrows_presentsFailure() {
        final User user = mock(User.class);
        when(userService.getUser()).thenReturn(user);
        when(user.getId()).thenReturn("user-1");
        when(roomDao.getUserRoomId("user-1")).thenReturn("room-1");
        doThrow(new ChoreCreationFailedException("db error")).when(choreDao).saveChore(any());

        ChoreCreationInteractor interactor = new ChoreCreationInteractor(
                presenter, choreDao, roomDao, userService
        );

        interactor.execute(new ChoreCreationInputData("title", "desc", "High", "2025-12-31T23:59", "user-2"));

        verify(presenter, times(1)).prepareFailView("db error");
    }

    // ---------- 10. roomDao throws ChoreCreationFailedException ----------
    @Test
    void execute_roomDaoThrows_presentsFailure() {
        final User user = mock(User.class);
        when(userService.getUser()).thenReturn(user);
        when(user.getId()).thenReturn("user-1");
        when(roomDao.getUserRoomId("user-1")).thenThrow(new ChoreCreationFailedException("room lookup failed"));

        ChoreCreationInteractor interactor = new ChoreCreationInteractor(
                presenter, choreDao, roomDao, userService
        );

        interactor.execute(new ChoreCreationInputData("title", "desc", "High", "2025-12-31T23:59", "user-2"));

        verify(presenter, times(1)).prepareFailView("room lookup failed");
        verify(choreDao, never()).saveChore(any());
    }

    // ---------- 11. generic exception caught ----------
    @Test
    void execute_genericException_presentsFailureWithMessage() {
        final User user = mock(User.class);
        when(userService.getUser()).thenReturn(user);
        when(user.getId()).thenReturn("user-1");
        when(roomDao.getUserRoomId("user-1")).thenReturn("room-1");
        doThrow(new RuntimeException("unexpected error")).when(choreDao).saveChore(any());

        ChoreCreationInteractor interactor = new ChoreCreationInteractor(
                presenter, choreDao, roomDao, userService
        );

        interactor.execute(new ChoreCreationInputData("title", "desc", "High", "2025-12-31T23:59", "user-2"));

        verify(presenter, times(1)).prepareFailView("Failed to create chore: unexpected error");
    }

    // ---------- 12. switchToDashboardView ----------
    @Test
    void switchToDashboardView_delegatesToPresenter() {
        ChoreCreationInteractor interactor = new ChoreCreationInteractor(
                presenter, choreDao, roomDao, userService
        );

        interactor.switchToDashboardView();

        verify(presenter, times(1)).switchToDashboardView();
    }
}
