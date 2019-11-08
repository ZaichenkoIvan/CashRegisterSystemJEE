package project.model.exception;

public class InvalidCreationRuntimeException extends RuntimeException {
    public InvalidCreationRuntimeException() {
    }

    public InvalidCreationRuntimeException(String message) {
        super(message);
    }

    public InvalidCreationRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
