package interface_adapter.git_console;

import use_case.git_console.GitConsoleInputBoundary;

/**
 * Controller for the Git Console Use Case.
 */
public class GitConsoleController {

    private final GitConsoleInputBoundary gitConsoleUseCaseInteractor;

    public GitConsoleController(GitConsoleInputBoundary gitConsoleUseCaseInteractor) {
        this.gitConsoleUseCaseInteractor = gitConsoleUseCaseInteractor;
    }

    /**
     * Executes the Git command inputted by the user, or displays an error message if the command is invalid.
     * @param command the command inputted by the user
     */
    public void executeCommand(String command) {
        gitConsoleUseCaseInteractor.executeCommand(command);
    }
}
