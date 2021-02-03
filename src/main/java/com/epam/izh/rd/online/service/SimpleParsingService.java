package com.epam.izh.rd.online.service;

import com.epam.izh.rd.online.enity.Operation;
import com.epam.izh.rd.online.enity.OperationType;
import com.epam.izh.rd.online.exception.IllegalExpressionException;

import java.util.ArrayDeque;
import java.util.StringJoiner;

public class SimpleParsingService implements ParsingService {
    private final String NUMBER_REGEX;
    private final String OPENING_BRACE_REGEX;
    private final String CLOSING_BRACE_REGEX;
    private final String PREFIX_FUNCTION_REGEX;
    private final String POSTFIX_FUNCTION_REGEX;
    private final String UNARY_OPERATION_REGEX;
    private final String BIN_OPERATION_REGEX;
    private final String ALL_OPERATION_AND_NUMBER_REGEX;
    private final String ALL_OPERATION_TEXT;

    public SimpleParsingService() {
        NUMBER_REGEX = "[+-]?\\d+(\\.?\\d+)?";
        OPENING_BRACE_REGEX = OperationType.OPENING_BRACE.getOperation().getRegex();
        CLOSING_BRACE_REGEX = OperationType.CLOSING_BRACE.getOperation().getRegex();
        UNARY_OPERATION_REGEX = OperationType.PLUS.getOperation().getRegex() + "|"
                + OperationType.MINUS.getOperation().getRegex() + "|";
        BIN_OPERATION_REGEX = OperationType.PLUS.getOperation().getRegex() + "|"
                + OperationType.MINUS.getOperation().getRegex() + "|"
                + OperationType.MULTIPLY.getOperation().getRegex() + "|"
                + OperationType.DIVIDE.getOperation().getRegex() + "|"
                + OperationType.POWER.getOperation().getRegex();

        StringJoiner sumStringForm = new StringJoiner(", ");
        StringJoiner sumRegex = new StringJoiner("|");
        StringJoiner prefixRegex = new StringJoiner("|");
        StringJoiner postfixRegex = new StringJoiner("|");
        for (OperationType operation : OperationType.values()) {
            if (operation.getOperation().getPriority() == 0) {
                prefixRegex.add(operation.getOperation().getRegex());
            }
            if (operation.getOperation().getPriority() == 6) {
                postfixRegex.add(operation.getOperation().getRegex());
            }
            sumRegex.add(operation.getOperation().getRegex());
            sumStringForm.add(operation.getOperation().getStringForm());
        }
        ALL_OPERATION_TEXT = sumStringForm.toString();
        PREFIX_FUNCTION_REGEX = prefixRegex.toString();
        POSTFIX_FUNCTION_REGEX = postfixRegex.toString();
        ALL_OPERATION_AND_NUMBER_REGEX = sumRegex.add(NUMBER_REGEX).toString();
    }

    public String splitExpression(String expression) {
        expression = expression.replaceAll("\\s+", "");
        for (OperationType operation : OperationType.values()) {
            String regex = operation.getOperation().getRegex();
            expression = expression.replaceAll(regex, " " + regex + " ");
        }
        return expression.trim();
    }

    @Override
    public void validateOnContent(String expression) {
        expression = splitExpression(expression);
        String[] tokens = expression.split("\\s+");
        for (String token : tokens) {
            if (token.matches(ALL_OPERATION_AND_NUMBER_REGEX)) {
                continue;
            }
            throw new IllegalExpressionException("Неверное построение выражения или неизвестные операторы в нем.");
        }
        if (tokens[tokens.length - 1].matches(BIN_OPERATION_REGEX)) {
            throw new IllegalExpressionException("Выражение не может заканчиваться бинарной операцией.");
        }
    }

    @Override
    public String toPolishNotation(String expression) {
        expression = splitExpression(expression);
        String[] tokens = expression.split("\\s+");
        StringBuilder polishNotation = new StringBuilder();
        ArrayDeque<Operation> operationStack = new ArrayDeque<>();
        boolean isPossibleUnarOperation = true;
        for (String token : tokens) {
            if (token.matches(NUMBER_REGEX) || token.matches(POSTFIX_FUNCTION_REGEX)) {
                polishNotation.append(" ").append(token);
                isPossibleUnarOperation = false;
                continue;
            }
            if (token.matches(PREFIX_FUNCTION_REGEX)) {
                operationStack.push(OperationType.getOperationByStringForm(token));
                isPossibleUnarOperation = false;
                continue;
            }
            if (token.matches(OPENING_BRACE_REGEX)) {
                operationStack.push(OperationType.getOperationByStringForm(token));
                isPossibleUnarOperation = true;
                continue;
            }
            if (token.matches(CLOSING_BRACE_REGEX)) {
                Operation lastOperation;
                while (true) {
                    if (operationStack.isEmpty()) {
                        throw new IllegalExpressionException("Неверая расстановка скобок в выражении.");
                    }
                    lastOperation = operationStack.pop();
                    if (OperationType.OPENING_BRACE.getOperation().equals(lastOperation)) {
                        break;
                    } else {
                        polishNotation.append(" ").append(lastOperation.getStringForm());
                    }
                }
                lastOperation = operationStack.peekFirst();
                if (lastOperation != null && lastOperation.getPriority() == 0) {
                    polishNotation.append(" ").append(lastOperation.getStringForm());
                    operationStack.pop();
                }
                isPossibleUnarOperation = false;
                continue;
            }
            if (token.matches(UNARY_OPERATION_REGEX)) {
                if (isPossibleUnarOperation) {
                    polishNotation.append(" 0");
                }
            }
            if (token.matches(BIN_OPERATION_REGEX)) {
                Operation currentOperation = OperationType.getOperationByStringForm(token);
                while (true) {
                    Operation lastOperation = operationStack.peekFirst();
                    if (lastOperation == null) {
                        break;
                    }
                    if (currentOperation.getPriority() <= lastOperation.getPriority()) {
                        polishNotation.append(" ").append(lastOperation.getStringForm());
                        operationStack.pop();
                    } else {
                        break;
                    }
                }
                operationStack.push(currentOperation);
                isPossibleUnarOperation = false;
            }
        }
        while (!operationStack.isEmpty()) {
            polishNotation.append(" ").append(operationStack.pop().getStringForm());
        }
        return polishNotation.toString().trim();
    }

    @Override
    public String getAllowedOperation() {
        return ALL_OPERATION_TEXT;
    }
}