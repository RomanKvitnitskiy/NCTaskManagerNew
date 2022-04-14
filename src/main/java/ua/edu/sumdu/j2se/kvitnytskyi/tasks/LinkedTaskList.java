package ua.edu.sumdu.j2se.kvitnytskyi.tasks;

public class LinkedTaskList {

    private class ListElement {
        Task data; // data
        ListElement next; // pointer to next element
    }

    private int fill;
    private ListElement head;

    public LinkedTaskList() {
        head = new ListElement();
    }

    public void add(Task task) {
        if (task == null)
            throw new NullPointerException("Task is null!");

        ListElement temp = head;
        head.data = task;

        head = new ListElement();
        head.next = temp;
        fill++;
    }

    public boolean remove(Task task) {
        if (task == null)
            throw new NullPointerException("Task is null!");

        ListElement search = head;
        if (fill == 0)
            return false;

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

    public int size() {
        return fill;
    }

    public Task getTask(int index) {
        if (index < 0 || index >= fill)
            throw new IndexOutOfBoundsException("Invalid index parameter!");

        index++;
        ListElement search = head;

        for (int i = fill; i > fill - index; i--)
            search = search.next;

        return search.data;
    }

    public LinkedTaskList incoming(int from, int to) {
        if (from > to)
            throw new IllegalArgumentException("Invalid interval parameters!");

        int nextTaskTime;
        ListElement search = new ListElement();
        LinkedTaskList returnList = new LinkedTaskList();

        while (search.next != null) {
            search = search.next;

            nextTaskTime = search.data.nextTimeAfter(from);

            if (nextTaskTime != -1 && nextTaskTime < to)
                returnList.add(search.data);
        }
        return returnList;
    }
}
