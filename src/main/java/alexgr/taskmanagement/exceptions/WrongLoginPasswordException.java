package alexgr.taskmanagement.exeptions;

public class WrongLoginPasswordException extends Exception {
    public WrongLoginPasswordException(String message) {
        super(message);
    }
}

