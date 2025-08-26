import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Custom Storage Class
 */
class Storage {
    private String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

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

/**
 * Custom exception for Sakura bot
 */
class SakuraException extends Exception {
    /**
     * Constructs a SakuraException Class
     *
     * @param message the message as a string
     */
    public SakuraException(String message) {
        super(message);
    }
}

/**
 * Represents a single Task with a description and completion status.
 */
class Task {
    private String description;
    private boolean Done;

    /**
     * Constructs a Task with the given description.
     * @param description the string description of the task
     */
    public Task(String description) {
        this.description = description;
        this.Done = false;
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
        return Done;
    }

    /**
     * Updates the description of this task.
     * @param description the new description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets the done status of this task.
     */
    public void setDone(boolean done) {
        this.Done = done;
    }

    /**
     * Returns the status symbol for this task.
     */
    public String getStatus() {
        return (Done ? "X" : " ");
    }

    /**
     * Marks this task as done.
     */
    public void markAsDone() {
        this.Done = true;
    }

    /**
     * Marks this task as not done.
     */
    public void NotDone() {
        this.Done = false;
    }

    /**
     * Returns a string representation of the task including status and description.
     */
    @Override
    public String toString() {
        return "[" + getStatus() + "] " + description;
    }
}

/**
 * Represents a To-Do task.
 */
class ToDo extends Task {
    /**
     * Constructs a ToDo with the given description.
     * @param description the description of the ToDo task
     */
    public ToDo(String description) {
        super(description);
    }

    /**
     * Returns a string representation of the ToDo task.
     * @return string including task type and details
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
    @Override
    public String SavingFormat() {
        return "T | " + super.SavingFormat();
    }
}

/**
 * Represents a Deadline task: a subclass of Task class
 */
class Deadline extends Task {
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

/**
 * Represents an Event task with start and end times.
 */
class Event extends Task {
    private String from;
    private String to;

    /**
     * Constructs an Event task with description, start time, and end time.
     * @param description the task description as a string
     * @param from        the start time of the event as a string
     * @param to          the end time of the event as a string
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns a string representation of the Event task.
     * @return string including task type, details, start time, and end time
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }
    @Override
    public String SavingFormat() {
        return "E | " + (getDone() ? "1" : "0") + " | " + getDescription() + " | " + from + " | " + to;
    }
}

/**
 * Manages the list of tasks and operations on them.
 */
class TaskList {
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

/**
 * Handles all user interaction and input parsing.
 */
class Ui {
    private Scanner sc;
    private TaskList taskList;

    public Ui(TaskList taskList) {
        this.taskList = taskList;
        this.sc = new Scanner(System.in);
    }

    public void start() {
        System.out.println("____________________________________________________________");
        System.out.println("Hi, I am your friendly bot \uD83C\uDF38Sakura\uD83C\uDF38, feel free to let me know what you need!");
        System.out.println("How can I assist you?");
        System.out.println("____________________________________________________________");

        while (true) {
            String input = sc.nextLine().trim();

            if (input.equals("bye")) {
                System.out.println("____________________________________________________________");
                System.out.println(" Bye, I wish you a pleasant day!\uD83C\uDF37");
                System.out.println("____________________________________________________________");
                break;
            }

            try {
                processInput(input);
            } catch (SakuraException e) {
                System.out.println("____________________________________________________________");
                System.out.println(" Error: " + e.getMessage());
                System.out.println("____________________________________________________________");
            } catch (NumberFormatException e) {
                System.out.println("____________________________________________________________");
                System.out.println(" Please Retry: You need to enter a valid number for the task.\uD83C\uDF38\uD83D\uDE22");
                System.out.println("____________________________________________________________");
            } catch (Exception e) {
                System.out.println("____________________________________________________________");
                System.out.println(" \uD83C\uDF38\uD83D\uDE22An unexpected error occurred: " + e.getMessage());
                System.out.println("____________________________________________________________");
            }
        }

        sc.close();
    }

    private void processInput(String input) throws SakuraException {
        if (input.equals("list")) {
            System.out.println("____________________________________________________________");
            System.out.println(" \uD83C\uDF38Here are the tasks in your list\uD83C\uDF38:");
            ArrayList<Task> tasks = taskList.getTasks();
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println(" " + (i + 1) + "." + tasks.get(i));
            }
            System.out.println("____________________________________________________________");
        }
        else if (input.startsWith("mark")) {
            int index = Integer.parseInt(input.substring(5)) - 1;
            taskList.markTaskDone(index);
            System.out.println("____________________________________________________________");
            System.out.println(" \uD83C\uDF37Nice! I've marked this task as done:");
            System.out.println("   " + taskList.getTasks().get(index));
            System.out.println("____________________________________________________________");
        }
        else if (input.startsWith("unmark")) {
            int index = Integer.parseInt(input.substring(7)) - 1;
            taskList.markTaskNotDone(index);
            System.out.println("____________________________________________________________");
            System.out.println(" \uD83C\uDF37I have marked this task as not done:");
            System.out.println("   " + taskList.getTasks().get(index));
            System.out.println("____________________________________________________________");
        }
        else if (input.startsWith("todo")) {
            String description = input.substring(5).trim();
            if (description.isEmpty()) {
                throw new SakuraException("\uD83C\uDF38The description of a todo cannot be empty, please reenter!!!\uD83C\uDF38");
            }
            taskList.addTask(new ToDo(description));
            System.out.println("____________________________________________________________");
            System.out.println(" \uD83C\uDF37I have added this task:");
            System.out.println("   " + taskList.getTasks().get(taskList.getTasks().size() - 1));
            System.out.println(" \uD83C\uDF37You now have " + taskList.getTasks().size() + " tasks in the list.");
            System.out.println("____________________________________________________________");
        }
        else if (input.startsWith("deadline")) {
            String The_Rest = input.substring(9).trim();
            String[] parts = The_Rest.split(" /by ", 2);
            if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
                throw new SakuraException("\uD83C\uDF38Please use the actual format properly!\uD83C\uDF38");
            }
            String description = parts[0].trim();
            String byString = parts[1].trim();

            Deadline newDeadline = new Deadline(description, byString);
            taskList.addTask(newDeadline);

            System.out.println("____________________________________________________________");
            System.out.println("  \uD83C\uDF37I have added this task:");
            System.out.println("   " + taskList.getTasks().get(taskList.getTasks().size() - 1));
            System.out.println(" \uD83C\uDF37You now have " + taskList.getTasks().size() + " tasks in the list.");
            System.out.println("____________________________________________________________");
        }
        else if (input.startsWith("event")) {
            String The_Rest1 = input.substring(6).trim();
            int fromIndex = The_Rest1.indexOf(" /from ");
            int toIndex = The_Rest1.indexOf(" /to ");
            if (fromIndex == -1 || toIndex == -1 || fromIndex >= toIndex) {
                throw new SakuraException("\uD83C\uDF38Please use the actual format properly!\uD83C\uDF38");
            }
            String description1 = The_Rest1.substring(0, fromIndex).trim();
            String from = The_Rest1.substring(fromIndex + 7, toIndex).trim();
            String to = The_Rest1.substring(toIndex + 5).trim();
            if (description1.isEmpty() || from.isEmpty() || to.isEmpty()) {
                throw new SakuraException("\uD83C\uDF38Description, start time and end time cannot be empty.\uD83C\uDF38");
            }
            taskList.addTask(new Event(description1, from, to));
            System.out.println("____________________________________________________________");
            System.out.println("  \uD83C\uDF37I have added this task:");
            System.out.println("   " + taskList.getTasks().get(taskList.getTasks().size() - 1));
            System.out.println(" \uD83C\uDF37You now have " + taskList.getTasks().size() + " tasks in the list.");
            System.out.println("____________________________________________________________");
        }
        else if (input.startsWith("delete")) {
            int index = Integer.parseInt(input.substring(7).trim()) - 1;
            Task removedTask = taskList.removeTask(index);
            System.out.println("____________________________________________________________");
            System.out.println(" \uD83C\uDF37I have removed this task:\uD83C\uDF37");
            System.out.println("   " + removedTask);
            System.out.println(" Now you have " + taskList.getTasks().size() + " tasks in the list.");
            System.out.println("____________________________________________________________");
        }
        else {
            throw new SakuraException("I do not know what that means.\uD83C\uDF38\uD83D\uDE22");
        }
    }
}

/**
 * FriendlyBotSakura is a simple flower-themed task manager bot.
 */
public class FriendlyBotSakura {

    public static void main(String[] args) {
        Storage storage = new Storage("./data/SakuraStorage.txt");
        TaskList taskList = new TaskList(storage);
        Ui ui = new Ui(taskList);

        ui.start();
    }
}