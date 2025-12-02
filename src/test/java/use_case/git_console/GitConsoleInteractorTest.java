package use_case.git_console;

import data_access.dynamo_db.RoomMetadataDataAccessObject;
import entity.User;
import interface_adapter.commit.CommitController;
import interface_adapter.commit.CommitPresenter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import use_case.logged_in.UserService;
import use_case.room.RoomDataAccessInterface;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Tests for GitConsoleInteractor to achieve 100% code coverage.
 */
@ExtendWith(MockitoExtension.class)
class GitConsoleInteractorTest {

    // Mocks for collaborators
    @Mock
    private CommitController commitController;

    @Mock
    private CommitPresenter commitPresenter;

    @Mock
    private RoomMetadataDataAccessObject roomMetadataDao;

    @Mock
    private UserService userService;

    @Mock
    private RoomDataAccessInterface roomDataAccess;

    private TestPresenter presenter;
    private GitConsoleInteractor interactor;

    /**
     * Simple fake presenter to capture the output that would go to the view.
     */
    private static class TestPresenter implements GitConsoleOutputBoundary {
        String lastCommand;
        String lastOutput;

        @Override
        public void presentResponse(String command, String output) {
            this.lastCommand = command;
            this.lastOutput = output;
        }
    }

    @BeforeEach
    void setUp() {
        presenter = new TestPresenter();
        interactor = new GitConsoleInteractor(
                presenter,
                commitController,
                commitPresenter,
                roomMetadataDao,
                userService,
                roomDataAccess
        );
    }

    // Validation of execute command (checks of correctness before it splits command into cases)

    @Test
    void executeCommand_nullCommand_showsPleaseEnter() {
        interactor.executeCommand(null);

        assertEquals(null, presenter.lastCommand);
        assertEquals("Please enter a command.", presenter.lastOutput);
    }

    @Test
    void executeCommand_blankCommand_showsPleaseEnter() {
        interactor.executeCommand("   ");

        assertEquals("   ", presenter.lastCommand);
        assertEquals("Please enter a command.", presenter.lastOutput);
    }

    @Test
    void executeCommand_invalidPrefix_showsInvalidCommand() {
        interactor.executeCommand("status");

        assertEquals("status", presenter.lastCommand);
        assertEquals("Invalid command. Commands must start with 'git'.", presenter.lastOutput);
    }

    @Test
    void executeCommand_missingSubcommand_showsMissingSubcommandMessage() {
        interactor.executeCommand("git");

        assertEquals("git", presenter.lastCommand);
        assertEquals("Missing subcommand after git.", presenter.lastOutput);
    }

    @Test
    void executeCommand_unknownSubcommand_showsUnknownSubcommand() {
        interactor.executeCommand("git something");

        assertEquals("git something", presenter.lastCommand);
        assertEquals("Unknown subcommand: something", presenter.lastOutput);
    }

    // ------------------------ COMMIT COMMAND ------------------------

    @Test
    void commit_withoutDashM_showsMissingMessageFlagError() {
        interactor.executeCommand("git commit some random text");

        assertEquals("git commit some random text", presenter.lastCommand);
        assertEquals("Your commit is missing an '-m' before the message", presenter.lastOutput);
    }

    @Test
    void commit_withEmptyMessage_showsEmptyMessageError_caseQuotes() {
        // Message is "" -> becomes empty after regex and trim
        interactor.executeCommand("git commit -m \"\"");

        assertEquals("git commit -m \"\"", presenter.lastCommand);
        assertEquals("Error: empty commit message", presenter.lastOutput);
    }

    @Test
    void commit_withEmptyMessage_showsEmptyMessageError_caseSpaces() {
        interactor.executeCommand("git commit -m    ");

        assertEquals("git commit -m    ", presenter.lastCommand);
        assertEquals("Error: empty commit message", presenter.lastOutput);
    }

    @Test
    void commit_withValidMessage_callsCommitControllerAndUsesPresenterMessage() {
        // Arrange user and room
        User mockUser = mock(User.class);
        when(userService.getUser()).thenReturn(mockUser);
        when(mockUser.getId()).thenReturn("user-1");
        when(roomDataAccess.getUserRoomId("user-1")).thenReturn("room-1");

        when(commitPresenter.getViewMessage()).thenReturn("Commit successful!");

        String command = "git commit -m \"Refactor chores\"";

        // Act
        interactor.executeCommand(command);

        // Assert
        assertEquals(command, presenter.lastCommand);
        assertEquals("Commit successful!", presenter.lastOutput);

        // Verify the commit use case was invoked with correct ids and message
        verify(commitController, times(1))
                .execute("room-1", "user-1", "\"Refactor chores\"");
    }

    // ------------------------ REQUEST REVIEW COMMAND ------------------------

    @Test
    void requestReview_userNotInRoom_returnsError() {
        // userService.getUser() will return null by default from Mockito
        interactor.executeCommand("git request_review Clean kitchen");

        assertEquals("git request_review Clean kitchen", presenter.lastCommand);
        assertEquals("Error: User not logged in or is not in a room", presenter.lastOutput);
    }

    @Test
    void requestReview_successfulAdd_returnsSuccessMessage() {
        // User + room present
        User mockUser = mock(User.class);
        when(userService.getUser()).thenReturn(mockUser);
        when(mockUser.getId()).thenReturn("user-1");
        when(roomDataAccess.getUserRoomId("user-1")).thenReturn("room-1");

        when(roomMetadataDao.addPendingReview("room-1", "Clean kitchen")).thenReturn(true);

        interactor.executeCommand("git request_review Clean kitchen");

        assertEquals("git request_review Clean kitchen", presenter.lastCommand);
        assertEquals("Review requested for chore: Clean kitchen", presenter.lastOutput);
    }

    @Test
    void requestReview_alreadyPending_returnsAlreadyPendingMessage() {
        User mockUser = mock(User.class);
        when(userService.getUser()).thenReturn(mockUser);
        when(mockUser.getId()).thenReturn("user-1");
        when(roomDataAccess.getUserRoomId("user-1")).thenReturn("room-1");

        String choreName = "Clean kitchen";
        when(roomMetadataDao.addPendingReview("room-1", choreName)).thenReturn(false);
        when(roomMetadataDao.getPendingReviews("room-1"))
                .thenReturn(List.of(choreName, "Other chore"));

        interactor.executeCommand("git request_review Clean kitchen");

        assertEquals("git request_review Clean kitchen", presenter.lastCommand);
        assertEquals("Chore already pending review: Clean kitchen", presenter.lastOutput);
    }

    @Test
    void requestReview_dbError_returnsDbErrorMessage() {
        User mockUser = mock(User.class);
        when(userService.getUser()).thenReturn(mockUser);
        when(mockUser.getId()).thenReturn("user-1");
        when(roomDataAccess.getUserRoomId("user-1")).thenReturn("room-1");

        String choreName = "Clean kitchen";
        when(roomMetadataDao.addPendingReview("room-1", choreName)).thenReturn(false);
        when(roomMetadataDao.getPendingReviews("room-1"))
                .thenReturn(List.of("Some other chore"));

        interactor.executeCommand("git request_review Clean kitchen");

        assertEquals("git request_review Clean kitchen", presenter.lastCommand);
        assertEquals("Error connecting to database. Contact support.", presenter.lastOutput);
    }

    // ------------------------ APPROVE REQUEST COMMAND ------------------------

    @Test
    void approveRequest_userNotInRoom_returnsError() {
        interactor.executeCommand("git approve_request Clean kitchen");

        assertEquals("git approve_request Clean kitchen", presenter.lastCommand);
        assertEquals("Error: User not logged in or is not in a room", presenter.lastOutput);
    }

    @Test
    void approveRequest_successfulRemove_returnsApprovedMessage() {
        User mockUser = mock(User.class);
        when(userService.getUser()).thenReturn(mockUser);
        when(mockUser.getId()).thenReturn("user-1");
        when(roomDataAccess.getUserRoomId("user-1")).thenReturn("room-1");

        String choreName = "Clean kitchen";
        when(roomMetadataDao.removePendingReview("room-1", choreName)).thenReturn(true);

        interactor.executeCommand("git approve_request Clean kitchen");

        assertEquals("git approve_request Clean kitchen", presenter.lastCommand);
        assertEquals("Approved request for chore: Clean kitchen", presenter.lastOutput);
    }

    @Test
    void approveRequest_choreDoesNotExist_returnsDoesNotExistMessage() {
        User mockUser = mock(User.class);
        when(userService.getUser()).thenReturn(mockUser);
        when(mockUser.getId()).thenReturn("user-1");
        when(roomDataAccess.getUserRoomId("user-1")).thenReturn("room-1");

        String choreName = "Clean kitchen";
        when(roomMetadataDao.removePendingReview("room-1", choreName)).thenReturn(false);
        when(roomMetadataDao.getPendingReviews("room-1"))
                .thenReturn(List.of("Other chore"));

        interactor.executeCommand("git approve_request Clean kitchen");

        assertEquals("git approve_request Clean kitchen", presenter.lastCommand);
        assertEquals("This chore does not exist or is not yet pending review: Clean kitchen", presenter.lastOutput);
    }

    @Test
    void approveRequest_dbError_returnsDbErrorMessage() {
        User mockUser = mock(User.class);
        when(userService.getUser()).thenReturn(mockUser);
        when(mockUser.getId()).thenReturn("user-1");
        when(roomDataAccess.getUserRoomId("user-1")).thenReturn("room-1");

        String choreName = "Clean kitchen";
        when(roomMetadataDao.removePendingReview("room-1", choreName)).thenReturn(false);
        when(roomMetadataDao.getPendingReviews("room-1"))
                .thenReturn(List.of(choreName));

        interactor.executeCommand("git approve_request Clean kitchen");

        assertEquals("git approve_request Clean kitchen", presenter.lastCommand);
        assertEquals("Error connecting to database. Contact support.", presenter.lastOutput);
    }
}
