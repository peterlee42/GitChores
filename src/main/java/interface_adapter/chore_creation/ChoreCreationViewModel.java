package interface_adapter.chore_creation;

import interface_adapter.ViewModel;

/**
 * The ViewModel for the Chore view.
 */
public class ChoreCreationViewModel extends ViewModel<ChoreCreationState> {

    public static final String TITLE_LABEL = "Create a Chore";
    public static final String TITLE_FIELD_LABEL = "Title";
    public static final String DESCRIPTION_FIELD_LABEL = "Description (optional)";
    public static final String PRIORITY_FIELD_LABEL = "Priority";
    public static final String DUE_DATE_FIELD_LABEL = "Due Date (YYYY-MM-DDTHH:MM)";
    public static final String ASSIGNED_USER_FIELD_LABEL = "Assigned User (optional)";
    public static final String CREATE_BUTTON_LABEL = "Create Chore";
    public static final String CANCEL_BUTTON_LABEL = "Cancel Creation";

    public ChoreCreationViewModel() {
        super("chore creation");
        setState(new ChoreCreationState());
    }
}
