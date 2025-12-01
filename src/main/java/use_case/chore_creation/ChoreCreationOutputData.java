package use_case.chore_creation;

/**
 * The output data for Chore Creation.
 */
public class ChoreCreationOutputData {

    private final String title;

    /**
     * Constructs the output data.
     *
     * @param title the title of the created chore
     */
    public ChoreCreationOutputData(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}