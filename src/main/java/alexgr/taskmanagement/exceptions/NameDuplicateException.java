package alexgr.taskmanagement.exeptions;

public class NameDuplicateException extends RuntimeException {
    public NameDuplicateException(String message) {
        super(message);
    }
}
