package com.epam.izh.rd.online.enity;

import com.epam.izh.rd.online.exception.IllegalNumberArgumentException;

public class MultiplyOperation extends Operation implements Calculable {

    public MultiplyOperation() {
        super("*", 2, 4, "\\*");
    }

    @Override
    public double getResult(double... args) {
        if (args.length != 2) {
            throw new IllegalNumberArgumentException("Неверное число агрументов в операции умножения.");
        }
        double value = args[0] * args[1];
        if (Double.isInfinite(value)) {
            throw new ArithmeticException("Переполнение вещественного числа при операции умножения.");
        }
        return value;
    }
}