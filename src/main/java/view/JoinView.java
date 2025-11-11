package view;

import java.awt.*;

import javax.swing.*;

import interface_adapter.join.JoinViewModel;

/**
 * The view for joining or creating a room.
 */
public class JoinView extends JPanel {
    private final String viewName = "join/create";
    // private final JoinViewModel joinViewModel;

    private final JTextField roomCodeField;
    private final JButton joinButton;
    private final JButton createButton;
    private final JButton profileButton;


    /**
     * Constructs a JoinView with the given JoinViewModel.
     * 
     * @param joinViewModel the JoinViewModel
     */
    public JoinView(JoinViewModel joinViewModel) {

        // this.joinViewModel = joinViewModel;

        final JLabel title = new JLabel("GitChores");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JPanel joinPanel = new JPanel();
        joinPanel.setLayout(new BoxLayout(joinPanel, BoxLayout.Y_AXIS));

        roomCodeField = new JTextField(Constants.TEXT_FIELD_COLUMNS);
        joinButton = new JButton("Join Room");
        createButton = new JButton(" Room");
        profileButton = new JButton("Profile");

        joinPanel.add(roomCodeField);
        joinPanel.add(joinButton);
        joinPanel.add(createButton);
        joinPanel.add(profileButton);

        // TODO: later, wire profileButton to actually switch to ProfileView
        profileButton.addActionListener(e -> JOptionPane.showMessageDialog(
                this,
                "Profile page is implemented; navigation will be wired next."
        ));

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(title);
        this.add(joinPanel);
    }


    public String getViewName() {
        return viewName;
    }
}
