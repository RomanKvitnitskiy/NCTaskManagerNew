package ua.edu.sumdu.j2se.kvitnytskyi.tasks;

public class Task {
    private String title;

    private int time;
    private int interval;
    private int start;
    private int end;

    private boolean active;
    private boolean isRepeated;

    /**
     * default constructor
     */
    public Task(){}

    /**
     * constructor for inactive non-repeating task
     *
     * @param title;
     * @param time;
     */
    public Task(String title, int time) {
        this.title = title;
        this.time = time;

        active = false;
        isRepeated = false;
    }

    /**
     * constructor for inactive repeating task
     *
     * @param title;
     * @param start;
     * @param end;
     * @param interval;
     */
    public Task(String title, int start, int end, int interval) {
        this.title = title;
        this.start = start;
        this.end = end;
        this.interval = interval;

        active = false;
        isRepeated = true;
    }

    /**
     * gets task name
     *
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     *sets task name
     *
     * @param title;
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *gets task the state of activity
     *
     * @return active
     */
    public boolean isActive () {
        return active;
    }

    /**
     * sets task the state of activity
     * @param active;
     */
    public void setActive (boolean active) {
        this.active = active;
    }

    /**
     * gets non repeated task the time
     *
     * @return time, start
     *
     */
    public int getTime() {
        if (isRepeated) {
            return start;
        } else {
            return time;
        }
    }

    /**
     * sets non repeated task the time
     *
     * @param time;
     */
    public void setTime(int time) {
        this.time = time;

        if(isRepeated) {
            isRepeated = false;
        }
    }

    /**
     * gets repeated task the start time
     *
     * @return start, time
     */
    public int getStartTime()
    {
        if (isRepeated) {
            return start;
        } else {
            return time;
        }
    }

    /**
     *gets repeated task the end time
     *
     * @return end, time
     */
    public int getEndTime() {
        if(isRepeated) {
            return end;
        } else {
            return time;
        }
    }

    /**
     * gets repeated task the interval
     *
     * @return interval
     */
    public int getRepeatInterval() {
        if (isRepeated) {
            return interval;
        } else {
            return 0;
        }
    }

    /**
     * sets repeated task the time and interval
     *
     * @param start;
     * @param end;
     * @param interval;
     */
    public void setTime(int start, int end, int interval) {
        if (!isRepeated) {
            this.isRepeated = true;
        }

        this.start = start;
        this.end = end;
        this.interval = interval;
    }

    /**
     * Check task is repeated
     *
     * @return isRepeated
     */
    public boolean isRepeated() {
        return isRepeated;
    }

    /**
     *returns the next time the task is after the specified time
     *
     * @param current;
     * @return nextTime, -1
     */
    public int nextTimeAfter(int current) {
        if (active) {
            if (time != 0 && current < time) {
                return time;
            }
            if (start != 0 && current < start) {
                return start;
            }
            if (current >= start && start != 0) {
                int nextTime = start;

                while(current >= nextTime) {
                    if (nextTime + interval <= end) {
                        nextTime += interval;
                    } else {
                        return -1;
                    }
                }
                return nextTime;
            }
        }
        return -1;
    }
}
