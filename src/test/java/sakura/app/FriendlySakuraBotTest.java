package sakura.app;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import sakura.task.ToDo;
import sakura.task.Deadline;
import sakura.task.SakuraException;
/**
 * A-JUnit requirement
 */
public class FriendlySakuraBotTest {

    @Test
    void todo_markAsDone_and_savingFormat() {
        // Create a ToDo
        ToDo todo = new ToDo("read book");
        assertFalse(todo.getDone(), "new todo should not be done");

        todo.markAsDone();
        assertTrue(todo.getDone(), "todo should be marked done after markAsDone()");


        String expected = "T | 1 | read book";
        assertEquals(expected, todo.saveFormat(), "SavingFormat should reflect done status and description");
    }

    @Test
    void deadline_parseValidDate_and_toString() throws SakuraException {
        Deadline d = new Deadline("submit report", "2023-08-20");

        assertNotNull(d.getBy(), "Deadline.getBy() should not be null for valid date");

        String s = d.toString();
        assertTrue(s.contains("Aug 20 2023"), "toString() should contain formatted date 'Aug 20 2023'");
        assertTrue(s.contains("[D]"), "toString() should include task type [D]");
        assertTrue(s.contains("submit report"), "toString() should include the description");
    }

    @Test
    void deadline_invalidDate_throwsSakuraException() {
        assertThrows(SakuraException.class, () -> {
            new Deadline("bad date", "2023-13-01");
        }, "Invalid date string should throw SakuraException");
    }
}
