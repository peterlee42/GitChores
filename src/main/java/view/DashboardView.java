package view;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;

import interface_adapter.dashboard.DashboardState;
import interface_adapter.dashboard.DashboardViewModel;

@SuppressWarnings("checkstyle:ClassDataAbstractionCoupling")
public class DashboardView extends JPanel implements PropertyChangeListener {

    private final ActivityTilesPanel activityTilesPanel;
    private final DashboardViewModel dashboardViewModel;

    public DashboardView(DashboardViewModel dashboardViewModel) {
        this.dashboardViewModel = dashboardViewModel;

        setLayout(new BorderLayout());
        setBackground(ViewColors.SAND_BACKGROUND);

        // Main content panel with grid layout
        final JPanel contentPanel = new JPanel(new GridBagLayout());
        final int pad = ViewConstants.DASHBOARD_PANEL_PADDING;
        contentPanel.setBorder(BorderFactory.createEmptyBorder(pad, pad, pad, pad));

        final GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        final int spacing = ViewConstants.DASHBOARD_COMPONENT_SPACING;
        constraints.insets = new Insets(spacing, spacing, spacing, spacing);

        // Activity Tiles Section (top, full width)
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.weightx = ViewConstants.DASHBOARD_WEIGHTX;
        constraints.weighty = ViewConstants.DASHBOARD_WEIGHTY;

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

        final JLabel titleLabel = new JLabel("Chore Activity");
        titleLabel.setFont(ViewConstants.TITLE_FONT);
        titleLabel.setForeground(ViewColors.DARK_BLUE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, ViewConstants.DASHBOARD_BORDER, 0));

        section.add(titleLabel, BorderLayout.NORTH);
        section.add(content, BorderLayout.CENTER);

        return section;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final DashboardState state = dashboardViewModel.getState();
        if (state.getErrorMessage() != null) {
            JOptionPane.showMessageDialog(this, state.getErrorMessage());
        }
    }

    /**
     * Returns the name of this view.
     *
     * @return the view name
     */
    public String getViewName() {
        return "dashboard";
    }
}
