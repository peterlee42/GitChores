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

import interface_adapter.room.join.JoinRoomController;
import interface_adapter.room.join.JoinState;
import interface_adapter.room.join.JoinViewModel;

/**
 * The view for joining or creating a room.
 */
@SuppressWarnings("checkstyle:ClassDataAbstractionCoupling")
public class JoinView extends JPanel implements ActionListener, PropertyChangeListener {
    private static final String VIEW_NAME = "join_room";

    private final JoinViewModel joinViewModel;

    private final JTextField inviteCodeField;

    private final JButton joinButton;
    private final JButton backToLoginButton;

    private JoinRoomController joinRoomController;

    private JButton toCreateButton;

    /**
     * Constructs a JoinView with the given JoinViewModel and SessionModel.
     *
     * @param joinViewModel the JoinViewModel
     */
    @SuppressWarnings("checkstyle:ExecutableStatementCountCheck")
    public JoinView(JoinViewModel joinViewModel) {
        this.joinViewModel = joinViewModel;
        this.joinViewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Main panel
        final JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(ViewConstants.DASHBOARD_PANEL_PADDING, ViewConstants.DASHBOARD_PANEL_PADDING,
                ViewConstants.DASHBOARD_PANEL_PADDING, ViewConstants.DASHBOARD_PANEL_PADDING));

        final GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(ViewConstants.DASHBOARD_COMPONENT_SPACING / 2, 0, ViewConstants.DASHBOARD_COMPONENT_SPACING / 2, 0);
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
        inviteCodeField = new JTextField(ViewConstants.INVITE_CODE_COLUMNS);
        joinButton = new ButtonBuilder()
                .setText(ViewConstants.JOIN_BUTTON_TEXT)
                .setFont(ViewConstants.BUTTON_FONT)
                .setBackground(ViewColors.ORANGE)
                .setForeground(Color.WHITE)
                .build();

        // Join Section
        final JPanel joinSection = buildJoinSection();
        constraints.gridy++;
        mainPanel.add(joinSection, constraints);

        // Separator
        constraints.gridy++;
        mainPanel.add(buildSeparator(), constraints);

        // Create Room button
        toCreateButton = new ButtonBuilder()
                .setText("Create Room")
                .setFont(ViewConstants.LABEL_FONT)
                .setBackground(ViewColors.DARK_BLUE)
                .setForeground(Color.WHITE)
                .build();
        constraints.gridy++;
        mainPanel.add(toCreateButton, constraints);

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

        joinButton.addActionListener(this);
        toCreateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                // Switch to create room view
                joinRoomController.switchToCreateView();
            }
        });
        backToLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                // Switch to login view
                joinRoomController.switchToLoginView();
            }
        });
    }

    private JPanel buildJoinSection() {
        final JPanel section = new JPanel();
        section.setLayout(new BoxLayout(section, BoxLayout.Y_AXIS));
        section.setBackground(Color.WHITE);
        section.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(ViewConstants.BORDER_COLOR, ViewConstants.BORDER_COLOR,
                        ViewConstants.BORDER_COLOR), ViewConstants.BORDER_WIDTH),
                BorderFactory.createEmptyBorder(ViewConstants.SPACING_20, ViewConstants.SPACING_20,
                        ViewConstants.SPACING_20, ViewConstants.SPACING_20)));

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
            } else if (joinRoomController != null) {
                joinRoomController.execute(inviteCode);
            }
        }
    }

    public void setJoinRoomController(JoinRoomController joinRoomController) {
        this.joinRoomController = joinRoomController;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // join view model
        if (evt.getSource() == joinViewModel) {
            final JoinState joinState = (JoinState) evt.getNewValue();

            if (joinState.getJoinError() != null) {
                JOptionPane.showMessageDialog(this,
                        joinState.getJoinError(),
                        ViewConstants.ERROR_PREFIX,
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public String getViewName() {
        return VIEW_NAME;
    }
}
