package com.epam.izh.rd.online;

import com.epam.izh.rd.online.service.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;

@DisplayName("Class for testing success results of text calculator")
class SuccessCalculatorTests {
    private static final double precision = 1E-6;
    private static CalculateService calculateService;
    private static ParsingService parsingService;

    @BeforeAll
    static void initSuccessCalculatorTests() {
        calculateService = new SimpleCalculateService();
        parsingService = new SimpleParsingService();
    }

    @DisplayName("Should calculate the correct sum")
    @ParameterizedTest(name = "{index} => a={0}, b={1}, sum={2}")
    @CsvSource({
            " 1,  1,  2",
            " 2,  3,  5",
            "-2,  3,  1",
            " 0,  3,  3",
            "-2,  0, -2"
    })
    void calculateSuccessSum(double a, double b, double sum) {
        double resultCalc = calculateService.calculateExpression(parsingService
                .toPolishNotation(a + "+" + b));
        assertThat(sum, closeTo(resultCalc, precision));
    }

    @DisplayName("Should calculate the correct subtraction")
    @ParameterizedTest(name = "{index} => a={0}, b={1}, subtraction={2}")
    @CsvSource({
            " 1,  1,  0",
            " 2,  3, -1",
            "-2,  3, -5",
            " 0,  3, -3",
            "-2,  0, -2"
    })
    void calculateSuccessSubtraction(double a, double b, double sub) {
        double resultCalc = calculateService.calculateExpression(parsingService
                .toPolishNotation(a + "-" + b));
        assertThat(sub, closeTo(resultCalc, precision));
    }

    @DisplayName("Should calculate the correct multiplication")
    @ParameterizedTest(name = "{index} => a={0}, b={1}, multiplication={2}")
    @CsvSource({
            " 1,  1,  1",
            " 2,  3,  6",
            "-2,  3, -6",
            " 0,  3,  0",
            "-2,  0,  0"
    })
    void calculateSuccessMultiplication(double a, double b, double mult) {
        double resultCalc = calculateService.calculateExpression(parsingService
                .toPolishNotation(a + "*" + b));
        assertThat(mult, closeTo(resultCalc, precision));
    }

    @DisplayName("Should calculate the correct division")
    @ParameterizedTest(name = "{index} => a={0}, b={1}, division={2}")
    @CsvSource({
            " 1,  1,  1",
            " 4,  2,  2",
            "-2,  2, -1",
            " 0,  3,  0",
            "-2,  1, -2"
    })
    void calculateSuccessDivision(double a, double b, double divide) {
        double resultCalc = calculateService.calculateExpression(parsingService
                .toPolishNotation(a + "/" + b));
        assertThat(divide, closeTo(resultCalc, precision));
    }

    @DisplayName("Should calculate the correct power")
    @ParameterizedTest(name = "{index} => a={0}, b={1}, power={2}")
    @CsvSource({
            " 1,  1,  1",
            " 3,  2,  9",
            " 2,  3,  8",
            " 9,  2,  81",
            " 5,  1,  5"
    })
    void calculateSuccessPower(double a, double b, double pow) {
        double resultCalc = calculateService.calculateExpression(parsingService
                .toPolishNotation(a + "^" + b));
        assertThat(pow, closeTo(resultCalc, precision));
    }

    @DisplayName("Should calculate the correct sin(x)")
    @ParameterizedTest(name = "{index} => x={0}, sin(x)={1}")
    @CsvSource({
            " 0.0000000,  0",
            " 3.1415926,  0",
            " 1.5707963,  1"
    })
    void calculateSuccessSin(double x, double sin) {
        double resultCalc = calculateService.calculateExpression(parsingService
                .toPolishNotation("sin(" + x + ")"));
        assertThat(sin, closeTo(resultCalc, precision));
    }

    @DisplayName("Should calculate the correct factorial")
    @ParameterizedTest(name = "{index} => n={0}, n!={1}")
    @CsvSource({
            " 0,  1",
            " 1,  1",
            " 4,  24",
            " 6,  720",
    })
    void calculateSuccessFactorial(double n, double fact) {
        double resultCalc = calculateService.calculateExpression(parsingService
                .toPolishNotation(n + "!"));
        assertThat(fact, closeTo(resultCalc, precision));
    }

    @DisplayName("Should calculate the correct with braces")
    @ParameterizedTest(name = "{index} => expression={0}, result={1}")
    @CsvSource({
            "(2+4)*(3-1)     ,  12",
            "(-2)            , -2",
            "2+(-5)          , -3",
            "(5+(-2)*(5+3!)) , -17",
    })
    void calculateSuccessWithBraces(String expr, double res) {
        double resultCalc = calculateService.calculateExpression(parsingService
                .toPolishNotation(expr));
        assertThat(res, closeTo(resultCalc, precision));
    }
}