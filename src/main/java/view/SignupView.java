package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
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
@SuppressWarnings("checkstyle:ClassDataAbstractionCouplingCheck")
public class SignupView extends JSplitPane implements ActionListener, PropertyChangeListener {
    private final String viewName = "sign up";

    private final SignupViewModel signupViewModel;

    private final JPanel leftPanel;
    private final JPanel rightPanel;

    private final JTextField usernameField = new JTextField(SignupViewModel.MAX_TEXT_FIELD_LENGTH);
    private final JTextField passwordField = new JTextField(SignupViewModel.MAX_TEXT_FIELD_LENGTH);
    private final JTextField repeatPasswordField = new JTextField(SignupViewModel.MAX_TEXT_FIELD_LENGTH);

    /**
     * Constructs a SignupView with the given SignupViewModel.
     * 
     * @param signupViewModel the SignupViewModel
     */
    public SignupView(SignupViewModel signupViewModel) {
        super(JSplitPane.HORIZONTAL_SPLIT);
        this.signupViewModel = signupViewModel;
        // signupViewModel.addPropertyChangeListener(this);

        leftPanel = buildLeftPanel(new JPanel());
        rightPanel = buildRightPanel(new JPanel());

        this.setLeftComponent(leftPanel);
        this.setRightComponent(rightPanel);
        this.setResizeWeight(SignupViewModel.RESIZE_WEIGHT);
        this.setDividerLocation(SignupViewModel.VIEW_WIDTH / 2);
        this.setContinuousLayout(true);
        this.setDividerSize(0);
        this.setBorder(null);

        this.setMinimumSize(new Dimension(SignupViewModel.VIEW_WIDTH, SignupViewModel.VIEW_HEIGHT));
    }

    @SuppressWarnings("checkstyle:ExecutableStatementCountCheck")
    private JPanel buildLeftPanel(JPanel panel) {
        final JLabel welcomeMessage = new JLabel(SignupViewModel.WELCOME_MESSAGE);
        welcomeMessage.setFont(SignupViewModel.WELCOME_FONT);

        final JLabel title = new JLabel(SignupViewModel.TITLE_LABEL);
        title.setFont(SignupViewModel.LABEL_FONT);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JLabel usernameLabel = new JLabel(SignupViewModel.USERNAME_LABEL);
        usernameLabel.setFont(SignupViewModel.LABEL_FONT);
        usernameField.setFont(SignupViewModel.LABEL_FONT);
        final LabelTextPanel usernameInfo = new LabelTextPanel(usernameLabel, usernameField);
        usernameInfo.setBackground(Color.WHITE);

        final JLabel passwordLabel = new JLabel(SignupViewModel.PASSWORD_LABEL);
        passwordLabel.setFont(SignupViewModel.LABEL_FONT);
        passwordField.setFont(SignupViewModel.LABEL_FONT);
        final LabelTextPanel passwordInfo = new LabelTextPanel(passwordLabel, passwordField);
        passwordInfo.setBackground(Color.WHITE);

        final JLabel repeatPasswordLabel = new JLabel(SignupViewModel.REPEAT_PASSWORD_LABEL);
        repeatPasswordLabel.setFont(SignupViewModel.LABEL_FONT);
        repeatPasswordField.setFont(SignupViewModel.LABEL_FONT);
        final LabelTextPanel repeatPasswordInfo = new LabelTextPanel(repeatPasswordLabel, repeatPasswordField);
        repeatPasswordInfo.setBackground(Color.WHITE);

        final JPanel buttons = new JPanel();
        buttons.setBackground(Color.WHITE);

        final JButton signupButton = createButton(SignupViewModel.SIGNUP_BUTTON_LABEL);
        buttons.add(signupButton);

        final JButton cancelButton = createButton(SignupViewModel.CANCEL_BUTTON_LABEL);
        buttons.add(cancelButton);

        panel.setLayout(new GridBagLayout());
        panel.setBackground(Color.WHITE);

        final GridBagConstraints panelConstraints = new GridBagConstraints();
        panelConstraints.fill = GridBagConstraints.HORIZONTAL;
        panelConstraints.insets = SignupViewModel.TEXT_FIELD_INSETS;

        final Component[] components = {
                welcomeMessage,
                title,
                usernameInfo,
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

        signupButton.addActionListener(this);
        cancelButton.addActionListener(this);

        return panel;
    }

    private JPanel buildRightPanel(JPanel panel) {
        final ImageLabel logoImage = new ImageLabel(SignupViewModel.LOGO_IMAGE_PATH,
                SignupViewModel.LOGO_IMAGE_WIDTH,
                SignupViewModel.LOGO_IMAGE_HEIGHT);

        final JLabel loginMessage = new JLabel(SignupViewModel.LOGIN_MESSAGE);
        final JButton loginButton = createButton(SignupViewModel.LOGIN_BUTTON_LABEL);

        panel.setLayout(new GridBagLayout());
        panel.setBackground(ViewColors.getSandBackground());

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

        loginButton.addActionListener(this);
        return panel;
    }

    private JButton createButton(String text) {
        final JButton button = new ButtonBuilder()
                .setText(text)
                .setFont(SignupViewModel.LABEL_FONT)
                .setBackground(ViewColors.getOrange())
                .setForeground(Color.WHITE)
                .setBorder(SignupViewModel.DEFAULT_TEXT_FIELD_BORDER)
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

    @SuppressWarnings("checkstyle:AnonInnerLength")
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
