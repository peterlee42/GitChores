package view;

import javax.swing.*;

public class LoginView extends JPanel {
    private final String viewName = "join/create";
    private final JoinViewModel viewModel;

    private final JTextField roomCodeField = new JTextField(10);

    private final JButton joinButton;
    private final JButton createButton;

    public JoinCreateView(joinViewModel joinViewModel) {
        this.joinViewModel = joinViewModel;
        this.joinViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel("GitChores");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JPanel joinCreatePanel = new JPanel();
        joinCreatePanel.setLayout(new BoxLayout(joinCreatePanel, BoxLayout.Y_AXIS));
        roomCodeField = new JTextField(10);
        joinButton = new JButton("Join Room");
        createButton = new JButton("Create Room");
        joinCreatePanel.add(roomCodeField);
        joinCreatePanel.add(joinButton);
        joinCreatePanel.add(createButton);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(title);
        this.add(joinCreatePanel);
    }

    public String getViewName() {
        return viewName;
    }
}