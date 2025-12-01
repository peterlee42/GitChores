package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import interface_adapter.git_console.GitConsoleController;
import interface_adapter.git_console.GitConsoleState;
import interface_adapter.git_console.GitConsoleViewModel;

/**
 * The view seen when the user wishes to add commits to the Git console.
 */
@SuppressWarnings("checkstyle:ClassDataAbstractionCouplingCheck")
public class GitConsoleView extends JPanel implements ActionListener, PropertyChangeListener {
    private static final int TITLE_FONT_SIZE = 20;
    private static final int GUIDE_FONT_SIZE = 13;
    private static final int VERTICAL_SPACING = 15;
    private static final int SMALL_VERTICAL_SPACING = 5;
    private static final int LEFT_SPACING = 5;
    private static final int GRID_ONE = 3;
    private static final int GRID_TWO = 4;

    private final String viewName = "console";
    private final GitConsoleViewModel gitConsoleViewModel;
    private final JPanel previousCommands;
    private final JTextField commandInputField = new JTextField(20);
    private final JButton submitCommand;
    private final JLabel outOperator;
    private GitConsoleController gitConsoleController;
    private final int fontSize = 14;
    private Font monospacedFont = new Font(Font.MONOSPACED, Font.PLAIN, fontSize);

    @SuppressWarnings("checkstyle:ExecutableStatementCount")
    public GitConsoleView(GitConsoleViewModel gitConsoleViewModel) {
        this.gitConsoleViewModel = gitConsoleViewModel;
        gitConsoleViewModel.addPropertyChangeListener(this);

        // Title
        final JLabel title = new JLabel("Git Console");
        final Font largeFont = title.getFont();
        title.setFont(largeFont.deriveFont(Font.BOLD, TITLE_FONT_SIZE));

        // Guide text panel
        final JPanel guidePanel = createGuidePanel();

        // Creating previous commands area
        previousCommands = new JPanel();
        previousCommands.setLayout(new BoxLayout(previousCommands, BoxLayout.Y_AXIS));
        previousCommands.setBackground(getBackground());
        final JScrollPane scrollPane = new JScrollPane(previousCommands);
        scrollPane.getViewport().setBackground(getBackground());
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Command entry box
        outOperator = new JLabel(GitConsoleViewModel.OPERATOR_LABEL);
        submitCommand = new JButton(GitConsoleViewModel.PROMPT_LABEL);
        final JPanel commandPanel = createCommandPanel();

        submitCommand.addActionListener(this);
        commandInputField.addActionListener(this);
        addCommandListener();

        // Grid bag layout change
        this.setLayout(new GridBagLayout());
        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(SMALL_VERTICAL_SPACING, LEFT_SPACING, 0, 0);

        // Title - centered
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        this.add(title, gbc);

        // Vertical spacing
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weighty = 0;
        this.add(Box.createVerticalStrut(VERTICAL_SPACING), gbc);

        // Guide panel
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.add(guidePanel, gbc);

        // Scroll pane with previous commands
        gbc.gridy = GRID_ONE;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        this.add(scrollPane, gbc);

        // Command panel at bottom
        gbc.gridy = GRID_TWO;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.add(commandPanel, gbc);
    }

    private JLabel createInstruction(String instruction) {
        final JLabel instructionLabel = new JLabel(instruction);
        instructionLabel.setFont(monospacedFont.deriveFont(Font.PLAIN, GUIDE_FONT_SIZE));
        instructionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        return instructionLabel;
    }

    private JPanel createGuidePanel() {
        final JPanel guidePanel = new JPanel();
        guidePanel.setLayout(new BoxLayout(guidePanel, BoxLayout.Y_AXIS));
        guidePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        guidePanel.setBackground(getBackground());

        final JLabel guideTitle = createInstruction("This is the console for GitChores! Here is a list of commands.");
        final JLabel command1 = createInstruction("1) git commit -m '<insert_message>' will push a commit.");
        final JLabel command2 = createInstruction("2) git request_review '<insert_chore_name>' will list that chore "
                + "for review, if it hasn't already.");
        final JLabel command3 = createInstruction("3) git approve_request '<insert_chore_name>' will approve a "
                + "chore that has been review requested, if it exists.");

        guidePanel.add(guideTitle);
        guidePanel.add(command1);
        guidePanel.add(command2);
        guidePanel.add(command3);
        guidePanel.add(Box.createVerticalStrut(VERTICAL_SPACING * 2));

        return guidePanel;
    }

    private JPanel createCommandPanel() {
        final JPanel commandPanel = new JPanel();
        commandPanel.setLayout(new BoxLayout(commandPanel, BoxLayout.X_AXIS));
        outOperator.setFont(monospacedFont);
        submitCommand.setBackground(ViewColors.ORANGE);
        submitCommand.setForeground(Color.WHITE);
        submitCommand.setFocusPainted(false);
        submitCommand.setBorderPainted(false);
        submitCommand.setOpaque(true);
        commandInputField.setMaximumSize(new Dimension(Integer.MAX_VALUE, commandInputField.getPreferredSize().height));
        commandInputField.setFont(monospacedFont);
        commandPanel.add(outOperator);
        commandPanel.add(commandInputField);
        commandPanel.add(submitCommand);

        return commandPanel;
    }

    @SuppressWarnings({ "checkstyle:AnonInnerLength", "checkstyle:SuppressWarnings" })
    private void addCommandListener() {
        commandInputField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final GitConsoleState currentState = gitConsoleViewModel.getState();
                currentState.setCommand(commandInputField.getText());
                gitConsoleViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                documentListenerHelper();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // To be implemented
        final GitConsoleState currentState = gitConsoleViewModel.getState();
        final String command = currentState.getCommand();

        gitConsoleController.executeCommand(command);
        // Reset text
        commandInputField.setText("");
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        if ("state".equals(evt.getPropertyName())) {
            final GitConsoleState currentState = (GitConsoleState) evt.getNewValue();

            if (currentState.getLastResponse() != null && currentState.getLastCommand() != null) {
                final JLabel commandLabel = new JLabel(GitConsoleViewModel.OPERATOR_LABEL
                        + " " + currentState.getLastCommand());
                commandLabel.setFont(monospacedFont.deriveFont(Font.BOLD));
                final JLabel responseLabel = new JLabel(currentState.getLastResponse());
                responseLabel.setFont(monospacedFont.deriveFont(Font.BOLD));

                previousCommands.add(commandLabel);
                previousCommands.add(responseLabel);

                // Added - Makes the panel refresh so changes actually appear
                previousCommands.revalidate();
                previousCommands.repaint();

                // Keeps scroll bar at bottom of terminal command chain
                final JScrollBar vertical = ((JScrollPane) previousCommands.getParent().getParent())
                        .getVerticalScrollBar();
                vertical.setValue(vertical.getMaximum());
            }
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setGitConsoleController(GitConsoleController controller) {
        this.gitConsoleController = controller;
    }
}
