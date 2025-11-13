package interface_adapter.signup;

import java.awt.Toolkit;

import interface_adapter.ViewModel;

public class SignupViewModel extends ViewModel<SignupState> {
    public static final String TITLE_LABEL = "Sign Up for GitChores";
    public static final String USERNAME_LABEL = "Username";
    public static final String PASSWORD_LABEL = "Password";
    public static final String REPEAT_PASSWORD_LABEL = "Confirm Password";

    public static final String SIGNUP_BUTTON_LABEL = "Sign Up";
    public static final String CANCEL_BUTTON_LABEL = "Cancel";

    public static final String LOGIN_BUTTON_LABEL = "Sign In";

    public static final int MAX_TEXT_FIELD_LENGTH = 20;

    public static final String LOGO_IMAGE_PATH = "src/main/resources/logo.png";
    public static final int LOGO_IMAGE_WIDTH = (int) (Toolkit.getDefaultToolkit().getScreenSize().width * 0.2);
    public static final int LOGO_IMAGE_HEIGHT = (int) (Toolkit.getDefaultToolkit().getScreenSize().width * 0.2);

    public static final int VIEW_WIDTH = (int) (Toolkit.getDefaultToolkit().getScreenSize().width * 0.6);
    public static final int VIEW_HEIGHT = (int) (Toolkit.getDefaultToolkit().getScreenSize().height * 0.6);

    /**
     * Constructor for a SignupViewModel.
     */
    public SignupViewModel() {
        super("signup");
        setState(new SignupState());
    }
}
