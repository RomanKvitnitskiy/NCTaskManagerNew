package ua.edu.sumdu.j2se.kvitnytskyi.tasks.model;

/**
 * Abstract Factory.
 *
 * @author kvitnytskyi
 */
public class TaskListFactory {

    /**
     * Returns the object of a certain task list
     * depending on the type of enumeration list.
     *
     * @param type - list enumeration type
     * @return ArrayTaskList object if ARRAY, LinkedTaskList if LINKED
     */
    static public AbstractTaskList createTaskList(ListTypes.types type) {
        if (type == ListTypes.types.ARRAY) {
            return new ArrayTaskList();
        } else {
            return new LinkedTaskList();
        }
    }
}
