package ua.edu.sumdu.j2se.kvitnytskyi.tasks.view;

import ua.edu.sumdu.j2se.kvitnytskyi.tasks.model.AbstractTaskList;

import java.time.LocalDateTime;

/**
 * Interface for displaying task information into console.
 *
 * @author kvitnytskyi
 */
public interface TaskView {

    /**
     * Displays available tasks into console.
     *
     * @param list - general task list
     */
    void showList(AbstractTaskList list);

    /**
     * Displays available task calendar into console.
     *
     * @param list - general task list
     */
    void getCalendar(AbstractTaskList list);

    /**
     * Gets task title from user input.
     *
     * @return task title
     */
    String getTitle();

    /**
     * Gets task repeat status from user input.
     *
     * @return repeat status
     */
    boolean getRepeatStatus();

    /**
     * Gets task start date from user input.
     *
     * @return start date of task
     */
    LocalDateTime getStartTime();

    /**
     * Gets task end date from user input.
     *
     * @return end date of task
     */
    LocalDateTime getEndTime();

    /**
     * Gets task interval from user input.
     *
     * @return task interval
     */
    int getInterval();

    /**
     * Gets task time from user input.
     *
     * @return task time
     */
    LocalDateTime getTime();

    /**
     * Gets index of the selected task.
     *
     * @param list - general task list
     * @return task index
     */
    int getTaskIndex(AbstractTaskList list);

    /**
     * Gets task activity status.
     *
     * @return task activity
     */
    boolean getActivity();
}
