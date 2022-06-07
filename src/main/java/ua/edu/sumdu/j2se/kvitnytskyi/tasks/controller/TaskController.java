package ua.edu.sumdu.j2se.kvitnytskyi.tasks.controller;

/**
 * Interface to perform main business logic.
 *
 * @author kvitnytskyi
 */
public interface TaskController {

    /**
     * Executes task addition command.
     */
    void addTask();

    /**
     * Executes task editing command.
     */
    void editTask();

    /**
     * Executes task deletion command.
     */
    void deleteTask();

    /**
     * Executes task list display command.
     */
    void showTaskList();

    /**
     * Executes task calendar display command.
     */
    void showCalendar();
}
