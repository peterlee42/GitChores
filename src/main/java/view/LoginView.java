package view;

import java.awt.Component;

import javax.swing.*;

import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;

public class LoginView extends JPanel {
    private final String viewName = "login";

    private final LoginViewModel loginViewModel;
    private final JTextField usernameField = new JTextField(LoginViewModel.MAX_TEXT_FIELD_LENGTH);
    private final JTextField passwordField = new JTextField(LoginViewModel.MAX_TEXT_FIELD_LENGTH);

    private final JButton loginButton;
    private final JButton signupButton;
    private final JButton cancelButton;

    /**
     * Constructs a LoginView with the given LoginViewModel.
     * 
     * @param loginViewModel the LoginViewModel
     */
    public LoginView(LoginViewModel loginViewModel) {
        this.loginViewModel = loginViewModel;

        final JLabel title = new JLabel(LoginViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final LabelTextPanel usernameInfo = new LabelTextPanel(
                new JLabel(LoginViewModel.USERNAME_LABEL), usernameField);
        final LabelTextPanel passwordInfo = new LabelTextPanel(
                new JLabel(LoginViewModel.PASSWORD_LABEL), passwordField);

        final JPanel buttons = new JPanel();
        loginButton = new JButton(LoginViewModel.LOGIN_BUTTON_LABEL);
        buttons.add(loginButton);
        signupButton = new JButton(LoginViewModel.SIGNUP_BUTTON_LABEL);
        buttons.add(signupButton);
        cancelButton = new JButton(LoginViewModel.CANCEL_BUTTON_LABEL);
        buttons.add(cancelButton);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(title);
        this.add(usernameInfo);
        this.add(passwordInfo);
        this.add(buttons);
    }
}
