package use_case.dashboard;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import entity.Commit;
import entity.Room;
import entity.User;
import use_case.commit.CommitDataAccessInterface;
import use_case.logged_in.UserService;
import use_case.room.RoomDataAccessInterface;

public class DashboardInteractor implements DashboardInputBoundary {
    private final DashboardOutputBoundary dashboardPresenter;
    private final UserService userService;
    private final RoomDataAccessInterface roomDataAccessObject;
    private final CommitDataAccessInterface commitDataAccessObject;

    public DashboardInteractor(DashboardOutputBoundary dashboardPresenter,
            UserService userService,
            RoomDataAccessInterface roomDataAccessObject,
            CommitDataAccessInterface commitDataAccessObject) {
        this.dashboardPresenter = dashboardPresenter;
        this.userService = userService;
        this.roomDataAccessObject = roomDataAccessObject;
        this.commitDataAccessObject = commitDataAccessObject;
    }

    @Override
    public void execute(DashboardInputData dashboardInputData) {
        final User user = userService.getUser();
        if (user == null) {
            dashboardPresenter.prepareFailView("User not logged in");
            return;
        }
        final String roomId = roomDataAccessObject.getUserRoomId(user.getId());
        if (roomId == null) {
            dashboardPresenter.prepareFailView("Room doesn't exist");
            return;
        }
        final Room room = roomDataAccessObject.getRoomById(roomId);
        if (room == null) {
            dashboardPresenter.prepareFailView("Room doesn't exist");
            return;
        }
        final List<Commit> commits = commitDataAccessObject.getCommitsForRoom(roomId);

        if (commits == null) {
            dashboardPresenter.prepareFailView("Could not retrieve commits");
            return;
        }

        final java.util.Map<LocalDate, Integer> counts = new HashMap<>();
        final java.util.Map<LocalDate, java.util.List<String>> messages = new HashMap<>();

        for (Commit commit : commits) {
            final LocalDate date = commit.getTimestamp().toLocalDate();
            counts.put(date, counts.getOrDefault(date, 0) + 1);
            messages.computeIfAbsent(date, key -> new ArrayList<>()).add(commit.getMessage());
        }

        final DashboardOutputData dashboardOutputData = new DashboardOutputData(true,
                user.getUsername(),
                room.getName(),
                room.getDescription(),
                room.getInviteCode(),
                counts,
                messages);

        dashboardPresenter.prepareSuccessView(dashboardOutputData);
    }

    @Override
    public void switchToChoreCreationView() {
        dashboardPresenter.presentChoreCreationView();
    }
}
