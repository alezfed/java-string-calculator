package com.epam.izh.rd.online.exception;

public class IllegalNumberArgumentException extends RuntimeException {
    public IllegalNumberArgumentException() {
    }

    public IllegalNumberArgumentException(String message) {
        super(message);
    }

    public IllegalNumberArgumentException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalNumberArgumentException(Throwable cause) {
        super(cause);
    }
}