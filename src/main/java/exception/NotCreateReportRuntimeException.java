package exception;

public class NotCreateReportRuntimeException extends RuntimeException {
    public NotCreateReportRuntimeException() {
    }

    public NotCreateReportRuntimeException(String message) {
        super(message);
    }

    public NotCreateReportRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
