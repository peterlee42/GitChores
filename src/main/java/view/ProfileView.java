package view;

import java.awt.BorderLayout;
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
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import interface_adapter.profile.ProfileController;
import interface_adapter.profile.ProfileState;
import interface_adapter.profile.ProfileViewModel;

/**
 * Profile screen that displays basic user information, allows selecting a
 * profile photo, and provides navigation actions.
 */
@SuppressWarnings("checkstyle:ClassDataAbstractionCouplingCheck")
public class ProfileView extends JPanel implements ActionListener, PropertyChangeListener {

    private ProfileController profileController;

    private final JLabel usernameValueLabel;
    private final JLabel emailValueLabel;
    private final JLabel messageLabel;

    private final JButton logoutButton;
    private final JButton saveButton;
    private final JButton leaveRoomButton;
    private final JButton changePhotoButton;

    private JLabel profilePhotoLabel;
    private String profilePhotoPath;

    private final ProfileViewModel profileViewModel;

    /**
     * Constructs a profile view.
     *
     * @param profileViewModel view model for this view
     */
    public ProfileView(ProfileViewModel profileViewModel) {

        this.profileViewModel = profileViewModel;

        this.usernameValueLabel = new JLabel("");
        this.emailValueLabel = new JLabel("");
        this.messageLabel = new JLabel("");

        this.logoutButton = createButton(ViewConstants.LOGOUT_BUTTON_TEXT);
        this.saveButton = createButton(ViewConstants.SAVE_BUTTON_TEXT);
        this.leaveRoomButton = createButton(ViewConstants.LEAVE_ROOM_BUTTON_TEXT);
        this.changePhotoButton = createButton(ViewConstants.CHANGE_PHOTO_BUTTON_TEXT);

        messageLabel.setFont(ViewConstants.LABEL_FONT);
        messageLabel.setForeground(ViewColors.DARK_BLUE);
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        initializeLayout();
        initializeListeners();
    }

    /**
     * Sets up the layout and adds all components.
     */
    private void initializeLayout() {

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        final JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(ViewColors.SAND_BACKGROUND);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(
                ViewConstants.DASHBOARD_PANEL_PADDING,
                ViewConstants.DASHBOARD_PANEL_PADDING,
                ViewConstants.DASHBOARD_PANEL_PADDING,
                ViewConstants.DASHBOARD_PANEL_PADDING));

        final GridBagConstraints constraint = new GridBagConstraints();
        constraint.fill = GridBagConstraints.HORIZONTAL;
        constraint.insets = new Insets(ViewConstants.DASHBOARD_COMPONENT_SPACING / 2, 0,
                ViewConstants.DASHBOARD_COMPONENT_SPACING / 2, 0);
        constraint.gridx = 0;
        constraint.gridy = 0;

        addTitleSection(mainPanel, constraint);

        constraint.gridy++;
        mainPanel.add(Box.createVerticalStrut(ViewConstants.SPACING_20), constraint);

        constraint.gridy++;
        mainPanel.add(buildProfileSection(), constraint);

        constraint.gridy++;
        mainPanel.add(Box.createVerticalStrut(ViewConstants.SPACING_20), constraint);

        constraint.gridy++;
        mainPanel.add(buildButtonSection(), constraint);

        constraint.gridy++;
        mainPanel.add(messageLabel, constraint);

        add(mainPanel, BorderLayout.CENTER);
    }

    /**
     * Adds the title label to the view.
     * 
     * @param mainPanel  main panel to add the title to
     * @param constraint layout constraints
     */
    private void addTitleSection(JPanel mainPanel, GridBagConstraints constraint) {
        final JLabel titleLabel = new JLabel(ViewConstants.PROFILE_TITLE_TEXT);
        titleLabel.setFont(ViewConstants.HEADER_FONT);
        titleLabel.setForeground(ViewColors.DARK_BLUE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(titleLabel, constraint);
    }

    /**
     * Adds the upper content: photo on the left, user info on the right.
     * 
     * @return panel containing the profile section
     */
    private JPanel buildProfileSection() {

        final JPanel section = new JPanel();
        section.setLayout(new BoxLayout(section, BoxLayout.X_AXIS));
        section.setBackground(Color.WHITE);

        section.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(
                        ViewConstants.BORDER_COLOR,
                        ViewConstants.BORDER_COLOR,
                        ViewConstants.BORDER_COLOR), ViewConstants.BORDER_WIDTH),
                BorderFactory.createEmptyBorder(
                        ViewConstants.SPACING_20,
                        ViewConstants.SPACING_20,
                        ViewConstants.SPACING_20,
                        ViewConstants.SPACING_20)));

        section.add(createPhotoColumn());
        section.add(Box.createHorizontalStrut(ViewConstants.SPACING_20));
        section.add(createInfoColumn());

        return section;
    }

    /**
     * Creates the left column with the profile photo and change-photo button.
     *
     * @return panel containing the photo UI
     */
    private JPanel createPhotoColumn() {
        final JPanel column = new JPanel();
        column.setBackground(Color.WHITE);
        column.setLayout(new BoxLayout(column, BoxLayout.Y_AXIS));

        profilePhotoLabel = new JLabel("No photo");
        profilePhotoLabel.setFont(ViewConstants.LABEL_FONT);
        profilePhotoLabel.setForeground(ViewColors.DARK_BLUE);
        profilePhotoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        profilePhotoLabel.setHorizontalAlignment(JLabel.CENTER);
        profilePhotoLabel.setVerticalAlignment(JLabel.CENTER);

        final Dimension photoSize = new Dimension(
                ViewConstants.PROFILE_PHOTO_WIDTH,
                ViewConstants.PROFILE_PHOTO_HEIGHT);

        profilePhotoLabel.setPreferredSize(photoSize);
        profilePhotoLabel.setMinimumSize(photoSize);
        profilePhotoLabel.setMaximumSize(photoSize);

        profilePhotoLabel.setBorder(
                BorderFactory.createLineBorder(ViewColors.DARK_BLUE, ViewConstants.PROFILE_PHOTO_BORDER_THICKNESS,
                        true));

        changePhotoButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        column.add(profilePhotoLabel);
        column.add(Box.createVerticalStrut(ViewConstants.SPACING_15));
        column.add(changePhotoButton);

        return column;
    }

    /**
     * Creates the right column with username and email labels.
     *
     * @return panel containing the user info UI
     */
    private JPanel createInfoColumn() {
        final JPanel column = new JPanel();
        column.setBackground(Color.WHITE);
        column.setLayout(new BoxLayout(column, BoxLayout.Y_AXIS));

        final JLabel usernameLabel = new JLabel(ViewConstants.USERNAME_LABEL_TEXT);
        usernameLabel.setFont(ViewConstants.LABEL_FONT);
        usernameLabel.setForeground(ViewColors.DARK_BLUE);

        final JLabel emailLabel = new JLabel(ViewConstants.EMAIL_LABEL_TEXT);
        emailLabel.setFont(ViewConstants.LABEL_FONT);
        emailLabel.setForeground(ViewColors.DARK_BLUE);

        usernameValueLabel.setFont(ViewConstants.LABEL_FONT);
        emailValueLabel.setFont(ViewConstants.LABEL_FONT);

        usernameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        usernameValueLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        emailLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        emailValueLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        column.add(usernameLabel);
        column.add(Box.createVerticalStrut(ViewConstants.SPACING_5));
        column.add(usernameValueLabel);
        column.add(Box.createVerticalStrut(ViewConstants.SPACING_15));
        column.add(emailLabel);
        column.add(Box.createVerticalStrut(ViewConstants.SPACING_5));
        column.add(emailValueLabel);

        return column;
    }

    /**
     * Adds the bottom row with Back, Save, and Leave Room buttons.
     * 
     * @return panel containing the buttons
     */
    private JPanel buildButtonSection() {
        final JPanel section = new JPanel();
        section.setBackground(Color.WHITE);
        section.setLayout(new BoxLayout(section, BoxLayout.X_AXIS));

        logoutButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        leaveRoomButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        section.add(logoutButton);
        section.add(Box.createHorizontalStrut(ViewConstants.SPACING_20));
        section.add(saveButton);
        section.add(Box.createHorizontalStrut(ViewConstants.SPACING_20));
        section.add(leaveRoomButton);

        return section;
    }

    /**
     * Sets up listeners for the buttons.
     */
    private void initializeListeners() {
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent event) {
                profileController.logout();
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent event) {
                handleSave(event);
                messageLabel.setText("Profile saved.");
            }
        });

        leaveRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent event) {
                profileController.leaveRoom();
            }
        });

        changePhotoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent event) {
                handleChangePhoto(event);
            }
        });
    }

    /**
     * Handles the Change Photo button press.
     *
     * @param event the action event
     */
    private void handleChangePhoto(final ActionEvent event) {
        final JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose Profile Photo");

        final int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {

            final File selectedFile = fileChooser.getSelectedFile();
            profilePhotoPath = selectedFile.getAbsolutePath();

            final ImageLabel newPhotoLabel = new ImageLabel(
                    profilePhotoPath,
                    ViewConstants.PROFILE_PHOTO_WIDTH,
                    ViewConstants.PROFILE_PHOTO_HEIGHT);

            final Dimension photoSize = new Dimension(
                    ViewConstants.PROFILE_PHOTO_WIDTH,
                    ViewConstants.PROFILE_PHOTO_HEIGHT);

            newPhotoLabel.setPreferredSize(photoSize);
            newPhotoLabel.setMinimumSize(photoSize);
            newPhotoLabel.setMaximumSize(photoSize);
            newPhotoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            newPhotoLabel.setBorder(
                    BorderFactory.createLineBorder(ViewColors.DARK_BLUE, ViewConstants.PROFILE_PHOTO_BORDER_THICKNESS,
                            true));

            replaceProfilePhotoLabel(newPhotoLabel);
            messageLabel.setText("Profile photo updated.");
        }
    }

    /**
     * Handles the Save button press.
     *
     * @param event the action event
     */
    private void handleSave(final ActionEvent event) {
        final String photoPath = profilePhotoPath;

        if (profileController != null) {
            profileController.saveProfile(photoPath);
        }

        if (messageLabel != null) {
            messageLabel.setText("Profile updated.");
        }
    }

    /**
     * Replaces the existing profile photo label with the given one, keeping it in
     * the same column panel.
     *
     * @param newLabel new label to display as the profile photo
     */
    private void replaceProfilePhotoLabel(final JLabel newLabel) {
        final java.awt.Container parent = profilePhotoLabel.getParent();
        if (parent == null) {
            return;
        }

        final java.awt.Component[] components = parent.getComponents();
        int index = -1;
        for (int i = 0; i < components.length; i++) {
            if (components[i] == profilePhotoLabel) {
                index = i;
                break;
            }
        }

        if (index != -1) {
            parent.remove(index);
            profilePhotoLabel = newLabel;
            parent.add(profilePhotoLabel, index);
            parent.revalidate();
            parent.repaint();
        }
    }

    /**
     * Creates a primary (orange) button matching the app design.
     *
     * @param text text to show on the button
     * @return configured JButton
     */
    private JButton createButton(final String text) {
        final JButton button = new ButtonBuilder()
                .setText(text)
                .setFont(ViewConstants.BUTTON_FONT)
                .setBackground(ViewColors.ORANGE)
                .setForeground(Color.WHITE)
                .build();

        return button;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        JOptionPane.showMessageDialog(this, "Not implemented.");
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final ProfileState state = profileViewModel.getState();
        if (state.getErrorMessage() != null) {
            JOptionPane.showMessageDialog(this, state.getErrorMessage());
        }
        usernameValueLabel.setText(state.getUsername());
        emailValueLabel.setText(state.getEmail());
    }

    /**
     * Returns the CardLayout key for this view.
     *
     * @return profile view name
     */
    public String getViewName() {
        return ViewConstants.PROFILE_VIEW_NAME;
    }

    /**
     * Sets the profile controller for this view.
     *
     * @param profileController controller to set
     */
    public void setProfileController(ProfileController profileController) {
        this.profileController = profileController;
    }
}
