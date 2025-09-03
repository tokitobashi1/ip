package sakura.task;

import sakura.storage.Storage;
import java.util.*;

/**
 * Manages a list of tasks and provides methods to add, remove,
 * mark as done, or mark as not done. Changes are saved to storage.
 */
public class TaskList {
    private ArrayList<Task> tasks;
    private Storage storage;

    /**
     * Creates a TaskList and loads tasks from the given storage.
     *
     * @param storage Storage object to load and save tasks.
     */
    public TaskList(Storage storage) {
        this.storage = storage;
        this.tasks = storage.loadTasks();
    }

    /**
     * Returns the list of tasks.
     *
     * @return ArrayList of Task objects.
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Adds a task and saves the updated list.
     *
     * @param task Task to add.
     */
    public void addTask(Task task) {
        tasks.add(task);
        storage.save(tasks);
    }

    /**
     * Removes the task at the specified index and saves the list.
     *
     * @param index Index of the task to remove.
     * @return The task that was removed.
     * @throws SakuraException If the index is invalid.
     */
    public Task removeTask(int index) throws SakuraException {
        if (index < 0 || index >= tasks.size()) {
            throw new SakuraException("\uD83C\uDF38That task number does not exist.\uD83C\uDF38");
        }
        Task removed = tasks.remove(index);
        storage.save(tasks);
        return removed;
    }

    /**
     * Marks the task at the given index as done and saves the list.
     *
     * @param index Index of the task to mark as done.
     * @throws SakuraException If the index is invalid.
     */
    public void markTaskDone(int index) throws SakuraException {
        if (index < 0 || index >= tasks.size()) {
            throw new SakuraException("\uD83C\uDF38That task number does not exist.\uD83C\uDF38");
        }
        tasks.get(index).markAsDone();
        storage.save(tasks);
    }

    /**
     * Marks the task at the given index as not done and saves the list.
     *
     * @param index Index of the task to mark as not done.
     * @throws SakuraException If the index is invalid.
     */
    public void markTaskNotDone(int index) throws SakuraException {
        if (index < 0 || index >= tasks.size()) {
            throw new SakuraException("\uD83C\uDF38That task number does not exist.\uD83C\uDF38");
        }
        tasks.get(index).NotDone();
        storage.save(tasks);
    }
    /**
     * Returns a list of tasks whose description contains the given keyword.
     *
     * @param keyword The keyword to search for.
     * @return ArrayList of matching tasks.
     */
    public ArrayList<Task> findTasks(String keyword) {
        ArrayList<Task> matchingTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                matchingTasks.add(task);
            }
        }
        return matchingTasks;
    }
}