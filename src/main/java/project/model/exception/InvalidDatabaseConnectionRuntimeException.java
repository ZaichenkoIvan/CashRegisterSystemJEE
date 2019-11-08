package project.model.exception;

public class InvalidDatabaseConnectionRuntimeException extends RuntimeException {
    public InvalidDatabaseConnectionRuntimeException() {
    }

    public InvalidDatabaseConnectionRuntimeException(String message) {
        super(message);
    }

    public InvalidDatabaseConnectionRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
