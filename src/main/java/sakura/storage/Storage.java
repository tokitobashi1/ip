package sakura.storage;

import sakura.task.Task;
import sakura.task.ToDo;
import sakura.task.Deadline;
import sakura.task.Event;
import sakura.task.SakuraException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Custom Storage Class
 */
public class Storage {
    private String filePath;
    /**
     * Constructs a Storage object with the given file path.
     *
     * @param filePath The path to the storage file.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }
<<<<<<< HEAD
    /**
     * Loads tasks from the storage file.
     * @return An ArrayList of tasks loaded from the file.
     */
    public ArrayList<Task> loading() {
=======

    /**
     * Loads tasks from the storage file.
     *
     * @return list of tasks loaded from file
     */
    public ArrayList<Task> load() {
>>>>>>> branch-A-CodingStandard
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            file.getParentFile().mkdirs();
            return tasks;
        }

        try {
            Scanner fileScanner = new Scanner(new File(filePath));
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(" \\| ");
                String type = parts[0];
                boolean isDone = parts[1].equals("1");

                switch (type) {
                    case "T":
                        Task todo = new ToDo(parts[2]);
                        todo.setDone(isDone);
                        tasks.add(todo);
                        break;
                    case "D":
                        // parts[3] is the date string in yyyy-MM-dd format
                        try {
                            Task deadline = new Deadline(parts[2], parts[3]);
                            deadline.setDone(isDone);
                            tasks.add(deadline);
                        } catch (SakuraException e) {
                            System.out.println("Error loading deadline task: " + e.getMessage());
                        }
                        break;
                    case "E":
                        Task event = new Event(parts[2], parts[3], parts[4]);
                        event.setDone(isDone);
                        tasks.add(event);
                        break;
                    default:
                        // ignore unknown task types
                        break;
                }
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found. Starting with empty list.");
        }

        return tasks;
    }
<<<<<<< HEAD
    /**
     * Clears all contents of the storage file.
     * If the file does not exist, it is created automatically.
     */
=======

>>>>>>> branch-A-CodingStandard
    public void clear() {
        try (FileWriter writer = new FileWriter(filePath, false)) {
            writer.write(""); // clear contents
        } catch (IOException e) {
            System.out.println("Error clearing storage file: " + e.getMessage());
        }
    }

    public void save(ArrayList<Task> tasks) {
        try {
            File file = new File(filePath);
            file.getParentFile().mkdirs(); // ensure folder exists
            PrintWriter writer = new PrintWriter(file);

            for (Task task : tasks) {
                writer.println(task.SavingFormat());
            }

            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }
}