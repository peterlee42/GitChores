package interface_adapter.dashboard;

import interface_adapter.ViewModel;

public class DashboardViewModel extends ViewModel<DashboardState> {
    /**
     * Constructor for a DashboardViewModel.
     */
    public DashboardViewModel() {
        super("dashboard");
        setState(new DashboardState());
    }

}
