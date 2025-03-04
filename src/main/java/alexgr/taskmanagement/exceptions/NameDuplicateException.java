package alexgr.taskmanagement.exceptions;

/**
 * Custom runtime exception to indicate that a name duplication error has occurred.
 * <p>
 * This exception is thrown when an operation attempts to create or update an entity
 * with a name that already exists in the system. It provides a way to handle
 * scenarios where duplicate name entries are detected.
 * </p>
 */
public class NameDuplicateException extends RuntimeException {

    /**
     * Constructs a new NameDuplicateException with the specified detail message.
     *
     * @param message The detail message explaining the cause of the exception.
     */
    public NameDuplicateException(String message) {
        super(message);
    }
}