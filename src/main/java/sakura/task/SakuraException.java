package sakura.task;

/**
 * Custom exception for Sakura bot
 */
public class SakuraException extends Exception {
    /**
     * Constructs a SakuraException Class
     *
     * @param message the message as a string
     */
    public SakuraException(String message) {
        super(message);
    }
}
