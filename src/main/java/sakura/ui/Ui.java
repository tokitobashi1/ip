package sakura.ui;

<<<<<<< HEAD
import java.util.ArrayList;
import java.util.Scanner;
import sakura.task.Deadline;
import sakura.task.Event;
import sakura.task.SakuraException;
import sakura.task.Task;
import sakura.task.TaskList;
import sakura.task.ToDo;
=======
import java.util.*;
import sakura.task.*;
>>>>>>> branch-Level-10

/**
 * Handles all user interaction and input parsing for the Sakura task manager.
 */
public class Ui {
    private Scanner sc;
    private TaskList taskList;

    /**
     * Constructs a Ui instance with the given task list.
     *
     * @param taskList TaskList to be managed by this Ui.
     */
    public Ui(TaskList taskList) {
        this.taskList = taskList;
        this.sc = new Scanner(System.in);
    }

    /**
     * Starts the UI, prompting the user for commands until "bye" is entered.
     */
    public void start() {
        System.out.println("____________________________________________________________");
        System.out.println(
                "Hi, I am your friendly bot \uD83C\uDF38Sakura\uD83C\uDF38, feel free to let me know what you need!");
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
                System.out.println(
                        " Please Retry: You need to enter a valid number for the task.\uD83C\uDF38\uD83D\uDE22");
                System.out.println("____________________________________________________________");
            } catch (Exception e) {
                System.out.println("____________________________________________________________");
                System.out.println(" \uD83C\uDF38\uD83D\uDE22An unexpected error occurred: " + e.getMessage());
                System.out.println("____________________________________________________________");
            }
        }

        sc.close();
    }

    /**
     * Processes a single user input command.
     *
     * @param input User input string.
     * @throws SakuraException If the input format is invalid.
     */
    private void processInput(String input) throws SakuraException {
        if (input.equals("list")) {
            System.out.println("____________________________________________________________");
            System.out.println(" \uD83C\uDF38Here are the tasks in your list\uD83C\uDF38:");
            ArrayList<Task> tasks = taskList.getTasks();
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println(" " + (i + 1) + "." + tasks.get(i));
            }
            System.out.println("____________________________________________________________");

        } else if (input.startsWith("mark")) {
            int index = Integer.parseInt(input.substring(5)) - 1;
            taskList.markTaskDone(index);
            System.out.println("____________________________________________________________");
            System.out.println(" \uD83C\uDF37Nice! I've marked this task as done:");
            System.out.println("   " + taskList.getTasks().get(index));
            System.out.println("____________________________________________________________");

        } else if (input.startsWith("unmark")) {
            int index = Integer.parseInt(input.substring(7)) - 1;
            taskList.markTaskNotDone(index);
            System.out.println("____________________________________________________________");
            System.out.println(" \uD83C\uDF37I have marked this task as not done:");
            System.out.println("   " + taskList.getTasks().get(index));
            System.out.println("____________________________________________________________");

        } else if (input.startsWith("todo")) {
            String description = input.substring(5).trim();
            if (description.isEmpty()) {
                throw new SakuraException(
                        "\uD83C\uDF38The description of a todo cannot be empty, please reenter!!!\uD83C\uDF38");
            }
            taskList.addTask(new ToDo(description));
            System.out.println("____________________________________________________________");
            System.out.println(" \uD83C\uDF37I have added this task:");
            System.out.println("   " + taskList.getTasks().get(taskList.getTasks().size() - 1));
            System.out.println(" \uD83C\uDF37You now have " + taskList.getTasks().size() + " tasks in the list.");
            System.out.println("____________________________________________________________");

        } else if (input.startsWith("deadline")) {
            String theRest = input.substring(9).trim();
            String[] parts = theRest.split(" /by ", 2);
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

        } else if (input.startsWith("event")) {
            String theRest = input.substring(6).trim();
            int fromIndex = theRest.indexOf(" /from ");
            int toIndex = theRest.indexOf(" /to ");

            if (fromIndex == -1 || toIndex == -1 || fromIndex >= toIndex) {
                throw new SakuraException("\uD83C\uDF38Please use the actual format properly!\uD83C\uDF38");
            }

            String description = theRest.substring(0, fromIndex).trim();
            String from = theRest.substring(fromIndex + 7, toIndex).trim();
            String to = theRest.substring(toIndex + 5).trim();

            if (description.isEmpty() || from.isEmpty() || to.isEmpty()) {
                throw new SakuraException("\uD83C\uDF38Description, start time and end time cannot be empty.\uD83C\uDF38");
            }

            taskList.addTask(new Event(description, from, to));
            System.out.println("____________________________________________________________");
            System.out.println("  \uD83C\uDF37I have added this task:");
            System.out.println("   " + taskList.getTasks().get(taskList.getTasks().size() - 1));
            System.out.println(" \uD83C\uDF37You now have " + taskList.getTasks().size() + " tasks in the list.");
            System.out.println("____________________________________________________________");

        } else if (input.startsWith("delete")) {
            int index = Integer.parseInt(input.substring(7).trim()) - 1;
            Task removedTask = taskList.removeTask(index);
            System.out.println("____________________________________________________________");
            System.out.println(" \uD83C\uDF37I have removed this task:\uD83C\uDF37");
            System.out.println("   " + removedTask);
            System.out.println(" Now you have " + taskList.getTasks().size() + " tasks in the list.");
            System.out.println("____________________________________________________________");

        } else if (input.startsWith("find ")) {
            String keyword = input.substring(5).trim();
            ArrayList<Task> matches = taskList.findTasks(keyword);

            System.out.println("____________________________________________________________");
            System.out.println(" \uD83C\uDF37Here are the matching tasks in your list:");
            for (int i = 0; i < matches.size(); i++) {
                System.out.println(" " + (i + 1) + "." + matches.get(i));
            }
            System.out.println("____________________________________________________________");

        } else {
            throw new SakuraException("I do not know what that means.\uD83C\uDF38\uD83D\uDE22");
        }
    }
}