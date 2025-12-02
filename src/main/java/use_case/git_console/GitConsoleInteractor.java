package use_case.git_console;

import java.util.Arrays;
import java.util.List;

import data_access.dynamo_db.RoomMetadataDataAccessObject;
import entity.User;
import interface_adapter.commit.CommitController;
import interface_adapter.commit.CommitPresenter;
import use_case.logged_in.UserService;
import use_case.room.RoomDataAccessInterface;

/**
 * The Git Console Interactor.
 */
public class GitConsoleInteractor implements GitConsoleInputBoundary {

    private final GitConsoleOutputBoundary presenter;
    private final CommitController commitController;
    private final CommitPresenter commitPresenter;
    private final RoomMetadataDataAccessObject roomMetadataDataAccessObject;
    private final UserService userService;
    private final RoomDataAccessInterface roomDataAccess;

    public GitConsoleInteractor(GitConsoleOutputBoundary presenter,
                CommitController commitController,
                CommitPresenter commitPresenter,
                RoomMetadataDataAccessObject roomMetadataDataAccessObject,
                UserService userService,
                RoomDataAccessInterface roomDataAccess) {
        this.presenter = presenter;
        this.commitPresenter = commitPresenter;
        this.commitController = commitController;
        this.roomMetadataDataAccessObject = roomMetadataDataAccessObject;
        this.userService = userService;
        this.roomDataAccess = roomDataAccess;

    }

    /**
     * Executes the command given by the user or provides an error message if it is invalid.
     * 
     * @param command The string command inputted to the console text box.
     */
    @SuppressWarnings("checkstyle:MultipleStringLiterals")
    @Override
    public void executeCommand(String command) {

        String output = "";
        // Handle exceptions (poorly structured messages)
        // Reject empty messages
        if (command == null || command.isBlank()) {
            output = "Please enter a command.";
        }
        // Verify the prefix of the command
        else if (!(command.startsWith("git"))) {
            output = "Invalid command. Commands must start with 'git'.";
        } else {
            // Break command into sub-parts for easier identification
            final String[] parts = command.split(" ");
            if (parts.length < 2) {
                output = "Missing subcommand after git.";
            } else {
                final String subcommand = parts[1];
                output = switch (subcommand) {
                    case "commit" -> handleCommit(command);
                    case "request_review" -> handleReviewRequest(Arrays.copyOfRange(parts, 2, parts.length));
                    case "approve_request" -> handleApproveRequest(Arrays.copyOfRange(parts, 2, parts.length));
                    default -> "Unknown subcommand: " + subcommand;
                };
            }
        }

        presenter.presentResponse(command, output);
    }

    /**
     * Helper method to return the current user's room ID.
     *
     * @return Room ID or null if user is not logged in/not in a room
     */
    private String getCurrentUserRoomId() {
        final User currentUser = userService.getUser();
        if (currentUser == null) {
            return null;
        }
        return roomDataAccess.getUserRoomId(currentUser.getId());
    }

    /**
     * Executes the commit command, or provides an error message if it is invalid.
     * 
     * @param command The string command inputted to the console text box
     * @return A message presented to the screen
     */
    @SuppressWarnings("checkstyle:AvoidInlineConditionals")
    private String handleCommit(String command) {
        final String output;

        if (!command.contains("-m")) {
            output = "Your commit is missing an '-m' before the message";
        } else {
            final String[] parts = command.split("-m", 2);
            final String message = parts[1].trim();

            if (message.isEmpty()) {
                output = "Error: empty commit message";
            } else {
                final String userId = userService.getUser().getId();
                final String roomId = roomDataAccess.getUserRoomId(userId);
                commitController.execute(roomId, userId, message);
                output = commitPresenter.getViewMessage();
            }
        }
        return output;
    }

    /**
     * Executes the review request command, or provides an error message if it is
     * invalid.
     * 
     * @param choreNameParts The name of the chore (could be separate list elements
     *                       if spaces exist)
     * @return A message presented to the screen
     */
    @SuppressWarnings("checkstyle:ReturnCount")
    private String handleReviewRequest(String[] choreNameParts) {
        final String choreName = String.join(" ", choreNameParts);
        final String roomId = getCurrentUserRoomId();

        if (roomId == null) {
            return "Error: User not logged in or is not in a room";
        }

        final boolean added = roomMetadataDataAccessObject.addPendingReview(roomId, choreName);
        if (!added) {
            final List<String> current = roomMetadataDataAccessObject.getPendingReviews(roomId);
            if (current.contains(choreName)) {
                return "Chore already pending review: " + choreName;
            }
            return "Error connecting to database. Contact support.";
        }
        return "Review requested for chore: " + choreName;
    }

    /**
     * Executes the approve request command, or provides an error message if it is
     * invalid.
     * 
     * @param choreNameParts The name of the chore (could be separate list elements
     *                       if spaces exist)
     * @return A message presented to the screen
     */
    @SuppressWarnings("checkstyle:ReturnCount")
    private String handleApproveRequest(String[] choreNameParts) {
        final String choreName = String.join(" ", choreNameParts);
        final String roomId = getCurrentUserRoomId();

        if (roomId == null) {
            return "Error: User not logged in or is not in a room";
        }

        final boolean removed = roomMetadataDataAccessObject.removePendingReview(roomId, choreName);
        if (!removed) {
            final List<String> current = roomMetadataDataAccessObject.getPendingReviews(roomId);
            if (!current.contains(choreName)) {
                return "This chore does not exist or is not yet pending review: " + choreName;
            }
            return "Error connecting to database. Contact support.";
        }
        return "Approved request for chore: " + choreName;
    }
}
