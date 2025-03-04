package alexgr.taskmanagement.exceptions;

/**
 * Custom runtime exception to indicate that a mail duplication error has occurred.
 * <p>
 * This exception is thrown when an operation attempts to create or update an entity
 * with a mail address that already exists in the system. It provides a way to handle
 * scenarios where duplicate mail entries are detected.
 * </p>
 */
public class MailDuplicationException extends RuntimeException {

    /**
     * Constructs a new MailDuplicationException with the specified detail message.
     *
     * @param message The detail message explaining the cause of the exception.
     */
    public MailDuplicationException(String message) {
        super(message);
    }
}