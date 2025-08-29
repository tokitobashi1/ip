package sakura.task;

/**
 * Represents a single Task with a description and completion status.
 */
public class Task {
    private String description;
    private boolean done;

    /**
     * Constructs a Task with the given description.
     *
     * @param description the string description of the task
     */
    public Task(String description) {
        this.description = description;
        this.done = false;
    }

    /**
     * Saves done status and description
     */
    public String SavingFormat() {
        return (getDone() ? "1" : "0") + " | " + getDescription();
    }

    /**
     * Returns the description of this task.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns whether this task is done via boolean variable
     */
    public boolean getDone() {
        return done;
    }

    /**
     * Updates the description of this task.
     *
     * @param description the new description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets the done status of this task.
     */
    public void setDone(boolean done) {
        this.done = done;
    }

    /**
     * Returns the status symbol for this task.
     */
    public String getStatus() {
        return (done ? "X" : " ");
    }

    /**
     * Marks this task as done.
     */
    public void markAsDone() {
        this.done = true;
    }

    /**
     * Marks this task as not done.
     */
    public void notDone() {
        this.done = false;
    }

    /**
     * Returns a string representation of the task including status and description.
     */
    @Override
    public String toString() {
        return "[" + getStatus() + "] " + description;
    }
}