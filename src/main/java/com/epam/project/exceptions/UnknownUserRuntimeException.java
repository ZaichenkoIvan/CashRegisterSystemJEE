package com.epam.project.exceptions;

public class UnknownUserRuntimeException extends RuntimeException {
    public UnknownUserRuntimeException() {
        super("Unknown user");
    }

    public UnknownUserRuntimeException(String message) {
        super(message);
    }

    public UnknownUserRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
