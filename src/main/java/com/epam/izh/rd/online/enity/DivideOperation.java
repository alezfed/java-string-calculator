package com.epam.izh.rd.online.enity;

import com.epam.izh.rd.online.exception.IllegalNumberArgumentException;

public class DivideOperation extends Operation implements Calculable {

    public DivideOperation() {
        super("/", 2, 4, "/");
    }

    @Override
    public double getResult(double... args) {
        if (args.length != 2) {
            throw new IllegalNumberArgumentException("Неверное число агрументов в операции вещественного деления.");
        }
        if (args[1] == 0.0D) {
            throw new ArithmeticException("Ошибка вещественного деления на ноль.");
        }
        double value = args[0] / args[1];
        if (Double.isInfinite(value)) {
            throw new ArithmeticException("Переполнение вещественного числа при операции деления.");
        }
        return args[0] / args[1];
    }
}