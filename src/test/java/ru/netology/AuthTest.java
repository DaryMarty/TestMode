package ru.netology;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.conditions.Text;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.utilits.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.utilits.DataGenerator.Registration.ActiveUser;
import static ru.netology.utilits.DataGenerator.Registration.User;
import static ru.netology.utilits.DataGenerator.generateLogin;
import static ru.netology.utilits.DataGenerator.generatePassword;

public class AuthTest {

    @BeforeEach
    void setUpEach() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
    }

    @Test
    void shouldSuccessfulLoginToAccountActiveUser() {
        val activeUser = ActiveUser("active");
        $("[data-test-id='login'] input").setValue(activeUser.getLogin());
        $("[data-test-id='password'] input").setValue(activeUser.getPassword());
        $(byText("Продолжить")).click();
        $(byText("Личный кабинет")).shouldBe(visible, Duration.ofSeconds(5));
    }

    @Test
    void shouldFailLoginToAccountUnregisteredUser() {
        val UnregisteredUser = User("active");
        $("[data-test-id='login'] input").setValue(generateLogin());
        $("[data-test-id='password'] input").setValue(generatePassword());
        $(byText("Продолжить")).click();
        $("[data-test-id='error-notification']").shouldBe(visible, Duration.ofSeconds(3));
        $("[data-test-id='error-notification']").shouldHave(text("Неверно указан логин или пароль"));
    }

    @Test
    void shouldFailInvalidLoginActiveUser() {
        val activeUser = User("active");
        $("[data-test-id='login'] input").setValue(generateLogin());
        $("[data-test-id='password'] input").setValue(activeUser.getPassword());
        $(byText("Продолжить")).click();
        $("[data-test-id='error-notification']").shouldBe(visible, Duration.ofSeconds(3));
        $("[data-test-id='error-notification']").shouldHave(text("Неверно указан логин или пароль"));
    }

    @Test
    void shouldFailInvalidPasswordActiveUser() {
        val activeUser = User("active");
        $("[data-test-id='login'] input").setValue(activeUser.getLogin());
        $("[data-test-id='password'] input").setValue(generatePassword());
        $(byText("Продолжить")).click();
        $("[data-test-id='error-notification']").shouldBe(visible, Duration.ofSeconds(3));
        $("[data-test-id='error-notification']").shouldHave(text("Неверно указан логин или пароль"));
    }

    @Test
    void shouldFailBlockedUser() {
        val blockedUser = User("blocked");
        $("[data-test-id='login'] input").setValue(blockedUser.getLogin());
        $("[data-test-id='password'] input").setValue(blockedUser.getPassword());
        $(byText("Продолжить")).click();
        $("[data-test-id='error-notification']").shouldBe(visible, Duration.ofSeconds(3));
        $("[data-test-id='error-notification']").shouldHave(text("Неверно указан логин или пароль"));
    }

}