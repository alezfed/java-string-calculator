package com.epam.izh.rd.online;

import com.epam.izh.rd.online.service.ConsoleNotificationService;
import com.epam.izh.rd.online.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@DisplayName("Testing of ConsoleUserNotificationService.class")
class UserNotificationServiceTest {
    private NotificationService notificationService;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    void initConsoleUserNotificationServiceTest() {
        outContent = new ByteArrayOutputStream();
        notificationService = new ConsoleNotificationService(new PrintStream(outContent));
    }

    @DisplayName("Checks input of a text message into console")
    @ParameterizedTest(name = "{index} => message={0}, result={0}")
    @CsvSource({
            "q121adas",
            "ha-ha-ha",
            "sin(x)+cos(777)"
    })
    void showStringMessageIntoConsoleTest(String message) {
        notificationService.showMessage(message);
        assertThat(outContent.toString(), is(message));
    }

    @DisplayName("Checks input of a text message with parameter into console")
    @ParameterizedTest(name = "{index} => message={0}, addParameter={1}")
    @CsvSource({
            "Ошибка: %s, Код ошибки",
            "Пример сообщения с выводом числа: %s, 25"
    })
    void showStringMessageWithOneStringParameterIntoConsoleTest(String message, String addParameter) {
        notificationService.showMessage(message, addParameter);
        assertThat(outContent.toString(), is(String.format(message, addParameter)));
    }
}