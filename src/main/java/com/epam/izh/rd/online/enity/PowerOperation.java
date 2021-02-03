package com.epam.izh.rd.online.enity;

import com.epam.izh.rd.online.exception.IllegalNumberArgumentException;

public class PowerOperation extends Operation implements Calculable {

    public PowerOperation() {
        super("^", 2, 5, "\\^");
    }

    @Override
    public double getResult(double... args) {
        if (args.length != 2) {
            throw new IllegalNumberArgumentException("Неверное число агрументов в операции возведения в степень.");
        }
        double value = Math.pow(args[0], args[1]);
        if (Double.isNaN(value)) {
            throw new ArithmeticException("Ошибка при операции возведения в степень.");
        }
        if (Double.isInfinite(value)) {
            throw new ArithmeticException("Переполнение вещественного числа при операции возведения в степень.");
        }
        return value;
    }
}