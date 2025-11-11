package view;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.*;

/**
 * View for displaying and editing the user's profile.
 */
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
        // Title
        titleLabel = new JLabel("Profile");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Fields
        usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(Constants.TEXT_FIELD_COLUMNS);

        emailLabel = new JLabel("Email:");
        emailField = new JTextField(Constants.TEXT_FIELD_COLUMNS);

        saveButton = new JButton("Save");
        backButton = new JButton("Back");

        // Rows
        JPanel usernameRow = new JPanel();
        usernameRow.add(usernameLabel);
        usernameRow.add(usernameField);

        JPanel emailRow = new JPanel();
        emailRow.add(emailLabel);
        emailRow.add(emailField);

        JPanel buttonsRow = new JPanel();
        buttonsRow.add(backButton);
        buttonsRow.add(saveButton);

        // Layout for the whole view
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(titleLabel);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(usernameRow);
        add(emailRow);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(buttonsRow);
    }

    public String getViewName() {
        return viewName;
    }
}
