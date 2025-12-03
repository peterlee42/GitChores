package use_case.signup;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import use_case.exception.SignupFailedException;

/**
 * Tests for SignupInteractor to achieve 100% code coverage.
 */
@ExtendWith(MockitoExtension.class)
class SignupInteractorTest {

    @Mock
    private SignupOutputBoundary presenter;

    @Mock
    private SignupDataAccessInterface dataAccess;

    @Mock
    private SignupInputData input;

    private SignupInteractor interactor;

    @BeforeEach
    void setUp() {
        interactor = new SignupInteractor(presenter, dataAccess);
    }

    @Test
    void emptyFieldsTriggersError() {
        when(input.getUsername()).thenReturn("");
        when(input.getEmail()).thenReturn("a@b.com");
        when(input.getPassword()).thenReturn("123");
        when(input.getConfirmPassword()).thenReturn("123");

        interactor.execute(input);

        verify(presenter).prepareFailView("All fields must be non-empty.");
        verifyNoMoreInteractions(dataAccess);
    }

    @Test
    void passwordMismatchTriggersError() {
        when(input.getUsername()).thenReturn("john");
        when(input.getEmail()).thenReturn("a@b.com");
        when(input.getPassword()).thenReturn("123");
        when(input.getConfirmPassword()).thenReturn("456");

        interactor.execute(input);

        verify(presenter).prepareFailView("Passwords do not match.");
    }

    @Test
    void invalidEmailTriggersError() {
        when(input.getUsername()).thenReturn("john");
        when(input.getEmail()).thenReturn("invalid-email");
        when(input.getPassword()).thenReturn("123");
        when(input.getConfirmPassword()).thenReturn("123");

        interactor.execute(input);

        verify(presenter).prepareFailView("Invalid email address.");
    }

    @Test
    void successfulSignupCallsDataAccessAndPresenter() {
        when(input.getUsername()).thenReturn("john");
        when(input.getEmail()).thenReturn("john@example.com");
        when(input.getPassword()).thenReturn("pass");
        when(input.getConfirmPassword()).thenReturn("pass");

        interactor.execute(input);

        verify(dataAccess).createUser("john", "john@example.com", "pass");
        verify(presenter).prepareSuccessView(any(SignupOutputData.class));
    }

    @Test
    void dataAccessExceptionTriggersFailView() {
        when(input.getUsername()).thenReturn("john");
        when(input.getEmail()).thenReturn("john@example.com");
        when(input.getPassword()).thenReturn("pass");
        when(input.getConfirmPassword()).thenReturn("pass");

        doThrow(new SignupFailedException("DB error"))
                .when(dataAccess)
                .createUser(anyString(), anyString(), anyString());

        interactor.execute(input);

        verify(presenter).prepareFailView("DB error");
    }

    @Test
    void switchToLoginViewForwardsToPresenter() {
        interactor.switchToLoginView();
        verify(presenter).switchToLoginView();
    }

    @Test
    void validEmailReturnsTrue() {
        assertTrue(SignupInteractor.isValidEmail("test@example.com"));
    }

    @Test
    void invalidEmailReturnsFalse() {
        assertFalse(SignupInteractor.isValidEmail("not-an-email"));
    }

    @Test
    void signupInputDataStoresFieldsCorrectly() {
        SignupInputData realInput = new SignupInputData(
                "jane",
                "jane@example.com",
                "secret",
                "secret"
        );

        assertEquals("jane", realInput.getUsername());
        assertEquals("jane@example.com", realInput.getEmail());
        assertEquals("secret", realInput.getPassword());
        assertEquals("secret", realInput.getConfirmPassword());
    }

    @Test
    void signupInputDataHandlesEmptyStrings() {
        SignupInputData realInput = new SignupInputData("", "", "", "");

        assertEquals("", realInput.getUsername());
        assertEquals("", realInput.getEmail());
        assertEquals("", realInput.getPassword());
        assertEquals("", realInput.getConfirmPassword());
    }

    @Test
    void successfulSignupSendsCorrectOutputData() {
        when(input.getUsername()).thenReturn("john");
        when(input.getEmail()).thenReturn("john@example.com");
        when(input.getPassword()).thenReturn("pass");
        when(input.getConfirmPassword()).thenReturn("pass");

        interactor.execute(input);

        ArgumentCaptor<SignupOutputData> captor =
                ArgumentCaptor.forClass(SignupOutputData.class);

        verify(presenter).prepareSuccessView(captor.capture());
        assertEquals("john", captor.getValue().getUsername());
    }

}
