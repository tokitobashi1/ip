package sakura.app;

import sakura.task.SakuraException;
/**
 * The main class for the Sakura chatbot.
 * Handles user input.
 */
public class Sakura {
    /**
     * Processes a user's input and returns the chatbot's response.
     *
     * @param input the raw input string from the user
     * @return the response string from Sakura
     */
    public String getResponse(String input) {
        input = input.trim();

        if ("help".equalsIgnoreCase(input)) {
            return getHelpMessage();
        }

        try {
            return processCommand(input);
        } catch (SakuraException e) {
            return "🌸 Error: " + e.getMessage();
        } catch (Exception e) {
            return "🌸 Unexpected error: " + e.getMessage();
        }
    }
    /**
     * Returns the help message showing all available commands.
     * @return a formatted help message string
     */
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
    private String processCommand(String input) throws SakuraException {
        return "Sakura heard: " + input;
    }
}