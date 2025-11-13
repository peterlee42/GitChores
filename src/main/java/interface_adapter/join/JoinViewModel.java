package interface_adapter.join;

import interface_adapter.ViewModel;

/**
 * The ViewModel for joining or creating a room.
 */
public class JoinViewModel extends ViewModel<JoinState> {

    public static final int MAX_TEXT_FIELD_LENGTH = 6;

    /**
     * Constructor for a JoinViewModel.
     */
    public JoinViewModel() {
        super("join");
        setState(new JoinState());
    }

}
