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
     * @param description the task description as a string
     * @param from        the start time of the event as a string
     * @param to          the end time of the event as a string
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

    @Override
    public String savingFormat() {
        return "E | " + (getDone() ? "1" : "0") + " | " + getDescription() + " | " + from + " | " + to;
    }
}
