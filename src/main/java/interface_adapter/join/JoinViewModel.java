package interface_adapter.join;

import interface_adapter.ViewModel;

/**
 * The ViewModel for joining or creating a room.
 */
public class JoinViewModel extends ViewModel<JoinState> {

    /**
     * Constructor for a JoinViewModel.
     */
    public JoinViewModel() {
        super("join");
        setState(new JoinState());
    }

}
