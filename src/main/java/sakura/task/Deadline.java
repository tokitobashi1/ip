package sakura.task;

import sakura.task.SakuraException;
import sakura.task.Task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a Deadline task: a subclass of Task class
 */
public class Deadline extends Task {
    private LocalDate by;
    private static final DateTimeFormatter INPUT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter OUTPUT = DateTimeFormatter.ofPattern("MMM dd yyyy");
    /**
     * Constructs a Deadline task with description and due date/time.
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
     * @return string including task type, details, and due date/time
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(OUTPUT) + ")";
    }

    @Override
    public String SavingFormat() {
        // Save in yyyy-MM-dd format so it can be parsed back properly
        return "D | " + (getDone() ? "1" : "0") + " | " + getDescription() + " | " + by.format(INPUT);
    }

    public LocalDate getBy() {
        return by;
    }
}
