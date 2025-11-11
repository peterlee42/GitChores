package interface_adapter.git_console;

import interface_adapter.ViewModel;
import interface_adapter.join.JoinViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * The View Model for the Git Console View.
 */
public class GitConsoleViewModel extends ViewModel<GitConsoleState> {

    public static final String TITLE_LABEL = "Console View";
    public static final String OPERATOR_LABEL = ">>";
    public static final String PROMPT_LABEL = "Enter command";

    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    private GitConsoleState state = new GitConsoleState();

    public GitConsoleViewModel() {
        super("git console");
        setState(new GitConsoleState());
    }

    @Override
    public GitConsoleState getState() {
        return state;
    }

    @Override
    public void setState(GitConsoleState state) {
        this.state = state;
    }

    @Override
    public void firePropertyChange() {
        propertyChangeSupport.firePropertyChange("state", null, this.state);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }
}
