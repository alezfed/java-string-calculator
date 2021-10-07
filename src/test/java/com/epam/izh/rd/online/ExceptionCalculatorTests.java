package com.epam.izh.rd.online;

import com.epam.izh.rd.online.exception.IllegalExpressionException;
import com.epam.izh.rd.online.exception.UnknownOperationException;
import com.epam.izh.rd.online.service.CalculateService;
import com.epam.izh.rd.online.service.ParsingService;
import com.epam.izh.rd.online.service.SimpleCalculateService;
import com.epam.izh.rd.online.service.SimpleParsingService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static net.obvj.junit.utils.matchers.AdvancedMatchers.throwsException;
import static org.hamcrest.MatcherAssert.assertThat;

@DisplayName("Class for testing exception results of text calculator")
class ExceptionCalculatorTests {
    private static CalculateService calculateService;
    private static ParsingService parsingService;

    @BeforeAll
    static void initExceptionCalculatorTests() {
        calculateService = new SimpleCalculateService();
        parsingService = new SimpleParsingService();
    }

    @DisplayName("Exception handling divide-by-zero")
    @ParameterizedTest(name = "{index} => a={0}, b={1}, message={2}")
    @CsvSource({"7, 0, exception was handled"})
    void calculateDivideByZero(double a, double b, String message) {
        assertThat(() -> calculateService.calculateExpression(parsingService
                        .toPolishNotation(a + "/" + b)),
                throwsException(ArithmeticException.class)
                        .withMessageContaining("деления на ноль"));
    }

    @DisplayName("Exception with double overflow during division operation")
    @ParameterizedTest(name = "{index} => a={0}, b={1}, message={2}")
    @CsvSource({"1.0E+307, 0.000000005, exception was handled"})
    void calculateDoubleOverflowInDivision(double a, double b, String message) {
        assertThat(() -> calculateService.calculateExpression(parsingService
                        .toPolishNotation("(10^307)/0.000000005")),
                throwsException(ArithmeticException.class)
                        .withMessageContaining("Переполнение вещественного числа при операции деления"));
    }

    @DisplayName("Exception with double overflow during subtraction operation")
    @ParameterizedTest(name = "{index} => a={0}, b={1}, message={2}")
    @CsvSource({"1.0E+308, -1.0E+308, exception was handled"})
    void calculateDoubleOverflowInSubtraction(double a, double b, String message) {
        assertThat(() -> calculateService.calculateExpression(parsingService
                        .toPolishNotation("(10^308)-(-10^308)")),
                throwsException(ArithmeticException.class)
                        .withMessageContaining("Переполнение вещественного числа при операции вычитания"));
    }

    @DisplayName("Exception with double overflow during multiply operation")
    @ParameterizedTest(name = "{index} => a={0}, b={1}, message={2}")
    @CsvSource({"1.0E+308, 1.0E+308, exception was handled"})
    void calculateDoubleOverflowInMultiply(double a, double b, String message) {
        assertThat(() -> calculateService.calculateExpression(parsingService
                        .toPolishNotation("(10^308)*(10^308)")),
                throwsException(ArithmeticException.class)
                        .withMessageContaining("Переполнение вещественного числа при операции умножения"));
    }

    @DisplayName("Exception with double overflow during sum operation")
    @ParameterizedTest(name = "{index} => a={0}, b={1}, message={2}")
    @CsvSource({"1.0E+308, 1.0E+308, exception was handled"})
    void calculateDoubleOverflowInSum(double a, double b, String message) {
        assertThat(() -> calculateService.calculateExpression(parsingService
                        .toPolishNotation("(10^308)+(10^308)")),
                throwsException(ArithmeticException.class)
                        .withMessageContaining("Переполнение вещественного числа при операции суммирования"));
    }

    @DisplayName("Exception with double overflow during power operation")
    @ParameterizedTest(name = "{index} => a={0}, b={1}, message={2}")
    @CsvSource({"10, 500, exception was handled"})
    void calculateDoubleOverflowInPower(double a, double b, String message) {
        assertThat(() -> calculateService.calculateExpression(parsingService
                        .toPolishNotation(a + "^" + b)),
                throwsException(ArithmeticException.class)
                        .withMessageContaining("Переполнение вещественного числа при операции возведения в степень"));
    }

    @DisplayName("Exception with double overflow during factorial operation")
    @ParameterizedTest(name = "{index} => n={0}, message={1}")
    @CsvSource({"1000, exception was handled"})
    void calculateDoubleOverflowInFactorial(double n, String message) {
        assertThat(() -> calculateService.calculateExpression(parsingService
                        .toPolishNotation(n + "!")),
                throwsException(ArithmeticException.class)
                        .withMessageContaining("Переполнение вещественного числа в функуии факториала"));
    }

    @DisplayName("Exception with NaN value in power function")
    @ParameterizedTest(name = "{index} => a={0}, b={1}, message={2}")
    @CsvSource({
            "-2.5, 1.7, exception was handled",
            "-3  , 7.3, exception was handled"
    })
    void calculateNanValueInPower(double a, double b, String message) {
        assertThat(() -> calculateService.calculateExpression(parsingService
                        .toPolishNotation("(" + a + ")^" + b)),
                throwsException(ArithmeticException.class)
                        .withMessageContaining("Ошибка при операции возведения в степень"));
    }

    @DisplayName("Exception with invalid argument in factorial")
    @ParameterizedTest(name = "{index} => n={0}, message={1}")
    @CsvSource({
            "2.5, exception was handled",
            "-3 , exception was handled"
    })
    void calculateInvalidArgumentInFactorial(double n, String message) {
        assertThat(() -> calculateService.calculateExpression(parsingService
                        .toPolishNotation("(" + n + ")!")),
                throwsException(ArithmeticException.class)
                        .withMessageContaining("Неверное значение агрумента в функуии факториала"));
    }

    @DisplayName("Exception handling on incorrect braces")
    @ParameterizedTest(name = "{index} => expression={0}, message={1}")
    @CsvSource({
            ")(-2),   exception was handled",
            "2+(-5)), exception was handled",
            "(5-)2),  exception was handled"
    })
    void calculateWithIncorrectBraces(String expr, String message) {
        assertThat(() -> calculateService.calculateExpression(parsingService
                .toPolishNotation(expr)), throwsException(IllegalExpressionException.class)
                .withMessageContaining("расстановка скобок"));
    }

    @DisplayName("Exception handling on incorrect double operation")
    @ParameterizedTest(name = "{index} => expression={0}, message={1}")
    @CsvSource({
            "3+-+++2, exception was handled",
            "-*2,     exception was handled",
            "(5-/+2), exception was handled"
    })
    void calculateWithIncorrectDoubleOperation(String expr, String message) {
        assertThat(() -> {
            parsingService.validateOnContent(expr);
            calculateService.calculateExpression(parsingService
                    .toPolishNotation(expr));
        }, throwsException(Exception.class));
    }

    @DisplayName("Exception handling on unknown operation or char in expression")
    @ParameterizedTest(name = "{index} => expression={0}, message={1}")
    @CsvSource({
            "xcv+2,    exception was handled",
            "cos(5)+3, exception was handled",
            "3-x,      exception was handled"
    })
    void calculateWithUnknownOperation(String expr, String message) {
        assertThat(() -> {
            parsingService.validateOnContent(expr);
            calculateService.calculateExpression(parsingService
                    .toPolishNotation(expr));
        }, throwsException(UnknownOperationException.class)
                .withMessageContaining("построение выражения или неизвестные операторы"));
    }

    @DisplayName("Exception handling on bin operation in the end")
    @ParameterizedTest(name = "{index} => expression={0}, message={1}")
    @CsvSource({
            "3-5+2*,  exception was handled",
            "sin(5)+, exception was handled",
            "2^3/,    exception was handled"
    })
    void calculateBinOperationInEnd(String expr, String message) {
        assertThat(() -> {
            parsingService.validateOnContent(expr);
            calculateService.calculateExpression(parsingService
                    .toPolishNotation(expr));
        }, throwsException(IllegalExpressionException.class)
                .withMessageContaining("не может заканчиваться бинарной операцией"));
    }
}