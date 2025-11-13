package interface_adapter.login;

import interface_adapter.ViewModel;

public class LoginViewModel extends ViewModel<LoginState> {
    public static final String TITLE_LABEL = "Login to GitChores";
    public static final String USERNAME_LABEL = "Enter username";
    public static final String PASSWORD_LABEL = "Enter password";

    public static final String SIGNUP_BUTTON_LABEL = "Sign Up";
    public static final String CANCEL_BUTTON_LABEL = "Cancel";
    public static final String LOGIN_BUTTON_LABEL = "Go to Login";

    public static final int MAX_TEXT_FIELD_LENGTH = 20;

    /**
     * Constructor for a SignupViewModel.
     */
    public LoginViewModel() {
        super("login");
        setState(new LoginState());
    }
}
