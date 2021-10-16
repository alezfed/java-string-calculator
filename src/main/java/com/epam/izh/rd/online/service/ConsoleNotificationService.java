package com.epam.izh.rd.online.service;

import java.io.PrintStream;

public class ConsoleNotificationService implements NotificationService {
    public static final String START = "Добро пожаловать в консольный калькулятор строковых выражений.\n";
    public static final String CHOICE = "\nВыберите действие, которое хотите выполнить:\n"
            + "   0 - выход из программы;\n"
            + "   1 - перейти к вводу выражения;\n"
            + "   2 - использовать для вычисления шаблонный пример.\n"
            + "Чтобы выбрать действие, введите число от 0 до 2 включительно: ";
    public static final String INPUT_EXPRESSION = "Для вычисления выражения в калькуляторе доступны следующие\n"
            + "операторы и функции: [%s].\n"
            + "Введите выражение для вычиления: ";
    public static final String ANSWER = "Ответ: %s\n";
    public static final String EXAMPLE = "(-2) - ((-4) * 3.5)";
    public static final String EXAMPLE_TEXT = "Будет вычислено значение шаблонного выражения: %s\n";
    public static final String ERROR_MENU = "Произошла ошибка выбора действия в меню программы. Попробуйте "
            + "ввести действие еще раз.\n";
    public static final String ERROR = "Ошибка: %s\n";
    public static final String ERROR_TEXT = "Неверая расстановка операций и их значений в выражении.\n";
    public static final String ANSWER_FORMAT = "#.#####";

    public ConsoleNotificationService() {
        System.setOut(System.out);
    }

    public ConsoleNotificationService(PrintStream printStream) {
        System.setOut(printStream);
    }

    @Override
    public void showMessage(String message, String... pasteText) {
        if (pasteText.length == 1) {
            System.out.printf(message, pasteText[0]);
        } else {
            System.out.print(message);
        }
    }
}