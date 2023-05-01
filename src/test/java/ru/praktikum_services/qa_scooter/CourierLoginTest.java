package ru.praktikum_services.qa_scooter;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;

public class CourierLoginTest {
    @Test
    @DisplayName("Успешный логин курьера")
    public void courierCanBeLogin() {
        CourierCredentials courierCredentials = new CourierCredentials("ninja777", "1234");
        CourierClient courierClient = new CourierClient();

        Response loginResponse = courierClient.login(courierCredentials);

        loginResponse
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("id", is(notNullValue()));
    }

    @Test
    @DisplayName("Неправильно указанный логин")
    public void courierWithWrongLogin() {
        CourierCredentials courierCredentials = new CourierCredentials("ninja23423525sd", "1234");
        CourierClient courierClient = new CourierClient();

        Response loginResponse = courierClient.login(courierCredentials);

        loginResponse
                .then()
                .log().all()
                .assertThat()
                .statusCode(404)
                .body("code", equalTo(404))
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Неправильно указанный пароль")
    public void courierWithWrongPassword() {
        CourierCredentials courierCredentials = new CourierCredentials("ninja777", "123454321");
        CourierClient courierClient = new CourierClient();

        Response loginResponse = courierClient.login(courierCredentials);

        loginResponse
                .then()
                .log().all()
                .assertThat()
                .statusCode(404)
                .body("code", equalTo(404))
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Ошибка если одного из полей нет")
    public void courierCanBeLoginErrorWithoutRequiredFields() {
        CourierCredentials courierCredentials = new CourierCredentials("123454321");
        CourierClient courierClient = new CourierClient();

        Response loginResponse = courierClient.login(courierCredentials);

        loginResponse
                .then()
                .log().all()
                .assertThat()
                .statusCode(400)
                .body("code", equalTo(400))
                .body("message", equalTo("Недостаточно данных для входа"));
    }
}

