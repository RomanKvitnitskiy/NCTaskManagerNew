package ua.edu.sumdu.j2se.kvitnytskyi.tasks;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class LinkedTaskList extends AbstractTaskList {

    private int size;
    private Node first;
    private Node last;

    static {
        type = ListTypes.types.LINKED;
    }

    @Override
    public Iterator<Task> iterator() {
        return new Iterator<Task>() {

            private int currentIndex = 0;
            private int lastRemoved = -1;

            @Override
            public boolean hasNext() {
                return currentIndex < size;
            }

            @Override
            public Task next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                lastRemoved = currentIndex;
                return getTask(currentIndex++);
            }

            @Override
            public void remove() {
                if (currentIndex < 1) {
                    throw new IllegalStateException();
                }
                LinkedTaskList.this.remove(getTask(lastRemoved));
                currentIndex--;
            }
        };
    }

    private class Node {
        Task task;
        Node next;
        Node prev;

        Node(Node prev, Task t, Node next) {
            task = t;
            this.next = next;
            this.prev = prev;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return Objects.equals(task, node.task) && Objects.equals(next, node.next) && Objects.equals(prev, node.prev);
        }

        @Override
        public int hashCode() {
            return Objects.hash(task, next, prev);
        }
    }

    @Override
    public void add(Task task) {
        if (task == null) {
            throw new IllegalArgumentException();
        }
        Node l = last;
        Node newNode = new Node(l, task, null);
        last = newNode;
        if (l == null) {
            first = newNode;
        } else {
            l.next = newNode;
        }
        size++;
    }

    @Override
    public boolean remove(Task task) {
        for (Node temp = first; temp != null; temp = temp.next) {
            if (task.equals(temp.task)) {
                removeTask(temp);
                return true;
            }
        }
        return false;
    }

    private void removeTask(Node link) {
        Node next = link.next;
        Node prev = link.prev;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            link.prev = null;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            link.next = null;
        }

        link.task = null;
        size--;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Task getTask(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        return task(index).task;
    }

    private Node task(int index) {
        Node t;
        if (index < (size >> 1)) {
            t = first;
            for (int i = 0; i < index; i++) {
                t = t.next;
            }
        } else {
            t = last;
            for (int i = size - 1; i > index; --i) {
                t = t.prev;
            }
        }
        return t;
    }

    @Override
    public int hashCode() {
        return Objects.hash(size, first, last);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        LinkedTaskList taskList = (LinkedTaskList) obj;
        for (int i = 0; i < size; ++i) {
            if (!taskList.getTask(i).equals(getTask(i))) {
                return false;
            }
        }
        return size == taskList.size;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
