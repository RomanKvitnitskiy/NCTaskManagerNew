package ua.edu.sumdu.j2se.kvitnytskyi.tasks;

public abstract class AbstractTaskList implements Iterable<Task>{

    protected static ListTypes.types type;

    public abstract void add(Task task);
    public abstract boolean remove(Task task);
    public abstract Task getTask(int index);

    public abstract int size();

    public AbstractTaskList incoming(int from, int to) {
        if(from > to)
            throw new IllegalArgumentException("Invalid interval parameters!");

        AbstractTaskList incomingTasks = TaskListFactory.createTaskList(type);
        for(int i = 0; i < size(); i++)
            if (getTask(i).nextTimeAfter(from) > from && getTask(i).nextTimeAfter(from) <= to)
                incomingTasks.add(getTask(i));

        return incomingTasks;
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
