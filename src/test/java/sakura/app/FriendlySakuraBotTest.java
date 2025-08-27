package sakura.app;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import sakura.task.ToDo;
import sakura.task.Deadline;
import sakura.task.SakuraException;
/**
 * Minimal unit tests for A-JUnit requirement
 */
public class FriendlySakuraBotTest {

    @Test
    void todo_markAsDone_and_savingFormat() {
        // Create a ToDo and verify initial state
        ToDo todo = new ToDo("read book");
        assertFalse(todo.getDone(), "new todo should not be done");

        // mark as done and verify
        todo.markAsDone();
        assertTrue(todo.getDone(), "todo should be marked done after markAsDone()");

        // SavingFormat: ToDo prepends "T | " + Task.SavingFormat()
        // Task.SavingFormat returns "<1 or 0> | <description>"
        String expected = "T | 1 | read book";
        assertEquals(expected, todo.SavingFormat(), "SavingFormat should reflect done status and description");
    }

    @Test
    void deadline_parseValidDate_and_toString() throws SakuraException {
        // use yyyy-MM-dd input as your Deadline expects (e.g., 2023-08-20)
        Deadline d = new Deadline("submit report", "2023-08-20");

        // getBy() should exist and return a LocalDate (this method is present in your Deadline)
        assertNotNull(d.getBy(), "Deadline.getBy() should not be null for valid date");

        // toString should contain the formatted date "Aug 20 2023" as per your OUTPUT formatter
        String s = d.toString();
        assertTrue(s.contains("Aug 20 2023"), "toString() should contain formatted date 'Aug 20 2023'");
        assertTrue(s.contains("[D]"), "toString() should include task type [D]");
        assertTrue(s.contains("submit report"), "toString() should include the description");
    }

    @Test
    void deadline_invalidDate_throwsSakuraException() {
        //  invalid month should cause constructor to throw SakuraException
        assertThrows(SakuraException.class, () -> {
            new Deadline("bad date", "2023-13-01");
        }, "Invalid date string should throw SakuraException");
    }
}
