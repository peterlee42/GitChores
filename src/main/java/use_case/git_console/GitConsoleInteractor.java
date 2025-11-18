package use_case.git_console;

import interface_adapter.commit.CommitController;
import interface_adapter.commit.CommitPresenter;

/**
 * The Git Console Interactor.
 */
public class GitConsoleInteractor implements GitConsoleInputBoundary {

    private final GitConsoleOutputBoundary presenter;
    private final CommitController commitController;
    private final CommitPresenter commitPresenter;

    public GitConsoleInteractor(GitConsoleOutputBoundary presenter,
                                CommitController commitController,
                                CommitPresenter commitPresenter) {
        this.presenter = presenter;
        this.commitPresenter = commitPresenter;
        this.commitController = commitController;

    }

    @Override
    public void executeCommand(String command) {

        String output = "";
        // Handle exceptions (poorly structured messages)
        // Reject empty messages
        if (command == null || command.isBlank()) {
            output = "Please enter a command.";
        }
        else if ("?guide".equals(command)) {
            // Extract outside of file
            output = "THIS IS THE GUIDE";
        }
        // Verify the prefix of the command
        else if (!(command.startsWith("git "))) {
            output = "Invalid command. Commands must start with 'git'. Type ?guide for help.";
        } else {
            // Break command into sub-parts for easier identification
            final String[] parts = command.split(" ");
            if (parts.length < 2) {
                output = "Missing subcommand after git. Type ?guide for help.";
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
            }
            else {
                // THESE ARE TEMP VARIABLES
                final String tempRoomId = "Different room";
                final String tempUserId = "PraneethSqw42";
                commitController.execute(tempRoomId, tempUserId, message);
                output = commitPresenter.getViewMessage();
            }
        }
        return output;
    }

    // THIS WILL BE REPLACED WITH REQUEST_REVIEW
    private String handlePush() {
        // Temporary: Will replace with specific branch name
        return "Pushed changes to <branch_name>";
    }

    // THIS WILL BE REPLACED WITH APPROVE
    private String handleCheckout(String command) {
        // Temporary: Will replace with specific branch name
        return "Switched branch to " + command;
    }
}
