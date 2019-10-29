package ua.cashregister.model.dao.exception;

public class DataBaseConnectionRuntimeException extends RuntimeException {

    public DataBaseConnectionRuntimeException() {
    }

    public DataBaseConnectionRuntimeException(String message) {
        super(message);
    }

    public DataBaseConnectionRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
