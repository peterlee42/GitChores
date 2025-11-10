package view;

import interface_adapter.git_console.GitConsoleViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * The view seen when the user wishes to add commits to the Git console
 */

public class GitConsoleView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "Git Console";
    private final GitConsoleViewModel gitConsoleViewModel;
    private final JPanel previousCommands;
    private final JTextField commandField;
    private final JButton enterButton;
    private final JLabel outOperator;

    public GitConsoleView(GitConsoleViewModel gitConsoleViewModel) {
        this.gitConsoleViewModel = gitConsoleViewModel;

        // Title
        final JLabel title = new JLabel(GitConsoleViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Creating previous text area
        previousCommands = new JPanel();
        previousCommands.setLayout(new BoxLayout(previousCommands, BoxLayout.Y_AXIS));
        previousCommands.setBackground(getBackground());
        JScrollPane scrollPane = new JScrollPane(previousCommands);
        scrollPane.getViewport().setBackground(getBackground());
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Command entry box
        final JPanel commandPanel = new JPanel();
        commandPanel.setLayout(new BoxLayout(commandPanel, BoxLayout.X_AXIS));
        outOperator = new JLabel(">>");
        commandField = new JTextField(20);
        enterButton = new JButton("Enter command");
        commandField.setMaximumSize(new Dimension(Integer.MAX_VALUE, commandField.getPreferredSize().height));
        commandPanel.add(outOperator);
        commandPanel.add(commandField);
        commandPanel.add(enterButton);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(title);
        this.add(scrollPane);
        this.add(commandPanel);
    }

    public void actionPerformed(ActionEvent e) {
        // To be implemented
    }

    public void propertyChange(PropertyChangeEvent evt) {
        // To be implemented
    }

    public String getViewName() {
        return viewName;
    }
}
