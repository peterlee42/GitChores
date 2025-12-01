package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupState;
import interface_adapter.signup.SignupViewModel;

/**
 * The view for joining or creating a room.
 */
@SuppressWarnings("checkstyle:ClassDataAbstractionCouplingCheck")
public class SignupView extends JSplitPane implements ActionListener, PropertyChangeListener {
    private final String viewName = "signup";

    private SignupController signupController;
    private final SignupViewModel signupViewModel;

    private final JPanel leftPanel;
    private final JPanel rightPanel;

    private final JTextField usernameField = new JTextField(SignupViewModel.MAX_TEXT_FIELD_LENGTH);
    private final JTextField emailField = new JTextField(SignupViewModel.MAX_TEXT_FIELD_LENGTH);
    private final JPasswordField passwordField = new JPasswordField(SignupViewModel.MAX_TEXT_FIELD_LENGTH);
    private final JPasswordField repeatPasswordField = new JPasswordField(SignupViewModel.MAX_TEXT_FIELD_LENGTH);

    private final JLabel title;
    private final JLabel welcomeMessage;
    private final JLabel usernameLabel;
    private final JLabel emailLabel;
    private final JLabel passwordLabel;
    private final JLabel repeatPasswordLabel;
    private final JLabel loginMessage;

    private final JButton signupButton;
    private final JButton loginButton;

    private final ImageLabel logoImage;

    /**
     * Constructs a SignupView with the given SignupViewModel.
     * 
     * @param signupViewModel the SignupViewModel
     */
    public SignupView(SignupViewModel signupViewModel) {
        super(JSplitPane.HORIZONTAL_SPLIT);
        this.signupViewModel = signupViewModel;
        signupViewModel.addPropertyChangeListener(this);

        // initialize components
        welcomeMessage = new JLabel(SignupViewModel.WELCOME_MESSAGE);
        title = new JLabel(SignupViewModel.TITLE_LABEL);
        usernameLabel = new JLabel(SignupViewModel.USERNAME_LABEL);
        emailLabel = new JLabel(SignupViewModel.EMAIL_LABEL);
        passwordLabel = new JLabel(SignupViewModel.PASSWORD_LABEL);
        repeatPasswordLabel = new JLabel(SignupViewModel.REPEAT_PASSWORD_LABEL);

        signupButton = createButton(SignupViewModel.SIGNUP_BUTTON_LABEL);

        leftPanel = buildLeftPanel(new JPanel());

        // initialize components
        logoImage = new ImageLabel(SignupViewModel.LOGO_IMAGE_PATH,
                SignupViewModel.LOGO_IMAGE_WIDTH,
                SignupViewModel.LOGO_IMAGE_HEIGHT);

        loginMessage = new JLabel(SignupViewModel.LOGIN_MESSAGE);
        loginButton = createButton(SignupViewModel.LOGIN_BUTTON_LABEL);

        rightPanel = buildRightPanel(new JPanel());

        this.setLeftComponent(leftPanel);
        this.setRightComponent(rightPanel);
        this.setResizeWeight(SignupViewModel.RESIZE_WEIGHT);
        this.setDividerLocation(SignupViewModel.VIEW_WIDTH / 2);
        this.setContinuousLayout(true);
        this.setDividerSize(0);
        this.setBorder(null);
    }

    @SuppressWarnings("checkstyle:ExecutableStatementCountCheck")
    private JPanel buildLeftPanel(JPanel panel) {
        welcomeMessage.setFont(ViewConstants.WELCOME_FONT);

        title.setFont(ViewConstants.LABEL_FONT);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        usernameLabel.setFont(ViewConstants.LABEL_FONT);
        usernameField.setFont(ViewConstants.LABEL_FONT);

        final LabelTextPanel usernameInfo = new LabelTextPanel(usernameLabel, usernameField);
        usernameInfo.setBackground(Color.WHITE);

        emailLabel.setFont(ViewConstants.LABEL_FONT);
        emailField.setFont(ViewConstants.LABEL_FONT);
        final LabelTextPanel emailInfo = new LabelTextPanel(emailLabel, emailField);
        emailInfo.setBackground(Color.WHITE);

        passwordLabel.setFont(ViewConstants.LABEL_FONT);
        passwordField.setFont(ViewConstants.LABEL_FONT);

        final LabelTextPanel passwordInfo = new LabelTextPanel(passwordLabel, passwordField);
        passwordInfo.setBackground(Color.WHITE);

        repeatPasswordLabel.setFont(ViewConstants.LABEL_FONT);
        repeatPasswordField.setFont(ViewConstants.LABEL_FONT);

        final LabelTextPanel repeatPasswordInfo = new LabelTextPanel(repeatPasswordLabel, repeatPasswordField);
        repeatPasswordInfo.setBackground(Color.WHITE);

        final JPanel buttons = new JPanel(new GridBagLayout());
        final GridBagConstraints btnConstraints = new GridBagConstraints();
        btnConstraints.fill = GridBagConstraints.HORIZONTAL;
        btnConstraints.weightx = 1.0;
        buttons.setBackground(Color.WHITE);
        buttons.add(signupButton, btnConstraints);

        panel.setLayout(new GridBagLayout());
        panel.setBackground(Color.WHITE);

        final GridBagConstraints panelConstraints = new GridBagConstraints();
        panelConstraints.fill = GridBagConstraints.HORIZONTAL;
        panelConstraints.insets = SignupViewModel.TEXT_FIELD_INSETS;

        final Component[] components = {
                welcomeMessage,
                title,
                usernameInfo,
                emailInfo,
                passwordInfo,
                repeatPasswordInfo,
                buttons,
        };

        for (Component component : components) {
            panelConstraints.gridx = 0;
            panelConstraints.gridy++;
            panel.add(component, panelConstraints);
        }

        // Listeners
        addUsernameListener();
        addPasswordListener();
        addRepeatPasswordListener();
        addEmailListener();

        signupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                signupController.execute(
                        usernameField.getText().strip(),
                        emailField.getText().strip(),
                        String.valueOf(passwordField.getPassword()).strip(),
                        String.valueOf(repeatPasswordField.getPassword()).strip());
            }
        });

        return panel;
    }

    private JPanel buildRightPanel(JPanel panel) {
        panel.setLayout(new GridBagLayout());
        panel.setBackground(ViewColors.SAND_BACKGROUND);

        final GridBagConstraints logoConstraints = new GridBagConstraints();
        logoConstraints.fill = GridBagConstraints.HORIZONTAL;
        logoConstraints.gridx = 0;
        logoConstraints.gridy = 0;
        panel.add(logoImage, logoConstraints);
        final GridBagConstraints loginMessageConstraints = new GridBagConstraints();
        loginMessageConstraints.gridx = 0;
        loginMessageConstraints.gridy = 1;
        loginMessageConstraints.insets = SignupViewModel.LOGIN_MESSAGE_INSETS;
        loginMessageConstraints.anchor = GridBagConstraints.CENTER;
        panel.add(loginMessage, loginMessageConstraints);

        final GridBagConstraints loginButtonConstraint = new GridBagConstraints();
        loginButtonConstraint.gridx = 0;
        loginButtonConstraint.gridy = 2;
        loginButtonConstraint.anchor = GridBagConstraints.CENTER;
        panel.add(loginButton, loginButtonConstraint);

        // Action Listeners
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                signupController.switchToLoginView();
            }
        });
        return panel;
    }

    private JButton createButton(String text) {
        final JButton button = new ButtonBuilder()
                .setText(text)
                .setFont(ViewConstants.LABEL_FONT)
                .setBackground(ViewColors.ORANGE)
                .setForeground(Color.WHITE)
                .build();
        return button;
    }

    @SuppressWarnings("checkstyle:AnonInnerLength")
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

    @SuppressWarnings("checkstyle:AnonInnerLength")
    private void addPasswordListener() {
        passwordField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final SignupState currentState = signupViewModel.getState();
                currentState.setPassword(String.valueOf(passwordField.getPassword()).strip());
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

    @SuppressWarnings("checkstyle:AnonInnerLength")
    private void addRepeatPasswordListener() {
        repeatPasswordField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final SignupState currentState = signupViewModel.getState();
                currentState.setRepeatPassword(String.valueOf(repeatPasswordField.getPassword()).strip());
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

    @SuppressWarnings("checkstyle:AnonInnerLength")
    private void addEmailListener() {
        emailField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final SignupState currentState = signupViewModel.getState();
                currentState.setEmail(emailField.getText().strip());
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
        JOptionPane.showMessageDialog(this, "Not implemented yet.");
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final SignupState state = signupViewModel.getState();
        if (state.getSignupError() != null) {
            JOptionPane.showMessageDialog(this, state.getSignupError());
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setSignupController(SignupController signupController) {
        this.signupController = signupController;
    }
}
