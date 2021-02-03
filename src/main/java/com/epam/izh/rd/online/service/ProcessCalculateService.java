package com.epam.izh.rd.online.service;

import com.epam.izh.rd.online.exception.IllegalExpressionException;
import com.epam.izh.rd.online.exception.IllegalNumberArgumentException;
import com.epam.izh.rd.online.exception.UnknownOperationException;

import java.text.DecimalFormat;

public class ProcessCalculateService {
    private final CalculateService calculateService;
    private final ParsingService parsingService;
    private final NotificationService notificationService;
    private final UserRequestService userRequestService;
    private final DecimalFormat decimalFormat;

    public ProcessCalculateService() {
        calculateService = new SimpleCalculateService();
        parsingService = new SimpleParsingService();
        notificationService = new ConsoleNotificationService();
        userRequestService = new ConsoleUserRequestService();
        decimalFormat = new DecimalFormat(ConsoleNotificationService.ANSWER_FORMAT);
    }

    public void calculateInConsole(String expression) {
        try {
            parsingService.validateOnContent(expression);
            notificationService.showMessage(ConsoleNotificationService.ANSWER,
                    decimalFormat.format(calculateService.calculateExpression(parsingService.toPolishNotation(expression))));
        } catch (ArithmeticException | IllegalExpressionException | IllegalNumberArgumentException |
                UnknownOperationException exception) {
            notificationService.showMessage(ConsoleNotificationService.ERROR, exception.getMessage());
        } catch (Exception exception) {
            notificationService.showMessage(ConsoleNotificationService.ERROR,ConsoleNotificationService.EXAMPLE_TEXT);
        }
    }

    public void startExpressionCalculator() {
        notificationService.showMessage(ConsoleNotificationService.START);
        while (true) {
            notificationService.showMessage(ConsoleNotificationService.CHOICE);
            int userChoice = userRequestService.getIntNumber();
            switch (userChoice) {
                case 0:
                    System.exit(0);
                    break;
                case 1:
                    notificationService.showMessage(ConsoleNotificationService.INPUT_EXPRESSION,
                            parsingService.getAllowedOperation());
                    calculateInConsole(userRequestService.getExpression());
                    break;
                case 2:
                    notificationService.showMessage(ConsoleNotificationService.EXAMPLE_TEXT,
                            ConsoleNotificationService.EXAMPLE);
                    calculateInConsole(ConsoleNotificationService.EXAMPLE);
                    break;
                default:
                    notificationService.showMessage(ConsoleNotificationService.ERROR_MENU);
                    break;
            }
        }
    }
}