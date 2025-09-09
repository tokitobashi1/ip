package sakura.storage;

import sakura.task.Deadline;
import sakura.task.Event;
import sakura.task.SakuraException;
import sakura.task.Task;
import sakura.task.ToDo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles reading and writing tasks to storage.
 */
public class Storage {

    /** Path to the storage file */
    private String filePath;

    /**
     * Constructs a Storage object for the specified file path.
     *
     * @param filePath the path of the file to load and save tasks
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the storage file into an ArrayList of {@link Task}.
     * <p>
     * Supports ToDo, Deadline, and Event tasks. If the file does not exist,
     * an empty task list is returned.
     */
    public ArrayList<Task> loadTasks() {
        ArrayList<Task> taskList = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            file.getParentFile().mkdirs();
            return taskList;
        }

        try {
            Scanner fileScanner = new Scanner(file);

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(" \\| ");
                String taskType = parts[0];
                boolean isDone = parts[1].equals("1");

                switch (taskType) {
                    case "T":
                        Task todoTask = new ToDo(parts[2]);
                        todoTask.setDone(isDone);
                        taskList.add(todoTask);
                        break;

                    case "D":
                        try {
                            Task deadlineTask = new Deadline(parts[2], parts[3]);
                            deadlineTask.setDone(isDone);
                            taskList.add(deadlineTask);
                        } catch (SakuraException e) {
                            System.out.println("Error loading deadline task: " + e.getMessage());
                        }
                        break;

                    case "E":
                        Task eventTask = new Event(parts[2], parts[3], parts[4]);
                        eventTask.setDone(isDone);
                        taskList.add(eventTask);
                        break;

                    default:
                        System.out.println("Unknown task type in storage: " + taskType);
                        break;
                }
            }

            fileScanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("Storage file not found. Starting with an empty list.");
        }

        return taskList;
    }

    /**
     * Clears the contents of the storage file.
     * <p>
     * This method overwrites the existing file with an empty string
     */
    public void clear() {
        try (FileWriter writer = new FileWriter(filePath, false)) { // overwrite
            writer.write(""); // clear contents
        } catch (IOException e) {
            System.out.println("Error clearing storage file: " + e.getMessage());
        }
    }

    /**
     * Saves a list of tasks to the storage file.
     * <p>
     * Each task is written in a specific format.
     * @param tasks the ArrayList of tasks to save
     */
    public void save(ArrayList<Task> tasks) {
        try {
            File file = new File(filePath);
            file.getParentFile().mkdirs(); // ensure folder exists

            PrintWriter writer = new PrintWriter(file);

            for (int i = 0; i < tasks.size(); i++) {
                Task task = tasks.get(i);
                writer.println(task.saveFormat());
            }

            writer.close();

        } catch (IOException e) {
            System.out.println("Error saving storage file: " + e.getMessage());
        }
    }
}
