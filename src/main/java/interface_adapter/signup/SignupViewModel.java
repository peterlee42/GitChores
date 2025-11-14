package interface_adapter.signup;

import java.awt.Font;
import java.awt.Insets;
import java.awt.Toolkit;

import interface_adapter.ViewModel;

public class SignupViewModel extends ViewModel<SignupState> {
    public static final String WELCOME_MESSAGE = "Hello!";
    public static final String TITLE_LABEL = "Please Sign Up to Continue";
    public static final String USERNAME_LABEL = "Username";
    public static final String PASSWORD_LABEL = "Password";
    public static final String REPEAT_PASSWORD_LABEL = "Confirm Password";

    public static final String SIGNUP_BUTTON_LABEL = "Sign Up";
    public static final String CANCEL_BUTTON_LABEL = "Cancel";

    public static final String LOGIN_MESSAGE = "Already have an account?";
    public static final String LOGIN_BUTTON_LABEL = "Login";

    public static final int MAX_TEXT_FIELD_LENGTH = 20;

    public static final String LOGO_IMAGE_PATH = "src/main/resources/logo.png";
    public static final int LOGO_IMAGE_WIDTH = (int) (Toolkit.getDefaultToolkit().getScreenSize().width * 0.15);
    public static final int LOGO_IMAGE_HEIGHT = (int) (Toolkit.getDefaultToolkit().getScreenSize().width * 0.15);

    public static final int VIEW_WIDTH = (int) (Toolkit.getDefaultToolkit().getScreenSize().width * 0.5);
    public static final int VIEW_HEIGHT = (int) (Toolkit.getDefaultToolkit().getScreenSize().height * 0.5);

    public static final Font LABEL_FONT = new Font("Inter", Font.PLAIN, 14);
    public static final Font WELCOME_FONT = new Font("Inter", Font.BOLD, 24);

    public static final double RESIZE_WEIGHT = 0.5;

    public static final Insets TEXT_FIELD_INSETS = new Insets(5, 5, 5, 5);
    public static final Insets LOGIN_MESSAGE_INSETS = new Insets(0, 0, 5, 0);

    /**
     * Constructor for a SignupViewModel.
     */
    public SignupViewModel() {
        super("signup");
        setState(new SignupState());
    }
}
