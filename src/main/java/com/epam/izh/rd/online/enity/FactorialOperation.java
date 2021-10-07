package com.epam.izh.rd.online.enity;

import com.epam.izh.rd.online.exception.IllegalNumberArgumentException;

public class FactorialOperation extends Operation implements Calculable {

    public FactorialOperation() {
        super("!", 1, 6, "!");
    }

    @Override
    public double getResult(double... args) {
        if (args.length != 1) {
            throw new IllegalNumberArgumentException("Неверное число агрументов в функции факториала.");
        }
        if (args[0] != (int) args[0] || args[0] < 0) {
            throw new ArithmeticException("Неверное значение агрумента в функуии факториала.");
        }
        if (args[0] == 0) {
            return 1;
        }
        double value = 1;
        for (int i=2; i<=args[0]; value*=i++);
        if (Double.isInfinite(value)) {
            throw new ArithmeticException("Переполнение вещественного числа в функуии факториала.");
        }
        return value;
    }
}