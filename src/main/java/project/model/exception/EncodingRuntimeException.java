package project.model.exception;

public class EncodingRuntimeException extends RuntimeException {
    public EncodingRuntimeException() {
    }

    public EncodingRuntimeException(String message) {
        super(message);
    }

    public EncodingRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
