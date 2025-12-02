package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import interface_adapter.chore_creation.ChoreCreationController;
import interface_adapter.chore_creation.ChoreCreationState;
import interface_adapter.chore_creation.ChoreCreationViewModel;

/**
 * The view for creating a chore.
 */
public class ChoreCreationView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "chore creation";

    private final ChoreCreationViewModel choreCreationViewModel;
    private ChoreCreationController choreCreationController;

    private final JTextField titleField = new JTextField(20);
    private final JTextField descriptionField = new JTextField(20);
    private final JTextField priorityField = new JTextField(20);
    private final JTextField dueDateField = new JTextField(20);
    private final JTextField assignedUserField = new JTextField(20);

    private final JButton createButton;
    private final JButton cancelButton;

    /**
     * Constructs a ChoreCreationView with the given ChoreCreationViewModel.
     *
     * @param choreCreationViewModel the ChoreCreationViewModel
     */
    public ChoreCreationView(ChoreCreationViewModel choreCreationViewModel) {
        this.choreCreationViewModel = choreCreationViewModel;
        choreCreationViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel(ChoreCreationViewModel.TITLE_LABEL);
        title.setFont(ViewConstants.LABEL_FONT);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JLabel titleLabel = new JLabel(ChoreCreationViewModel.TITLE_FIELD_LABEL);
        titleLabel.setFont(ViewConstants.LABEL_FONT);
        titleField.setFont(ViewConstants.LABEL_FONT);
        final LabelTextPanel titleInfo = new LabelTextPanel(titleLabel, titleField);
        titleInfo.setBackground(Color.WHITE);

        final JLabel descriptionLabel = new JLabel(ChoreCreationViewModel.DESCRIPTION_FIELD_LABEL);
        descriptionLabel.setFont(ViewConstants.LABEL_FONT);
        descriptionField.setFont(ViewConstants.LABEL_FONT);
        final LabelTextPanel descInfo = new LabelTextPanel(descriptionLabel, descriptionField);
        descInfo.setBackground(Color.WHITE);

        final JLabel priorityLabel = new JLabel(ChoreCreationViewModel.PRIORITY_FIELD_LABEL);
        priorityLabel.setFont(ViewConstants.LABEL_FONT);
        priorityField.setFont(ViewConstants.LABEL_FONT);
        final LabelTextPanel priorityInfo = new LabelTextPanel(priorityLabel, priorityField);
        priorityInfo.setBackground(Color.WHITE);

        final JLabel dueDateLabel = new JLabel(ChoreCreationViewModel.DUE_DATE_FIELD_LABEL);
        dueDateLabel.setFont(ViewConstants.LABEL_FONT);
        dueDateField.setFont(ViewConstants.LABEL_FONT);
        final LabelTextPanel dueDateInfo = new LabelTextPanel(dueDateLabel, dueDateField);
        dueDateInfo.setBackground(Color.WHITE);

        final JLabel assignedLabel = new JLabel(ChoreCreationViewModel.ASSIGNED_USER_FIELD_LABEL);
        assignedLabel.setFont(ViewConstants.LABEL_FONT);
        assignedUserField.setFont(ViewConstants.LABEL_FONT);
        final LabelTextPanel assignedInfo = new LabelTextPanel(assignedLabel, assignedUserField);
        assignedInfo.setBackground(Color.WHITE);

        final JPanel buttons = new JPanel();
        buttons.setBackground(Color.WHITE);

        createButton = new ButtonBuilder()
                .setText(ChoreCreationViewModel.CREATE_BUTTON_LABEL)
                .setBackground(ViewColors.ORANGE)
                .setForeground(Color.WHITE)
                .setFont(ViewConstants.LABEL_FONT)
                .setBorder(ViewConstants.DEFAULT_BUTTON_FOCUS_BORDER)
                .build();

        cancelButton = new ButtonBuilder()
                .setText(ChoreCreationViewModel.CANCEL_BUTTON_LABEL)
                .setBackground(ViewColors.ORANGE)
                .setForeground(Color.WHITE)
                .setFont(ViewConstants.LABEL_FONT)
                .setBorder(ViewConstants.DEFAULT_BUTTON_FOCUS_BORDER)
                .build();

        buttons.add(createButton);
        buttons.add(cancelButton);

        this.setLayout(new GridBagLayout());
        this.setBackground(Color.WHITE);

        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = ViewConstants.TEXT_FIELD_INSETS;

        final Component[] components = {
                titleInfo,
                descInfo,
                priorityInfo,
                dueDateInfo,
                assignedInfo,
                buttons
        };

        int row = 0;
        for (Component c : components) {
            gbc.gridx = 0;
            gbc.gridy = row++;
            this.add(c, gbc);
        }

        addTitleListener();
        addDescriptionListener();
        addDueDateListener();
        addAssignedUserListener();
        addPriorityListener();

        createButton.addActionListener(this);
        cancelButton.addActionListener(this);
    }

    @SuppressWarnings("checkstyle:AnonInnerLength")
    private void addTitleListener() {
        titleField.getDocument().addDocumentListener(new DocumentListener() {
            private void documentListenerHelper() {
                final ChoreCreationState currentState = choreCreationViewModel.getState();
                currentState.setTitle(titleField.getText());
                choreCreationViewModel.setState(currentState);
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

    @SuppressWarnings("checkstyle:AnonInnerLength")
    private void addDescriptionListener() {
        descriptionField.getDocument().addDocumentListener(new DocumentListener() {
            private void documentListenerHelper() {
                final ChoreCreationState currentState = choreCreationViewModel.getState();
                currentState.setDescription(descriptionField.getText());
                choreCreationViewModel.setState(currentState);
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

    @SuppressWarnings("checkstyle:AnonInnerLength")
    private void addPriorityListener() {
        priorityField.getDocument().addDocumentListener(new DocumentListener() {
            private void documentListenerHelper() {
                final ChoreCreationState currentState = choreCreationViewModel.getState();
                currentState.setPriority(priorityField.getText());
                choreCreationViewModel.setState(currentState);
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

    @SuppressWarnings("checkstyle:AnonInnerLength")
    private void addDueDateListener() {
        dueDateField.getDocument().addDocumentListener(new DocumentListener() {
            private void documentListenerHelper() {
                final ChoreCreationState currentState = choreCreationViewModel.getState();
                currentState.setDueDate(dueDateField.getText());
                choreCreationViewModel.setState(currentState);
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

    @SuppressWarnings("checkstyle:AnonInnerLength")
    private void addAssignedUserListener() {
        assignedUserField.getDocument().addDocumentListener(new DocumentListener() {
            private void documentListenerHelper() {
                final ChoreCreationState currentState = choreCreationViewModel.getState();
                currentState.setAssignedUser(assignedUserField.getText());
                choreCreationViewModel.setState(currentState);
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
    public void actionPerformed(ActionEvent evt) {
        final ChoreCreationState currentState = choreCreationViewModel.getState();
        if (evt.getSource() == createButton) {
            if (currentState.getDescription() == null || currentState.getDescription().trim().isEmpty()) {
                currentState.setDescription("None");
            }
            if (currentState.getAssignedUser() == null || currentState.getAssignedUser().trim().isEmpty()) {
                currentState.setAssignedUser("None");
            }
            choreCreationController.execute(
                    currentState.getTitle(),
                    currentState.getDescription(),
                    currentState.getPriority(),
                    currentState.getDueDate(),
                    currentState.getAssignedUser()
            );

        } else if (evt.getSource() == cancelButton) {
            choreCreationController.switchToDashboardView();
        }
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final ChoreCreationState state = (ChoreCreationState) evt.getNewValue();
        if (state.getChoreError() != null) {
            JOptionPane.showMessageDialog(this, state.getChoreError());
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setChoreCreationController(ChoreCreationController controller) {
        this.choreCreationController = controller;
    }
}
