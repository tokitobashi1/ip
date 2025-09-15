package sakura.app;

import sakura.task.SakuraException;

public class Sakura {

    // You probably have a TaskList or some task storage here
    // private TaskList taskList;

    public String getResponse(String input) {
        input = input.trim();

        if ("help".equalsIgnoreCase(input)) {
            return getHelpMessage(); // returns the help string
        }

        // Handle other commands (list, todo, deadline, etc.)
        try {
            return processCommand(input);
        } catch (SakuraException e) {
            return "🌸 Error: " + e.getMessage();
        } catch (Exception e) {
            return "🌸 Unexpected error: " + e.getMessage();
        }
    }

    private String getHelpMessage() {
        return """
           🌸 Here are the commands you can use:
            list                     : List all tasks
            todo [description]       : Add a todo task
            deadline [desc] /by [date]: Add a deadline task
            event [desc] /from [start] /to [end] : Add an event
            mark [num]               : Mark a task as done
            unmark [num]             : Unmark a task
            delete [num]             : Delete a task
            find [keyword]           : Find tasks containing a keyword
            bye                      : Exit the app
            help                     : Show this help message
           """;
    }


    // Placeholder for your command processing logic
    private String processCommand(String input) throws SakuraException {
        // Implement parsing and execution of commands like list, todo, mark, etc.
        return "Sakura heard: " + input;
    }
}