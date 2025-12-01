package interface_adapter.main;

import interface_adapter.ViewModel;

public class MainViewModel extends ViewModel<MainState> {

    public MainViewModel() {
        super("main");
        setState(new MainState());
    }

}
