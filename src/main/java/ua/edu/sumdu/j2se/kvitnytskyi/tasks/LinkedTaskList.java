package ua.edu.sumdu.j2se.kvitnytskyi.tasks;

public class LinkedTaskList extends AbstractTaskList {

    private class ListElement {
        Task data; // data
        ListElement next; // pointer to next element
    }

    static {
        type = ListTypes.types.LINKED;
    }

    private int fill;
    private ListElement head;

    public LinkedTaskList() {
        head = new ListElement();
    }

    @Override
    public void add(Task task) {
        if (task == null)
            throw new NullPointerException("Task is null!");

        ListElement temp = head;
        head.data = task;

        head = new ListElement();
        head.next = temp;
        fill++;
    }

    @Override
    public boolean remove(Task task) {
        if (task == null)
            throw new NullPointerException("Task is null!");

        if (fill == 0)
            return false;

        ListElement search = head;

        while (search.next != null) {                   // while the next item exists
            if (search.next.data.equals(task)) {
                search.next = search.next.next;
                fill--;
                return true;
            }
            search = search.next;
        }
        return false;
    }

    @Override
    public int size() {
        return fill;
    }

    @Override
    public Task getTask(int index) {
        if (index < 0 || index >= fill)
            throw new IndexOutOfBoundsException("Invalid index parameter!");

        index++;
        ListElement search = head;

        for (int i = fill; i > fill - index; i--)
            search = search.next;

        return search.data;
    }
}
