package use_case.login;

import entity.Token;
import entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import use_case.exception.LoginFailedException;
import use_case.room.RoomDataAccessInterface;
import use_case.session.SessionDataAccessInterface;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginInteractorTest {

    @Mock
    private LoginOutputBoundary presenter;

    @Mock
    private LoginDataAccessInterface userDataAccess;

    @Mock
    private SessionDataAccessInterface sessionDataAccess;

    @Mock
    private RoomDataAccessInterface roomDataAccess;

    @Mock
    private LoginInputData inputData;

    private LoginInteractor interactor;

    @BeforeEach
    void setUp() {
        interactor = new LoginInteractor(
                presenter,
                userDataAccess,
                sessionDataAccess,
                roomDataAccess);
    }

    @Test
    void emptyUsernameTriggersError() {
        when(inputData.getUsername()).thenReturn("");
        when(inputData.getPassword()).thenReturn("pass");

        interactor.execute(inputData);

        verify(presenter).prepareFailView("Username and password cannot be empty.");
    }

    @Test
    void emptyPasswordTriggersError() {
        when(inputData.getUsername()).thenReturn("peter");
        when(inputData.getPassword()).thenReturn("");

        interactor.execute(inputData);

        verify(presenter).prepareFailView("Username and password cannot be empty.");
    }

    @Test
    void nullUsernameTriggersError() {
        when(inputData.getUsername()).thenReturn(null);
        when(inputData.getPassword()).thenReturn("pass");

        interactor.execute(inputData);

        verify(presenter).prepareFailView("Username and password cannot be empty.");
    }

    @Test
    void nullPasswordTriggersError() {
        when(inputData.getUsername()).thenReturn("peter");
        when(inputData.getPassword()).thenReturn(null);

        interactor.execute(inputData);

        verify(presenter).prepareFailView("Username and password cannot be empty.");
    }

    @Test
    void successfulLoginWhenUserInRoom() {
        when(inputData.getUsername()).thenReturn("peter");
        when(inputData.getPassword()).thenReturn("pass");

        final Token token = new Token("t1", "access", "refresh");
        when(userDataAccess.login("peter", "pass")).thenReturn(token);

        final User user = new User("u1", "peter", "peter@example.com");
        when(userDataAccess.getCurrentUser(token)).thenReturn(user);

        when(roomDataAccess.getUserRoomId("u1")).thenReturn("room-1");

        interactor.execute(inputData);

        verify(sessionDataAccess).setCurrentToken(token);
        final ArgumentCaptor<LoginOutputData> captor = ArgumentCaptor.forClass(LoginOutputData.class);
        verify(presenter).prepareSuccessView(captor.capture());
        final LoginOutputData actual = captor.getValue();

        assertEquals("peter", actual.getUsername());
        assertTrue(actual.isInRoom());
    }

    @Test
    void successfulLoginWhenUserNotInRoom() {
        when(inputData.getUsername()).thenReturn("peter");
        when(inputData.getPassword()).thenReturn("pass");

        final Token token = new Token("t1", "access", "refresh");
        when(userDataAccess.login("peter", "pass")).thenReturn(token);

        final User user = new User("u1", "peter", "peter@example.com");
        when(userDataAccess.getCurrentUser(token)).thenReturn(user);

        when(roomDataAccess.getUserRoomId("u1")).thenReturn(null);

        interactor.execute(inputData);

        verify(sessionDataAccess).setCurrentToken(token);
        final ArgumentCaptor<LoginOutputData> captor = ArgumentCaptor.forClass(LoginOutputData.class);
        verify(presenter).prepareSuccessView(captor.capture());
        final LoginOutputData actual = captor.getValue();

        assertEquals("peter", actual.getUsername());
        assertFalse(actual.isInRoom());
    }

    @Test
    void loginFailureTriggersFailView() {
        when(inputData.getUsername()).thenReturn("peter");
        when(inputData.getPassword()).thenReturn("pass");

        doThrow(new LoginFailedException("Invalid credentials"))
                .when(userDataAccess)
                .login(anyString(), anyString());

        interactor.execute(inputData);

        verify(presenter).prepareFailView("Invalid credentials");
    }

    @Test
    void switchToSignupViewForwardsToPresenter() {
        interactor.switchToSignupView();
        verify(presenter).switchToSignupView();
    }

    @Test
    void loginInputDataStoresUsernameAndPassword() {
        LoginInputData data = new LoginInputData("alice", "secret");
        assertEquals("alice", data.getUsername());
        assertEquals("secret", data.getPassword());
    }

    @Test
    void loginInputDataHandlesEmptyValues() {
        LoginInputData data = new LoginInputData("", "");
        assertEquals("", data.getUsername());
        assertEquals("", data.getPassword());
    }
}
