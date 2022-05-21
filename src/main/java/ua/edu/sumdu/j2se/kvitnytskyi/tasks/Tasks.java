package ua.edu.sumdu.j2se.kvitnytskyi.tasks;

import java.time.LocalDateTime;
import java.util.*;

public class Tasks {

    public static Iterable<Task> incoming(Iterable<Task> tasks, LocalDateTime start, LocalDateTime end) {
        AbstractTaskList atl = TaskListFactory.createTaskList(ListTypes.types.ARRAY);

        if (atl == null) throw new IllegalStateException();
        for (Task t : tasks) {
            if (t.nextTimeAfter(start) != null
                    && t.nextTimeAfter(start).compareTo(end) <= 0) {
                atl.add(t);
            }
        }
        return atl;
    }

    public static SortedMap<LocalDateTime, Set<Task>> calendar
            (Iterable<Task> tasks, LocalDateTime start, LocalDateTime end) {

        SortedMap<LocalDateTime, Set<Task>> dateSetSortedMap = new TreeMap<>(LocalDateTime::compareTo);
        Iterable<Task> taskIterable = incoming(tasks, start, end);

        for (Task t : taskIterable) {
            for (LocalDateTime i = t.nextTimeAfter(start);
                 i != null && (i.isBefore(end) || i.isEqual(end));
                 i = t.nextTimeAfter(i)) {

                if (dateSetSortedMap.containsKey(i)) {
                    dateSetSortedMap.get(i).add(t);
                } else {
                    Set<Task> taskSet = new HashSet<>();
                    taskSet.add(t);
                    dateSetSortedMap.put(i, taskSet);
                }
            }
        }
        return dateSetSortedMap;
    }
}
