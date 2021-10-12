package com.epam.izh.rd.online;

import com.epam.izh.rd.online.enity.OperationType;
import com.epam.izh.rd.online.exception.IllegalExpressionException;
import com.epam.izh.rd.online.exception.UnknownOperationException;
import com.epam.izh.rd.online.service.ParsingService;
import com.epam.izh.rd.online.service.SimpleParsingService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.StringJoiner;

import static net.obvj.junit.utils.matchers.AdvancedMatchers.throwsException;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@DisplayName("Testing of SimpleParsingService.class")
class ParsingServiceTest {
    private final ParsingService parsingService = new SimpleParsingService();

    @DisplayName("Validate the string expression with unknown operation")
    @ParameterizedTest(name = "{index} => expression={0}, message={1}")
    @CsvSource({"(1+1)+cos(x), exception was handled"})
    void shouldReturnUnknownOperationExceptionWhenTakeUnknownChars(String expression) {
        assertThat(() -> parsingService.validateOnContent(expression),
                throwsException(UnknownOperationException.class)
                        .withMessageContaining("Неверное построение выражения или неизвестные операторы в нем."));

    }

    @DisplayName("Validate the string expression with ends on bin operation")
    @ParameterizedTest(name = "{index} => expression={0}, message={1}")
    @CsvSource({"(1+1)+, exception was handled"})
    void shouldReturnIllegalExpressionExceptionWhenExpressionEndsBinOperation(String expression) {
        assertThat(() -> parsingService.validateOnContent(expression),
                throwsException(IllegalExpressionException.class)
                        .withMessageContaining("Выражение не может заканчиваться бинарной операцией."));

    }

    @DisplayName("Validate the string expression with no errors")
    @ParameterizedTest(name = "{index} => expression={0}, message={1}")
    @CsvSource({
            "(2+4)*(3-1)     , Validate exception was succses",
            "(-2)*sin(4)     , Validate exception was succses",
            "2+(-5)          , Validate exception was succses",
            "(5+(-2)*(5+3!)) , Validate exception was succses",
    })
    void shouldGivenNoErrorsWhenValidateExpression(String expression) {
        parsingService.validateOnContent(expression);
        assertThat(expression, is(expression));
    }

    @DisplayName("Transforms the string expression to polish notation without errors")
    @ParameterizedTest(name = "{index} => initExpression={0}, polishNotation={1}")
    @CsvSource({
            "(2+4*3-1)       , 2 4 3 * + 1 -",
            "(2+4)*(3-1)     , 2 4 + 3 1 - *",
            "(-2)*sin(4)     , 0 2 - 4 sin *",
            "2+(-5)          , 2 0 5 - +",
            "(5+(-2)*(5+3!)) , 5 0 2 - 5 3 ! + * +",
    })
    void shouldTransformsStringExpressionToPolishNotationWithoutErrors(String expression, String polishNotation) {
        assertThat(parsingService.toPolishNotation(expression), is(polishNotation));
    }

    @DisplayName("Give error for transforming expression with wrong braces")
    @ParameterizedTest(name = "{index} => expression={0}, message={1}")
    @CsvSource({
            ")(-2),   exception was handled",
            "2+(-5)), exception was handled",
            "(5-)2),  exception was handled"
    })
    void shouldReturnIllegalExpressionExceptionWhenWrongBracesInExpression(String expression) {
        assertThat(() -> parsingService.toPolishNotation(expression),
                throwsException(IllegalExpressionException.class)
                        .withMessageContaining("Неверная расстановка скобок в выражении."));

    }

    @DisplayName("Check all operations which can be used in string calculator")
    @ParameterizedTest(name = "Test was successful")
    @ValueSource(strings = {""})
    public void shouldCheckAllOperationInCalculatorParsingService() {
        StringJoiner sumStringForm = new StringJoiner(", ");
        for (OperationType operation : OperationType.values()) {
            sumStringForm.add(operation.getOperation().getStringForm());
        }
        assertThat(parsingService.getAllowedOperation(), is(sumStringForm.toString()));
    }
}