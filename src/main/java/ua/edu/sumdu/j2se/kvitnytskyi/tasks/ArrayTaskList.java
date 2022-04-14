package ua.edu.sumdu.j2se.kvitnytskyi.tasks;

import java.io.StringReader;

public class ArrayTaskList extends AbstractTaskList {

    private static final int SIZE_ARRAY = 5;

    private Task[] tasks;
    private int fill;

    static {
        type = ListTypes.types.ARRAY;
    }

    public ArrayTaskList() {
        tasks = new Task[SIZE_ARRAY];
    }

    @Override
    public void add(Task task) {
        if (task == null) {
            throw new NullPointerException();
        }
        if (tasks.length == fill) {
            Task[] tempTasks = new Task[fill + SIZE_ARRAY];
            System.arraycopy(tasks, 0, tempTasks, 0, fill);
            tasks = tempTasks;
        }
        tasks[fill++] = task;
    }

    @Override
    public boolean remove(Task task) {
        boolean report = false;

        int i = 0;

        while ((tasks[i] != task) && (i != fill)) {
            i++;
        }

        if (i != fill) {
            fill--;

            if (fill - i >= 0) {
                System.arraycopy(tasks, i + 1, tasks, i, fill - i);
                report = true;
            }
        }

        return report;
    }

    @Override
    public int size() {
        return fill;
    }

    @Override
    public Task getTask(int index) {
        if (index < 0 || index >= fill) {
            throw new IndexOutOfBoundsException();
        }

        return tasks[index];
    }
}
