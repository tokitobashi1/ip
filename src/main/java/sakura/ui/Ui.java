package sakura.ui;

import java.util.ArrayList;
import java.util.Scanner;
import sakura.task.Deadline;
import sakura.task.Event;
import sakura.task.Task;
import sakura.task.TaskList;
import sakura.task.ToDo;
import sakura.task.SakuraException;

/**
 * Handles all user interaction and input parsing for Sakura task manager.
 */
public class Ui {

    private static final String DIVIDER = "____________________________________________________________";

    private final Scanner scanner;
    private final TaskList taskList;

    /**
     * Constructs a UI instance with the given TaskList.
     *
     * @param taskList TaskList to be managed by this UI.
     */
    public Ui(TaskList taskList) {
        this.taskList = taskList;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Starts the user interface loop.
     */
    public void start() {
        printDivider();
        System.out.println("Hi, I am your friendly bot \uD83C\uDF38Sakura\uD83C\uDF38, feel free to let me know what you need!");
        System.out.println("How can I assist you?");
        printDivider();

        while (true) {
            String input = scanner.nextLine().trim();

            if ("bye".equals(input)) {
                exit();
                break;
            }

            try {
                processInput(input);
            } catch (NumberFormatException e) {
                printError("Please Retry: You need to enter a valid number for the task.\uD83C\uDF38\uD83D\uDE22");
            } catch (SakuraException e) {
                printError("Error: " + e.getMessage());
            } catch (Exception e) {
                printError("\uD83C\uDF38\uD83D\uDE22An unexpected error occurred: " + e.getMessage());
            }
        }

        scanner.close();
    }

    private void processInput(String input) throws SakuraException {
        if ("list".equals(input)) {
            handleList();
            return;
        }

        if ("help".equals(input)) {
            handleHelp();
            return;
        }

        if (input.startsWith("mark ")) {
            handleMark(input);
            return;
        }

        if (input.startsWith("unmark ")) {
            handleUnmark(input);
            return;
        }

        if (input.startsWith("todo ")) {
            handleTodo(input);
            return;
        }

        if (input.startsWith("deadline ")) {
            handleDeadline(input);
            return;
        }

        if (input.startsWith("event ")) {
            handleEvent(input);
            return;
        }

        if (input.startsWith("delete ")) {
            handleDelete(input);
            return;
        }

        if (input.startsWith("find ")) {
            handleFind(input);
            return;
        }

        throw new SakuraException("I do not know what that means.\uD83C\uDF38\uD83D\uDE22");
    }

    private void handleList() {
        printDivider();
        System.out.println(" \uD83C\uDF38Here are the tasks in your list\uD83C\uDF38:");
        ArrayList<Task> tasks = taskList.getTasks();
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(" " + (i + 1) + "." + tasks.get(i));
        }
        printDivider();
    }

    private void handleMark(String input) {
        try {
            int index = Integer.parseInt(input.substring(5).trim()) - 1;
            taskList.markTaskDone(index);
            printTaskStatus("Nice! I've marked this task as done:", index);
        } catch (SakuraException e) {
            printError("Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            printError("Please Retry: You need to enter a valid number for the task.\uD83C\uDF38\uD83D\uDE22");
        }
    }

    private void handleUnmark(String input) {
        try {
            int index = Integer.parseInt(input.substring(7).trim()) - 1;
            taskList.markTaskNotDone(index);
            printTaskStatus("I have marked this task as not done:", index);
        } catch (SakuraException e) {
            printError("Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            printError("Please Retry: You need to enter a valid number for the task.\uD83C\uDF38\uD83D\uDE22");
        }
    }

    private void handleTodo(String input) throws SakuraException {
        String description = input.substring(5).trim();
        if (description.isEmpty()) {
            throw new SakuraException("\uD83C\uDF38The description of a todo cannot be empty, please reenter!!!\uD83C\uDF38");
        }
        ToDo todo = new ToDo(description);
        taskList.addTask(todo);
        printTaskAdded(todo);
    }

    private void handleDeadline(String input) throws SakuraException {
        String remainder = input.substring(9).trim();
        String[] parts = remainder.split(" /by ", 2);
        if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
            throw new SakuraException("\uD83C\uDF38Please use the actual format properly!\uD83C\uDF38");
        }
        Deadline deadline = new Deadline(parts[0].trim(), parts[1].trim());
        taskList.addTask(deadline);
        printTaskAdded(deadline);
    }

    private void handleEvent(String input) throws SakuraException {
        String remainder = input.substring(6).trim();
        int fromIndex = remainder.indexOf(" /from ");
        int toIndex = remainder.indexOf(" /to ");
        if (fromIndex == -1 || toIndex == -1 || fromIndex >= toIndex) {
            throw new SakuraException("\uD83C\uDF38Please use the actual format properly!\uD83C\uDF38");
        }
        String description = remainder.substring(0, fromIndex).trim();
        String from = remainder.substring(fromIndex + 7, toIndex).trim();
        String to = remainder.substring(toIndex + 5).trim();
        if (description.isEmpty() || from.isEmpty() || to.isEmpty()) {
            throw new SakuraException("\uD83C\uDF38Description, start time and end time cannot be empty.\uD83C\uDF38");
        }
        Event event = new Event(description, from, to);
        taskList.addTask(event);
        printTaskAdded(event);
    }

    private void handleDelete(String input) {
        try {
            int index = Integer.parseInt(input.substring(7).trim()) - 1;
            Task removedTask = taskList.removeTask(index);
            printDivider();
            System.out.println(" \uD83C\uDF37I have removed this task:\uD83C\uDF37");
            System.out.println("   " + removedTask);
            System.out.println(" Now you have " + taskList.getTasks().size() + " tasks in the list.");
            printDivider();
        } catch (SakuraException e) {
            printError("Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            printError("Please Retry: You need to enter a valid number for the task.\uD83C\uDF38\uD83D\uDE22");
        }
    }


    private void handleFind(String input) {
        String keyword = input.substring(5).trim();
        ArrayList<Task> matches = taskList.findTasks(keyword);
        printDivider();
        System.out.println(" \uD83C\uDF37Here are the matching tasks in your list:");
        for (int i = 0; i < matches.size(); i++) {
            System.out.println(" " + (i + 1) + "." + matches.get(i));
        }
        printDivider();
    }

    private void handleHelp() {
        printDivider();
        System.out.println(" \uD83C\uDF38Here are the commands you can use:");
        System.out.println(" list                     : List all tasks");
        System.out.println(" todo [description]       : Add a todo task");
        System.out.println(" deadline [desc] /by [date]: Add a deadline task");
        System.out.println(" event [desc] /from [start] /to [end] : Add an event");
        System.out.println(" mark [num]               : Mark a task as done");
        System.out.println(" unmark [num]             : Unmark a task");
        System.out.println(" delete [num]             : Delete a task");
        System.out.println(" find [keyword]           : Find tasks containing a keyword");
        System.out.println(" bye                      : Exit the app");
        System.out.println(" help                     : Show this help message");
        printDivider();
    }
    private void printDivider() {
        System.out.println(DIVIDER);
    }

    private void printError(String message) {
        printDivider();
        System.out.println(" " + message);
        printDivider();
    }

    private void printTaskAdded(Task task) {
        printDivider();
        System.out.println(" \uD83C\uDF37I have added this task:");
        System.out.println("   " + task);
        System.out.println(" \uD83C\uDF37You now have " + taskList.getTasks().size() + " tasks in the list.");
        printDivider();
    }

    private void printTaskStatus(String message, int index) {
        printDivider();
        System.out.println(" \uD83C\uDF37" + message);
        System.out.println("   " + taskList.getTasks().get(index));
        printDivider();
    }

    private void exit() {
        printDivider();
        System.out.println(" Bye, I wish you a pleasant day!\uD83C\uDF37");
        printDivider();
    }
}
