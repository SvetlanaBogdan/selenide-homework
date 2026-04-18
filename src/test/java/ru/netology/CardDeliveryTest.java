package ru.netology;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class CardDeliveryTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    void shouldSubmitFormSuccessfully() {

        // генерируем дату (ВАЖНО — не хардкод!)
        String planningDate = LocalDate.now()
                .plusDays(3)
                .format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        // город (БЕЗ выпадающего списка!)
        $("[data-test-id=city] input").setValue("Казань");

        // очищаем дату и вводим новую
        $("[data-test-id=date] input")
                .sendKeys(Keys.chord(Keys.COMMAND, "a"), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(planningDate);

        // имя
        $("[data-test-id=name] input").setValue("Иван Петров");

        // телефон
        $("[data-test-id=phone] input").setValue("+79000000000");

        // чекбокс
        $("[data-test-id=agreement]").click();

        // кнопка
        $$("button").findBy(text("Забронировать")).click();

        // проверка (ВАЖНО — проверяем И текст, И дату)
        $(".notification__content")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(text("Встреча успешно забронирована"))
                .shouldHave(text(planningDate));
    }
}