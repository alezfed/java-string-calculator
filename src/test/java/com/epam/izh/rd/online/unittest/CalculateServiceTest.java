package com.epam.izh.rd.online.unittest;

import com.epam.izh.rd.online.service.CalculateService;
import com.epam.izh.rd.online.service.SimpleCalculateService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;

@DisplayName("Testing of SimpleCalculateService.class")
class CalculateServiceTest {
    private static final double precision = 1E-6;
    private CalculateService calculateService = new SimpleCalculateService();

    @DisplayName("Calculate result from polish notation expression without errors")
    @ParameterizedTest(name = "{index} => polishNotationExpression={0}, rresult={1}")
    @CsvSource({
            "2 4 3 * + 1 -      ,  13",
            "2 4 + 3 1 - *      ,  12",
            "0 2 - 4 sin *      ,  1.5136049",
            "2 0 5 - +          , -3",
            "5 0 2 - 5 3 ! + * +, -17",
    })
    void shouldCalculateResultFromPolishNotationWithoutErrors(String polishNotation, Double result) {
        assertThat(calculateService.calculateExpression(polishNotation), closeTo(result, precision));
    }
}