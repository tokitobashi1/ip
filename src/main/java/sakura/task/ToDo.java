package sakura.task;

/**
 * Represents a To-Do task.
 */
public class ToDo extends Task {

    /**
     * Constructs a ToDo with the given description.
     *
     * @param description the description of the ToDo task
     */
    public ToDo(String description) {
        super(description);
    }

    /**
     * Returns a string representation of the ToDo task.
     *
     * @return string including task type and details
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    @Override
    public String savingFormat() {
        return "T | " + super.savingFormat();
    }
}