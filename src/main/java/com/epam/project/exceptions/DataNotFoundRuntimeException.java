package com.epam.project.exceptions;

public class DataNotFoundRuntimeException extends RuntimeException {
    public DataNotFoundRuntimeException() {
        super("Data not found in DB by specified key");
    }

    public DataNotFoundRuntimeException(String message) {
        super(message);
    }

    public DataNotFoundRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
