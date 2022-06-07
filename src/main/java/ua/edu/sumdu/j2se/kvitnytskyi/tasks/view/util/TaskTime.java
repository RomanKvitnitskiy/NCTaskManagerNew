package ua.edu.sumdu.j2se.kvitnytskyi.tasks.view.util;

/**
 * Util class to convert time.
 *
 * @author kvitnytskyi
 */
public final class TaskTime {

    private static final int SECONDS_IN_HOUR = 3600;
    private static final int SECONDS_IN_DAY = SECONDS_IN_HOUR * 24;
    private static final int SECONDS_IN_MINUTE = 60;

    /**
     * Returns converted task interval.
     *
     * @param repeatInterval - task interval for converting
     * @return String representation of the interval
     */
    public static String intervalFormatter(int repeatInterval) {
        StringBuilder interval = new StringBuilder();

        int days = repeatInterval / SECONDS_IN_DAY;
        if (days > 0) interval.append(getDays(days));
        repeatInterval %= SECONDS_IN_DAY;

        int hours = repeatInterval / SECONDS_IN_HOUR;
        if (hours > 0) interval.append(getHours(hours));
        repeatInterval %= SECONDS_IN_HOUR;

        int minutes = repeatInterval / SECONDS_IN_MINUTE;
        if (minutes > 0) interval.append(getMinutes(minutes));
        repeatInterval %= SECONDS_IN_MINUTE;

        int seconds = repeatInterval;
        if (seconds > 0) interval.append(getSeconds(seconds));

        return interval.toString();
    }

    private static String getDays(int d) {
        StringBuilder days = new StringBuilder();
        if (d > 1) return days.append(d).append(" ").append("days ").toString();
        return days.append(d).append(" ").append("day ").toString();
    }

    private static String getHours(int h) {
        StringBuilder hours = new StringBuilder();
        if (h > 1) return hours.append(h).append(" ").append("hours ").toString();
        return hours.append(h).append(" ").append("hour ").toString();
    }

    private static String getMinutes(int m) {
        StringBuilder minutes = new StringBuilder();
        if (m > 1) return minutes.append(m).append(" ").append("minutes ").toString();
        return minutes.append(m).append(" ").append("minute ").toString();
    }

    private static String getSeconds(int s) {
        StringBuilder seconds = new StringBuilder();
        if (s > 1) return seconds.append(s).append(" ").append("seconds ").toString();
        return seconds.append(s).append(" ").append("second ").toString();
    }
}
