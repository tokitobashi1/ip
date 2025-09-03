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
 * Custom Storage Class for saving and loading tasks.
 */
public class Storage {

    /** Path of the file to load and save tasks. */
    private String filePath;

    /**
     * Constructs a Storage object for the specified file path.
     *
     * @param filePath The path of the file to load and save tasks.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the storage file into an ArrayList of {@link Task}.
     * <p>
     * Supports ToDo, Deadline, and Event tasks. If the file does not exist,
     * an empty task list is returned.
     *
     * @return An ArrayList containing tasks loaded from the file.
     */
    public ArrayList<Task> loadTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            file.getParentFile().mkdirs();
            return tasks;
        }

        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(" \\| ");
                String type = parts[0];
                boolean isDone = parts[1].equals("1");

                switch (type) {
                    case "T" -> {
                        Task todo = new ToDo(parts[2]);
                        todo.setDone(isDone);
                        tasks.add(todo);
                    }
                    case "D" -> {
                        try {
                            Task deadline = new Deadline(parts[2], parts[3]);
                            deadline.setDone(isDone);
                            tasks.add(deadline);
                        } catch (SakuraException e) {
                            System.out.println("Error loading deadline task: " + e.getMessage());
                        }
                    }
                    case "E" -> {
                        Task event = new Event(parts[2], parts[3], parts[4]);
                        event.setDone(isDone);
                        tasks.add(event);
                    }
                    default -> System.out.println("Unknown task type: " + type);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found. Starting with empty list.");
        }

        return tasks;
    }

    /**
     * Clears the contents of the storage file.
     * This method overwrites the existing file with an empty string,
     * effectively removing all saved tasks.
     */
    public void clear() {
        try (FileWriter writer = new FileWriter(filePath, false)) {
            writer.write(""); // clear contents
        } catch (IOException e) {
            System.out.println("Error clearing storage file: " + e.getMessage());
        }
    }

    /**
     * Saves a list of tasks to the storage file.
     * <p>
     * Each task is written in a specific format.
     *
     * @param tasks The ArrayList of tasks to save.
     */
    public void save(ArrayList<Task> tasks) {
        try {
            File file = new File(filePath);
            file.getParentFile().mkdirs(); // Ensure folder exists

            try (PrintWriter writer = new PrintWriter(file)) {
                for (Task task : tasks) {
                    writer.println(task.savingFormat());
                }
            }
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }
}
