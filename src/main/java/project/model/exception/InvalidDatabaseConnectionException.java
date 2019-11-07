package project.model.exception;

public class InvalidDatabaseConnectionException extends RuntimeException {
    public InvalidDatabaseConnectionException() {
    }

    public InvalidDatabaseConnectionException(String message) {
        super(message);
    }

    public InvalidDatabaseConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
