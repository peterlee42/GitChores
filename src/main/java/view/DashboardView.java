package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;

import interface_adapter.dashboard.DashboardController;
import interface_adapter.dashboard.DashboardState;
import interface_adapter.dashboard.DashboardViewModel;

@SuppressWarnings("checkstyle:ClassDataAbstractionCoupling")
public class DashboardView extends JPanel implements PropertyChangeListener {

    private final ActivityTilesPanel activityTilesPanel;
    private final DashboardViewModel dashboardViewModel;
    private DashboardController dashboardController;
    private final JLabel roomNameLabel;
    private final JLabel inviteCodeLabel;
    private final JLabel descriptionLabel;

    @SuppressWarnings("checkstyle:ExecutableStatementCount")
    public DashboardView(DashboardViewModel dashboardViewModel) {
        this.dashboardViewModel = dashboardViewModel;

        setLayout(new BorderLayout());

        final JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(Color.WHITE);
        final int pad = ViewConstants.DASHBOARD_PANEL_PADDING;
        contentPanel.setBorder(BorderFactory.createEmptyBorder(pad, pad, pad, pad));

        final GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.VERTICAL;
        final int spacing = ViewConstants.SPACING_5;
        constraints.insets = new Insets(1, spacing, 1, spacing);

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.weightx = ViewConstants.DASHBOARD_WEIGHTX;
        constraints.weighty = ViewConstants.DASHBOARD_WEIGHTY;

        roomNameLabel = new JLabel();
        roomNameLabel.setFont(ViewConstants.TITLE_FONT);
        roomNameLabel.setForeground(ViewColors.DARK_BLUE);
        contentPanel.add(roomNameLabel, constraints);

        constraints.anchor = GridBagConstraints.EAST;
        inviteCodeLabel = new JLabel();
        inviteCodeLabel.setFont(ViewConstants.LABEL_FONT);
        inviteCodeLabel.setForeground(ViewColors.DARK_BLUE);
        contentPanel.add(inviteCodeLabel, constraints);

        constraints.gridy++;
        constraints.anchor = GridBagConstraints.CENTER;
        descriptionLabel = new JLabel();
        descriptionLabel.setFont(ViewConstants.LABEL_FONT);
        descriptionLabel.setForeground(ViewColors.DARK_BLUE);
        contentPanel.add(descriptionLabel, constraints);

        constraints.gridy++;
        final DashboardState dashboardState = dashboardViewModel.getState();
        activityTilesPanel = new ActivityTilesPanel(dashboardState.getActivityData(),
                dashboardState.getCommitsMessages());
        final JPanel tilesSection = createSection(activityTilesPanel);
        contentPanel.add(tilesSection, constraints);

        add(contentPanel, BorderLayout.CENTER);
    }

    private JPanel createSection(Component content) {
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

        final JLabel titleLabel = new JLabel("Chore Activity");
        titleLabel.setFont(ViewConstants.TITLE_FONT);
        titleLabel.setForeground(ViewColors.DARK_BLUE);

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

        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(createChoreButton, BorderLayout.EAST);
        section.add(headerPanel, BorderLayout.NORTH);
        section.add(content, BorderLayout.CENTER);

        return section;
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
