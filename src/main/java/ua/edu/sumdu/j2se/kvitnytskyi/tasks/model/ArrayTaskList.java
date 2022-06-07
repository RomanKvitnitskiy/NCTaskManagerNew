package ua.edu.sumdu.j2se.kvitnytskyi.tasks.model;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Class for storing tasks.
 *
 * @author kvitnytskyi
 */
public class ArrayTaskList extends AbstractTaskList {

    private Task[] tasks;
    private int size;
    private int curr;

    static {
        type = ListTypes.types.ARRAY;
    }

    public ArrayTaskList() {
        size = 1;
        tasks = new Task[size];
    }

    /**
     * Adds the task to the array task list.
     *
     * @param task - task to be added
     */
    @Override
    public void add(Task task) {
        if (task == null) {
            throw new IllegalArgumentException();
        }
        if (curr == size) {
            Task[] temp = new Task[size * 2];
            System.arraycopy(tasks, 0, temp, 0, tasks.length);
            size *= 2;
            tasks = temp;
        }
        tasks[curr] = task;
        ++curr;
    }

    /**
     * Deletes the task from the array task list.
     *
     * @param task - task to be deleted
     * @return true if the task was deleted, false if not
     */
    @Override
    public boolean remove(Task task) {
        int index = findTask(task);
        if (task == null || index == -1) {
            return false;
        }
        if (index >= 0) {
            System.arraycopy(tasks, 0, tasks, 0, index);
        }

        if (curr - (index + 1) >= 0) {
            System.arraycopy(tasks, index + 1, tasks, index + 1 - 1, curr - (index + 1));
        }
        --curr;
        return true;
    }

    private int findTask(Task task) {
        for (int i = 0; i < curr; ++i) {
            if (tasks[i].equals(task)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Returns array task list size.
     *
     * @return size of the array task list
     */
    @Override
    public int size() {
        return curr;
    }

    /**
     * Returns task by index.
     *
     * @param index - index to find a task in the array task list
     * @return desired task
     */
    @Override
    public Task getTask(int index) {
        if (index < 0 || index >= curr) {
            throw new IndexOutOfBoundsException();
        }
        return tasks[index];
    }

    /**
     * Returns the iterator for navigating through the array task list.
     *
     * @return array task list iterator
     */
    @Override
    public Iterator<Task> iterator() {
        return new Iterator<Task>() {

            private int currIndex = 0;
            private int lastRemoved = -1;

            @Override
            public boolean hasNext() {
                return currIndex < curr;
            }

            @Override
            public Task next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                lastRemoved = currIndex;
                return tasks[currIndex++];
            }

            @Override
            public void remove() {
                if (currIndex < 1) {
                    throw new IllegalStateException();
                }
                ArrayTaskList.this.remove(tasks[lastRemoved]);
                currIndex--;
            }
        };
    }

    @Override
    public int hashCode() {
        int hash = Objects.hash(size, curr);
        hash = 31 * hash + Arrays.hashCode(tasks);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ArrayTaskList taskList = (ArrayTaskList) obj;
        return size == taskList.size &&
                curr == taskList.curr &&
                Arrays.equals(tasks, taskList.tasks);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        ArrayTaskList retObj = new ArrayTaskList();
        for(int counter = 0; counter < curr; counter++) {
            retObj.add(tasks[counter]);
        }
        return retObj;
    }
}
