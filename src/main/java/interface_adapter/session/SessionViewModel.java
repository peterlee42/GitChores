package interface_adapter.session;

import interface_adapter.ViewModel;

/**
 * Model for tracking the current user session.
 */
public class SessionViewModel extends ViewModel<SessionState> {

    /**
     * Constructor for a SignupViewModel.
     */
    public SessionViewModel() {
        super("main");
        setState(new SessionState());
    }
}
