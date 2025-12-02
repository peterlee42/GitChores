package use_case.chore_creation;

import entity.Chore;
import entity.ChoreStatus;
import entity.DomainIdGenerator;
import entity.User;
import use_case.chore.ChoreDataAccessInterface;
import use_case.exception.ChoreCreationFailedException;
import use_case.logged_in.UserService;
import use_case.room.RoomDataAccessInterface;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

/**
 * Interactor for the Chore Creation Use Case.
 */
public class ChoreCreationInteractor implements ChoreCreationInputBoundary {

    private final ChoreCreationOutputBoundary presenter;
    private final ChoreDataAccessInterface choreDao;
    private final RoomDataAccessInterface roomDao;
    private final UserService userService;

    public ChoreCreationInteractor(
            ChoreCreationOutputBoundary presenter,
            ChoreDataAccessInterface choreDao,
            RoomDataAccessInterface roomDao,
            UserService userService) {
        this.presenter = presenter;
        this.choreDao = choreDao;
        this.roomDao = roomDao;
        this.userService = userService;
    }

    @Override
    public void execute(ChoreCreationInputData inputData) {
        try {

            User user = userService.getUser();
            if (user == null) {
                presenter.prepareFailView("User must be logged in.");
                return;
            }

            String roomId = roomDao.getUserRoomId(user.getId());
            if (roomId == null) {
                presenter.prepareFailView("User must be in a room.");
                return;
            }

            LocalDateTime dueDate;
            try {
                dueDate = LocalDateTime.parse(inputData.getDueDate());
            } catch (DateTimeParseException e) {
                presenter.prepareFailView("Invalid due date format. Use YYYY-MM-DDTHH:MM.");
                return;
            }

            String priority = inputData.getPriority();
            if (priority == null || priority.isBlank()) {
                priority = "Medium";
            }

            // Assigned user
            String assignedUserId = inputData.getAssignedUserId();
            if (assignedUserId == null || assignedUserId.isBlank()) {
                assignedUserId = user.getId();
            }

            String choreId = DomainIdGenerator.generateIdWithPrefix("chore");
            Chore chore = new Chore(
                    choreId,
                    roomId,
                    assignedUserId,
                    user.getId(),
                    inputData.getTitle(),
                    inputData.getDescription(),
                    dueDate,
                    ChoreStatus.PENDING,
                    false
            );
            choreDao.saveChore(chore);
            ChoreCreationOutputData output = new ChoreCreationOutputData(chore.getTitle());
            presenter.prepareSuccessView(output);

        } catch (ChoreCreationFailedException ex) {
            presenter.prepareFailView(ex.getMessage());
        } catch (Exception ex) {
            presenter.prepareFailView("Failed to create chore: " + ex.getMessage());
        }
    }

    @Override
    public void switchToDashboardView() {
        presenter.switchToDashboardView();
    }
}
