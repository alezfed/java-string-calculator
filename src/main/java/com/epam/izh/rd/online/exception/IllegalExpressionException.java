package com.epam.izh.rd.online.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class IllegalExpressionException extends RuntimeException {

    public IllegalExpressionException(String message) {
        super(message);
    }
}