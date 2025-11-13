package view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;

/**
 * The Login View.
 */
public class LoginView extends JPanel implements ActionListener, PropertyChangeListener {
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

        addUsernameListener();
        addPasswordListener();

        loginButton.addActionListener(this);
        signupButton.addActionListener(this);
        cancelButton.addActionListener(this);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(title);
        this.add(usernameInfo);
        this.add(passwordInfo);
        this.add(buttons);
    }

    @SuppressWarnings({ "checkstyle:AnonInnerLength", "checkstyle:SuppressWarnings" })
    private void addUsernameListener() {
        usernameField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final LoginState currentState = loginViewModel.getState();
                currentState.setCurrentUsername(usernameField.getText());
                loginViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                documentListenerHelper();
            }
        });
    }

    @SuppressWarnings({ "checkstyle:AnonInnerLength", "checkstyle:SuppressWarnings" })
    private void addPasswordListener() {
        passwordField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final LoginState currentState = loginViewModel.getState();
                currentState.setPassword(new String(passwordField.getText()));
                loginViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                documentListenerHelper();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        JOptionPane.showMessageDialog(this, "Cancel not implemented yet.");
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final LoginState state = (LoginState) evt.getNewValue();
        if (state.getLoginError() != null) {
            JOptionPane.showMessageDialog(this, state.getLoginError());
        }
    }

    public String getViewName() {
        return viewName;
    }
}
