package view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.function.Consumer;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import interface_adapter.ViewManagerModel;

/**
 * Profile screen that displays/edits basic user info and can navigate back.
 */
public class ProfileView extends JPanel {
    private final ViewManagerModel viewManagerModel;
    private final String backTargetViewName;
    private final Consumer<String> navigator;

    private final JTextField usernameField;
    private final JTextField emailField;

    /**
     * A.
     * 
     * @param viewManagerModel   shared model used to switch screens (can be null)
     * @param backTargetViewName card to show when Back is clicked (e.g.,
     *                           signupView.getViewName())
     * @param navigator          callback that shows a given card name via
     *                           CardLayout
     */
    public ProfileView(final ViewManagerModel viewManagerModel,
            final String backTargetViewName,
            final Consumer<String> navigator) {
        this.viewManagerModel = viewManagerModel;
        this.backTargetViewName = backTargetViewName;
        this.navigator = navigator;

        final JLabel titleLabel = new JLabel(ViewConstants.PROFILE_TITLE_TEXT);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JLabel usernameLabel = new JLabel(ViewConstants.USERNAME_LABEL_TEXT);
        usernameField = new JTextField(ViewConstants.TEXT_FIELD_COLUMNS);

        final JLabel emailLabel = new JLabel(ViewConstants.EMAIL_LABEL_TEXT);
        emailField = new JTextField(ViewConstants.TEXT_FIELD_COLUMNS);

        final JButton saveButton = new JButton(ViewConstants.SAVE_BUTTON_TEXT);
        final JButton backButton = new JButton(ViewConstants.BACK_BUTTON_TEXT);

        final JPanel usernameRow = new JPanel();
        usernameRow.add(usernameLabel);
        usernameRow.add(usernameField);

        final JPanel emailRow = new JPanel();
        emailRow.add(emailLabel);
        emailRow.add(emailField);

        final JPanel buttonsRow = new JPanel();
        buttonsRow.add(backButton);
        buttonsRow.add(saveButton);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(Box.createRigidArea(new Dimension(0, ViewConstants.V_GAP)));
        add(titleLabel);
        add(Box.createRigidArea(new Dimension(0, ViewConstants.V_GAP)));
        add(usernameRow);
        add(emailRow);
        add(Box.createRigidArea(new Dimension(0, ViewConstants.V_GAP)));
        add(buttonsRow);

        backButton.addActionListener((final ActionEvent evt) -> {
            if (this.viewManagerModel != null) {
                this.viewManagerModel.setActiveViewName(backTargetViewName);
            }
            this.navigator.accept(backTargetViewName);
        });
    }

    /**
     * A.
     * 
     * @return the CardLayout key for this view.
     */
    public String getViewName() {
        return ViewConstants.PROFILE_VIEW_NAME;
    }
}
