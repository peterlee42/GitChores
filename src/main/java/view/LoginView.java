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

import interface_adapter.login.LoginController;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;

/**
 * The Login View.
 */
@SuppressWarnings("checkstyle:ClassDataAbstractionCouplingCheck")
public class LoginView extends JSplitPane implements ActionListener, PropertyChangeListener {
    private final String viewName = "login";

    private final LoginViewModel loginViewModel;
    private LoginController loginController;

    private final JPanel leftPanel;
    private final JPanel rightPanel;

    private final JTextField usernameField = new JTextField(LoginViewModel.MAX_TEXT_FIELD_LENGTH);
    private final JTextField passwordField = new JTextField(LoginViewModel.MAX_TEXT_FIELD_LENGTH);

    private final JLabel welcomeMessage;
    private final JLabel title;
    private final JLabel usernameLabel;
    private final JLabel passwordLabel;
    private final JLabel signupMessage;

    private final JButton loginButton;
    private final JButton signupButton;

    private final ImageLabel logoImage;

    /**
     * Constructs a LoginView with the given LoginViewModel.
     * 
     * @param loginViewModel the LoginViewModel
     */
    public LoginView(LoginViewModel loginViewModel) {
        super(JSplitPane.HORIZONTAL_SPLIT);
        this.loginViewModel = loginViewModel;
        // loginViewModel.addPropertyChangeListener(this);

        // initialize components
        welcomeMessage = new JLabel(LoginViewModel.WELCOME_MESSAGE);
        title = new JLabel(LoginViewModel.TITLE_LABEL);
        usernameLabel = new JLabel(LoginViewModel.USERNAME_LABEL);
        passwordLabel = new JLabel(LoginViewModel.PASSWORD_LABEL);
        loginButton = createButton(LoginViewModel.LOGIN_BUTTON_LABEL);
        logoImage = new ImageLabel(LoginViewModel.LOGO_IMAGE_PATH,
                LoginViewModel.LOGO_IMAGE_WIDTH,
                LoginViewModel.LOGO_IMAGE_HEIGHT);
        // build right panel
        rightPanel = buildRightPanel(new JPanel());

        // initialize components
        signupMessage = new JLabel(LoginViewModel.SIGNUP_MESSAGE);
        signupButton = createButton(LoginViewModel.SIGNUP_BUTTON_LABEL);
        // build left panel
        leftPanel = buildLeftPanel(new JPanel());

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
        welcomeMessage.setFont(ViewConstants.WELCOME_FONT);

        title.setFont(ViewConstants.LABEL_FONT);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        usernameLabel.setFont(ViewConstants.LABEL_FONT);
        usernameField.setFont(ViewConstants.LABEL_FONT);

        final LabelTextPanel usernameInfo = new LabelTextPanel(usernameLabel, usernameField);
        usernameInfo.setBackground(Color.WHITE);

        passwordLabel.setFont(ViewConstants.LABEL_FONT);
        passwordField.setFont(ViewConstants.LABEL_FONT);
        final LabelTextPanel passwordInfo = new LabelTextPanel(passwordLabel, passwordField);
        passwordInfo.setBackground(Color.WHITE);

        final JPanel buttons = new JPanel(new GridBagLayout());
        final GridBagConstraints btnConstraints = new GridBagConstraints();
        btnConstraints.fill = GridBagConstraints.HORIZONTAL;
        btnConstraints.weightx = 1.0;
        buttons.setBackground(Color.WHITE);
        buttons.add(loginButton, btnConstraints);

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

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginController.execute(usernameField.getText().strip(), passwordField.getText().strip());
            }
        });

        return panel;
    }

    private JPanel buildLeftPanel(JPanel panel) {

        panel.setLayout(new GridBagLayout());
        panel.setBackground(ViewColors.SAND_BACKGROUND);

        final GridBagConstraints logoConstraints = new GridBagConstraints();
        logoConstraints.fill = GridBagConstraints.HORIZONTAL;
        logoConstraints.gridx = 0;
        logoConstraints.gridy = 0;
        panel.add(logoImage, logoConstraints);
        final GridBagConstraints signupMessageConstraints = new GridBagConstraints();
        signupMessageConstraints.gridx = 0;
        signupMessageConstraints.gridy = 1;
        signupMessageConstraints.insets = LoginViewModel.SIGNUP_MESSAGE_INSETS;
        signupMessageConstraints.anchor = GridBagConstraints.CENTER;
        panel.add(signupMessage, signupMessageConstraints);

        final GridBagConstraints signupButtonConstraint = new GridBagConstraints();
        signupButtonConstraint.gridx = 0;
        signupButtonConstraint.gridy = 2;
        signupButtonConstraint.anchor = GridBagConstraints.CENTER;
        panel.add(signupButton, signupButtonConstraint);

        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginController.switchToSignupView();
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

    @SuppressWarnings({ "checkstyle:AnonInnerLength", "checkstyle:SuppressWarnings" })
    private void addUsernameListener() {
        usernameField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final LoginState currentState = loginViewModel.getState();
                currentState.setUsername(usernameField.getText());
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

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }
}
