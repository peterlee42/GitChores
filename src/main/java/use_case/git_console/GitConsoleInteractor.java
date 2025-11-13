package use_case.git_console;

/**
 * The Git Console Interactor.
 */
public class GitConsoleInteractor implements GitConsoleInputBoundary {

    // TO DO: Implement data access features once set up
    // TO DO: Implement a guide when the user types ?guide

    private final GitConsoleOutputBoundary presenter;

    public GitConsoleInteractor(GitConsoleOutputBoundary presenter) {
        this.presenter = presenter;
    }

    @Override
    public void executeCommand(String command) {

        String output = "";
        // Handle exceptions (poorly structured messages)
        // Reject empty messages
        if (command == null || command.isBlank()) {
            presenter.presentResponse(command, "Please enter a command.");
        }
        // Verify the prefix of the command
        else if (!(command.startsWith("git "))) {
            presenter.presentResponse(command, "Invalid command. Commands must start with 'git'.");
        } else {
            // Break command into sub-parts for easier identification
            final String[] parts = command.split(" ");
            if (parts.length < 2) {
                presenter.presentResponse(command, "Missing subcommand after git.");
            } else {
                final String subcommand = parts[1];
                output = switch (subcommand) {
                    case "commit" -> handleCommit(command);
                    case "push" -> handlePush();
                    case "checkout" -> handleCheckout(command);
                    default -> "Unknown subcommand: " + subcommand;
                };
            }
        }

        presenter.presentResponse(command, output);
    }

    /**
     * THESE COMMANDS ARE YET TO BE IMPLEMENTED PROPERLY.
     *
     * @param command command
     * @return PLACEHOLDER
     */
    private String handleCommit(String command) {
        final String output;

        if (!command.contains("-m")) {
            output = "Your commit is missing an '-m' before the message";
        } else {
            final String[] parts = command.split("-m", 2);
            final String message;
            if (parts.length > 1) {
                message = parts[1].trim().replaceAll("^\"\"$|", "");
            } else {
                message = "";
            }
            if (message.isEmpty()) {
                output = "Error: empty commit message";
            } else {
                output = "Commit successful! Message: " + "\"" + message + "\"";
            }
        }
        return output;
    }

    private String handlePush() {
        // Temporary: Will replace with specific branch name
        return "Pushed changes to <branch_name>";
    }

    private String handleCheckout(String command) {
        // Temporary: Will replace with specific branch name
        return "Switched branch to " + command;
    }
}
