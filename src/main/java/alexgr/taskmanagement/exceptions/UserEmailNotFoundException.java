package alexgr.taskmanagement.exceptions;

/**
 * Custom runtime exception to indicate that a user's email was not found.
 * <p>
 * This exception is thrown when an operation attempts to access or manipulate
 * a user entity using an email address that does not exist in the system.
 * It provides a way to handle scenarios where an expected email address is missing or invalid.
 * </p>
 */
public class UserEmailNotFoundException extends RuntimeException {

    /**
     * Constructs a new UserEmailNotFoundException with the specified detail message.
     *
     * @param message The detail message explaining the cause of the exception.
     */
    public UserEmailNotFoundException(String message) {
        super(message);
    }
}
