package view;

import java.awt.*;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

/** Centralized UI constants. */
public final class ViewConstants {

    // ---- Card names ----
    public static final String APPLICATION_TITLE = "GitChores";
    public static final String JOIN_VIEW_NAME = "join/create";
    public static final String SIGNUP_VIEW_NAME = "sign up";
    public static final String PROFILE_VIEW_NAME = "profile";

    // ---- Labels ----
    public static final String JOIN_TITLE_TEXT = "GitChores";
    public static final String PROFILE_TITLE_TEXT = "Profile";
    public static final String USERNAME_LABEL_TEXT = "Username:";
    public static final String EMAIL_LABEL_TEXT = "Email:";
    public static final String SAVE_BUTTON_TEXT = "Save";
    public static final String BACK_BUTTON_TEXT = "Back";
    public static final String JOIN_BUTTON_TEXT = "Join Room";
    public static final String CREATE_BUTTON_TEXT = "Room";
    public static final String PROFILE_BUTTON_TEXT = "Profile";
    public static final String CHANGE_PHOTO_BUTTON_TEXT = "Change Photo";
    public static final String LEAVE_ROOM_BUTTON_TEXT = "Leave Room";
    public static final String LOGOUT_BUTTON_TEXT = "Log Out";

    // ---- Error Labels ----
    public static final String ERROR_PREFIX = "Error: ";

    // ---- Layout ----
    public static final int TEXT_FIELD_COLUMNS = 24;
    public static final int V_GAP = 16;
    public static final int PROFILE_MAIN_CENTER_GAP = V_GAP * 3;
    public static final int DASHBOARD_PANEL_PADDING = 20;
    public static final int DASHBOARD_COMPONENT_SPACING = 15;

    // ---- Profile photo ----
    public static final int PROFILE_PHOTO_WIDTH = 140;
    public static final int PROFILE_PHOTO_HEIGHT = 140;
    public static final int PROFILE_PHOTO_BORDER_THICKNESS = 2;

    // ---- Font ----
    public static final String FONT_FAMILY = "Inter";

    // ---- Font Sizes ----
    public static final int FONT_SIZE_SMALL = 12;
    public static final int FONT_SIZE_REGULAR = 14;
    public static final int FONT_SIZE_MEDIUM = 16;
    public static final int FONT_SIZE_LARGE = 20;
    public static final int FONT_SIZE_TITLE = 24;
    public static final int FONT_SIZE_HEADER = 32;

    // ---- Common Fonts ----
    public static final Font LABEL_FONT = new Font(FONT_FAMILY, Font.PLAIN, FONT_SIZE_REGULAR);
    public static final Font LABEL_BOLD_FONT = new Font(FONT_FAMILY, Font.BOLD, FONT_SIZE_REGULAR);
    public static final Font SMALL_FONT = new Font(FONT_FAMILY, Font.PLAIN, FONT_SIZE_SMALL);
    public static final Font MEDIUM_FONT = new Font(FONT_FAMILY, Font.PLAIN, FONT_SIZE_MEDIUM);
    public static final Font TITLE_FONT = new Font(FONT_FAMILY, Font.BOLD, FONT_SIZE_TITLE);
    public static final Font WELCOME_FONT = new Font(FONT_FAMILY, Font.BOLD, FONT_SIZE_TITLE);
    public static final Font HEADER_FONT = new Font(FONT_FAMILY, Font.BOLD, FONT_SIZE_HEADER);
    public static final Font BUTTON_FONT = new Font(FONT_FAMILY, Font.BOLD, FONT_SIZE_REGULAR);

    // ---- Border ----
    public static final Border EMPTY_BORDER = BorderFactory.createEmptyBorder(5, 15, 5, 15);
    public static final Border DEFAULT_BUTTON_FOCUS_BORDER = BorderFactory.createLineBorder(ViewColors.DARK_BLUE, 2);

    // ---- Dashboard Constants ----
    public static final double DASHBOARD_WEIGHTX = 1.0;
    public static final double DASHBOARD_WEIGHTY = 0.3;
    public static final int DASHBOARD_BORDER = 10;
    public static final int DASHBOARD_230 = 230;

    // ---- Join Constants ----
    public static final int PANEL_PADDING = 40;
    public static final int COMPONENT_SPACING = 15;
    public static final int JOIN_TEXT_FIELD_COLUMNS = 20;
    public static final int INVITE_CODE_COLUMNS = 10;
    public static final int SEPARATOR_HEIGHT = 30;
    public static final int SPACING_20 = 20;
    public static final int SPACING_15 = 15;
    public static final int SPACING_10 = 10;
    public static final int SPACING_5 = 5;
    public static final int FIELD_HEIGHT = 30;
    public static final int FIELD_WIDTH = 300;
    public static final int CODE_FIELD_WIDTH = 200;
    public static final int BORDER_WIDTH = 1;
    public static final int BORDER_COLOR = 230;

    // ---- Activity Tiles Constants ----
    public static final int TILE_SIZE = 18;
    public static final int TILE_GAP = 4;
    public static final int WEEKS_TO_SHOW = 36;
    public static final int DAYS_PER_WEEK = 7;
    public static final int DISMISS_DELAY = 10000;

    // ---- Insets ----
    public static final Insets TEXT_FIELD_INSETS = new Insets(5, 5, 5, 5);

    private ViewConstants() {
    }
}
