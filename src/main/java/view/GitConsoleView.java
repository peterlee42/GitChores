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

    private final String viewName = "console";
    private final GitConsoleViewModel gitConsoleViewModel;
    private final JPanel previousCommands;
    private final JTextField commandInputField = new JTextField(20);
    private final JButton submitCommand;
    private final JLabel outOperator;
    private GitConsoleController gitConsoleController;
    private final int fontSize = 14;
    private Font monospacedFont = new Font(Font.MONOSPACED, Font.PLAIN, fontSize);

    public GitConsoleView(GitConsoleViewModel gitConsoleViewModel) {
        this.gitConsoleViewModel = gitConsoleViewModel;
        gitConsoleViewModel.addPropertyChangeListener(this);

        // Title
        final JLabel title = new JLabel("Git Console");
        final Font largeFont = title.getFont();
        final int titleFontSize = 16;
        title.setFont(largeFont.deriveFont(Font.BOLD, titleFontSize));

        // Creating previous text area
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

        // May have to expand on this based on the ca-lab
        submitCommand.addActionListener(this);
        commandInputField.addActionListener(this);
        addCommandListener();

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(title);
        this.add(scrollPane);
        this.add(commandPanel);
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
