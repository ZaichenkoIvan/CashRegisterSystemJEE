package com.epam.project.exceptions;

public class InvoiceServiceRuntimeException extends RuntimeException {

    public InvoiceServiceRuntimeException() {
        super();
    }

    public InvoiceServiceRuntimeException(String message) {
        super(message);
    }

    public InvoiceServiceRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
