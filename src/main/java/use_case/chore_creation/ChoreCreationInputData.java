package use_case.chore_creation;


public class ChoreCreationInputData {
    private String title;
    private String description;
    private String priority;
    private String dueDate;
    private String assignedUserId;
    public ChoreCreationInputData(String title, String description, String priority, String dueDate, String assignedUserId) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.dueDate = dueDate;
        this.assignedUserId = assignedUserId;
    }

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

    public String getAssignedUserId() {
        return assignedUserId;
    }
}
