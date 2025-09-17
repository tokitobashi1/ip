package sakura.app;

import sakura.task.SakuraException;
import sakura.task.TaskList;
import sakura.task.Task;
import sakura.task.ToDo;
import sakura.task.Deadline;
import sakura.task.Event;
import sakura.storage.Storage;

import java.util.ArrayList;

/**
 * The main class for the Sakura chatbot.
 * Handles user input and generates responses.
 */
public class Sakura {

    private final TaskList taskList;

    /**
     * Constructs a Sakura chatbot instance.
     * Uses a default storage file internally.
     */
    public Sakura() {
        String defaultFilePath = "data/sakura_tasks.txt";
        Storage storage = new Storage(defaultFilePath);
        this.taskList = new TaskList(storage);
    }

    /**
     * Processes a user's input and returns the chatbot response.
     *
     * @param input the raw input string from the user
     * @return the response string from Sakura
     */
    public String getResponse(String input) {
        if (input == null) {
            return "Error: Null input received.";
        }

        input = input.trim();

        if (input.isEmpty()) {
            return "";
        }

        if ("help".equalsIgnoreCase(input)) {
            return getHelpMessage();
        }

        try {
            return processCommand(input);
        } catch (SakuraException e) {
            return "Error: " + e.getMessage();
        } catch (Exception e) {
            return "Unexpected error: " + e.getMessage();
        }
    }

    /**
     * Returns the help message showing all available commands.
     *
     * @return a formatted help message string
     */
    private String getHelpMessage() {
        return String.join("\n",
                "Here are the commands you can use:",
                " list                     : List all tasks",
                " todo [description]       : Add a todo task",
                " deadline [desc] /by [date]: Add a deadline task",
                " event [desc] /from [start] /to [end] : Add an event",
                " mark [num]               : Mark a task as done",
                " unmark [num]             : Unmark a task",
                " delete [num]             : Delete a task",
                " find [keyword]           : Find tasks containing a keyword",
                " bye                      : Exit the app",
                " help                     : Show this help message"
        );
    }

    /**
     * Determines the command type and calls the appropriate handler.
     *
     * @param input the command string
     * @return the response string
     * @throws SakuraException if the command is invalid or fails
     */
    private String processCommand(String input) throws SakuraException {
        String trimmed = input.trim();
        if (trimmed.isEmpty()) {
            throw new SakuraException("I do not know what that means.");
        }

        String lower = trimmed.toLowerCase();

        if ("list".equals(lower)) {
            return handleList();
        }
        if (lower.startsWith("todo ")) {
            String arg = trimmed.substring(5).trim();
            return handleTodo(arg);
        }
        if (lower.startsWith("deadline ")) {
            String arg = trimmed.substring(9).trim();
            return handleDeadline(arg);
        }
        if (lower.startsWith("event ")) {
            String arg = trimmed.substring(6).trim();
            return handleEvent(arg);
        }
        if (lower.startsWith("mark ")) {
            String arg = trimmed.substring(5).trim();
            return handleMark(arg);
        }
        if (lower.startsWith("unmark ")) {
            String arg = trimmed.substring(7).trim();
            return handleUnmark(arg);
        }
        if (lower.startsWith("delete ")) {
            String arg = trimmed.substring(7).trim();
            return handleDelete(arg);
        }
        if (lower.startsWith("find ")) {
            String arg = trimmed.substring(5).trim();
            return handleFind(arg);
        }
        if ("bye".equals(lower)) {
            return "Bye, I wish you a pleasant day!";
        }

        throw new SakuraException("I do not know what that means.");
    }

    /**
     * Lists all tasks in the TaskList.
     *
     * @return a formatted string of all tasks
     */
    private String handleList() {
        ArrayList<Task> tasks = taskList.getTasks();
        if (tasks.isEmpty()) {
            return "Your task list is empty!";
        }
        StringBuilder sb = new StringBuilder("Here are the tasks in your list:\n");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append(" ").append(i + 1).append(". ").append(tasks.get(i)).append("\n");
        }
        return sb.toString();
    }

    /**
     * Adds a ToDo task to the TaskList.
     *
     * @param desc the description of the ToDo
     * @return a confirmation string
     * @throws SakuraException if description is empty
     */
    private String handleTodo(String desc) throws SakuraException {
        if (desc == null || desc.trim().isEmpty()) {
            throw new SakuraException("The description of a todo cannot be empty!");
        }
        ToDo todo = new ToDo(desc.trim());
        taskList.addTask(todo);
        return "I have added this task:\n  " + todo + "\nYou now have " + taskList.getTasks().size() + " tasks in the list.";
    }

    /**
     * Adds a Deadline task to the TaskList.
     *
     * @param input the command input containing description and /by date
     * @return a confirmation string
     * @throws SakuraException if the format is invalid
     */
    private String handleDeadline(String input) throws SakuraException {
        if (input == null) {
            throw new SakuraException("Please provide a description and /by date!");
        }
        String marker = " /by ";
        int idx = input.indexOf(marker);
        if (idx == -1) {
            throw new SakuraException("Please provide a description and /by date!");
        }
        String desc = input.substring(0, idx).trim();
        String by = input.substring(idx + marker.length()).trim();
        if (desc.isEmpty() || by.isEmpty()) {
            throw new SakuraException("Please provide a description and /by date!");
        }
        // Let Deadline constructor validate date format if it does so; propagate its SakuraException
        Deadline deadline = new Deadline(desc, by);
        taskList.addTask(deadline);
        return "I have added this task:\n  " + deadline + "\nYou now have " + taskList.getTasks().size() + " tasks in the list.";
    }

    /**
     * Adds an Event task to the TaskList.
     *
     * @param input the command input containing description, /from start, /to end
     * @return a confirmation string
     * @throws SakuraException if the format is invalid
     */
    private String handleEvent(String input) throws SakuraException {
        if (input == null) {
            throw new SakuraException("Please use the format: event [desc] /from [start] /to [end]");
        }
        String fromMarker = " /from ";
        String toMarker = " /to ";
        int fromIdx = input.indexOf(fromMarker);
        int toIdx = input.indexOf(toMarker);
        if (fromIdx == -1 || toIdx == -1 || fromIdx >= toIdx) {
            throw new SakuraException("Please use the format: event [desc] /from [start] /to [end]");
        }
        String desc = input.substring(0, fromIdx).trim();
        String from = input.substring(fromIdx + fromMarker.length(), toIdx).trim();
        String to = input.substring(toIdx + toMarker.length()).trim();
        if (desc.isEmpty() || from.isEmpty() || to.isEmpty()) {
            throw new SakuraException("Description, start, and end time cannot be empty!");
        }
        Event event = new Event(desc, from, to);
        taskList.addTask(event);
        return "I have added this task:\n  " + event + "\nYou now have " + taskList.getTasks().size() + " tasks in the list.";
    }

    /**
     * Marks a task as done.
     *
     * @param numStr the task number as string
     * @return a confirmation string
     * @throws SakuraException if the number is invalid
     */
    private String handleMark(String numStr) throws SakuraException {
        if (numStr == null || numStr.trim().isEmpty()) {
            throw new SakuraException("Please enter a valid task number!");
        }
        try {
            int index = Integer.parseInt(numStr.trim()) - 1;
            taskList.markTaskDone(index);
            return "Nice! I've marked this task as done:\n  " + taskList.getTasks().get(index);
        } catch (NumberFormatException e) {
            throw new SakuraException("Please enter a valid task number!");
        }
    }

    /**
     * Marks a task as not done.
     *
     * @param numStr the task number as string
     * @return a confirmation string
     * @throws SakuraException if the number is invalid
     */
    private String handleUnmark(String numStr) throws SakuraException {
        if (numStr == null || numStr.trim().isEmpty()) {
            throw new SakuraException("Please enter a valid task number!");
        }
        try {
            int index = Integer.parseInt(numStr.trim()) - 1;
            taskList.markTaskNotDone(index);
            return "I have marked this task as not done:\n  " + taskList.getTasks().get(index);
        } catch (NumberFormatException e) {
            throw new SakuraException("Please enter a valid task number!");
        }
    }

    /**
     * Deletes a task from the TaskList.
     *
     * @param numStr the task number as string
     * @return a confirmation string
     * @throws SakuraException if the number is invalid
     */
    private String handleDelete(String numStr) throws SakuraException {
        if (numStr == null || numStr.trim().isEmpty()) {
            throw new SakuraException("Please enter a valid task number!");
        }
        try {
            int index = Integer.parseInt(numStr.trim()) - 1;
            Task removed = taskList.removeTask(index);
            return "I have removed this task:\n  " + removed + "\nNow you have " + taskList.getTasks().size() + " tasks in the list.";
        } catch (NumberFormatException e) {
            throw new SakuraException("Please enter a valid task number!");
        }
    }

    /**
     * Finds tasks containing the given keyword.
     *
     * @param keyword the search keyword
     * @return a formatted list of matching tasks
     */
    private String handleFind(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return "No tasks match your search!";
        }
        ArrayList<Task> matches = taskList.findTasks(keyword.trim());
        if (matches.isEmpty()) return "No tasks match your search!";
        StringBuilder sb = new StringBuilder("Here are the matching tasks in your list:\n");
        for (int i = 0; i < matches.size(); i++) {
            sb.append(" ").append(i + 1).append(". ").append(matches.get(i)).append("\n");
        }
        return sb.toString();
    }
}
