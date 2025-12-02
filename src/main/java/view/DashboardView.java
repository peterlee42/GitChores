package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.*;

import entity.Chore;
import interface_adapter.dashboard.DashboardController;
import interface_adapter.dashboard.DashboardState;
import interface_adapter.dashboard.DashboardViewModel;

@SuppressWarnings("checkstyle:ClassDataAbstractionCoupling")
public class DashboardView extends JPanel implements PropertyChangeListener {

    private final ActivityTilesPanel activityTilesPanel;
    private final JPanel choresListPanel;
    private final DashboardViewModel dashboardViewModel;
    private DashboardController dashboardController;
    private final JLabel roomNameLabel;
    private final JLabel inviteCodeLabel;
    private final JLabel descriptionLabel;

    @SuppressWarnings({ "checkstyle:ExecutableStatementCountCheck", "JavaNCSS" })
    public DashboardView(DashboardViewModel dashboardViewModel) {
        this.dashboardViewModel = dashboardViewModel;

        setLayout(new BorderLayout());

        final JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(Color.WHITE);
        final int pad = ViewConstants.DASHBOARD_PANEL_PADDING;
        contentPanel.setBorder(BorderFactory.createEmptyBorder(pad, pad, pad, pad));

        final GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(1, 1, 1, 1);

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.weightx = ViewConstants.DASHBOARD_WEIGHTX;
        constraints.weighty = ViewConstants.DASHBOARD_WEIGHTY;
        constraints.anchor = GridBagConstraints.WEST;

        roomNameLabel = new JLabel();
        roomNameLabel.setFont(ViewConstants.TITLE_FONT);
        roomNameLabel.setForeground(ViewColors.DARK_BLUE);
        contentPanel.add(roomNameLabel, constraints);

        constraints.gridx = 1;
        constraints.anchor = GridBagConstraints.EAST;
        inviteCodeLabel = new JLabel();
        inviteCodeLabel.setFont(ViewConstants.LABEL_FONT);
        inviteCodeLabel.setForeground(ViewColors.DARK_BLUE);
        contentPanel.add(inviteCodeLabel, constraints);

        constraints.gridx = 0;
        constraints.gridy++;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        descriptionLabel = new JLabel();
        descriptionLabel.setFont(ViewConstants.LABEL_FONT);
        descriptionLabel.setForeground(ViewColors.DARK_BLUE);
        contentPanel.add(descriptionLabel, constraints);

        constraints.gridy++;
        constraints.gridwidth = ViewConstants.DASHBOARD_GRID_WIDTH;
        constraints.weighty = ViewConstants.DASHBOARD_SECTION_WEIGHT;
        final DashboardState dashboardState = dashboardViewModel.getState();
        activityTilesPanel = new ActivityTilesPanel(dashboardState.getActivityData(),
                dashboardState.getCommitsMessages());
        final JPanel tilesSection = createSection("Chore Activity", activityTilesPanel, true);
        contentPanel.add(tilesSection, constraints);

        constraints.gridy++;
        constraints.gridwidth = ViewConstants.DASHBOARD_GRID_WIDTH;
        constraints.weighty = ViewConstants.DASHBOARD_SECTION_WEIGHT;

        this.choresListPanel = new JPanel();
        choresListPanel.setLayout(new BoxLayout(choresListPanel, BoxLayout.Y_AXIS));
        choresListPanel.setBackground(Color.WHITE);
        final JScrollPane choresScrollPane = new JScrollPane(choresListPanel);
        choresScrollPane.setBorder(null);
        final JPanel choresSection = createSection("Upcoming Chores", choresScrollPane, false);
        contentPanel.add(choresSection, constraints);

        add(contentPanel, BorderLayout.CENTER);
    }

    private JPanel createSection(String title, Component content, boolean hasButton) {
        final JPanel section = new JPanel(new BorderLayout());
        section.setBackground(ViewColors.SAND_BACKGROUND);
        section.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(ViewConstants.DASHBOARD_230, ViewConstants.DASHBOARD_230,
                        ViewConstants.DASHBOARD_230), 1),
                BorderFactory.createEmptyBorder(ViewConstants.DASHBOARD_BORDER, ViewConstants.DASHBOARD_BORDER,
                        ViewConstants.DASHBOARD_BORDER, ViewConstants.DASHBOARD_BORDER)));

        final JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(ViewColors.SAND_BACKGROUND);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, ViewConstants.DASHBOARD_BORDER, 0));

        final JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(ViewConstants.TITLE_FONT);
        titleLabel.setForeground(ViewColors.DARK_BLUE);

        headerPanel.add(titleLabel, BorderLayout.WEST);

        if (hasButton) {
            final JButton createChoreButton = new ButtonBuilder()
                    .setText("Create Chore")
                    .setFont(ViewConstants.LABEL_FONT)
                    .setBackground(ViewColors.ORANGE)
                    .setForeground(Color.WHITE)
                    .build();

            createChoreButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dashboardController.switchToChoreCreationView();
                }
            });

            headerPanel.add(createChoreButton, BorderLayout.EAST);
        }

        section.add(headerPanel, BorderLayout.NORTH);
        section.add(content, BorderLayout.CENTER);

        return section;
    }

    private void updateChoresList(List<Chore> chores) {
        choresListPanel.removeAll();

        if (chores == null || chores.isEmpty()) {
            final JLabel emptyLabel = new JLabel("No chores yet. Click Create Chore to add one!");
            emptyLabel.setFont(ViewConstants.LABEL_FONT);
            emptyLabel.setForeground(ViewColors.CHORE_DESCRIPTION_TEXT);
            emptyLabel.setBorder(BorderFactory.createEmptyBorder(
                    ViewConstants.CHORE_EMPTY_MESSAGE_PADDING,
                    ViewConstants.CHORE_EMPTY_MESSAGE_PADDING,
                    ViewConstants.CHORE_EMPTY_MESSAGE_PADDING,
                    ViewConstants.CHORE_EMPTY_MESSAGE_PADDING));
            choresListPanel.add(emptyLabel);
        } else {
            for (Chore chore : chores) {
                choresListPanel.add(createChoreCard(chore));
                choresListPanel.add(Box.createRigidArea(new Dimension(0, ViewConstants.CHORE_CARD_SPACING)));
            }
        }

        choresListPanel.revalidate();
        choresListPanel.repaint();
    }

    @SuppressWarnings({ "checkstyle:ExecutableStatementCountCheck", "JavaNCSS" })
    private JPanel createChoreCard(Chore chore) {
        final JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        ViewColors.CHORE_CARD_BORDER,
                        ViewConstants.CHORE_CARD_BORDER_THICKNESS),
                BorderFactory.createEmptyBorder(
                        ViewConstants.CHORE_CARD_PADDING_VERTICAL,
                        ViewConstants.CHORE_CARD_PADDING_HORIZONTAL,
                        ViewConstants.CHORE_CARD_PADDING_VERTICAL,
                        ViewConstants.CHORE_CARD_PADDING_HORIZONTAL)
        ));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, ViewConstants.CHORE_CARD_MAX_HEIGHT));

        final JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(Color.WHITE);

        final JLabel titleLabel = new JLabel(chore.getTitle());
        titleLabel.setFont(ViewConstants.LABEL_FONT.deriveFont(Font.BOLD));
        titleLabel.setForeground(ViewColors.DARK_BLUE);

        final String description = chore.getDescription();
        final JLabel descLabel = new JLabel(description);
        descLabel.setFont(ViewConstants.LABEL_FONT.deriveFont((float) ViewConstants.CHORE_DESCRIPTION_FONT_SIZE));
        descLabel.setForeground(ViewColors.CHORE_DESCRIPTION_TEXT);

        leftPanel.add(titleLabel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, ViewConstants.SPACING_5)));
        leftPanel.add(descLabel);

        final JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setAlignmentY(Component.CENTER_ALIGNMENT);

        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm");
        final JLabel dueDateLabel = new JLabel("Due: " + chore.getDueDate().format(formatter));
        dueDateLabel.setFont(ViewConstants.LABEL_FONT.deriveFont((float) ViewConstants.CHORE_DESCRIPTION_FONT_SIZE));

        final JLabel statusLabel = new JLabel(chore.getStatus().name());
        statusLabel.setFont(ViewConstants.LABEL_FONT.deriveFont(
                Font.BOLD, (float) ViewConstants.CHORE_STATUS_FONT_SIZE));
        statusLabel.setOpaque(true);
        statusLabel.setBorder(BorderFactory.createEmptyBorder(
                ViewConstants.CHORE_STATUS_PADDING,
                ViewConstants.CHORE_STATUS_PADDING_HORIZONTAL,
                ViewConstants.CHORE_STATUS_PADDING,
                ViewConstants.CHORE_STATUS_PADDING_HORIZONTAL));

        switch (chore.getStatus()) {
            case INACTIVE:
                statusLabel.setBackground(ViewColors.STATUS_INACTIVE_BG);
                statusLabel.setForeground(ViewColors.STATUS_INACTIVE_FG);
                break;
            case PENDING:
                statusLabel.setBackground(ViewColors.STATUS_PENDING_BG);
                statusLabel.setForeground(ViewColors.STATUS_PENDING_FG);
                break;
            case REVIEW_PENDING:
                statusLabel.setBackground(ViewColors.STATUS_REVIEW_PENDING_BG);
                statusLabel.setForeground(ViewColors.STATUS_REVIEW_PENDING_FG);
                break;
            case COMPLETED:
                statusLabel.setBackground(ViewColors.STATUS_COMPLETED_BG);
                statusLabel.setForeground(ViewColors.STATUS_COMPLETED_FG);
                break;
            default:
                statusLabel.setBackground(ViewColors.STATUS_INACTIVE_BG);
                statusLabel.setForeground(ViewColors.STATUS_INACTIVE_FG);
        }

        rightPanel.add(dueDateLabel);
        rightPanel.add(Box.createRigidArea(new Dimension(0, ViewConstants.SPACING_5)));
        rightPanel.add(statusLabel);

        card.add(leftPanel, BorderLayout.CENTER);
        card.add(rightPanel, BorderLayout.EAST);

        return card;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final DashboardState state = dashboardViewModel.getState();
        if (state.getErrorMessage() != null) {
            JOptionPane.showMessageDialog(this, state.getErrorMessage());
        }

        activityTilesPanel.setActivityData(state.getActivityData());
        activityTilesPanel.setDetailedActivityData(state.getActivityData(), state.getCommitsMessages());

        roomNameLabel.setText("Welcome to " + state.getRoomName());
        descriptionLabel.setText("Description: " + state.getRoomDescription());
        inviteCodeLabel.setText("Invite Code: " + state.getRoomCode());

        updateChoresList(state.getChores());

        revalidate();
        repaint();
    }

    /**
     * Returns the name of this view.
     *
     * @return the view name
     */
    public String getViewName() {
        return "dashboard";
    }

    public void setDashboardController(DashboardController dashboardController) {
        this.dashboardController = dashboardController;
    }
}
