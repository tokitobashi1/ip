import java.util.ArrayList;
import java.util.Scanner;

// Task class: Represents a single Task(Encapsulation)
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
            else if (input.equals("list")) {
                System.out.println("____________________________________________________________");
                System.out.println(" Here are the tasks in your list:");
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.println(" " + (i + 1) + "." + tasks.get(i));
                }
                System.out.println("____________________________________________________________");
            }
            else if (input.startsWith("mark ")) {
                int index = Integer.parseInt(input.substring(5)) - 1;
                tasks.get(index).markAsDone();
                System.out.println("____________________________________________________________");
                System.out.println(" \uD83C\uDF37Nice! I've marked this task as done:");
                System.out.println("   " + tasks.get(index));
                System.out.println("____________________________________________________________");
            }
            else if (input.startsWith("unmark")) {
                int index = Integer.parseInt(input.substring(7)) - 1;
                tasks.get(index).NotDone();
                System.out.println("____________________________________________________________");
                System.out.println(" \uD83C\uDF37I have marked this task as not done:");
                System.out.println("   " + tasks.get(index));
                System.out.println("____________________________________________________________");
            }
            else {
                tasks.add(new Task(input)); // add to list
                System.out.println("____________________________________________________________");
                System.out.println(" added: " + input);
                System.out.println("____________________________________________________________");
            }
        }

        sc.close();
    }
}
