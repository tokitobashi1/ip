package sakura.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a Deadline task: a subclass of Task class.
 */
public class Deadline extends Task {
    private LocalDate by;

    private static final DateTimeFormatter INPUT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter OUTPUT = DateTimeFormatter.ofPattern("MMM dd yyyy");

    /**
     * Constructs a Deadline task with description and due date/time.
     *
     * @param description the description of the task
     * @param byString the due date string in yyyy-MM-dd format
     * @throws SakuraException if the date format is invalid
     */
    public Deadline(String description, String byString) throws SakuraException {
        super(description);
        try {
            this.by = LocalDate.parse(byString, INPUT);
        } catch (DateTimeParseException e) {
            throw new SakuraException("Invalid date format! Please use yyyy-MM-dd");
        }
    }

    /**
     * Returns a string representation of the Deadline task.
     *
     * @return string including task type, details, and due date/time
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(OUTPUT) + ")";
    }

    /**
     * Returns a formatted string for saving this task to storage.
     *
     * @return formatted string for storage
     */
    public String savingFormat() {
        return "D | " + (getDone() ? "1" : "0") + " | " + getDescription() + " | " + by.format(INPUT);
    }

    public LocalDate getBy() {
        return by;
    }
}