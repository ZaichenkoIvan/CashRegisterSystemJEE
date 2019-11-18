package com.epam.project.exceptions;

public class TransactionServiceRuntimeException extends RuntimeException {
    public TransactionServiceRuntimeException() {
        super();
    }

    public TransactionServiceRuntimeException(String message) {
        super(message);
    }

    public TransactionServiceRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
