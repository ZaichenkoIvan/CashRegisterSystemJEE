package com.epam.project.exceptions;

public class ProductServiceRuntimeException extends RuntimeException {
    public ProductServiceRuntimeException() {
        super();
    }

    public ProductServiceRuntimeException(String message) {
        super(message);
    }

    public ProductServiceRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
