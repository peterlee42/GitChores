package interface_adapter.dashboard;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DashboardState {
    private String currentUsername;
    private String roomName;
    private String roomDescription;
    private String roomCode;
    private Map<LocalDate, Integer> activityData = new HashMap<>();
    private Map<LocalDate, List<String>> commitsMessages = new HashMap<>();
    private String errorMessage;

    public Map<LocalDate, Integer> getActivityData() {
        return activityData;
    }

    public String getCurrentUsername() {
        return currentUsername;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public String getRoomDescription() {
        return roomDescription;
    }

    public String getRoomName() {
        return roomName;
    }

    public Map<LocalDate, List<String>> getCommitsMessages() {
        return commitsMessages;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setActivityData(Map<LocalDate, Integer> activityData) {
        this.activityData = activityData;
    }

    public void setCurrentUsername(String currentUsername) {
        this.currentUsername = currentUsername;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public void setRoomDescription(String roomDescription) {
        this.roomDescription = roomDescription;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public void setCommitsMessages(Map<LocalDate, List<String>> commitsMessages) {
        this.commitsMessages = commitsMessages;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
