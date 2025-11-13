package interface_adapter.signup;

import interface_adapter.ViewModel;

public class SignupViewModel extends ViewModel<SignupState> {
    public static final String TITLE_LABEL = "Sign Up for GitChores";
    public static final String USERNAME_LABEL = "Choose username";
    public static final String PASSWORD_LABEL = "Choose password";
    public static final String REPEAT_PASSWORD_LABEL = "Enter password again";

    public static final String SIGNUP_BUTTON_LABEL = "Sign Up";
    public static final String CANCEL_BUTTON_LABEL = "Cancel";

    public static final String LOGIN_BUTTON_LABEL = "Go to Login";

    public static final int MAX_TEXT_FIELD_LENGTH = 20;

    /**
     * Constructor for a SignupViewModel.
     */
    public SignupViewModel() {
        super("signup");
        setState(new SignupState());
    }
}
