package view;

import software.amazon.awssdk.services.dynamodb.endpoints.internal.Value;

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

public class ActivityTilesPanel extends JPanel {
    private static final int TILE_SIZE = 18;
    private static final int TILE_GAP = 4;
    private static final int WEEKS_TO_SHOW = 36;
    private static final int DAYS_PER_WEEK = 7;

    private static final Color TILE_EMPTY = new Color(235, 237, 240);
    private static final Color TILE_LEVEL_1 = new Color(155, 233, 168);
    private static final Color TILE_LEVEL_2 = new Color(64, 196, 99);
    private static final Color TILE_LEVEL_3 = new Color(48, 161, 78);
    private static final Color TILE_LEVEL_4 = new Color(33, 110, 57);

    private final Map<LocalDate, Integer> activityData;
    private final Map<LocalDate, List<String>> commitMessages;

    public ActivityTilesPanel() {
        this.activityData = new HashMap<>();
        this.commitMessages = new HashMap<>();
        setupPanel();
        setupTooltips();
    }

    public ActivityTilesPanel(Map<LocalDate, Integer> activityData) {
        this.activityData = activityData != null ? activityData : new HashMap<>();
        this.commitMessages = new HashMap<>();
        setupPanel();
        setupTooltips();
    }

    private void setupPanel() {
        final int width = WEEKS_TO_SHOW * (TILE_SIZE + TILE_GAP) + TILE_GAP * 2;
        final int height = DAYS_PER_WEEK * (TILE_SIZE + TILE_GAP) + TILE_GAP * 2;

        setPreferredSize(new Dimension(width, height));
        setMinimumSize(new Dimension(width, height));
        setMaximumSize(new Dimension(width, height));
        setBackground(Color.WHITE);
    }

    private void setupTooltips() {
        ToolTipManager.sharedInstance().setInitialDelay(0);
        ToolTipManager.sharedInstance().setDismissDelay(10000);

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
        }
        else {
            setToolTipText(null);
        }
    }

    private LocalDate getDateAtPoint(Point point) {
        final LocalDate today = LocalDate.now();
        final LocalDate startDate = today.minusWeeks(WEEKS_TO_SHOW - 1);

        for (int week = 0; week < WEEKS_TO_SHOW; week++) {
            for (int day = 0; day < DAYS_PER_WEEK; day++) {
                final LocalDate currentDate = startDate.plusWeeks(week).plusDays(day);

                if (!currentDate.isAfter(today)) {
                    final int xPosition = week * (TILE_SIZE + TILE_GAP) + TILE_GAP;
                    final int yPosition = day * (TILE_SIZE + TILE_GAP) + TILE_GAP;

                    if (point.x >= xPosition && point.x <= xPosition + TILE_SIZE
                            && point.y >= yPosition && point.y <= yPosition + TILE_SIZE) {
                        return currentDate;
                    }
                }
            }
        }

        return null;
    }

    private String generateTooltipText(LocalDate date) {
        final Integer count =  activityData.get(date);
        final List<String> messages = commitMessages.get(date);
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy");

        if (count == null || count == 0) {
            return String.format("<html><b>%s</b><br/>No chores completed</html>",
                    date.format(formatter));
        }

        final StringBuilder tooltip = new StringBuilder("<html>");
        tooltip.append(String.format("<b>%s</b><br/>", date.format(formatter)));
        tooltip.append(String.format("<b>%d chore%s completed</b><br/><br/>",
                count, count == 1 ? "" : "s"));

        if (messages != null && !messages.isEmpty()) {
            for (int i = 0; i < messages.size(); i++) {
                tooltip.append(String.format("• %s<br/>", messages.get(i)));
            }
        }

        tooltip.append("</html>");
        return tooltip.toString();
    }

    private void addCommit(LocalDate date, String message) {
        activityData.put(date, activityData.getOrDefault(date, 0) + 1);
        commitMessages.computeIfAbsent(date, k -> new ArrayList<>()).add(message);
    }

    public void setActivityData(Map<LocalDate, Integer> activityData) {
        this.activityData.clear();

        if (activityData != null) {
            this.activityData.putAll(activityData);
        }
        repaint();
    }

    public void setDetailedActivityData(Map<LocalDate, Integer> activityData,
                                        Map<LocalDate, List<String>> commitMessages) {
        this.activityData.clear();
        this.commitMessages.clear();
        if (activityData != null) {
            this.activityData.putAll(activityData);
        }
        if (commitMessages != null) {
            this.commitMessages.putAll(commitMessages);
        }
        repaint();
    }

    public void setActivityForDate(LocalDate date, int count) {
        activityData.put(date, count);
        repaint();
    }

    public void addCommitForDate(LocalDate date, String message) {
        addCommit(date, message);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        final Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        final LocalDate today = LocalDate.now();
        final LocalDate startDate = today.minusWeeks(WEEKS_TO_SHOW - 1);

        for (int week = 0; week < WEEKS_TO_SHOW; week++) {
            for (int day = 0; day < DAYS_PER_WEEK; day++) {
                final LocalDate currentDate = startDate.plusWeeks(week).plusDays(day);

                if (!currentDate.isAfter(today)) {
                    final int xPosition = week * (TILE_SIZE + TILE_GAP) + TILE_GAP;
                    final int yPosition = day * (TILE_SIZE + TILE_GAP) + TILE_GAP;

                    final Color tileColor = getColorForActivity(currentDate);
                    graphics2D.setColor(tileColor);
                    graphics2D.fillRoundRect(xPosition, yPosition, TILE_SIZE, TILE_SIZE, 2, 2);
                }
            }
        }
    }

    private Color getColorForActivity(LocalDate date) {
        final Integer count = activityData.get(date);
        if (count == null || count == 0) {
            return TILE_EMPTY;
        }
        else if (count <= 2) {
            return TILE_LEVEL_1;
        }
        else if (count <= 4) {
            return TILE_LEVEL_2;
        }
        else if (count <= 6) {
            return TILE_LEVEL_3;
        }
        else {
            return TILE_LEVEL_4;
        }
    }
}
