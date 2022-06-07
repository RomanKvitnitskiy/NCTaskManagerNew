package ua.edu.sumdu.j2se.kvitnytskyi.tasks.view;

import ua.edu.sumdu.j2se.kvitnytskyi.tasks.model.AbstractTaskList;

import java.time.LocalDateTime;

/**
 * Interface for displaying user menu into console.
 *
 * @author kvitnytskyi
 */
public interface View {

    /**
     * Displays application main menu.
     */
    void showMenu();

    /**
     * Displays application edit menu.
     */
    void editMenu();

    /**
     * Displays task list menu.
     *
     * @param list - general task list
     */
    void showTaskList(AbstractTaskList list);

    /**
     * Displays task calendar menu.
     *
     * @param list - general task list
     */
    void showCalendar(AbstractTaskList list);

    /**
     * Displays user choice menu.
     *
     * @return true if user input equal to yes, false if equal to no
     */
    boolean checkUserChoice();

    /**
     * Displays task title input menu.
     *
     * @return task title
     */
    String getTitle();

    /**
     * Displays task repeat status input menu.
     *
     * @return true if task is repeated, false if task is not repeated
     */
    boolean getRepeatStatus();

    /**
     * Displays task start time input menu.
     *
     * @return start time
     */
    LocalDateTime getStartTime();

    /**
     * Displays task end time input menu.
     *
     * @return end time
     */
    LocalDateTime getEndTime();

    /**
     * Display task interval input menu.
     *
     * @return interval
     */
    int getInterval();

    /**
     * Displays task time input menu.
     *
     * @return time
     */
    LocalDateTime getTime();

    /**
     * Displays task index for action input menu.
     *
     * @param list - general task list
     * @return task index
     */
    int getIndex(AbstractTaskList list);

    /**
     * Displays task activity input menu.
     *
     * @return true if task is active, false if task is inactive
     */
    boolean getActivity();
}
