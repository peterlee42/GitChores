package use_case.git_console;

/**
 * Input Boundary for the actions related to the Git Console.
 */
public interface GitConsoleInputBoundary {

    /**
     * Executes the git command use case.
     * @param command the command inputted by the user
     */
    void executeCommand(String command);
}
