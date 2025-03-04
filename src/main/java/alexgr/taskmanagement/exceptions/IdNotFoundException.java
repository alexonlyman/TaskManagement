package alexgr.taskmanagement.exceptions;

/**
 * Custom exception to indicate that an ID was not found.
 * <p>
 * This exception is thrown when an operation attempts to access or manipulate
 * an entity using an ID that does not exist in the system. It provides a way
 * to handle scenarios where an expected ID is missing or invalid.
 * </p>
 */
public class IdNotFoundException extends Exception {

    /**
     * Constructs a new IdNotFoundException with the specified detail message.
     *
     * @param message The detail message explaining the cause of the exception.
     */
    public IdNotFoundException(String message) {
        super(message);
    }
}