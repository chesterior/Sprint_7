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
        CourierClient courierClient = new CourierClient();
        Courier courier = CourierGenerator.getRandom();
        CourierCredentials courierCredentials = new CourierCredentials(courier.getLogin(), courier.getPassword());

        courierClient.create(courier);

        Response loginResponse = courierClient.login(courierCredentials);

        loginResponse
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("id", is(notNullValue()));

        int courierId = loginResponse.path("id");

        courierClient.delete(courierId);
    }

    @Test
    @DisplayName("Неправильно указанный логин")
    public void courierWithWrongLogin() {
        CourierClient courierClient = new CourierClient();
        Courier courier = CourierGenerator.getRandom();
        CourierCredentials courierCredentialsWrongLogin = new CourierCredentials(courier.getLogin()+"tf",
                courier.getPassword());
        CourierCredentials courierCredentials = new CourierCredentials(courier.getLogin(), courier.getPassword());

        courierClient.create(courier);

        Response loginResponseWrongLogin = courierClient.login(courierCredentialsWrongLogin);

        loginResponseWrongLogin
                .then()
                .log().all()
                .assertThat()
                .statusCode(404)
                .body("code", equalTo(404))
                .body("message", equalTo("Учетная запись не найдена"));

        Response loginResponse = courierClient.login(courierCredentials);
        int courierId = loginResponse.path("id");

        courierClient.delete(courierId);
    }

    @Test
    @DisplayName("Неправильно указанный пароль")
    public void courierWithWrongPassword() {
        CourierClient courierClient = new CourierClient();
        Courier courier = CourierGenerator.getRandom();
        CourierCredentials courierCredentialsWrongPassword = new CourierCredentials(courier.getLogin(),
                courier.getPassword()+"we");
        CourierCredentials courierCredentials = new CourierCredentials(courier.getLogin(), courier.getPassword());

        courierClient.create(courier);

        Response loginResponseWrongPassword = courierClient.login(courierCredentialsWrongPassword);

        loginResponseWrongPassword
                .then()
                .log().all()
                .assertThat()
                .statusCode(404)
                .body("code", equalTo(404))
                .body("message", equalTo("Учетная запись не найдена"));

        Response loginResponse = courierClient.login(courierCredentials);
        int courierId = loginResponse.path("id");

        courierClient.delete(courierId);
    }

    @Test
    @DisplayName("Ошибка если одного из полей нет")
    public void courierCanBeLoginErrorWithoutRequiredFields() {
        Courier courier = CourierGenerator.getRandom();
        CourierCredentials courierCredentialsPassword = new CourierCredentials(courier.getPassword());
        CourierCredentials courierCredentials = new CourierCredentials(courier.getLogin(), courier.getPassword());
        CourierClient courierClient = new CourierClient();

        courierClient.create(courier);

        Response loginResponsePassword = courierClient.login(courierCredentialsPassword);
        loginResponsePassword
                .then()
                .log().all()
                .assertThat()
                .statusCode(400)
                .body("code", equalTo(400))
                .body("message", equalTo("Недостаточно данных для входа"));

        Response loginResponse = courierClient.login(courierCredentials);
        int courierId = loginResponse.path("id");

        courierClient.delete(courierId);
    }
}

