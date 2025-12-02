package interface_adapter.logged_in;

public class LoggedInState {
    private String activeTab = "dashboard";

    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getActiveTab() {
        return activeTab;
    }

    public void setActiveTab(String activeTab) {
        this.activeTab = activeTab;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
