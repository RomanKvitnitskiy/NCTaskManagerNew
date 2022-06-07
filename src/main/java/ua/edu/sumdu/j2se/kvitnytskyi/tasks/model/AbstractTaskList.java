package ua.edu.sumdu.j2se.kvitnytskyi.tasks.model;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Abstract class for storing tasks.
 *
 * @author kvitnytskyi
 */
public abstract class AbstractTaskList implements Iterable<Task>{

    protected static ListTypes.types type;

    /**
     * Adds the task to the list.
     *
     * @param task - task to be added
     */
    public abstract void add(Task task);

    /**
     * Deletes the task from the list.
     *
     * @param task - task to be deleted
     * @return true if the task was deleted, false if not
     */
    public abstract boolean remove(Task task);

    /**
     * Returns task by index.
     *
     * @param index - index to find a task in the list
     * @return desired task
     */
    public abstract Task getTask(int index);

    /**
     * Returns list size.
     *
     * @return size of the array task list
     */
    public abstract int size();

    /**
     * Returns a subset of tasks.
     * Which are scheduled to run at least once:
     * after the "from" time
     * and no later than "to".
     *
     * @param from - the time after which to look for
     * @param to   - the time up to which to look
     * @return subset of tasks
     */
    public final AbstractTaskList incoming(LocalDateTime from, LocalDateTime  to) {
        if(from.isAfter(to))
            throw new IllegalArgumentException("Invalid interval parameters!");

        AbstractTaskList atl = TaskListFactory.createTaskList(type);
        getStream().filter(t -> t.nextTimeAfter(from).isAfter(from)
                && t.nextTimeAfter(to).isBefore(to)).forEach(atl::add);
        return atl;
    }

    public Stream<Task> getStream() {
        Task[] tasks = new Task[this.size()];
        for (int i = 0; i < tasks.length; ++i) {
            tasks[i] = getTask(i);
        }
        return Arrays.stream(tasks);
    }

    @Override
    public int hashCode() {
        int hash = 1;
        for (Task t : this) {
            hash = 31 * hash + ((t == null) ? 0 : t.hashCode());
        }
        return hash;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        AbstractTaskList atl = TaskListFactory.createTaskList(type);
        for (Task t : this) {
            atl.add(t);
        }
        return atl;
    }
}
