package sakura.task;

import sakura.storage.Storage;
import java.util.*;

/**
 * Manages the list of tasks and operations on them.
 */
public class TaskList {
    private ArrayList<Task> tasks;
    private Storage storage;

    public TaskList(Storage storage) {
        this.storage = storage;
        this.tasks = storage.loading();
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
        storage.save(tasks);
    }

    public Task removeTask(int index) throws SakuraException {
        if (index < 0 || index >= tasks.size()) {
            throw new SakuraException("\uD83C\uDF38That task number does not exist.\uD83C\uDF38");
        }
        Task removed = tasks.remove(index);
        storage.save(tasks);
        return removed;
    }

    public void markTaskDone(int index) throws SakuraException {
        if (index < 0 || index >= tasks.size()) {
            throw new SakuraException("\uD83C\uDF38That task number does not exist.\uD83C\uDF38");
        }
        tasks.get(index).markAsDone();
        storage.save(tasks);
    }

    public void markTaskNotDone(int index) throws SakuraException {
        if (index < 0 || index >= tasks.size()) {
            throw new SakuraException("\uD83C\uDF38That task number does not exist.\uD83C\uDF38");
        }
        tasks.get(index).NotDone();
        storage.save(tasks);
    }
}
