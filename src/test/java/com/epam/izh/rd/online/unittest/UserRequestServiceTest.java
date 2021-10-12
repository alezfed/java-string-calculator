package com.epam.izh.rd.online;

import com.epam.izh.rd.online.service.ConsoleUserRequestService;
import com.epam.izh.rd.online.service.UserRequestService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Scanner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@DisplayName("Testing of ConsoleUserRequestService.class")
class UserRequestServiceTest {
    private Scanner mockScanner;
    private UserRequestService userRequestService;

    @DisplayName("Checks input of a text expression from the console")
    @ParameterizedTest(name = "{index} => stringExpression={0}, result={0}")
    @CsvSource({
            "1+1-2*576",
            "ha-ha-ha",
            "sin(x)+cos(777)"
    })
    void getStringExpressionFromConsoleTest(String stringExpression) {
        mockScanner = new Scanner("\n" + stringExpression + "\n");
        userRequestService = new ConsoleUserRequestService(mockScanner);
        assertThat(userRequestService.getExpression(), is(stringExpression));
    }

    @DisplayName("Checks input of integer numbers from the console")
    @ParameterizedTest(name = "{index} => stringExpression={0}, result={1}")
    @CsvSource({
            "+-***,    -1",
            "ha-ha-ha, -1",
            "10,       10",
            "4,         4"
    })
    void getIntNumberFromConsoleElseGetMinusOneTest(String stringExpression, int result) {
        mockScanner = new Scanner(stringExpression);
        userRequestService = new ConsoleUserRequestService(mockScanner);
        assertThat(userRequestService.getIntNumber(), comparesEqualTo(result));
    }
}