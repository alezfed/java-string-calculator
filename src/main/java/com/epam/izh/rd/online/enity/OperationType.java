package com.epam.izh.rd.online.enity;

import com.epam.izh.rd.online.exception.UnknownOperationException;

public enum OperationType {

    PLUS(new PlusOperation()),
    MINUS(new MinusOperation()),
    DIVIDE(new DivideOperation()),
    MULTIPLY(new MultiplyOperation()),
    CLOSING_BRACE(new ClosingBraceOperation()),
    OPENING_BRACE(new OpeningBraceOperation()),
    POWER(new PowerOperation()),
    SIN(new SinOperation()),
    FACTORIAL(new FactorialOperation());

    private final Operation operation;

    OperationType(Operation operation) {
        this.operation = operation;
    }

    public Operation getOperation() {
        return operation;
    }

    public static Operation getOperationByStringForm(String operationForm) {
        for (OperationType anyOperation : OperationType.values()) {
            if (anyOperation.getOperation().getStringForm().equalsIgnoreCase(operationForm)) {
                return anyOperation.getOperation();
            }
        }
        throw new UnknownOperationException("Неизвестная операция в выражении.");
    }
}