package ua.edu.sumdu.j2se.kvitnytskyi.tasks.controller.impl;

import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.kvitnytskyi.tasks.controller.TaskController;
import ua.edu.sumdu.j2se.kvitnytskyi.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.kvitnytskyi.tasks.model.Task;
import ua.edu.sumdu.j2se.kvitnytskyi.tasks.view.View;
import ua.edu.sumdu.j2se.kvitnytskyi.tasks.view.util.Output;

import java.time.LocalDateTime;
import java.util.Scanner;

/**
 * Class that implements the TaskController interface.
 *
 * @author kvitnytskyi
 */
public class TaskControllerImpl implements TaskController {

    private static final Logger log = Logger.getLogger(TaskControllerImpl.class);
    private final Scanner scanner;
    private final View view;
    private final AbstractTaskList list;

    /**
     * Constructor.
     *
     * @param list - general task list
     * @param view - view object
     */
    public TaskControllerImpl(AbstractTaskList list, View view) {
        this.list = list;
        this.view = view;
        scanner = new Scanner(System.in);
    }

    /**
     * Adds a new task to the general list.
     */
    @Override
    public void addTask() {
        log.info("Execute add action");
        String title = view.getTitle();
        if (view.getRepeatStatus()) {
            createRepeated(title);
        } else {
            createNotRepeated(title);
        }
    }

    private void createRepeated(String title) {
        log.info("Creating repeated task");
        Task task;
        LocalDateTime start = view.getStartTime();
        LocalDateTime end = view.getEndTime();
        int interval = view.getInterval();

        if (checkParams(start, end, interval)) {
            task = new Task(title, start, end, interval);
            task.setActive(true);

            list.add(task);
            Output.println(Output.SUCCESSFUL_ADD);
        } else {
            Output.println(Output.FAILURE_ADD);
        }
    }

    private boolean checkParams(LocalDateTime start, LocalDateTime end, int interval) {
        return (start.isBefore(end) || !start.equals(end))
                && start.plusSeconds(interval).isBefore(end);
    }

    private void createNotRepeated(String title) {
        log.info("Creating not repeated task");
        Task task;
        LocalDateTime time = view.getTime();

        task = new Task(title, time);
        task.setActive(true);

        list.add(task);
        Output.println(Output.SUCCESSFUL_ADD);
    }

    /**
     * Edits an existing task in the general list.
     */
    @Override
    public void editTask() {
        log.info("Execute edit action");
        int index = view.getIndex(list) - 1;
        if (index > -1) {
            Task task = list.getTask(index);
            while (true) {
                view.editMenu();

                int choice = scanner.nextInt();
                if (choice == 0) {
                    Output.println(Output.MAIN_MENU);
                    break;
                } else {
                    switch (choice) {
                        case 1:
                            editTaskTitle(task);
                            break;
                        case 2:
                            editTaskRepeatedStatus(task);
                            break;
                        case 3:
                            changeTaskActivityStatus(task);
                            break;
                        case 4:
                            changeTaskTimeOptions(task);
                            break;
                    }
                }
            }
        }
    }

    private void changeTaskTimeOptions(Task task) {
        log.info("Changing task time options");
        if (task.isRepeated()) {
            editRepeatedTime(task);
        } else {
            editNotRepeatedTime(task);
        }
    }

    private void editRepeatedTime(Task task) {
        log.info("Changing repeated task time");
        Output.println("-_Enter new repeated task time_-");
        while (true) {
            LocalDateTime start = view.getStartTime();
            LocalDateTime end = view.getEndTime();
            int interval = view.getInterval();

            if (checkParams(start, end, interval)) {
                task.setTime(start, end, interval);
                Output.println("Repeated task has successfully changed its time");
                break;
            } else {
                Output.println("Changing the time of the repeated task was a failure");
            }
        }
    }

    private void editNotRepeatedTime(Task task) {
        log.info("Changing not repeated task time");
        Output.println("-_Enter new task time_-");
        LocalDateTime time = view.getTime();
        task.setTime(time);
        Output.println("Not repeated task has successfully changed its time");
    }

    private void editTaskTitle(Task task) {
        log.info("Changing task title");
        Output.println("-_Enter new task title: ");
        String title = view.getTitle();
        task.setTitle(title);
        Output.println("The task successfully changed the name");
    }

    private void editTaskRepeatedStatus(Task task) {
        log.info("Changing task repeated status");
        Output.println("The task has the following repetition status -> " + task.isRepeated());
        if (task.isRepeated()) {
            makeNotRepeated(task);
        } else {
            makeRepeated(task);
        }
    }

    private void makeNotRepeated(Task task) {
        log.info("Changing: repeated task -> not repeated task");
        task.setTime(view.getTime());
        task.setRepeated(false);
    }

    private void makeRepeated(Task task) {
        log.info("Changing: not repeated task -> repeated task");
        LocalDateTime start = view.getStartTime();
        LocalDateTime end = view.getEndTime();
        int interval = view.getInterval();

        if (checkParams(start, end, interval)) {
            task.setTime(start, end, interval);
            task.setRepeated(true);
            Output.println(Output.SUCCESSFUL_EDIT);
        } else {
            Output.println(Output.FAILURE_EDIT);
        }
    }

    private void changeTaskActivityStatus(Task task) {
        log.info("Changing task activity");
        Output.print("-_Enter new task activity status: ");
        boolean activity = view.getActivity();
        task.setActive(activity);
        Output.println("The task successfully changed the activity status");
    }

    /**
     * Deletes an existing task from the general list.
     */
    @Override
    public void deleteTask() {
        log.info("Execute delete action");
        int index = view.getIndex(list) - 1;
        if (index > -1) {
            Task task = list.getTask(index);
            list.remove(task);
            Output.println("Task #" + (index + 1) + " was deleted");
        }
    }

    /**
     * Shows the general task list.
     */
    @Override
    public void showTaskList() {
        log.info("Execute show task list action");
        view.showTaskList(list);
    }

    /**
     * Shows the general calendar of tasks.
     */
    @Override
    public void showCalendar() {
        log.info("Execute show task calendar action");
        view.showCalendar(list);
    }
}
