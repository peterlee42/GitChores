package interface_adapter.chore_creation;

/**
 * The state for the Chore View Model.
 */
public class ChoreCreationState {
    private String title = "";
    private String description = "";
    private String priority = "Low";
    private String dueDate = "";
    private String assignedUser = "";
    private String choreError;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPriority() {
        return priority;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getAssignedUser() {
        return assignedUser;
    }

    public String getChoreError() {
        return choreError;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public void setAssignedUser(String assignedUser) {
        this.assignedUser = assignedUser;
    }

    public void setChoreError(String choreError) {
        this.choreError = choreError;
    }
}
