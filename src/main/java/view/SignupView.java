package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import interface_adapter.signup.SignupState;
import interface_adapter.signup.SignupViewModel;

/**
 * The view for joining or creating a room.
 */
@SuppressWarnings({ "checkstyle:ClassDataAbstractionCouplingCheck", "checkstyle:SuppressWarnings" })
public class SignupView extends JSplitPane implements ActionListener, PropertyChangeListener {
    private final String viewName = "sign up";

    private final SignupViewModel signupViewModel;
    private final JTextField usernameField = new JTextField(SignupViewModel.MAX_TEXT_FIELD_LENGTH);
    private final JTextField passwordField = new JTextField(SignupViewModel.MAX_TEXT_FIELD_LENGTH);
    private final JTextField repeatPasswordField = new JTextField(SignupViewModel.MAX_TEXT_FIELD_LENGTH);

    private final JButton loginButton;
    private final JButton signupButton;
    private final JButton cancelButton;

    /**
     * Constructs a SignupView with the given SignupViewModel.
     * 
     * @param signupViewModel the SignupViewModel
     */
    public SignupView(SignupViewModel signupViewModel) {
        super(JSplitPane.HORIZONTAL_SPLIT);
        this.signupViewModel = signupViewModel;
        // signupViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel(SignupViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final LabelTextPanel usernameInfo = new LabelTextPanel(
                new JLabel(SignupViewModel.USERNAME_LABEL), usernameField);
        final LabelTextPanel passwordInfo = new LabelTextPanel(
                new JLabel(SignupViewModel.PASSWORD_LABEL), passwordField);
        final LabelTextPanel repeatPasswordInfo = new LabelTextPanel(
                new JLabel(SignupViewModel.REPEAT_PASSWORD_LABEL), repeatPasswordField);

        signupButton = new JButton(SignupViewModel.SIGNUP_BUTTON_LABEL);

        final JPanel buttons = new JPanel();
        loginButton = new JButton(SignupViewModel.LOGIN_BUTTON_LABEL);
        buttons.add(loginButton);
        cancelButton = new JButton(SignupViewModel.CANCEL_BUTTON_LABEL);
        buttons.add(cancelButton);

        final JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.add(title);
        leftPanel.add(usernameInfo);
        leftPanel.add(passwordInfo);
        leftPanel.add(repeatPasswordInfo);
        leftPanel.add(buttons);

        final ImageLabel logoImage = new ImageLabel(SignupViewModel.LOGO_IMAGE_PATH,
                SignupViewModel.LOGO_IMAGE_WIDTH,
                SignupViewModel.LOGO_IMAGE_HEIGHT);

        final JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.add(logoImage);
        rightPanel.setBackground(new Color(252, 244, 235));

        addUsernameListener();
        addPasswordListener();
        addRepeatPasswordListener();

        loginButton.addActionListener(this);
        signupButton.addActionListener(this);
        cancelButton.addActionListener(this);

        this.setLeftComponent(leftPanel);
        this.setRightComponent(rightPanel);
        this.setResizeWeight(0.5);
        this.setDividerLocation(0.5);
        this.setContinuousLayout(true);
        this.setDividerSize(1);
        this.setBorder(null);

        this.setPreferredSize(new Dimension(SignupViewModel.VIEW_WIDTH, SignupViewModel.VIEW_HEIGHT));
    }

    @SuppressWarnings({ "checkstyle:AnonInnerLength", "checkstyle:SuppressWarnings" })
    private void addUsernameListener() {
        usernameField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final SignupState currentState = signupViewModel.getState();
                currentState.setUsername(usernameField.getText());
                signupViewModel.setState(currentState);
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
                final SignupState currentState = signupViewModel.getState();
                currentState.setPassword(new String(passwordField.getText()));
                signupViewModel.setState(currentState);
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
    private void addRepeatPasswordListener() {
        repeatPasswordField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final SignupState currentState = signupViewModel.getState();
                currentState.setRepeatPassword(new String(repeatPasswordField.getText()));
                signupViewModel.setState(currentState);
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
        final SignupState state = (SignupState) evt.getNewValue();
        if (state.getUsernameError() != null) {
            JOptionPane.showMessageDialog(this, state.getUsernameError());
        }
    }

    public String getViewName() {
        return viewName;
    }
}
