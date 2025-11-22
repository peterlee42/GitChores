package interface_adapter.login;

import java.awt.Insets;
import java.awt.Toolkit;

import interface_adapter.ViewModel;

public class LoginViewModel extends ViewModel<LoginState> {
    public static final String WELCOME_MESSAGE = "Welcome Back!";
    public static final String TITLE_LABEL = "Please login to continue";
    public static final String USERNAME_LABEL = "Username";
    public static final String PASSWORD_LABEL = "Password";

    public static final String LOGIN_BUTTON_LABEL = "Login";
    public static final String CANCEL_BUTTON_LABEL = "Cancel";

    public static final String SIGNUP_MESSAGE = "Don't have an account?";
    public static final String SIGNUP_BUTTON_LABEL = "Sign Up";

    public static final int MAX_TEXT_FIELD_LENGTH = 20;

    public static final String LOGO_IMAGE_PATH = "src/main/resources/logo.png";
    public static final int LOGO_IMAGE_WIDTH = (int) (Toolkit.getDefaultToolkit().getScreenSize().width * 0.15);
    public static final int LOGO_IMAGE_HEIGHT = (int) (Toolkit.getDefaultToolkit().getScreenSize().width * 0.15);

    public static final int VIEW_WIDTH = (int) (Toolkit.getDefaultToolkit().getScreenSize().width * 0.5);
    public static final int VIEW_HEIGHT = (int) (Toolkit.getDefaultToolkit().getScreenSize().height * 0.5);

    public static final double RESIZE_WEIGHT = 0.5;

    public static final Insets TEXT_FIELD_INSETS = new Insets(5, 5, 5, 5);
    public static final Insets LOGIN_MESSAGE_INSETS = new Insets(0, 0, 5, 0);

    /**
     * Constructor for a SignupViewModel.
     */
    public LoginViewModel() {
        super("login");
        setState(new LoginState());
    }
}
