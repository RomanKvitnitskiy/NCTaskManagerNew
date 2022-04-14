package ua.edu.sumdu.j2se.kvitnytskyi.tasks;

public abstract class AbstractTaskList {

    protected int taskAmount;
    protected static ListTypes.types type;

    public abstract void add(Task task);
    public abstract boolean remove(Task task);
    public abstract Task getTask(int index);

    public int size() {
        return taskAmount;
    }

    public AbstractTaskList incoming(int from, int to) {
        if(from > to)
            throw new IllegalArgumentException("Invalid interval parameters!");

        AbstractTaskList incomingTasks = TaskListFactory.createTaskList(type);
        for(int i = 0; i < size(); i++)
            if (getTask(i).nextTimeAfter(from) > from && getTask(i).nextTimeAfter(from) <= to)
                incomingTasks.add(getTask(i));

        return incomingTasks;
    }
}
