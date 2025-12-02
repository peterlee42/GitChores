package use_case.chore_creation;

public interface ChoreCreationInputBoundary {
    /**
     * Executes the chore creation use case.
     *
     * @param choreCreationInputData input data
     */
    void execute(ChoreCreationInputData choreCreationInputData);

    /**
     * Executes the switch to dashboard.
     */
    void switchToDashboardView();
}
