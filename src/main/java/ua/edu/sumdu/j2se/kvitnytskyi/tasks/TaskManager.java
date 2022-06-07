package ua.edu.sumdu.j2se.kvitnytskyi.tasks;

import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.kvitnytskyi.tasks.controller.Controller;
import ua.edu.sumdu.j2se.kvitnytskyi.tasks.controller.impl.MainControllerImpl;
import ua.edu.sumdu.j2se.kvitnytskyi.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.kvitnytskyi.tasks.model.ListTypes;
import ua.edu.sumdu.j2se.kvitnytskyi.tasks.model.TaskIO;
import ua.edu.sumdu.j2se.kvitnytskyi.tasks.model.TaskListFactory;
import ua.edu.sumdu.j2se.kvitnytskyi.tasks.view.View;
import ua.edu.sumdu.j2se.kvitnytskyi.tasks.view.impl.MainViewImpl;
import ua.edu.sumdu.j2se.kvitnytskyi.tasks.view.util.Output;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Class for running the basic program logic.
 *
 * @author kvitnytskyi
 */
public class TaskManager {

    private static final Logger log = Logger.getLogger(TaskManager.class);
    private static final String JSON_FILE = "tasks.json";
    private final Path jsonPath;
    private final AbstractTaskList atl;

    /**
     * Empty constructor.
     */
    public TaskManager() {
        atl = TaskListFactory.createTaskList(ListTypes.types.ARRAY);
        jsonPath = FileSystems.getDefault().getPath(JSON_FILE).toAbsolutePath();
    }

    /**
     * Launches the Task Manager application.
     */
    public void start() {
        log.info("Starting Task Manager application");
        loadTaskData();

        log.info("Declare View and Controller");
        View view = new MainViewImpl();
        Controller controller = new MainControllerImpl(atl, view);
        controller.execute();

        saveTaskData();
        log.info("Task Manager application has finished its work");
    }

    private void saveTaskData() {
        try {
            log.info("Deleting json file if exist");
            Files.deleteIfExists(jsonPath);

            log.info("Write tasks' data into json file");
            TaskIO.writeText(atl, new File(String.valueOf(jsonPath.toAbsolutePath())));
            log.info("Writing data was successful");
        } catch (IOException e) {
            log.error(e);
            Output.println("Error. Can't read tasks' data");
        }
    }

    private void loadTaskData() {
        try {
            log.info("Reading tasks' data from json file");
            TaskIO.readText(atl, new File(String.valueOf(jsonPath.toAbsolutePath())));
            log.info("Reading data was successful");
        } catch (IOException e) {
            log.error(e);
            Output.println("Error. Can't read tasks' data");
        }
    }
}
