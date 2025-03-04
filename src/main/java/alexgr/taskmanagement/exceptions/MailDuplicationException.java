package alexgr.taskmanagement.exeptions;

public class MailDuplicationException extends RuntimeException {
    public MailDuplicationException(String message) {
        super(message);
    }
}
