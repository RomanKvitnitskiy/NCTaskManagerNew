package ua.edu.sumdu.j2se.kvitnytskyi.tasks;

import java.util.Arrays;
import java.util.stream.Stream;

public abstract class AbstractTaskList implements Iterable<Task>{

    protected static ListTypes.types type;

    public abstract void add(Task task);
    public abstract boolean remove(Task task);
    public abstract Task getTask(int index);

    public abstract int size();

    public final AbstractTaskList incoming(int from, int to) {
        if(from > to)
            throw new IllegalArgumentException("Invalid interval parameters!");

        AbstractTaskList atl = TaskListFactory.createTaskList(type);
        getStream().filter(t -> t.nextTimeAfter(from) != -1
                && t.nextTimeAfter(from) <= to).forEach(atl::add);
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
