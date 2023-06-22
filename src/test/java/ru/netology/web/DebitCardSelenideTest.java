package ru.netology.web;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class DebitCardSelenideTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setupUp() {
        driver = new ChromeDriver();
        open("http://localhost:9999");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    public void shouldSendFormSelenide() throws InterruptedException {
        SelenideElement form = $("form[enctype]");
        form.$("[data-test-id=name] input").setValue("Александр");
        form.$("[data-test-id=phone] input").setValue("+79120009999");
        form.$(".checkbox__box").click();
        form.$(".button").click();
        $("[data-test-id=order-success]").shouldHave(Condition.exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));

        Thread.sleep(5000);
    }

    @Test
    public void shouldValidateNameFormSelenide() throws InterruptedException {
        SelenideElement form = $("form[enctype]");
        form.$("[data-test-id=name] input").setValue("Aleksandr");
        form.$("[data-test-id=phone] input").setValue("+79120009999");
        form.$(".checkbox__box").click();
        form.$(".button").click();
        $("span.input__sub").shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));

        Thread.sleep(5000);
    }

    @Test
    public void shouldNoNameFormSelenide() throws InterruptedException {
        SelenideElement form = $("form[enctype]");
        form.$("[data-test-id=phone] input").setValue("+79120009999");
        form.$(".checkbox__box").click();
        form.$(".button").click();
        $("span.input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"));

        Thread.sleep(5000);
    }

    @Test
    public void shouldValidatePhoneFormSelenide() throws InterruptedException {
        SelenideElement form = $("form[enctype]");
        form.$("[data-test-id=name] input").setValue("Александр");
        form.$("[data-test-id=phone] input").setValue("+7912000");
        form.$(".checkbox__box").click();
        form.$(".button").click();
        $("[data-test-id=phone] span.input__sub").shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));

        Thread.sleep(5000);
    }

    @Test
    public void shouldNoPhoneFormSelenide() throws InterruptedException {
        SelenideElement form = $("form[enctype]");
        form.$("[data-test-id=name] input").setValue("Александр");
        form.$(".checkbox__box").click();
        form.$(".button").click();
        $("[data-test-id=phone] span.input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"));

        Thread.sleep(5000);
    }
}
