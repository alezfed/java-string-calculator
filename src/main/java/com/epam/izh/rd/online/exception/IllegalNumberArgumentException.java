package com.epam.izh.rd.online.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class IllegalNumberArgumentException extends RuntimeException {

    public IllegalNumberArgumentException(String message) {
        super(message);
    }
}