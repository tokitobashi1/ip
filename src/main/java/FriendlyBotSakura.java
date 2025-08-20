import java.util.ArrayList;
import java.util.Scanner;

public class FriendlyBotSakura {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<String> tasks = new ArrayList<>(); // store tasks in this variable here!

        System.out.println("____________________________________________________________");
        System.out.println("Hi, I am your friendly bot \uD83C\uDF38Sakura\uD83C\uDF38, feel free to let me know what you need!");
        System.out.println("How can I assist you?");
        System.out.println("____________________________________________________________");

        String input;
        while (true) {
            input = sc.nextLine();

            if (input.equals("bye")) {
                System.out.println("____________________________________________________________");
                System.out.println(" Bye, I wish you a pleasant day!\uD83C\uDF37");
                System.out.println("____________________________________________________________");
                break;
            }
            else if (input.equals("list")) {
                System.out.println("____________________________________________________________");
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.println(" " + (i + 1) + ". " + tasks.get(i));
                }
                System.out.println("____________________________________________________________");
            }
            else {
                tasks.add(input); // add to list
                System.out.println("____________________________________________________________");
                System.out.println(" added: " + input);
                System.out.println("____________________________________________________________");
            }
        }

        sc.close();
    }
}