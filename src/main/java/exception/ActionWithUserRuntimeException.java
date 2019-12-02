package main.java.exception;

public class ActionWithUserRuntimeException extends RuntimeException {
    public ActionWithUserRuntimeException() {
    }

    public ActionWithUserRuntimeException(String message) {
        super(message);
    }

    public ActionWithUserRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
