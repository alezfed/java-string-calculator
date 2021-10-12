package com.epam.izh.rd.online.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UnknownOperationException extends RuntimeException {

    public UnknownOperationException(String message) {
        super(message);
    }
}