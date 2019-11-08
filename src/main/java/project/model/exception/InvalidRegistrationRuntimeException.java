package project.model.exception;

public class InvalidRegistrationRuntimeException extends RuntimeException {
    public InvalidRegistrationRuntimeException() {
    }

    public InvalidRegistrationRuntimeException(String s) {
        super(s);
    }

    public InvalidRegistrationRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
