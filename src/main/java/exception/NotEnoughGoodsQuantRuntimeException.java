package exception;

public class NotEnoughGoodsQuantRuntimeException extends RuntimeException {
    public NotEnoughGoodsQuantRuntimeException() {
    }

    public NotEnoughGoodsQuantRuntimeException(String message) {
        super(message);
    }

    public NotEnoughGoodsQuantRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
