package use_case.git_console;

/**
 * The Git Console Interactor.
 */
public class GitConsoleInteractor implements GitConsoleInputBoundary {

    // TODO: Implement data access features once set up
    // TODO: Implement a guide when the user types ?guide

    private final GitConsoleOutputBoundary presenter;

    public GitConsoleInteractor(GitConsoleOutputBoundary presenter) {
        this.presenter = presenter;
    }

    @Override
    public void executeCommand(String command) {

        // Handle exceptions (poorly structured messages)
        // Reject empty messages
        if (command == null || command.isBlank()) {
            presenter.presentResponse(command,"Please enter a command.");
            return;
        }
        // Verify the prefix of the command
        if (!(command.startsWith("git "))) {
            presenter.presentResponse(command,"Invalid command. Commands must start with 'git'.");
            return;
        }

        // Break command into sub-parts for easier identification
        String [] parts = command.split(" ");
        if (parts.length < 2) {
            presenter.presentResponse(command,"Missing subcommand after git.");
            return;
        }

        String subcommand = parts[1];
        String output = switch (subcommand) {
            case "commit" -> handleCommit(command);
            case "push" -> handlePush();
            case "checkout" -> handleCheckout(command);
            default -> "Unknown subcommand: " + subcommand;
        };

        presenter.presentResponse(command, output);
    }

    /**
     * THESE COMMANDS ARE YET TO BE IMPLEMENTED PROPERLY
     */
    private String handleCommit(String command) {
        if (!command.contains("-m")) {
            return "Your commit is missing an '-m' before the message";
        }
        String [] parts = command.split("-m",2);
        String message;
        if (parts.length > 1) {
            message = parts[1].trim().replaceAll("^\"\"$|", "");
        }
        else {
            message = "";
        }
        if (message.isEmpty()) {
            return "Error: empty commit message";
        }
        return "Commit successful! Message: " + "\"" + message + "\"";
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
