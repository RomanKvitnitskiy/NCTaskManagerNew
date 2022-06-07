package ua.edu.sumdu.j2se.kvitnytskyi.tasks.controller.notification;

import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.kvitnytskyi.tasks.model.Task;
import ua.edu.sumdu.j2se.kvitnytskyi.tasks.view.util.Output;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

/**
 * Class that implements the <strong>CalendarNotification</strong> interface.
 *
 * @author kvitnytskyi
 */
public class CalendarNotificationImpl implements CalendarNotification {

    private static final Logger log = Logger.getLogger(CalendarNotificationImpl.class);
    private static CalendarNotificationImpl instance;

    /**
     * Singleton.
     *
     * @return object of type CalendarNotification
     */
    public static CalendarNotificationImpl getInstance() {
        if (instance == null) {
            instance = new CalendarNotificationImpl();
        }
        return instance;
    }

    /**
     * Displays upcoming tasks to the user.
     *
     * @param calendar - general task calendar to notify
     */
    @Override
    public void notify(SortedMap<LocalDateTime, Set<Task>> calendar) {
        log.info("Execute notify command");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        Output.println("\n~Task Manager: @You have upcoming tasks@");
        Output.println("~Tasks in the near future~");
        Output.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        for (Map.Entry<LocalDateTime, Set<Task>> entry : calendar.entrySet()) {
            log.info("Getting calendar date for notify");
            String date = entry.getKey().format(formatter);
            Output.notifyCalendarDate(date);
            for (Task t : entry.getValue()) {
                log.info("Getting calendar tasks by date: " + date);
                Output.notifyCalendarTask(t.getTitle());
            }
            Output.println("");
        }
        Output.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        log.info("Notify command is completed");
    }
}
