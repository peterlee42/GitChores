package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.ToolTipManager;

import data_access.dynamo_db.CommitDataAccessObject;
import data_access.dynamo_db.DynamoDbClientFactory;

public class ActivityTilesPanel extends JPanel {
    private final Map<LocalDate, Integer> activityData;
    private final Map<LocalDate, List<String>> commitMessages;

    public ActivityTilesPanel() {
        this.activityData = new HashMap<>();
        this.commitMessages = new HashMap<>();
        setupPanel();
        setupTooltips();
    }

    private void setupPanel() {
        final int width = ViewConstants.WEEKS_TO_SHOW * (ViewConstants.TILE_SIZE + ViewConstants.TILE_GAP)
                + ViewConstants.TILE_GAP * 2;
        final int height = ViewConstants.DAYS_PER_WEEK * (ViewConstants.TILE_SIZE + ViewConstants.TILE_GAP)
                + ViewConstants.TILE_GAP * 2;

        setPreferredSize(new Dimension(width, height));
        setMinimumSize(new Dimension(width, height));
        setMaximumSize(new Dimension(width, height));
        setBackground(ViewColors.SAND_BACKGROUND);
    }

    private void setupTooltips() {
        ToolTipManager.sharedInstance().setInitialDelay(0);
        ToolTipManager.sharedInstance().setDismissDelay(ViewConstants.DISMISS_DELAY);

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent event) {
                updateTooltip(event.getPoint());
            }
        });
    }

    private void updateTooltip(Point mousePoint) {
        final LocalDate date = getDateAtPoint(mousePoint);
        if (date != null) {
            setToolTipText(generateTooltipText(date));
        } else {
            setToolTipText(null);
        }
    }

    private LocalDate getDateAtPoint(Point point) {
        final LocalDate today = LocalDate.now();
        final LocalDate startDate = today.minusWeeks(ViewConstants.WEEKS_TO_SHOW - 1);

        for (int week = 0; week < ViewConstants.WEEKS_TO_SHOW; week++) {
            for (int day = 0; day < ViewConstants.DAYS_PER_WEEK; day++) {
                final LocalDate currentDate = startDate.plusWeeks(week).plusDays(day);

                if (!currentDate.isAfter(today)) {
                    final int xPosition = week * (ViewConstants.TILE_SIZE + ViewConstants.TILE_GAP)
                            + ViewConstants.TILE_GAP;
                    final int yPosition = day * (ViewConstants.TILE_SIZE + ViewConstants.TILE_GAP)
                            + ViewConstants.TILE_GAP;

                    if (point.x >= xPosition && point.x <= xPosition + ViewConstants.TILE_SIZE
                            && point.y >= yPosition && point.y <= yPosition + ViewConstants.TILE_SIZE) {
                        return currentDate;
                    }
                }
            }
        }

        return null;
    }

    private String generateTooltipText(LocalDate date) {
        final Integer count = activityData.get(date);
        final List<String> messages = commitMessages.get(date);
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy");

        if (count == null || count == 0) {
            return String.format("<html><b>%s</b><br/>No chores completed</html>",
                    date.format(formatter));
        }

        final StringBuilder tooltip = new StringBuilder("<html>");
        tooltip.append(String.format("<b>%s</b><br/>", date.format(formatter)));

        final String suffix;
        if (count == 1) {
            suffix = "";
        } else {
            suffix = "s";
        }

        tooltip.append(String.format("<b>%d chore%s completed</b><br/><br/>",
                count, suffix));

        if (messages != null && !messages.isEmpty()) {
            for (int i = 0; i < messages.size(); i++) {
                final String message = messages.get(i);
                tooltip.append(String.format("%d. %s<br/>", i + 1, message));
            }
        }

        tooltip.append("</html>");
        return tooltip.toString();
    }

    /**
     * Adds commit.
     *
     * @param date      the date to add the activity for
     * @param message   the commit message to add
     */
    public void addCommit(LocalDate date, String message) {
        activityData.put(date, activityData.getOrDefault(date, 0) + 1);
        commitMessages.computeIfAbsent(date, key -> new ArrayList<>()).add(message);
    }

    /**
     * Sets the activity data to be displayed in the tiles.
     *
     * @param activityData a map where the key is the date and the value is the
     *                     number of chores completed on that date
     */
    public void setActivityData(Map<LocalDate, Integer> activityData) {
        this.activityData.clear();

        if (activityData != null) {
            this.activityData.putAll(activityData);
        }
        repaint();
    }

    /**
     * Sets the detailed activity data including commit messages.
     *
     * @param activityDataParam   a map where the key is the date and the value is
     *                            the
     *                            number of chores completed on that date
     * @param commitMessagesParam a map where the key is the date and the value is a
     *                            list
     *                            of commit messages for that date
     */
    public void setDetailedActivityData(Map<LocalDate, Integer> activityDataParam,
            Map<LocalDate, List<String>> commitMessagesParam) {
        this.activityData.clear();
        this.commitMessages.clear();
        if (activityDataParam != null) {
            this.activityData.putAll(activityDataParam);
        }
        if (commitMessagesParam != null) {
            this.commitMessages.putAll(commitMessagesParam);
        }
        repaint();
    }

    /**
     * Sets the activity count for a specific date.
     * 
     * @param date  the date to set the activity for
     * @param count the number of chores completed on that date
     */
    public void setActivityForDate(LocalDate date, int count) {
        activityData.put(date, count);
        repaint();
    }

    /**
     * Adds a commit message for a specific date.
     * 
     * @param date    the date to add the commit message for
     * @param message the commit message to add
     */
    public void addCommitForDate(LocalDate date, String message) {
        addCommit(date, message);
        repaint();
    }

    /**
     * Loads commit data for the given room.
     * TODO: Refactor to use a use-case interactor.
     *
     * @param roomId the room id to load commits for
     */
    public void loadFromCommitDao(String roomId) {
        if (roomId == null) {
            return;
        }

        new Thread(() -> fetchAndApplyCommits(roomId)).start();
    }

    // TODO: Refactor to use a use-case interactor.
    private void fetchAndApplyCommits(String roomId) {
        final CommitDataAccessObject dao = new CommitDataAccessObject(DynamoDbClientFactory.createClient());
        final java.util.List<entity.Commit> commits = dao.getCommitsForRoom(roomId);

        if (commits == null) {
            return;
        }

        final java.util.Map<LocalDate, Integer> counts = new HashMap<>();
        final java.util.Map<LocalDate, java.util.List<String>> messages = new HashMap<>();

        for (entity.Commit c : commits) {
            final LocalDate date = c.getTimestamp().toLocalDate();
            counts.put(date, counts.getOrDefault(date, 0) + 1);
            messages.computeIfAbsent(date, key -> new ArrayList<>()).add(c.getMessage());
        }

        javax.swing.SwingUtilities.invokeLater(() -> setDetailedActivityData(counts, messages));
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        final Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        final LocalDate today = LocalDate.now();
        final LocalDate startDate = today.minusWeeks(ViewConstants.WEEKS_TO_SHOW - 1);

        for (int week = 0; week < ViewConstants.WEEKS_TO_SHOW; week++) {
            for (int day = 0; day < ViewConstants.DAYS_PER_WEEK; day++) {
                final LocalDate currentDate = startDate.plusWeeks(week).plusDays(day);

                if (!currentDate.isAfter(today)) {
                    final int xPosition = week * (ViewConstants.TILE_SIZE + ViewConstants.TILE_GAP)
                            + ViewConstants.TILE_GAP;
                    final int yPosition = day * (ViewConstants.TILE_SIZE + ViewConstants.TILE_GAP)
                            + ViewConstants.TILE_GAP;

                    final Color tileColor = getColorForActivity(currentDate);
                    graphics2D.setColor(tileColor);
                    graphics2D.fillRoundRect(xPosition, yPosition, ViewConstants.TILE_SIZE,
                            ViewConstants.TILE_SIZE, 2, 2);
                }
            }
        }
    }

    private Color getColorForActivity(LocalDate date) {
        final Integer count = activityData.get(date);
        final int thresholdLevel1 = 2;
        final int thresholdLevel2 = 4;
        final int thresholdLevel3 = 6;

        if (count == null || count == 0) {
            return ViewColors.TILE_EMPTY;
        } else if (count <= thresholdLevel1) {
            return ViewColors.TILE_LEVEL_1;
        } else if (count <= thresholdLevel2) {
            return ViewColors.TILE_LEVEL_2;
        } else if (count <= thresholdLevel3) {
            return ViewColors.TILE_LEVEL_3;
        } else {
            return ViewColors.TILE_LEVEL_4;
        }
    }
}
