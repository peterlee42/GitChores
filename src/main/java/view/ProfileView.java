package view;

import java.awt.Component;
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

    public ProfileView(
            ProfileViewModel profileViewModel) {

        this.profileViewModel = profileViewModel;

        this.usernameValueLabel = new JLabel("");
        this.emailValueLabel = new JLabel("");
        this.messageLabel = new JLabel("");

        this.logoutButton = createPrimaryButton(ViewConstants.LOGOUT_BUTTON_TEXT);
        this.saveButton = createPrimaryButton(ViewConstants.SAVE_BUTTON_TEXT);
        this.leaveRoomButton = createPrimaryButton(ViewConstants.LEAVE_ROOM_BUTTON_TEXT);
        this.changePhotoButton = createPrimaryButton(ViewConstants.CHANGE_PHOTO_BUTTON_TEXT);
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
        setBackground(ViewColors.SAND_BACKGROUND);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        addTitleSection();
        add(Box.createVerticalStrut(ViewConstants.V_GAP));
        addMainContentSection();
        add(Box.createVerticalStrut(ViewConstants.V_GAP));
        addButtonsSection();
    }

    /**
     * Adds the title label to the view.
     */
    private void addTitleSection() {
        final JLabel titleLabel = new JLabel(ViewConstants.PROFILE_TITLE_TEXT);
        titleLabel.setFont(ViewConstants.WELCOME_FONT);
        titleLabel.setForeground(ViewColors.DARK_BLUE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(Box.createVerticalStrut(ViewConstants.V_GAP));
        add(titleLabel);
    }

    /**
     * Adds the upper content: photo on the left, user info on the right.
     */
    private void addMainContentSection() {
        final JPanel mainRow = new JPanel();
        mainRow.setBackground(ViewColors.SAND_BACKGROUND);
        mainRow.setLayout(new BoxLayout(mainRow, BoxLayout.X_AXIS));

        final JPanel photoColumn = createPhotoColumn();
        final JPanel infoColumn = createInfoColumn();

        mainRow.add(Box.createHorizontalStrut(ViewConstants.V_GAP * 2));
        mainRow.add(photoColumn);
        mainRow.add(Box.createHorizontalStrut(ViewConstants.PROFILE_MAIN_CENTER_GAP));
        mainRow.add(infoColumn);
        mainRow.add(Box.createHorizontalStrut(ViewConstants.V_GAP * 2));

        add(mainRow);
    }

    /**
     * Creates the left column with the profile photo and change-photo button.
     *
     * @return panel containing the photo UI
     */
    private JPanel createPhotoColumn() {
        final JPanel column = new JPanel();
        column.setBackground(ViewColors.SAND_BACKGROUND);
        column.setLayout(new BoxLayout(column, BoxLayout.Y_AXIS));

        profilePhotoLabel = new JLabel("No photo");
        profilePhotoLabel.setFont(ViewConstants.LABEL_FONT);
        profilePhotoLabel.setForeground(ViewColors.DARK_BLUE);
        profilePhotoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        profilePhotoLabel.setHorizontalAlignment(JLabel.CENTER);
        profilePhotoLabel.setVerticalAlignment(JLabel.CENTER);

        final java.awt.Dimension photoSize = new java.awt.Dimension(
                ViewConstants.PROFILE_PHOTO_WIDTH,
                ViewConstants.PROFILE_PHOTO_HEIGHT);

        profilePhotoLabel.setPreferredSize(photoSize);
        profilePhotoLabel.setMinimumSize(photoSize);
        profilePhotoLabel.setMaximumSize(photoSize);

        profilePhotoLabel.setBorder(
                BorderFactory.createLineBorder(
                        ViewColors.DARK_BLUE,
                        ViewConstants.PROFILE_PHOTO_BORDER_THICKNESS,
                        true));

        changePhotoButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        column.add(profilePhotoLabel);
        column.add(Box.createVerticalStrut(ViewConstants.V_GAP / 2));
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
        column.setBackground(ViewColors.SAND_BACKGROUND);
        column.setLayout(new BoxLayout(column, BoxLayout.Y_AXIS));

        final JLabel usernameLabel = new JLabel(ViewConstants.USERNAME_LABEL_TEXT);
        usernameLabel.setFont(ViewConstants.LABEL_FONT);
        usernameLabel.setForeground(ViewColors.DARK_BLUE);

        final JLabel emailLabel = new JLabel(ViewConstants.EMAIL_LABEL_TEXT);
        emailLabel.setFont(ViewConstants.LABEL_FONT);
        emailLabel.setForeground(ViewColors.DARK_BLUE);

        usernameValueLabel.setFont(ViewConstants.LABEL_FONT);
        emailValueLabel.setFont(ViewConstants.LABEL_FONT);

        final JPanel usernameRow = new JPanel();
        usernameRow.setBackground(ViewColors.SAND_BACKGROUND);
        usernameRow.add(usernameLabel);
        usernameRow.add(usernameValueLabel);

        final JPanel emailRow = new JPanel();
        emailRow.setBackground(ViewColors.SAND_BACKGROUND);
        emailRow.add(emailLabel);
        emailRow.add(emailValueLabel);

        column.add(usernameRow);
        column.add(Box.createVerticalStrut(ViewConstants.V_GAP));
        column.add(emailRow);

        return column;
    }

    /**
     * Adds the bottom row with Back, Save, and Leave Room buttons.
     */
    private void addButtonsSection() {
        final JPanel buttonsRow = new JPanel();
        buttonsRow.setBackground(ViewColors.SAND_BACKGROUND);
        buttonsRow.add(logoutButton);
        buttonsRow.add(Box.createHorizontalStrut(ViewConstants.V_GAP));
        buttonsRow.add(saveButton);
        buttonsRow.add(Box.createHorizontalStrut(ViewConstants.V_GAP));
        buttonsRow.add(leaveRoomButton);

        add(buttonsRow);

        add(Box.createVerticalStrut(ViewConstants.V_GAP / 2));
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(messageLabel);

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
            final ImageLabel newPhotoLabel = new ImageLabel(profilePhotoPath,
                    ViewConstants.PROFILE_PHOTO_WIDTH, ViewConstants.PROFILE_PHOTO_HEIGHT);
            newPhotoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            final java.awt.Dimension photoSize = new java.awt.Dimension(
                    ViewConstants.PROFILE_PHOTO_WIDTH,
                    ViewConstants.PROFILE_PHOTO_HEIGHT);
            newPhotoLabel.setPreferredSize(photoSize);
            newPhotoLabel.setMinimumSize(photoSize);
            newPhotoLabel.setMaximumSize(photoSize);

            newPhotoLabel.setBorder(
                    BorderFactory.createLineBorder(
                            ViewColors.DARK_BLUE,
                            ViewConstants.PROFILE_PHOTO_BORDER_THICKNESS,
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
        // Get the current email shown on the profile.
        final String email = emailValueLabel.getText();
        final String photoPath = profilePhotoPath;

        if (profileController != null) {
            profileController.saveProfile(email, photoPath);
        }

        // Keep local feedback so user sees something immediately.
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
    private JButton createPrimaryButton(final String text) {
        final JButton button = new JButton(text);
        button.setFont(ViewConstants.LABEL_FONT);
        button.setBackground(ViewColors.ORANGE);
        button.setForeground(java.awt.Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(ViewConstants.EMPTY_BORDER);

        // These two lines force the orange fill to be visible (especially on macOS).
        button.setOpaque(true);
        button.setContentAreaFilled(true);

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
