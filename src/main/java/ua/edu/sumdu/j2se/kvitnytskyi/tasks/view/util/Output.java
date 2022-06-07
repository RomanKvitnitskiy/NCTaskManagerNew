package ua.edu.sumdu.j2se.kvitnytskyi.tasks.view.util;

/**
 * Interface for displaying information in the console and storing message constants.
 *
 * @author kvitnytskyi
 */
public interface Output {

    /**
     * Table header for repeated task.
     */
    String HEADER_REPEATED = "#\tTitle\t\t\tStart\t\t\t\tEnd\t\t\t\tInterval\t\tActive";

    /**
     * Table header for not repeated task.
     */
    String HEADER_NOT_REPEATED = "#\tTitle\t\t\tTime\t\t\tActive";

    /**
     * Table header for calendar.
     */
    String CALENDAR_HEADER = "#========\t\tCALENDAR\t\t========#";

    /**
     * Table bottom for calendar.
     */
    String CALENDAR_BOTTOM = "#\t========\t\tEND\t\t========\t#";

    /**
     * Message about successful addition.
     */
    String SUCCESSFUL_ADD = "Addition the task was successful";

    /**
     * Message about failure addition.
     */
    String FAILURE_ADD = "Addition the task was failure";

    /**
     * Message about successful editing.
     */
    String SUCCESSFUL_EDIT = "Task editing was successful";

    /**
     * Message about failure editing.
     */
    String FAILURE_EDIT = "Task editing was failure";

    /**
     * Message about an empty list.
     */
    String EMPTY_LIST = "The task list is empty. " +
            "You can fill it by adding at least one task.";

    /**
     * Message about returning to the main menu.
     */
    String MAIN_MENU = "Returning to main menu";

    /**
     * Outputs the message to the console with line separator.
     *
     * @param message -
     */
    static void println(String message) {
        System.out.println(message);
    }

    /**
     * Outputs the message to the console in one line.
     *
     * @param message - output message
     */
    static void print(String message) {
        System.out.print(message);
    }

    /**
     * Displays the calendar date in the console.
     *
     * @param date - calendar date to output
     */
    static void printlnCalendarDate(String date) {
        println("@\t======\t" + date + "\t======\t@");
    }

    /**
     * Displays the title of the calendar task in the console.
     *
     * @param title - calendar title to output
     */
    static void printlnCalendarTask(String title) {
        println("\t\t@\t~~~\t" + title + "\t~~~\t@");
    }

    /**
     * Notifies the calendar date in the console.
     *
     * @param date - calendar date for notification
     */
    static void notifyCalendarDate(String date) {
        println("%\t!!!!!!\t" + date + "\t!!!!!!\t%");
    }

    /**
     * Notifies about the title of the calendar date in the console
     *
     * @param title - calendar title for notification
     */
    static void notifyCalendarTask(String title) {
        println("\t\t%\t&&&\t" + title + "\t&&&\t%");
    }
}
