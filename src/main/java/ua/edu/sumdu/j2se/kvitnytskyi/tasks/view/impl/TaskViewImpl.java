package ua.edu.sumdu.j2se.kvitnytskyi.tasks.view.impl;

import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.kvitnytskyi.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.kvitnytskyi.tasks.model.Task;
import ua.edu.sumdu.j2se.kvitnytskyi.tasks.model.Tasks;
import ua.edu.sumdu.j2se.kvitnytskyi.tasks.view.TaskView;
import ua.edu.sumdu.j2se.kvitnytskyi.tasks.view.util.Output;
import ua.edu.sumdu.j2se.kvitnytskyi.tasks.view.util.TaskTime;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.SortedMap;

/**
 * Class that implements the <strong>TaskView</strong> interface.
 *
 * @author kvitnytskyi
 */
public class TaskViewImpl implements TaskView {

    private static final Logger log = Logger.getLogger(TaskViewImpl.class);
    private final Scanner scanner;

    /**
     * Constructor.
     *
     * @param scanner - scanner to search for user-entered data in the console
     */
    public TaskViewImpl(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Shows task list to user.
     *
     * @param list - general task list
     */
    @Override
    public void showList(AbstractTaskList list) {
        log.info("Showing all task list");
        if (list.size() == 0) {
            Output.println(Output.EMPTY_LIST);
            log.info("List is empty");
        } else {
            getList(list);
        }
    }

    private void getList(AbstractTaskList list) {
        log.info("Getting not empty task list");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        StringBuilder repeated = new StringBuilder();
        StringBuilder notRepeated = new StringBuilder();
        int i = 1;
        for (Task t : list) {
            String title = t.getTitle();
            String time = t.getTime().format(formatter);
            String start = t.getStartTime().format(formatter);
            String end = t.getEndTime().format(formatter);
            String interval = TaskTime.intervalFormatter(t.getRepeatInterval());
            String active = t.isActive() ? "active" : "inactive";

            if (t.isRepeated()) {
                log.info("Setting repeated task to general list");
                repeated.append(i++).append("\t")
                        .append(title).append("\t\t")
                        .append(start).append("\t")
                        .append(end).append("\t")
                        .append(interval).append("\t")
                        .append(active).append("\n");
            } else {
                log.info("Setting not repeated task to general list");
                notRepeated.append(i++).append("\t")
                        .append(title).append("\t\t")
                        .append(time).append("\t")
                        .append(active).append("\n");
            }
        }
        showRepeated(repeated);
        showNotRepeated(notRepeated);
    }

    private void showRepeated(StringBuilder repeated) {
        log.info("Printing repeated task list");
        Output.println(Output.HEADER_REPEATED);
        Output.print(repeated.toString());
    }

    private void showNotRepeated(StringBuilder notRepeated) {
        log.info("Printing not repeated task list");
        Output.println(Output.HEADER_NOT_REPEATED);
        Output.print(notRepeated.toString());
    }

    /**
     * Shows task calendar to user.
     *
     * @param list - general task list
     */
    @Override
    public void getCalendar(AbstractTaskList list) {
        log.info("Getting task calendar");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        while (true) {
            LocalDateTime start = getStartTime();
            LocalDateTime end = getEndTime();

            if (start.isBefore(end)) {
                SortedMap<LocalDateTime, Set<Task>> calendar =
                        Tasks.calendar(list, start, end);

                if (!calendar.isEmpty()) {
                    Output.println(Output.CALENDAR_HEADER);
                    for (Map.Entry<LocalDateTime, Set<Task>> entry : calendar.entrySet()) {
                        Output.printlnCalendarDate(entry.getKey().format(formatter));
                        for (Task t : entry.getValue()) {
                            Output.printlnCalendarTask(t.getTitle());
                        }
                        Output.println("");
                    }
                    Output.println(Output.CALENDAR_BOTTOM);
                } else {
                    log.info("Calendar is empty");
                    Output.println("Calendar is empty");
                }
                break;
            } else {
                log.info("Wrong date input, trying again");
                Output.println("Error. Enter start and end date correctly");
            }
        }
    }

    /**
     * Shows task title input menu to user.
     *
     * @return task title
     */
    @Override
    public String getTitle() {
        log.info("Getting task title");
        String title = "";
        Output.print("Input task title: ");
        while (title.isEmpty()) {
            title = scanner.nextLine();
            if (title.isEmpty()) {
                Output.print("Try again. Task title: ");
            }
        }
        return title;
    }

    /**
     * Shows task repeat status input menu to user.
     *
     * @return repeat status
     */
    @Override
    public boolean getRepeatStatus() {
        log.info("Getting repeated status");
        String repeat = "";
        boolean repeatStatus = false;
        Output.print("Input task repeat status (true:false): ");
        while (repeat.isEmpty()
                || (!repeat.equalsIgnoreCase("true")
                && !repeat.equalsIgnoreCase("false"))) {
            repeat = scanner.nextLine();
            if (repeat.isEmpty() || (!repeat.equalsIgnoreCase("true")
                    && !repeat.equalsIgnoreCase("false"))) {
                Output.print("Try again. Repeat status (true:false): ");
            } else {
                repeatStatus = Boolean.parseBoolean(repeat.toLowerCase());
            }
        }
        return repeatStatus;
    }

    /**
     * Shows task start time input menu to user.
     *
     * @return start time
     */
    @Override
    public LocalDateTime getStartTime() {
        log.info("Getting start time");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        String date;
        LocalDateTime start;
        Output.print("Enter start date (dd-MM-yyyy HH:mm): ");
        while (true) {
            try {
                date = scanner.nextLine();
                start = LocalDateTime.parse(date, formatter);
                break;
            } catch (DateTimeParseException e) {
                Output.print("Error. Try input start date again (dd-MM-yyyy HH:mm): ");
                log.error(e);
            }
        }
        return start;
    }

    /**
     * Shows task end time input menu to user.
     *
     * @return end time
     */
    @Override
    public LocalDateTime getEndTime() {
        log.info("Getting end time");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        String date;
        LocalDateTime end;
        Output.print("Enter end date (dd-MM-yyyy HH:mm): ");
        while (true) {
            try {
                date = scanner.nextLine();
                end = LocalDateTime.parse(date, formatter);
                break;
            } catch (DateTimeParseException e) {
                Output.print("Error. Try input end date again (dd-MM-yyyy HH:mm): ");
                log.error(e);
            }
        }
        return end;
    }

    /**
     * Shows task interval input menu to user.
     *
     * @return interval
     */
    @Override
    public int getInterval() {
        log.info("Getting interval");
        int interval;
        Output.print("Enter the task repetition interval in minutes: ");
        while (true) {
            interval = Integer.parseInt(scanner.nextLine());
            if (interval > 0) {
                return interval * 60;
            } else {
                Output.print("Try again. Enter interval in minutes: ");
            }
        }
    }

    /**
     * Shows task time input menu to user.
     *
     * @return time
     */
    @Override
    public LocalDateTime getTime() {
        log.info("Getting time");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        String date;
        LocalDateTime time;
        Output.print("Enter task date (dd-MM-yyyy HH:mm): ");
        while (true) {
            try {
                date = scanner.nextLine();
                time = LocalDateTime.parse(date, formatter);
                break;
            } catch (DateTimeParseException e) {
                Output.print("Error. Try input task date again (dd-MM-yyyy HH:mm): ");
                log.error(e);
            }
        }
        return time;
    }

    /**
     * Shows task index input menu to user.
     *
     * @param list - general task list
     * @return index for action
     */
    @Override
    public int getTaskIndex(AbstractTaskList list) {
        log.info("Getting index for action");
        int id = 0;
        if (list.size() == 0) {
            Output.println(Output.EMPTY_LIST);
        } else {
            showList(list);
            Output.print("Enter the task id: ");
            while (true) {
                id = scanner.nextInt();
                scanner.nextLine();
                if (id > 0 && id <= list.size()) return id;
                Output.print("Try again. Enter task id: ");
            }
        }
        return id;
    }

    /**
     * Shows task activity status input menu to user.
     *
     * @return activity status
     */
    @Override
    public boolean getActivity() {
        log.info("Getting task activity");
        String active = "";
        boolean activeStatus = false;
        Output.print("Input task activity status (true:false): ");
        while (active.isEmpty()
                || (!active.equalsIgnoreCase("true")
                && !active.equalsIgnoreCase("false"))) {
            active = scanner.nextLine();
            if (active.isEmpty() || (!active.equalsIgnoreCase("true")
                    && !active.equalsIgnoreCase("false"))) {
                Output.print("Try again. Activity status (true:false): ");
            } else {
                activeStatus = Boolean.parseBoolean(active.toLowerCase());
            }
        }
        return activeStatus;
    }

}
