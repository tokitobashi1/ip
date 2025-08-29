package sakura.storage;

import sakura.task.*;
import java.io.*;
import java.util.*;

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
    /**
     * Loads tasks from the storage file.
     * @return An ArrayList of tasks loaded from the file.
     */
    public ArrayList<Task> loading() {
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
                        Task t = new ToDo(parts[2]);
                        t.setDone(isDone);
                        tasks.add(t);
                        break;
                    case "D":
                        // parts[3] is the date string in yyyy-MM-dd format
                        try {
                            Task d = new Deadline(parts[2], parts[3]);
                            d.setDone(isDone);
                            tasks.add(d);
                        } catch (SakuraException e) {
                            System.out.println("Error loading deadline task: " + e.getMessage());
                        }
                        break;
                    case "E":
                        Task e = new Event(parts[2], parts[3], parts[4]);
                        e.setDone(isDone);
                        tasks.add(e);
                        break;
                }
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found. Starting with empty list.");
        }
        return tasks;
    }
    /**
     * Clears all contents of the storage file.
     * If the file does not exist, it is created automatically.
     */
    public void clear() {
        try (FileWriter writer = new FileWriter(filePath, false)) { // overwrite
            writer.write(""); // clear contents
        } catch (IOException e) {
            System.out.println("Error clearing storage file: " + e.getMessage());
        }
    }

    public void save(ArrayList<Task> tasks) {
        try {
            File file = new File(filePath);
            file.getParentFile().mkdirs(); // Ensure folder exists
            PrintWriter writer = new PrintWriter(file);
            for (Task t : tasks) {
                writer.println(t.SavingFormat());
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }
}