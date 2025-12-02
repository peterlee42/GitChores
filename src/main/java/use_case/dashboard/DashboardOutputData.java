package use_case.dashboard;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class DashboardOutputData {
    private final boolean success;
    private final String currentUsername;
    private final String roomName;
    private final String roomDescription;
    private final String roomCode;
    private final Map<LocalDate, Integer> activityData;
    private final Map<LocalDate, List<String>> commitsMessages;

    public DashboardOutputData(boolean success,
            String currentUsername,
            String roomName,
            String roomDescription,
            String roomCode,
            Map<LocalDate, Integer> activityData,
            Map<LocalDate, List<String>> commitsMessages) {
        this.success = success;
        this.currentUsername = currentUsername;
        this.roomName = roomName;
        this.roomDescription = roomDescription;
        this.roomCode = roomCode;
        this.activityData = activityData;
        this.commitsMessages = commitsMessages;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getCurrentUsername() {
        return currentUsername;
    }

    public String getRoomName() {
        return roomName;
    }

    public String getRoomDescription() {
        return roomDescription;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public Map<LocalDate, Integer> getActivityData() {
        return activityData;
    }

    public Map<LocalDate, List<String>> getCommitsMessages() {
        return commitsMessages;
    }
}
