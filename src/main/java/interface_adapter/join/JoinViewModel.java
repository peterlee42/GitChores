package interface_adapter.join;

import interface_adapter.ViewModel;

public class JoinViewModel extends ViewModel<JoinState> {

    public JoinViewModel() {
        super("join");
        setState(new JoinState());
    }

}
