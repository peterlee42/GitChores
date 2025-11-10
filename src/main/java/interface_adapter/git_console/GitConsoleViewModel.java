package interface_adapter.git_console;

import interface_adapter.ViewModel;
import interface_adapter.join.JoinViewModel;

/**
 * The View Model for the Git Console View.
 */
public class GitConsoleViewModel extends ViewModel<GitConsoleState> {

    public static final String TITLE_LABEL = "Console View";

    public GitConsoleViewModel() {
        super("git console idk");
        setState(new GitConsoleState());
    }
}
