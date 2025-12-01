package interface_adapter.main;

public class MainState {
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
