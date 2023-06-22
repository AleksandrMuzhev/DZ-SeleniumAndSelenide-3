package ru.netology.web;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class DebitCardTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    public void shouldSendForm() throws InterruptedException {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Александр");
        elements.get(1).sendKeys("+79120009999");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(new By.ByCssSelector("[data-test-id=order-success]")).getText();
        Assertions.assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());

        Thread.sleep(5000);
    }

    @Test
    public void shouldValidateNameForm() throws InterruptedException {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Aleksandr");
        elements.get(1).sendKeys("+79120009999");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(new By.ByCssSelector("span.input__sub")).getText();
        Assertions.assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());

        Thread.sleep(5000);
    }

    @Test
    public void shouldNoNameForm() throws InterruptedException {
        driver.get("http://localhost:9999");
        driver.findElement(new By.ByCssSelector("[data-test-id=phone] input")).sendKeys("79120009999");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(new By.ByCssSelector("span.input__sub")).getText();
        Assertions.assertEquals("Поле обязательно для заполнения", text.trim());

        Thread.sleep(5000);
    }

    @Test
    public void shouldValidatePhoneForm() throws InterruptedException {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Александр");
        elements.get(1).sendKeys("+7912000");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(new By.ByCssSelector("[data-test-id=phone] span.input__sub")).getText();
        Assertions.assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());

        Thread.sleep(5000);
    }

    @Test
    public void shouldNoPhoneForm() throws InterruptedException {
        driver.get("http://localhost:9999");
        driver.findElement(new By.ByCssSelector("[data-test-id=name] input")).sendKeys("Александр");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(new By.ByCssSelector("[data-test-id=phone] span.input__sub")).getText();
        Assertions.assertEquals("Поле обязательно для заполнения", text.trim());

        Thread.sleep(5000);
    }

    @Test
    public void shouldSendFormSelenide() throws InterruptedException {
        open("http://localhost:9999");
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
        open("http://localhost:9999");
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
        open("http://localhost:9999");
        SelenideElement form = $("form[enctype]");
        form.$("[data-test-id=phone] input").setValue("+79120009999");
        form.$(".checkbox__box").click();
        form.$(".button").click();
        $("span.input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"));

        Thread.sleep(5000);
    }

    @Test
    public void shouldValidatePhoneFormSelenide() throws InterruptedException {
        open("http://localhost:9999");
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
        open("http://localhost:9999");
        SelenideElement form = $("form[enctype]");
        form.$("[data-test-id=name] input").setValue("Александр");
        form.$(".checkbox__box").click();
        form.$(".button").click();
        $("[data-test-id=phone] span.input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"));

        Thread.sleep(5000);
    }
}
