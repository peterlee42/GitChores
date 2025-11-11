package view;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.*;

public class ProfileView extends JPanel {

    private final String viewName = "profile";

    private final JLabel titleLabel;
    private final JLabel usernameLabel;
    private final JTextField usernameField;
    private final JLabel emailLabel;
    private final JTextField emailField;
    private final JButton saveButton;
    private final JButton backButton;

    public ProfileView() {
        // title
        titleLabel = new JLabel("Profile");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // fields
        usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(Constants.TEXT_FIELD_COLUMNS);

        emailLabel = new JLabel("Email:");
        emailField = new JTextField(Constants.TEXT_FIELD_COLUMNS);

        saveButton = new JButton("Save");
        backButton = new JButton("Back");

        // form panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

        JPanel userRow = new JPanel();
        userRow.add(usernameLabel);
        userRow.add(usernameField);

        JPanel emailRow = new JPanel();
        emailRow.add(emailLabel);
        emailRow.add(emailField);

        JPanel buttonsRow = new JPanel();
        buttonsRow.add(backButton);
        buttonsRow.add(saveButton);

        formPanel.add(userRow);
        formPanel.add(emailRow);
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        formPanel.add(buttonsRow);

        // main layout
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(titleLabel);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(formPanel);
    }

    public String getViewName() {
        return viewName;
    }
}
