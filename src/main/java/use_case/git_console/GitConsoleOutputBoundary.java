package use_case.git_console;

/**
 * The output boundary for the Git Console Use Case.
 */
public interface GitConsoleOutputBoundary {

    /**
     * Prepares the view with both the user's command and the Git emulator system
     * response.
     * 
     * @param command  the user inputted command
     * @param response the system response
     */
    void presentResponse(String command, String response);
}
