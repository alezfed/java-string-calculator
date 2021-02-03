package com.epam.izh.rd.online.exception;

public class UnknownOperationException extends RuntimeException{
    public UnknownOperationException() {
    }

    public UnknownOperationException(String message) {
        super(message);
    }

    public UnknownOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownOperationException(Throwable cause) {
        super(cause);
    }
}