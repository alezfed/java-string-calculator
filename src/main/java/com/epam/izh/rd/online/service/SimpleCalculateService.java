package com.epam.izh.rd.online.service;

import com.epam.izh.rd.online.enity.Calculable;
import com.epam.izh.rd.online.enity.Operation;
import com.epam.izh.rd.online.enity.OperationType;

import java.util.ArrayDeque;

public class SimpleCalculateService implements CalculateService {
    private final ArrayDeque<Double> numberStack;

    public SimpleCalculateService() {
        numberStack = new ArrayDeque<>();
    }

    public double calculateOperation(Operation operation) {
        int argumentCount = operation.getOperandCount();
        double[] arguments = new double[argumentCount];
        for (int i = argumentCount - 1; i >= 0; i--) {
            arguments[i] = numberStack.pop();
        }
        return ((Calculable) operation).getResult(arguments);
    }

    @Override
    public double calculateExpression(String polishNotation) {
        String[] tokens = polishNotation.split("\\s+");
        numberStack.clear();
        for (String token : tokens) {
            if (token.matches("[+-]?\\d+(\\.?\\d+)?")) {
                numberStack.push(Double.parseDouble(token));
            } else {
                numberStack.push(calculateOperation(OperationType.getOperationByStringForm(token)));
            }
        }
        return numberStack.pop();
    }
}