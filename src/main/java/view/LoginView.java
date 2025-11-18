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

import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;

/**
 * The Login View.
 */
@SuppressWarnings("checkstyle:ClassDataAbstractionCouplingCheck")
public class LoginView extends JSplitPane implements ActionListener, PropertyChangeListener {
    private final String viewName = "login";

    private final LoginViewModel loginViewModel;

    private final JPanel leftPanel;
    private final JPanel rightPanel;

    private final JTextField usernameField = new JTextField(LoginViewModel.MAX_TEXT_FIELD_LENGTH);
    private final JTextField passwordField = new JTextField(LoginViewModel.MAX_TEXT_FIELD_LENGTH);

    /**
     * Constructs a LoginView with the given LoginViewModel.
     * 
     * @param loginViewModel the LoginViewModel
     */
    public LoginView(LoginViewModel loginViewModel) {
        super(JSplitPane.HORIZONTAL_SPLIT);
        this.loginViewModel = loginViewModel;
        // loginViewModel.addPropertyChangeListener(this);

        leftPanel = buildLeftPanel(new JPanel());
        rightPanel = buildRightPanel(new JPanel());

        this.setLeftComponent(leftPanel);
        this.setRightComponent(rightPanel);
        this.setResizeWeight(LoginViewModel.RESIZE_WEIGHT);
        this.setDividerLocation(LoginViewModel.VIEW_WIDTH / 2);
        this.setContinuousLayout(true);
        this.setDividerSize(0);
        this.setBorder(null);

        this.setMinimumSize(new Dimension(LoginViewModel.VIEW_WIDTH, LoginViewModel.VIEW_HEIGHT));
    }

    @SuppressWarnings("checkstyle:ExecutableStatementCountCheck")
    private JPanel buildRightPanel(JPanel panel) {
        final JLabel welcomeMessage = new JLabel(LoginViewModel.WELCOME_MESSAGE);
        welcomeMessage.setFont(ViewConstants.WELCOME_FONT);

        final JLabel title = new JLabel(LoginViewModel.TITLE_LABEL);
        title.setFont(ViewConstants.LABEL_FONT);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JLabel usernameLabel = new JLabel(LoginViewModel.USERNAME_LABEL);
        usernameLabel.setFont(ViewConstants.LABEL_FONT);
        usernameField.setFont(ViewConstants.LABEL_FONT);
        final LabelTextPanel usernameInfo = new LabelTextPanel(usernameLabel, usernameField);
        usernameInfo.setBackground(Color.WHITE);

        final JLabel passwordLabel = new JLabel(LoginViewModel.PASSWORD_LABEL);
        passwordLabel.setFont(ViewConstants.LABEL_FONT);
        passwordField.setFont(ViewConstants.LABEL_FONT);
        final LabelTextPanel passwordInfo = new LabelTextPanel(passwordLabel, passwordField);
        passwordInfo.setBackground(Color.WHITE);

        final JPanel buttons = new JPanel();
        buttons.setBackground(Color.WHITE);

        final JButton loginButton = createButton(LoginViewModel.LOGIN_BUTTON_LABEL);
        buttons.add(loginButton);

        final JButton cancelButton = createButton(LoginViewModel.CANCEL_BUTTON_LABEL);
        buttons.add(cancelButton);

        panel.setLayout(new GridBagLayout());
        panel.setBackground(Color.WHITE);

        final GridBagConstraints panelConstraints = new GridBagConstraints();
        panelConstraints.fill = GridBagConstraints.HORIZONTAL;
        panelConstraints.insets = LoginViewModel.TEXT_FIELD_INSETS;

        final Component[] components = {
                welcomeMessage,
                title,
                usernameInfo,
                passwordInfo,
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

        loginButton.addActionListener(this);
        cancelButton.addActionListener(this);

        return panel;
    }

    private JPanel buildLeftPanel(JPanel panel) {
        final ImageLabel logoImage = new ImageLabel(LoginViewModel.LOGO_IMAGE_PATH,
                LoginViewModel.LOGO_IMAGE_WIDTH,
                LoginViewModel.LOGO_IMAGE_HEIGHT);

        final JLabel loginMessage = new JLabel(LoginViewModel.SIGNUP_MESSAGE);
        final JButton loginButton = createButton(LoginViewModel.SIGNUP_BUTTON_LABEL);

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
        loginMessageConstraints.insets = LoginViewModel.LOGIN_MESSAGE_INSETS;
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
                .setFont(ViewConstants.LABEL_FONT)
                .setBackground(ViewColors.ORANGE)
                .setForeground(Color.WHITE)
                .setBorder(LoginViewModel.DEFAULT_TEXT_FIELD_BORDER)
                .build();
        return button;
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
