package app;

import java.awt.*;

import javax.swing.*;

import data_access.SessionDataAccessObject;
import data_access.cognito.CognitoUserDataAccessObject;
import data_access.cognito.IdentityProviderClientSingleton;
import data_access.dynamo_db.CommitDataAccessObject;
import data_access.dynamo_db.DynamoDbClientSingleton;
import data_access.dynamo_db.RoomDataAccessObject;
import data_access.dynamo_db.RoomMetadataDataAccessObject;
import interface_adapter.ViewManagerModel;
import interface_adapter.commit.CommitController;
import interface_adapter.commit.CommitPresenter;
import interface_adapter.git_console.GitConsoleController;
import interface_adapter.git_console.GitConsolePresenter;
import interface_adapter.git_console.GitConsoleViewModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;
import interface_adapter.main.MainViewModel;
import interface_adapter.profile.ProfileController;
import interface_adapter.profile.ProfilePresenter;
import interface_adapter.profile.ProfileViewModel;
import interface_adapter.room.create.CreateRoomController;
import interface_adapter.room.create.CreateRoomPresenter;
import interface_adapter.room.create.CreateRoomViewModel;
import interface_adapter.room.join.JoinRoomController;
import interface_adapter.room.join.JoinRoomPresenter;
import interface_adapter.room.join.JoinViewModel;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupPresenter;
import interface_adapter.signup.SignupViewModel;
import use_case.commit.CommitDataAccessInterface;
import use_case.commit.CommitInputBoundary;
import use_case.commit.CommitInteractor;
import use_case.commit.RoomMetadataDataAccessInterface;
import use_case.git_console.GitConsoleInputBoundary;
import use_case.git_console.GitConsoleInteractor;
import use_case.git_console.GitConsoleOutputBoundary;
import use_case.logged_in.UserService;
import use_case.login.LoginDataAccessInterface;
import use_case.login.LoginInputBoundary;
import use_case.login.LoginInteractor;
import use_case.login.LoginOutputBoundary;
import use_case.profile.UpdateProfileInputBoundary;
import use_case.profile.UpdateProfileInteractor;
import use_case.profile.UpdateProfileOutputBoundary;
import use_case.room.RoomDataAccessInterface;
import use_case.room.create.CreateRoomInputBoundary;
import use_case.room.create.CreateRoomInteractor;
import use_case.room.join.JoinRoomInputBoundary;
import use_case.room.join.JoinRoomInteractor;
import use_case.signup.SignupDataAccessInterface;
import use_case.signup.SignupInputBoundary;
import use_case.signup.SignupInteractor;
import use_case.signup.SignupOutputBoundary;
import view.CreateRoomView;
import view.DashboardView;
import view.GitConsoleView;
import view.JoinView;
import view.LoginView;
import view.MainView;
import view.ProfileView;
import view.SignupView;
import view.ViewManager;

/**
 * Class for building the app.
 */
@SuppressWarnings({ "checkstyle:ClassDataAbstractionCoupling", "ClassFanOutComplexityCheck",
        "checkstyle:SuppressWarnings" })
public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();

    private MainView mainView;
    private MainViewModel mainViewModel;
    private JoinView joinView;
    private JoinViewModel joinViewModel;
    private SignupView signupView;
    private SignupViewModel signupViewModel;
    private LoginView loginView;
    private LoginViewModel loginViewModel;
    private DashboardView dashboardView;
    private GitConsoleView gitConsoleView;
    private GitConsoleViewModel gitConsoleViewModel;
    private CreateRoomView createRoomView;
    private CreateRoomViewModel createRoomViewModel;
    private ProfileView profileView;
    private ProfileViewModel profileViewModel;

    private SessionDataAccessObject sessionDataAccess;
    private final CognitoUserDataAccessObject userDataAccess;

    private final UserService userService;

    /**
     * Constructor for AppBuilder.
     */
    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
        final ViewManager viewManager = new ViewManager(cardPanel, cardLayout);
        viewManagerModel.addPropertyChangeListener(viewManager);
        this.sessionDataAccess = new SessionDataAccessObject();
        this.userDataAccess = new CognitoUserDataAccessObject(IdentityProviderClientSingleton.getInstance());
        this.userService = new UserService(userDataAccess, sessionDataAccess);
    }

    /**
     * Adds main view.
     *
     * @return AppBuilder
     */
    public AppBuilder addMainView() {
        mainViewModel = new MainViewModel();
        mainView = new MainView(mainViewModel, dashboardView, gitConsoleView, profileView);
        cardPanel.add(mainView, mainView.getViewName());
        return this;
    }

    /**
     * Adds dashboard view - incomplete.
     *
     * @return AppBuilder
     */
    public AppBuilder addDashboardView() {
        dashboardView = new DashboardView();
        return this;
    }

    /**
     * Adds join view.
     *
     * @return AppBuilder
     */
    public AppBuilder addJoinView() {
        joinViewModel = new JoinViewModel();
        joinView = new JoinView(joinViewModel);
        cardPanel.add(joinView, joinView.getViewName());
        return this;
    }

    /**
     * Adds create room view.
     *
     * @return AppBuilder
     */
    public AppBuilder addCreateRoomView() {
        createRoomViewModel = new CreateRoomViewModel();
        createRoomView = new CreateRoomView(createRoomViewModel);
        cardPanel.add(createRoomView, createRoomView.getViewName());
        return this;
    }

    /**
     * Adds Sign Up View.
     *
     * @return AppBuilder
     */
    public AppBuilder addSignupView() {
        signupViewModel = new SignupViewModel();
        signupView = new SignupView(signupViewModel);
        cardPanel.add(signupView, signupView.getViewName());

        return this;
    }

    /**
     * Adds Login View.
     *
     * @return AppBuilder
     */
    public AppBuilder addLoginView() {
        loginViewModel = new LoginViewModel();
        loginView = new LoginView(loginViewModel);
        cardPanel.add(loginView, loginView.getViewName());

        return this;
    }

    /**
     * Adds Git Console view.
     *
     * @return AppBuilder
     */
    public AppBuilder addGitConsoleView() {
        gitConsoleViewModel = new GitConsoleViewModel();
        gitConsoleView = new GitConsoleView(gitConsoleViewModel);
        return this;
    }

    /**
     * Adds Git Console use case.
     *
     * @return AppBuilder
     */
    public AppBuilder addGitConsoleUseCase() {

        final GitConsoleOutputBoundary gitConsoleOutputBoundary = new GitConsolePresenter(gitConsoleViewModel);

        // Commit Use case Layer (backend logic)
        final CommitDataAccessInterface commitDataAccess = new CommitDataAccessObject(
                DynamoDbClientSingleton.getInstance());
        final RoomMetadataDataAccessInterface roomMetadataDataAccess = new RoomMetadataDataAccessObject(
                DynamoDbClientSingleton.getInstance());
        final CommitPresenter commitPresenter = new CommitPresenter();
        final CommitInputBoundary commitInteractor = new CommitInteractor(commitDataAccess,
                roomMetadataDataAccess, commitPresenter);
        final CommitController commitController = new CommitController(commitInteractor);
        final RoomMetadataDataAccessObject roomMetadataDataAccessObject = new RoomMetadataDataAccessObject(
                DynamoDbClientSingleton.getInstance());
        final RoomDataAccessInterface roomDataAccess = new RoomDataAccessObject(DynamoDbClientSingleton.getInstance());

        // If the dashboard exists and we can determine a current room, load activity
        // tiles
        // TODO: Refactor to use a proper use case interactor
        if (dashboardView != null) {
            final entity.User current = userService.getUser();
            if (current != null) {
                final String currentRoomId = roomDataAccess.getUserRoomId(current.getId());
                if (currentRoomId != null) {
                    dashboardView.loadActivity(currentRoomId);
                }
            } else {
                // Demo fallback: allow overriding a room id via system property for local
                // testing
                final String demoRoomId = System.getProperty("demo.roomId");
                if (demoRoomId != null && !demoRoomId.isBlank()) {
                    dashboardView.loadActivity(demoRoomId);
                }
            }
        }

        // Git Console Use Case Layer
        final GitConsoleInputBoundary gitConsoleInteractor = new GitConsoleInteractor(gitConsoleOutputBoundary,
                commitController, commitPresenter, roomMetadataDataAccessObject,
                userService, roomDataAccess);

        final GitConsoleController controller = new GitConsoleController(gitConsoleInteractor);
        gitConsoleView.setGitConsoleController(controller);
        return this;
    }

    /**
     * Adds Profile view.
     *
     * @return AppBuilder
     */
    public AppBuilder addProfileView() {
        profileViewModel = new ProfileViewModel();
        profileView = new ProfileView(profileViewModel);
        return this;
    }

    /**
     * Adds room use cases (Create and Join).
     *
     * @return AppBuilder
     */
    public AppBuilder addRoomUseCases() {
        if (joinView == null || joinViewModel == null) {
            return this;
        }
        if (createRoomView == null || createRoomViewModel == null) {
            return this;
        }

        final RoomDataAccessInterface roomDataAccess = new RoomDataAccessObject(DynamoDbClientSingleton.getInstance());

        // Create Room use case
        final CreateRoomPresenter createRoomPresenter = new CreateRoomPresenter(createRoomViewModel,
                viewManagerModel, loginViewModel, joinViewModel, mainViewModel);
        final CreateRoomInputBoundary createRoomInteractor = new CreateRoomInteractor(roomDataAccess,
                sessionDataAccess, createRoomPresenter, userService);
        final CreateRoomController createRoomController = new CreateRoomController(createRoomInteractor);

        createRoomView.setCreateRoomController(createRoomController);

        // Join Room use case (reuse existing JoinViewModel)
        final JoinRoomPresenter joinRoomPresenter = new JoinRoomPresenter(joinViewModel, viewManagerModel,
                loginViewModel, createRoomViewModel, mainViewModel);
        final JoinRoomInputBoundary joinRoomInteractor = new JoinRoomInteractor(roomDataAccess,
                sessionDataAccess, joinRoomPresenter, userService);
        final JoinRoomController joinRoomController = new JoinRoomController(joinRoomInteractor);

        joinView.setJoinRoomController(joinRoomController);

        return this;
    }

    /**
     * Adds Signup use case.
     *
     * @return AppBuilder
     */
    public AppBuilder addSignupUseCase() {
        final SignupDataAccessInterface signupDataAccess = userDataAccess;
        final SignupOutputBoundary signupOutputBoundary = new SignupPresenter(viewManagerModel, signupViewModel,
                loginViewModel);
        final SignupInputBoundary signupInteractor = new SignupInteractor(signupOutputBoundary, signupDataAccess);

        final SignupController controller = new SignupController(signupInteractor);
        signupView.setSignupController(controller);
        return this;
    }

    /**
     * Adds Login use case.
     *
     * @return AppBuilder
     */
    public AppBuilder addLoginUseCase() {
        final LoginDataAccessInterface loginDataAccess = userDataAccess;
        final RoomDataAccessInterface roomDataAccess = new RoomDataAccessObject(DynamoDbClientSingleton.getInstance());
        final LoginOutputBoundary loginOutputBoundary = new LoginPresenter(viewManagerModel, loginViewModel,
                signupViewModel, joinViewModel);
        final LoginInputBoundary loginInteractor = new LoginInteractor(loginOutputBoundary, loginDataAccess,
                sessionDataAccess, roomDataAccess);

        final LoginController controller = new LoginController(loginInteractor);
        loginView.setLoginController(controller);
        return this;
    }

    /**
     * Adds Profile use case.
     * 
     * @return AppBuilder
     */
    public AppBuilder addProfileUseCase() {
        final RoomDataAccessInterface roomDataAccess = new RoomDataAccessObject(DynamoDbClientSingleton.getInstance());
        final UpdateProfileOutputBoundary updateProfileOutputBoundary = new ProfilePresenter(viewManagerModel,
                profileViewModel, loginViewModel, joinViewModel);
        final UpdateProfileInputBoundary updateProfileInteractor = new UpdateProfileInteractor(
                updateProfileOutputBoundary, sessionDataAccess, roomDataAccess, userService);

        final ProfileController controller = new ProfileController(updateProfileInteractor);
        profileView.setProfileController(controller);
        return this;
    }

    /**
     * Builds the view.
     *
     * @return JFrame
     */
    public JFrame build() {
        final JFrame application = new JFrame("GitChores");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        application.add(cardPanel);

        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final int defaultWidth = (int) (screenSize.getWidth() * 0.7);
        final int defaultHeight = (int) (screenSize.getHeight() * 0.7);
        final int minWidth = (int) (screenSize.getWidth() * 0.5);
        final int minHeight = (int) (screenSize.getHeight() * 0.5);

        application.setPreferredSize(new Dimension(defaultWidth, defaultHeight));
        application.setMinimumSize(new Dimension(minWidth, minHeight));

        application.pack();

        // Start on a sensible screen if the ViewManager is wired.
        if (viewManagerModel != null) {
            if (mainView != null) {
                viewManagerModel.setActiveViewName(mainView.getViewName());
            } else if (signupView != null) {
                viewManagerModel.setActiveViewName(signupView.getViewName());
            } else if (joinView != null) {
                viewManagerModel.setActiveViewName(joinView.getViewName());
            } else if (gitConsoleView != null) {
                viewManagerModel.setActiveViewName(gitConsoleView.getViewName());
            }
        }

        return application;
    }
}
