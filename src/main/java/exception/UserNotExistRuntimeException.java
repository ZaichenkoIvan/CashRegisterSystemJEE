package main.java.exception;

public class UserNotExistRuntimeException extends RuntimeException {
    public UserNotExistRuntimeException() {
    }

    public UserNotExistRuntimeException(String message) {
        super(message);
    }

    public UserNotExistRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
