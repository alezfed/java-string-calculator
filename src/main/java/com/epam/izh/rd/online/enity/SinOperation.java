package com.epam.izh.rd.online.enity;

import com.epam.izh.rd.online.exception.IllegalNumberArgumentException;

public class SinOperation extends Operation implements Calculable {

    public SinOperation() {
        super("sin", 1, 0, "sin");
    }

    @Override
    public double getResult(double... args) {
        if (args.length != 1) {
            throw new IllegalNumberArgumentException("Неверное число аргументов в функции sin.");
        }
        return Math.sin(args[0]);
    }
}
