import java.util.Scanner;


public class FriendlyBotSakura {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("____________________________________________________________");
        System.out.println("Hi, I am your friendly bot Sakura, feel free to let me know what you need!");
        System.out.println("How can i assist you?");


        String input;
        while (true){
            input = sc.nextLine(); // assign it again, so it changes based on the input of the user

            if (input.equals("bye")) {
                System.out.println("____________________________________________________________");
                System.out.println(" Bye, I wish you a pleasant day!");
                break;
            }

            System.out.println("____________________________________________________________");
            System.out.println(" " + input);
            System.out.println("____________________________________________________________");
        }
    }
}
