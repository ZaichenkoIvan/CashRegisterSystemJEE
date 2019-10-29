package ua.cashregister.model.dao.exception;

public class DataNotFoundRuntimeException extends RuntimeException {
    public DataNotFoundRuntimeException() {
    }

    public DataNotFoundRuntimeException(String message) {
        super(message);
    }

    public DataNotFoundRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
