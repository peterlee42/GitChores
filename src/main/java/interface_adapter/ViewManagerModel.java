package interface_adapter;

/**
 * Model for the View Manager. Its state is the name of the View which
 * is currently active. An initial state of "" is used.
 */
public class ViewManagerModel extends ViewModel<String> {
    /**
     * Model for the View Manager.
     */
    public ViewManagerModel() {
        super("view manager");
        this.setState("");
    }

    /**
     * Set the active view name (CardLayout key).
     *
     * @param viewName the view to display
     */
    public void setActiveViewName(final String viewName) {
        this.setState(viewName);
    }
}
