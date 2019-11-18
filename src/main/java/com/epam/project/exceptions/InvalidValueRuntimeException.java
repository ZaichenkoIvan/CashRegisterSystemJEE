package com.epam.project.exceptions;

public class InvalidValueRuntimeException extends RuntimeException {
    public InvalidValueRuntimeException() {
    }

    public InvalidValueRuntimeException(String message) {
        super(message);
    }

    public InvalidValueRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
