package interface_adapter.chore_creation;

import use_case.chore_creation.ChoreCreationInputBoundary;
import use_case.chore_creation.ChoreCreationInputData;

public class ChoreCreationController {

    private final ChoreCreationInputBoundary choreCreationInteractor;

    public ChoreCreationController(ChoreCreationInputBoundary choreCreationInteractor) {
        this.choreCreationInteractor = choreCreationInteractor;
    }

    /**
     * Executes the Create Chore Use Case.
     *
     * @param title          the chore title
     * @param description    the chore description
     * @param priority       the chore priority
     * @param dueDate        the chore due date as a string
     * @param assignedUserId the ID of the assigned user
     */
    public void execute(String title, String description, String priority, String dueDate, String assignedUserId) {
        final ChoreCreationInputData inputData =
                new ChoreCreationInputData(title, description, priority, dueDate, assignedUserId);
        choreCreationInteractor.execute(inputData);
    }

    /**
     * Executes the "switch to Signup View" use case.
     */
    public void switchToSignupView() {
        choreCreationInteractor.switchToSignupView();
    }
}
