package view;

import javax.swing.*;
import java.awt.*;

import interface_adapter.join.JoinViewModel;

public class JoinView extends JPanel {
    private final String viewName = "join/create";
    private final JoinViewModel joinViewModel;

    private final JTextField roomCodeField;

    private final JButton joinButton;
    private final JButton createButton;

    public JoinView(JoinViewModel joinViewModel) {
        this.joinViewModel = joinViewModel;

        // TODO: Add property change listener
        // this.joinViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel("GitChores");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JPanel joinPanel = new JPanel();
        joinPanel.setLayout(new BoxLayout(joinPanel, BoxLayout.Y_AXIS));
        roomCodeField = new JTextField(10);
        joinButton = new JButton("Join Room");
        createButton = new JButton(" Room");
        joinPanel.add(roomCodeField);
        joinPanel.add(joinButton);
        joinPanel.add(createButton);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(title);
        this.add(joinPanel);
    }

    public String getViewName() {
        return viewName;
    }
}