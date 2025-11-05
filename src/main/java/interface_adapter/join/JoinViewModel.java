package interface_adapter.join;

import interface_adapter.ViewModel;
import main.java.interface_adapter.join.JoinState;

public class JoinViewModel extends ViewModel<JoinState> {

    public JoinViewModel() {
        super("join");
        setState(new JoinState());
    }

}