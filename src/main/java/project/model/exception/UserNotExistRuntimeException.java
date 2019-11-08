package project.model.exception;

public class UserNotExistRuntimeException extends RuntimeException {
    public UserNotExistRuntimeException() {
    }

    public UserNotExistRuntimeException(String s) {
        super(s);
    }

    public UserNotExistRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
