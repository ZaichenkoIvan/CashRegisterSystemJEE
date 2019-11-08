package project.model.exception;

public class UserAlreadyRegisteredRuntimeException extends RuntimeException {
    public UserAlreadyRegisteredRuntimeException() {
    }

    public UserAlreadyRegisteredRuntimeException(String s) {
        super(s);
    }

    public UserAlreadyRegisteredRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
