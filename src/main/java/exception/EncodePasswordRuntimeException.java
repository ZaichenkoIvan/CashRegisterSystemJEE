package exception;

public class EncodePasswordRuntimeException extends RuntimeException {
    public EncodePasswordRuntimeException() {
    }

    public EncodePasswordRuntimeException(String message) {
        super(message);
    }

    public EncodePasswordRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
