package view;

import java.time.LocalDate;
import java.util.Random;

/**
 * Utility class to initialize sample data for the ActivityTilesPanel.
 */
public final class ViewSampleData {

    private ViewSampleData() {
        // utility class
    }

    /**
     * Initializes the given ActivityTilesPanel with sample data representing chore activity.
     *
     * @param panel the ActivityTilesPanel to populate with sample data
     */
    public static void initializeSampleData(ActivityTilesPanel panel) {
        initializeSampleData(panel, ViewConstants.WEEKS_TO_SHOW);
    }

    /**
     * Initializes the given ActivityTilesPanel with sample data representing chore activity.
     *
     * @param panel the ActivityTilesPanel to populate with sample data
     * @param weeks how many weeks in the past to generate data for
     */
    @SuppressWarnings({"checkstyle:ExecutableStatementCount", "checkstyle:JavaNCSS",
            "checkstyle:MultipleStringLiterals", "checkstyle:MagicNumber"})
    public static void initializeSampleData(ActivityTilesPanel panel, int weeks) {
        final int safeWeeks = Math.max(1, weeks);
        final LocalDate today = LocalDate.now();
        final LocalDate start = today.minusWeeks(safeWeeks).plusDays(1);

        for (LocalDate d = start; !d.isAfter(today); d = d.plusDays(1)) {
            // Deterministic pseudo-random per day
            final int commits = getCommits(d);

            for (int i = 1; i <= commits; i++) {
                panel.addCommitForDate(d, String.format("Sample chore %s #%d", d, i));
            }
        }
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    private static int getCommits(LocalDate day) {
        final Random rnd = new Random(day.toEpochDay());

        final int dayOfWeek = day.getDayOfWeek().getValue();
        final int base = rnd.nextInt(3);
        int weekendExtra = 0;
        if (dayOfWeek == 6 || dayOfWeek == 7) {
            weekendExtra = rnd.nextInt(3);
        }

        final boolean monthlySpike = Math.abs(day.toEpochDay() % 30) == 0;
        int spikeExtra = 0;
        if (monthlySpike) {
            spikeExtra = 1 + rnd.nextInt(3);
        }

        return base + weekendExtra + spikeExtra;
    }
}
