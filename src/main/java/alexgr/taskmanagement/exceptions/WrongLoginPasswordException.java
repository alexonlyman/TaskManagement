package alexgr.taskmanagement.exceptions;

/**
 * Custom exception to indicate that a login attempt failed due to incorrect credentials.
 * <p>
 * This exception is thrown when an operation attempts to authenticate a user
 * with a login and password that do not match any existing user credentials in the system.
 * It provides a way to handle scenarios where authentication fails due to incorrect input.
 * </p>
 */
public class WrongLoginPasswordException extends Exception {

    /**
     * Constructs a new WrongLoginPasswordException with the specified detail message.
     *
     * @param message The detail message explaining the cause of the exception.
     */
    public WrongLoginPasswordException(String message) {
        super(message);
    }
}

