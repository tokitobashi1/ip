package sakura.task;

/**
 * Represents an Event task with start and end times.
 */
public class Event extends Task {
    private String from;
    private String to;

    /**
     * Constructs an Event task with description, start time, and end time.
     *
     * @param description the task description
     * @param from the start time of the event
     * @param to the end time of the event
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns a string representation of the Event task.
     *
     * @return string including task type, details, start time, and end time
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }

<<<<<<< HEAD
    /**
     * Returns the storage string format of the Event task.
     *
     * @return a formatted string for saving the event in storage
     */
    public String savingFormat() {
        return "E | " + (getDone() ? "1" : "0") + " | "
                + getDescription() + " | " + from + " | " + to;
=======
    @Override
    public String savingFormat() {
        return "E | " + (getDone() ? "1" : "0") + " | " + getDescription() + " | " + from + " | " + to;
>>>>>>> branch-Level-10
    }
}