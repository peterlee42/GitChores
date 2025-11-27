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

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import interface_adapter.SessionModel;
import interface_adapter.join.JoinState;
import interface_adapter.join.JoinViewModel;
import interface_adapter.room.create.CreateRoomController;
import interface_adapter.room.join.JoinRoomController;

/**
 * The view for joining or creating a room.
 */
@SuppressWarnings("checkstyle:ClassDataAbstractionCoupling")
public class JoinView extends JPanel implements ActionListener, PropertyChangeListener {
    private static final String VIEW_NAME = "join/create";

    private final JoinViewModel joinViewModel;
    private final SessionModel sessionModel;

    private final JTextField roomNameField;
    private final JTextField roomDescriptionField;
    private final JTextField inviteCodeField;

    private final JButton joinButton;
    private final JButton createButton;
    private final JButton backToSignupButton;

    private JoinRoomController joinRoomController;
    private CreateRoomController createRoomController;
    private String currentUserId;

    /**
     * Constructs a JoinView with the given JoinViewModel and SessionModel.
     *
     * @param joinViewModel the JoinViewModel
     * @param sessionModel  the SessionModel
     */
    @SuppressWarnings("checkstyle:ExecutableStatementCountCheck")
    public JoinView(JoinViewModel joinViewModel, SessionModel sessionModel) {
        this.joinViewModel = joinViewModel;
        this.sessionModel = sessionModel;
        this.joinViewModel.addPropertyChangeListener(this);
        this.sessionModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Main panel
        final JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(ViewConstants.PANEL_PADDING, ViewConstants.PANEL_PADDING,
                ViewConstants.PANEL_PADDING, ViewConstants.PANEL_PADDING));

        final GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(ViewConstants.COMPONENT_SPACING / 2, 0, ViewConstants.COMPONENT_SPACING / 2, 0);
        constraints.gridx = 0;
        constraints.gridy = 0;

        // Title
        final JLabel titleLabel = new JLabel(ViewConstants.JOIN_TITLE_TEXT);
        titleLabel.setFont(ViewConstants.HEADER_FONT);
        titleLabel.setForeground(ViewColors.DARK_BLUE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        constraints.gridy++;
        mainPanel.add(titleLabel, constraints);

        // Spacing
        constraints.gridy++;
        mainPanel.add(Box.createRigidArea(new Dimension(0, ViewConstants.SPACING_20)), constraints);

        // Initialize fields
        roomNameField = new JTextField(ViewConstants.TEXT_FIELD_COLUMNS);
        roomDescriptionField = new JTextField(ViewConstants.TEXT_FIELD_COLUMNS);
        inviteCodeField = new JTextField(ViewConstants.INVITE_CODE_COLUMNS);
        joinButton = new ButtonBuilder()
                .setText(ViewConstants.JOIN_BUTTON_TEXT)
                .setFont(ViewConstants.BUTTON_FONT)
                .setBackground(ViewColors.ORANGE)
                .setForeground(Color.WHITE)
                .build();
        createButton = new ButtonBuilder()
                .setText("Create " + ViewConstants.CREATE_BUTTON_TEXT)
                .setFont(ViewConstants.BUTTON_FONT)
                .setBackground(ViewColors.DARK_BLUE)
                .setForeground(Color.WHITE)
                .build();

        // Join Section
        final JPanel joinSection = buildJoinSection();
        constraints.gridy++;
        mainPanel.add(joinSection, constraints);

        // Separator
        constraints.gridy++;
        mainPanel.add(buildSeparator(), constraints);

        // Create Section
        final JPanel createSection = buildCreateSection();
        constraints.gridy++;
        mainPanel.add(createSection, constraints);

        // Back button
        constraints.gridy++;
        mainPanel.add(Box.createRigidArea(new Dimension(0, ViewConstants.SPACING_20)), constraints);

        backToSignupButton = new ButtonBuilder()
                .setText("Back to Sign Up")
                .setFont(ViewConstants.LABEL_FONT)
                .setBackground(Color.LIGHT_GRAY)
                .setForeground(ViewColors.DARK_BLUE)
                .build();
        backToSignupButton.addActionListener(this);
        constraints.gridy++;
        mainPanel.add(backToSignupButton, constraints);

        add(mainPanel, BorderLayout.CENTER);

        joinButton.addActionListener(this);
        createButton.addActionListener(this);
    }

    private JPanel buildJoinSection() {
        final JPanel section = new JPanel();
        section.setLayout(new BoxLayout(section, BoxLayout.Y_AXIS));
        section.setBackground(Color.WHITE);
        section.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(ViewConstants.BORDER_COLOR, ViewConstants.BORDER_COLOR,
                        ViewConstants.BORDER_COLOR), ViewConstants.BORDER_WIDTH),
                BorderFactory.createEmptyBorder(ViewConstants.SPACING_20, ViewConstants.SPACING_20,
                        ViewConstants.SPACING_20, ViewConstants.SPACING_20)
        ));

        final JLabel sectionTitle = new JLabel("Join Existing Room");
        sectionTitle.setFont(ViewConstants.TITLE_FONT);
        sectionTitle.setForeground(ViewColors.DARK_BLUE);
        sectionTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        inviteCodeField.setFont(ViewConstants.LABEL_FONT);
        inviteCodeField.setMaximumSize(new Dimension(ViewConstants.CODE_FIELD_WIDTH, ViewConstants.FIELD_HEIGHT));

        final JLabel codeLabel = new JLabel("Enter Invite Code:");
        codeLabel.setFont(ViewConstants.LABEL_FONT);
        codeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        joinButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        section.add(sectionTitle);
        section.add(Box.createRigidArea(new Dimension(0, ViewConstants.SPACING_15)));
        section.add(codeLabel);
        section.add(Box.createRigidArea(new Dimension(0, ViewConstants.SPACING_5)));
        section.add(inviteCodeField);
        section.add(Box.createRigidArea(new Dimension(0, ViewConstants.SPACING_15)));
        section.add(joinButton);

        return section;
    }

    @SuppressWarnings("checkstyle:ExecutableStatementCountCheck")
    private JPanel buildCreateSection() {
        final JPanel section = new JPanel();
        section.setLayout(new BoxLayout(section, BoxLayout.Y_AXIS));
        section.setBackground(Color.WHITE);
        section.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(ViewConstants.BORDER_COLOR, ViewConstants.BORDER_COLOR,
                        ViewConstants.BORDER_COLOR), ViewConstants.BORDER_WIDTH),
                BorderFactory.createEmptyBorder(ViewConstants.SPACING_20, ViewConstants.SPACING_20,
                        ViewConstants.SPACING_20, ViewConstants.SPACING_20)
        ));

        final JLabel sectionTitle = new JLabel("Create New Room");
        sectionTitle.setFont(ViewConstants.TITLE_FONT);
        sectionTitle.setForeground(ViewColors.DARK_BLUE);
        sectionTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        roomNameField.setFont(ViewConstants.LABEL_FONT);
        roomNameField.setMaximumSize(new Dimension(ViewConstants.FIELD_WIDTH, ViewConstants.FIELD_HEIGHT));

        roomDescriptionField.setFont(ViewConstants.LABEL_FONT);
        roomDescriptionField.setMaximumSize(new Dimension(ViewConstants.FIELD_WIDTH, ViewConstants.FIELD_HEIGHT));

        final JLabel nameLabel = new JLabel("Room Name:");
        nameLabel.setFont(ViewConstants.LABEL_FONT);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JLabel descLabel = new JLabel("Description (optional):");
        descLabel.setFont(ViewConstants.LABEL_FONT);
        descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        createButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        section.add(sectionTitle);
        section.add(Box.createRigidArea(new Dimension(0, ViewConstants.SPACING_15)));
        section.add(nameLabel);
        section.add(Box.createRigidArea(new Dimension(0, ViewConstants.SPACING_5)));
        section.add(roomNameField);
        section.add(Box.createRigidArea(new Dimension(0, ViewConstants.SPACING_10)));
        section.add(descLabel);
        section.add(Box.createRigidArea(new Dimension(0, ViewConstants.SPACING_5)));
        section.add(roomDescriptionField);
        section.add(Box.createRigidArea(new Dimension(0, ViewConstants.SPACING_15)));
        section.add(createButton);

        return section;
    }

    private Component buildSeparator() {
        final JPanel separator = new JPanel();
        separator.setLayout(new BorderLayout());
        separator.setBackground(Color.WHITE);

        final JLabel orLabel = new JLabel("OR");
        orLabel.setFont(ViewConstants.LABEL_FONT);
        orLabel.setForeground(Color.GRAY);
        orLabel.setHorizontalAlignment(JLabel.CENTER);

        separator.add(orLabel, BorderLayout.CENTER);
        separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, ViewConstants.SEPARATOR_HEIGHT));

        return separator;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() == joinButton) {
            final String inviteCode = inviteCodeField.getText().trim();
            if (inviteCode.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter an invite code",
                        ViewConstants.ERROR_PREFIX, JOptionPane.ERROR_MESSAGE);
            }
            else if (currentUserId == null) {
                JOptionPane.showMessageDialog(this, "User not logged in",
                        ViewConstants.ERROR_PREFIX, JOptionPane.ERROR_MESSAGE);
            }
            else if (joinRoomController != null) {
                joinRoomController.execute(inviteCode, currentUserId);
            }
        }
        else if (evt.getSource() == createButton) {
            final String roomName = roomNameField.getText().trim();
            if (roomName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a room name",
                        ViewConstants.ERROR_PREFIX, JOptionPane.ERROR_MESSAGE);
            }
            else if (currentUserId == null) {
                JOptionPane.showMessageDialog(this, "User not logged in",
                        ViewConstants.ERROR_PREFIX, JOptionPane.ERROR_MESSAGE);
            }
            else if (createRoomController != null) {
                final String description = roomDescriptionField.getText().trim();
                createRoomController.execute(roomName, description, currentUserId);
            }
        }
        else if (evt.getSource() == backToSignupButton) {
            JOptionPane.showMessageDialog(this, "Please sign up or login first");
        }
    }

    public void setJoinRoomController(JoinRoomController joinRoomController) {
        this.joinRoomController = joinRoomController;
    }

    public void setCreateRoomController(CreateRoomController createRoomController) {
        this.createRoomController = createRoomController;
    }

    public void setCurrentUserId(String userId) {
        this.currentUserId = userId;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getSource() == sessionModel) {
            // Update currentUserId from session when user logs in
            this.currentUserId = sessionModel.getUserId();
        }
        else if (evt.getSource() == joinViewModel) {
            final JoinState state = (JoinState) evt.getNewValue();
            if (state.getJoinError() != null) {
                JOptionPane.showMessageDialog(this, state.getJoinError(),
                        ViewConstants.ERROR_PREFIX, JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public String getViewName() {
        return VIEW_NAME;
    }
}
