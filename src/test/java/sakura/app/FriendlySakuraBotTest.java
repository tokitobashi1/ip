

package sakura.app;

import org.junit.jupiter.api.Test;
import java.io.*;
import static org.junit.jupiter.api.Assertions.*;

class FriendlyBotSakuraFullOutputTest {

    @Test
    void testFullBotOutput() {
        String inputCommands = String.join("\n",
            "todo Read book",
            "todo",
            "deadline Return book /by 2019-12-02",
            "deadline Finish project /by 2023-13-01",
            "deadline Submit report /by 2023-08-20",
            "event Team meeting /from 14:00 /to 15:30",
            "event Conference /from 09:00 /to 08:00",
            "event Party /from 18:00",
            "list",
            "mark 1",
            "mark 99",
            "unmark 1",
            "unmark 0",
            "delete 2",
            "delete 99",
            "unknowncommand",
            "find book",
            "bye"
        );

        InputStream in = new ByteArrayInputStream(inputCommands.getBytes());
        System.setIn(in);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Run the main bot
        FriendlyBotSakura.main(new String[]{});

        String expectedOutput = String.join("\n",
            "____________________________________________________________",
            "Hi, I am your friendly bot ?Sakura?, feel free to let me know what you need!",
            "How can I assist you?",
            "____________________________________________________________",
            "____________________________________________________________",
            " ?I have added this task:",
            "   [T][ ] Read book",
            " ?You now have 1 tasks in the list.",
            "____________________________________________________________",
            "____________________________________________________________",
            " ??An unexpected error occurred: String index out of range: -1",
            "____________________________________________________________",
            "____________________________________________________________",
            "  ?I have added this task:",
            "   [D][ ] Return book (by: Dec 02 2019)",
            " ?You now have 2 tasks in the list.",
            "____________________________________________________________",
            "____________________________________________________________",
            " Error: Invalid date format! Please use yyyy-MM-dd",
            "____________________________________________________________",
            "____________________________________________________________",
            "  ?I have added this task:",
            "   [D][ ] Submit report (by: Aug 20 2023)",
            " ?You now have 3 tasks in the list.",
            "____________________________________________________________",
            "____________________________________________________________",
            "  ?I have added this task:",
            "   [E][ ] Team meeting (from: 14:00 to: 15:30)",
            " ?You now have 4 tasks in the list.",
            "____________________________________________________________",
            "____________________________________________________________",
            "  ?I have added this task:",
            "   [E][ ] Conference (from: 09:00 to: 08:00)",
            " ?You now have 5 tasks in the list.",
            "____________________________________________________________",
            "____________________________________________________________",
            " Error: ?Please use the actual format properly!?",
            "____________________________________________________________",
            "____________________________________________________________",
            " ?Here are the tasks in your list?:",
            " 1.[T][ ] Read book",
            " 2.[D][ ] Return book (by: Dec 02 2019)",
            " 3.[D][ ] Submit report (by: Aug 20 2023)",
            " 4.[E][ ] Team meeting (from: 14:00 to: 15:30)",
            " 5.[E][ ] Conference (from: 09:00 to: 08:00)",
            "____________________________________________________________",
            "____________________________________________________________",
            " ?Nice! I've marked this task as done:",
            "   [T][X] Read book",
            "____________________________________________________________",
            "____________________________________________________________",
            " Error: ?That task number does not exist.?",
            "____________________________________________________________",
            "____________________________________________________________",
            " ?I have marked this task as not done:",
            "   [T][ ] Read book",
            "____________________________________________________________",
            "____________________________________________________________",
            " Error: ?That task number does not exist.?",
            "____________________________________________________________",
            "____________________________________________________________",
            " ?I have removed this task:?",
            "   [D][ ] Return book (by: Dec 02 2019)",
            " Now you have 4 tasks in the list.",
            "____________________________________________________________",
            "____________________________________________________________",
            " Error: ?That task number does not exist.?",
            "____________________________________________________________",
            "____________________________________________________________",
            " Error: I do not know what that means.??",
            "____________________________________________________________",
            "____________________________________________________________",
            " Bye, I wish you a pleasant day!?",
            "____________________________________________________________"
        ) + System.lineSeparator(); // final newline

        assertEquals(expectedOutput, outContent.toString());
    }
}