package ua.edu.sumdu.j2se.kvitnytskyi.tasks.controller.notification;

import ua.edu.sumdu.j2se.kvitnytskyi.tasks.model.Task;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.SortedMap;

/**
 * Interface for notifying the user about upcoming tasks.
 *
 * @author kvitnytskyi
 */
public interface CalendarNotification {

    /**
     * Notifies the user about upcoming tasks.
     *
     * @param calendar - general task calendar to notify
     */
    void notify(SortedMap<LocalDateTime, Set<Task>> calendar);
}
