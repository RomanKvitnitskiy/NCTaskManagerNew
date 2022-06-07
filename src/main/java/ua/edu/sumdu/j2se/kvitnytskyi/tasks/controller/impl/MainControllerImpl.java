package ua.edu.sumdu.j2se.kvitnytskyi.tasks.controller.impl;

import ua.edu.sumdu.j2se.kvitnytskyi.tasks.controller.Controller;
import ua.edu.sumdu.j2se.kvitnytskyi.tasks.controller.SecurityController;
import ua.edu.sumdu.j2se.kvitnytskyi.tasks.controller.TaskController;
import ua.edu.sumdu.j2se.kvitnytskyi.tasks.controller.notification.NotificationManager;
import ua.edu.sumdu.j2se.kvitnytskyi.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.kvitnytskyi.tasks.view.View;
import ua.edu.sumdu.j2se.kvitnytskyi.tasks.view.util.Output;
import org.apache.log4j.Logger;


import java.util.Scanner;

/**
 * Class that implements the Controller interface.
 *
 * @author kvitnytskyi
 */
public class MainControllerImpl implements Controller {

    private static final Logger log = Logger.getLogger(MainControllerImpl.class);
    private static final String QUESTION = "Are you confident in your choice? (Yes:No) ";
    private final TaskController controller;
    private final SecurityController securityController;
    private final View view;
    private final Scanner scanner;

    /**
     * Constructor.
     *
     * @param list - general task list
     * @param view - view object
     */
    public MainControllerImpl(AbstractTaskList list, View view) {
        this.view = view;
        controller = new TaskControllerImpl(list, view);
        securityController = new SecurityControllerImpl();
        scanner = new Scanner(System.in);

        NotificationManager manager = new NotificationManager(list);
        manager.setDaemon(true);
        manager.start();
    }

    /**
     * Executes main controller logic.
     */
    @Override
    public void execute() {
        log.info("Password verification");
        if (securityController.checkPassword()) {

            log.info("Executing main controller command");
            while (true) {
                view.showMenu();
                int choice = scanner.nextInt();
                scanner.nextLine();
                if (choice == 0) {
                    Output.print("Do you want to finish? (Yes:No): ");
                    if (view.checkUserChoice()) {
                        Output.println("Program was finished");
                        log.info("The Task Manager application is getting ready to end");
                        break;
                    }
                } else {
                    switch (choice) {
                        case 1:
                            Output.print(QUESTION);
                            if (view.checkUserChoice()) controller.addTask();
                            break;
                        case 2:
                            Output.print(QUESTION);
                            if (view.checkUserChoice()) controller.editTask();
                            break;
                        case 3:
                            Output.print(QUESTION);
                            if (view.checkUserChoice()) controller.deleteTask();
                            break;
                        case 4:
                            controller.showTaskList();
                            break;
                        case 5:
                            controller.showCalendar();
                            break;
                    }
                }
            }
        }
    }
}
