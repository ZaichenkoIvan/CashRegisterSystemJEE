package ua.cashregister.model.dao.exception;

public class IncorrectPropertyRuntimeException extends RuntimeException {

    public IncorrectPropertyRuntimeException() {
    }

    public IncorrectPropertyRuntimeException(String message) {
        super(message);
    }

    public IncorrectPropertyRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
