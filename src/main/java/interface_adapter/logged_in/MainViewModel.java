package interface_adapter.logged_in;

import interface_adapter.ViewModel;

public class MainViewModel extends ViewModel<TokenState> {
    public static final int NAV_BUTTON_BORDER = 8;
    public static final int NAV_BAR_HEIGHT = 32;

    /**
     * Constructor for a MainViewModel.
     */
    public MainViewModel() {
        super("main");
        setState(new TokenState());
    }
}
