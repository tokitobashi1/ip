import java.util.ArrayList;
import java.util.Scanner;

// Custom exception for Duke-specific errors
class SakuraException extends Exception {
    public SakuraException(String message) {
        super(message);
    }
}

// Task class: Represents a single Task (Encapsulation)
class Task {
    private String description;
    private boolean Done;

    public Task(String description) {
        this.description = description;
        this.Done = false;
    }

    public String getDescription() {
        return description;
    }

    public boolean getDone() {
        return Done;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDone(boolean done) {
        this.Done = done;
    }

    public String getStatus() {
        return (Done ? "X" : " ");
    }

    public void markAsDone() {
        this.Done = true;
    }

    public void NotDone() {
        this.Done = false;
    }

    @Override
    public String toString() {
        return "[" + getStatus() + "] " + description;
    }
}

// To_Do class subclass
class ToDo extends Task {
    public ToDo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}

// Deadline subclass
class Deadline extends Task {
    private String by;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}

// Event subclass
class Event extends Task {
    private String from;
    private String to;

    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }
}

public class FriendlyBotSakura {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Task> tasks = new ArrayList<>(); // store tasks here

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
                if (input.equals("list")) {
                    System.out.println("____________________________________________________________");
                    System.out.println(" \uD83C\uDF38Here are the tasks in your list\uD83C\uDF38:");
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println(" " + (i + 1) + "." + tasks.get(i));
                    }
                    System.out.println("____________________________________________________________");
                }
                else if (input.startsWith("mark")) {
                    int index = Integer.parseInt(input.substring(5)) - 1;
                    if (index < 0 || index >= tasks.size()) {
                        throw new SakuraException("That task number does not exist.");
                    }
                    tasks.get(index).markAsDone();
                    System.out.println("____________________________________________________________");
                    System.out.println(" \uD83C\uDF37Nice! I've marked this task as done:");
                    System.out.println("   " + tasks.get(index));
                    System.out.println("____________________________________________________________");
                }
                else if (input.startsWith("unmark")) {
                    int index = Integer.parseInt(input.substring(7)) - 1;
                    if (index < 0 || index >= tasks.size()) {
                        throw new SakuraException("That task number does not exist.");
                    }
                    tasks.get(index).NotDone();
                    System.out.println("____________________________________________________________");
                    System.out.println(" \uD83C\uDF37I have marked this task as not done:");
                    System.out.println("   " + tasks.get(index));
                    System.out.println("____________________________________________________________");
                }
                else if (input.startsWith("todo")) {
                    String description = input.substring(5).trim();
                    if (description.isEmpty()) {
                        throw new SakuraException("The description of a todo cannot be empty, please reenter!!!");
                    }
                    tasks.add(new ToDo(description));
                    System.out.println("____________________________________________________________");
                    System.out.println(" \uD83C\uDF37I have added this task:");
                    System.out.println("   " + tasks.get(tasks.size() - 1));
                    System.out.println(" \uD83C\uDF37You now have " + tasks.size() + " tasks in the list.");
                    System.out.println("____________________________________________________________");
                }
                else if (input.startsWith("deadline")) {
                    String The_Rest = input.substring(9).trim();
                    String[] parts = The_Rest.split(" /by ", 2);
                    if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
                        throw new SakuraException("Please use the actual format properly!");
                    }
                    tasks.add(new Deadline(parts[0].trim(), parts[1].trim()));
                    System.out.println("____________________________________________________________");
                    System.out.println("  \uD83C\uDF37I have added this task:");
                    System.out.println("   " + tasks.get(tasks.size() - 1));
                    System.out.println(" \uD83C\uDF37You now have " + tasks.size() + " tasks in the list.");
                    System.out.println("____________________________________________________________");
                }
                else if (input.startsWith("event")) {
                    String The_Rest1 = input.substring(6).trim();
                    int fromIndex = The_Rest1.indexOf(" /from ");
                    int toIndex = The_Rest1.indexOf(" /to ");
                    if (fromIndex == -1 || toIndex == -1 || fromIndex >= toIndex) {
                        throw new SakuraException("Please use the actual format properly!");
                    }
                    String description1 = The_Rest1.substring(0, fromIndex).trim();
                    String from = The_Rest1.substring(fromIndex + 7, toIndex).trim();
                    String to = The_Rest1.substring(toIndex + 5).trim();
                    if (description1.isEmpty() || from.isEmpty() || to.isEmpty()) {
                        throw new SakuraException("Description, start time and end time cannot be empty.");
                    }
                    tasks.add(new Event(description1, from, to));
                    System.out.println("____________________________________________________________");
                    System.out.println("  \uD83C\uDF37I have added this task:");
                    System.out.println("   " + tasks.get(tasks.size() - 1));
                    System.out.println(" \uD83C\uDF37You now have " + tasks.size() + " tasks in the list.");
                    System.out.println("____________________________________________________________");
                }
                else if (input.startsWith("delete")) {
                    int index = Integer.parseInt(input.substring(7).trim()) - 1;
                    if (index < 0 || index >= tasks.size()) {
                        throw new SakuraException("That task number does not exist.");
                    }
                    Task removedTask = tasks.remove(index);
                    System.out.println("____________________________________________________________");
                    System.out.println(" \uD83C\uDF37I have removed this task:\uD83C\uDF37");
                    System.out.println("   " + removedTask);
                    System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
                    System.out.println("____________________________________________________________");
                }
                else {
                    throw new SakuraException("I do not know what that means.\uD83C\uDF38\uD83D\uDE22");
                }
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
}