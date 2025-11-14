package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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

    private final JLabel welcomeMessage;
    private final JLabel loginMessage;

    private final ImageLabel logoImage;

    /**
     * Constructs a SignupView with the given SignupViewModel.
     * 
     * @param signupViewModel the SignupViewModel
     */
    public SignupView(SignupViewModel signupViewModel) {
        super(JSplitPane.HORIZONTAL_SPLIT);
        this.signupViewModel = signupViewModel;
        // signupViewModel.addPropertyChangeListener(this);

        welcomeMessage = new JLabel(SignupViewModel.WELCOME_MESSAGE);
        welcomeMessage.setFont(SignupViewModel.WELCOME_FONT);

        final JLabel title = new JLabel(SignupViewModel.TITLE_LABEL);
        title.setFont(SignupViewModel.LABEL_FONT);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JLabel usernameLabel = new JLabel(SignupViewModel.USERNAME_LABEL);
        usernameLabel.setFont(SignupViewModel.LABEL_FONT);
        final LabelTextPanel usernameInfo = new LabelTextPanel(usernameLabel, usernameField);
        usernameInfo.setBackground(Color.WHITE);

        final JLabel passwordLabel = new JLabel(SignupViewModel.PASSWORD_LABEL);
        passwordLabel.setFont(SignupViewModel.LABEL_FONT);
        final LabelTextPanel passwordInfo = new LabelTextPanel(passwordLabel, passwordField);
        passwordInfo.setBackground(Color.WHITE);

        final JLabel repeatPasswordLabel = new JLabel(SignupViewModel.REPEAT_PASSWORD_LABEL);
        repeatPasswordLabel.setFont(SignupViewModel.LABEL_FONT);
        final LabelTextPanel repeatPasswordInfo = new LabelTextPanel(repeatPasswordLabel, repeatPasswordField);
        repeatPasswordInfo.setBackground(Color.WHITE);

        final JPanel buttons = new JPanel();
        buttons.setBackground(Color.WHITE);
        buttons.setAlignmentX(Component.CENTER_ALIGNMENT);
        signupButton = new JButton(SignupViewModel.SIGNUP_BUTTON_LABEL);
        signupButton.setBackground(new Color(234, 89, 44));
        signupButton.setOpaque(true);
        signupButton.setBorderPainted(false);
        signupButton.setForeground(Color.WHITE);
        signupButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        buttons.add(signupButton);
        cancelButton = new JButton(SignupViewModel.CANCEL_BUTTON_LABEL);
        cancelButton.setBackground(new Color(234, 89, 44));
        cancelButton.setOpaque(true);
        cancelButton.setBorderPainted(false);
        cancelButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        cancelButton.setForeground(Color.WHITE);
        buttons.add(cancelButton);

        final JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridBagLayout());
        leftPanel.setBackground(Color.WHITE);

        final GridBagConstraints leftPanelConstraints = new GridBagConstraints();
        leftPanelConstraints.fill = GridBagConstraints.HORIZONTAL;
        leftPanelConstraints.insets = new Insets(10, 10, 10, 10);

        leftPanelConstraints.gridx = 0;
        leftPanelConstraints.gridy = 0;
        leftPanel.add(welcomeMessage, leftPanelConstraints);

        leftPanelConstraints.gridy = 1;
        leftPanel.add(title, leftPanelConstraints);

        leftPanelConstraints.gridy = 2;
        leftPanel.add(usernameInfo, leftPanelConstraints);

        leftPanelConstraints.gridy = 3;
        leftPanel.add(passwordInfo, leftPanelConstraints);

        leftPanelConstraints.gridy = 4;
        leftPanel.add(repeatPasswordInfo, leftPanelConstraints);

        leftPanelConstraints.gridy = 5;
        leftPanel.add(buttons, leftPanelConstraints);

        logoImage = new ImageLabel(SignupViewModel.LOGO_IMAGE_PATH,
                SignupViewModel.LOGO_IMAGE_WIDTH,
                SignupViewModel.LOGO_IMAGE_HEIGHT);

        loginMessage = new JLabel(SignupViewModel.LOGIN_MESSAGE);
        loginButton = new JButton(SignupViewModel.LOGIN_BUTTON_LABEL);
        loginButton.setBackground(new Color(234, 89, 44));
        loginButton.setOpaque(true);
        loginButton.setBorderPainted(false);
        loginButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        loginButton.setForeground(Color.WHITE);

        final JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(new Color(252, 244, 235));

        final GridBagConstraints logoConstraints = new GridBagConstraints();
        logoConstraints.fill = GridBagConstraints.HORIZONTAL;
        logoConstraints.gridx = 0;
        logoConstraints.gridy = 0;
        rightPanel.add(logoImage, logoConstraints);

        final GridBagConstraints loginMessageConstraints = new GridBagConstraints();
        loginMessageConstraints.gridx = 0;
        loginMessageConstraints.gridy = 1;
        loginMessageConstraints.insets = new Insets(0, 0, 10, 0);
        loginMessageConstraints.anchor = GridBagConstraints.CENTER;
        rightPanel.add(loginMessage, loginMessageConstraints);

        final GridBagConstraints loginButtonConstraint = new GridBagConstraints();
        loginButtonConstraint.gridx = 0;
        loginButtonConstraint.gridy = 2;
        loginButtonConstraint.anchor = GridBagConstraints.CENTER;
        rightPanel.add(loginButton, loginButtonConstraint);

        // Listeners
        addUsernameListener();
        addPasswordListener();
        addRepeatPasswordListener();

        loginButton.addActionListener(this);
        signupButton.addActionListener(this);
        cancelButton.addActionListener(this);

        this.setLeftComponent(leftPanel);
        this.setRightComponent(rightPanel);
        this.setResizeWeight(0.5);
        this.setDividerLocation(SignupViewModel.VIEW_WIDTH / 2);
        this.setContinuousLayout(true);
        this.setDividerSize(0);
        this.setBorder(null);

        this.setMinimumSize(new Dimension(SignupViewModel.VIEW_WIDTH, SignupViewModel.VIEW_HEIGHT));
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
