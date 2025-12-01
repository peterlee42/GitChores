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

import interface_adapter.room.create.CreateRoomController;
import interface_adapter.room.create.CreateRoomState;
import interface_adapter.room.create.CreateRoomViewModel;

/**
 * The view for joining or creating a room.
 */
@SuppressWarnings("checkstyle:ClassDataAbstractionCoupling")
public class CreateRoomView extends JPanel implements ActionListener,
        PropertyChangeListener {
    private static final String VIEW_NAME = "create_room";

    private final CreateRoomViewModel createRoomViewModel;

    private final JTextField roomNameField;
    private final JTextField roomDescriptionField;

    private final JButton createButton;
    private final JButton backToLoginButton;
    private final JButton toJoinButton;

    private CreateRoomController createRoomController;

    /**
     * Constructs a CreateRoomView with the given CreateRoomViewModel and
     * SessionModel.
     *
     * @param createRoomViewModel the CreateRoomViewModel
     */
    @SuppressWarnings("checkstyle:ExecutableStatementCountCheck")
    public CreateRoomView(CreateRoomViewModel createRoomViewModel) {
        this.createRoomViewModel = createRoomViewModel;
        this.createRoomViewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Main panel
        final JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(ViewConstants.DASHBOARD_PANEL_PADDING,
                ViewConstants.DASHBOARD_PANEL_PADDING,
                ViewConstants.DASHBOARD_PANEL_PADDING, ViewConstants.DASHBOARD_PANEL_PADDING));

        final GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(ViewConstants.DASHBOARD_COMPONENT_SPACING / 2, 0,
                ViewConstants.DASHBOARD_COMPONENT_SPACING / 2, 0);
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
        mainPanel.add(Box.createRigidArea(new Dimension(0,
                ViewConstants.SPACING_20)), constraints);

        // Initialize fields
        roomNameField = new JTextField(ViewConstants.TEXT_FIELD_COLUMNS);
        roomDescriptionField = new JTextField(ViewConstants.TEXT_FIELD_COLUMNS);
        createButton = new ButtonBuilder()
                .setText("Create " + ViewConstants.CREATE_BUTTON_TEXT)
                .setFont(ViewConstants.BUTTON_FONT)
                .setBackground(ViewColors.DARK_BLUE)
                .setForeground(Color.WHITE)
                .build();

        // Create Section
        final JPanel createSection = buildCreateSection();
        constraints.gridy++;
        mainPanel.add(createSection, constraints);

        // Separator
        constraints.gridy++;
        mainPanel.add(buildSeparator(), constraints);

        // Join Room button
        toJoinButton = new ButtonBuilder()
                .setText("Join Room")
                .setFont(ViewConstants.LABEL_FONT)
                .setBackground(ViewColors.ORANGE)
                .setForeground(Color.WHITE)
                .build();
        constraints.gridy++;
        mainPanel.add(toJoinButton, constraints);

        // Back button
        constraints.gridy++;
        mainPanel.add(Box.createRigidArea(new Dimension(0, ViewConstants.SPACING_20)), constraints);

        backToLoginButton = new ButtonBuilder()
                .setText("Back to Login")
                .setFont(ViewConstants.LABEL_FONT)
                .setForeground(ViewColors.DARK_BLUE)
                .build();
        constraints.gridy++;
        mainPanel.add(backToLoginButton, constraints);

        add(mainPanel, BorderLayout.CENTER);

        createButton.addActionListener(this);
        toJoinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                createRoomController.switchToJoinView();
            }
        });
        backToLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                createRoomController.switchToLoginView();
            }
        });
    }

    @SuppressWarnings("checkstyle:ExecutableStatementCountCheck")
    private JPanel buildCreateSection() {
        final JPanel section = new JPanel();
        section.setLayout(new BoxLayout(section, BoxLayout.Y_AXIS));
        section.setBackground(Color.WHITE);
        section.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(ViewConstants.BORDER_COLOR,
                        ViewConstants.BORDER_COLOR,
                        ViewConstants.BORDER_COLOR), ViewConstants.BORDER_WIDTH),
                BorderFactory.createEmptyBorder(ViewConstants.SPACING_20,
                        ViewConstants.SPACING_20,
                        ViewConstants.SPACING_20, ViewConstants.SPACING_20)));

        final JLabel sectionTitle = new JLabel("Create New Room");
        sectionTitle.setFont(ViewConstants.TITLE_FONT);
        sectionTitle.setForeground(ViewColors.DARK_BLUE);
        sectionTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        roomNameField.setFont(ViewConstants.LABEL_FONT);
        roomNameField.setMaximumSize(new Dimension(ViewConstants.FIELD_WIDTH,
                ViewConstants.FIELD_HEIGHT));

        roomDescriptionField.setFont(ViewConstants.LABEL_FONT);
        roomDescriptionField.setMaximumSize(new Dimension(ViewConstants.FIELD_WIDTH,
                ViewConstants.FIELD_HEIGHT));

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
        separator.setMaximumSize(new Dimension(Integer.MAX_VALUE,
                ViewConstants.SEPARATOR_HEIGHT));

        return separator;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() == createButton) {
            final String roomName = roomNameField.getText().trim();
            if (roomName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a room name",
                        ViewConstants.ERROR_PREFIX, JOptionPane.ERROR_MESSAGE);
            } else if (createRoomController != null) {
                final String description = roomDescriptionField.getText().trim();
                createRoomController.execute(roomName, description);
                roomNameField.setText("");
                roomDescriptionField.setText("");
            }
        }
    }

    public void setCreateRoomController(CreateRoomController createRoomController) {
        this.createRoomController = createRoomController;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // create room view model
        if (evt.getSource() == createRoomViewModel) {
            final CreateRoomState createRoomState = (CreateRoomState) evt.getNewValue();

            if (createRoomState.getError() != null) {
                JOptionPane.showMessageDialog(this,
                        createRoomState.getError(),
                        ViewConstants.ERROR_PREFIX,
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public String getViewName() {
        return VIEW_NAME;
    }
}
