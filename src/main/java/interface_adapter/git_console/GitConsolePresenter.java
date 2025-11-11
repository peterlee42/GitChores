package interface_adapter.git_console;

import use_case.git_console.GitConsoleOutputBoundary;

/**
 * The presenter for the Git Console Use Case.
 */
public class GitConsolePresenter implements GitConsoleOutputBoundary {

    private final GitConsoleViewModel gitConsoleViewModel;
    public GitConsolePresenter(GitConsoleViewModel gitConsoleViewModel) {
        this.gitConsoleViewModel = gitConsoleViewModel;
    }

    @Override
    public void presentResponse(String command, String response) {
        GitConsoleState state = gitConsoleViewModel.getState();

        state.setLastCommand(command);
        state.setLastResponse(response);

        gitConsoleViewModel.setState(state);
        gitConsoleViewModel.firePropertyChange();
    }
}
