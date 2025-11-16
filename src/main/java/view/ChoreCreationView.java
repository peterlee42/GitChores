package view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import interface_adapter.chore_creation.ChoreCreationState;
import interface_adapter.chore_creation.ChoreCreationViewModel;

/**
 * The view for creating a chore.
 */
public class ChoreCreationView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "chore creation";

    private final ChoreCreationViewModel choreCreationViewModel;

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
        this.choreCreationViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel(ChoreCreationViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final LabelTextPanel titleInfo = new LabelTextPanel(
                new JLabel(ChoreCreationViewModel.TITLE_FIELD_LABEL), titleField);

        final LabelTextPanel descInfo = new LabelTextPanel(
                new JLabel(ChoreCreationViewModel.DESCRIPTION_FIELD_LABEL), descriptionField);

        final LabelTextPanel priorityInfo = new LabelTextPanel(
                new JLabel(ChoreCreationViewModel.PRIORITY_FIELD_LABEL), priorityField);

        final LabelTextPanel dueDateInfo = new LabelTextPanel(
                new JLabel(ChoreCreationViewModel.DUE_DATE_FIELD_LABEL), dueDateField);

        final LabelTextPanel assignedInfo = new LabelTextPanel(
                new JLabel(ChoreCreationViewModel.ASSIGNED_USER_FIELD_LABEL), assignedUserField);

        createButton = new ButtonBuilder()
                .setText(ChoreCreationViewModel.CREATE_BUTTON_LABEL)
                .build();

        cancelButton = new ButtonBuilder()
                .setText(ChoreCreationViewModel.CANCEL_BUTTON_LABEL)
                .build();

        final JPanel buttons = new JPanel();
        buttons.add(createButton);
        buttons.add(cancelButton);

        addTitleListener();
        addDescriptionListener();
        addDueDateListener();
        addAssignedUserListener();
        addPriorityListener();

        createButton.addActionListener(this);
        cancelButton.addActionListener(this);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(title);
        this.add(titleInfo);
        this.add(descInfo);
        this.add(priorityInfo);
        this.add(dueDateInfo);
        this.add(assignedInfo);
        this.add(buttons);
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
        if (evt.getSource() == createButton) {
            final ChoreCreationState currentState = choreCreationViewModel.getState();
            if (currentState.getDescription() == null || currentState.getDescription().trim().isEmpty()) {
                currentState.setDescription("None");
                choreCreationViewModel.setState(currentState);
            }
            if (currentState.getAssignedUser() == null || currentState.getAssignedUser().trim().isEmpty()) {
                currentState.setAssignedUser("None");
                choreCreationViewModel.setState(currentState);
            }
            JOptionPane.showMessageDialog(this, "Create not implemented yet.");
        } else if (evt.getSource() == cancelButton) {
            JOptionPane.showMessageDialog(this, "Cancel not implemented yet.");
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
}
