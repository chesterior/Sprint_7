package ru.praktikum_services.qa_scooter;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;

public class CourierCreateTest {
    @Test
    @DisplayName("Успешное создание курьера")
    public void courierCanBeCreated() {
        CourierClient courierClient = new CourierClient();
        Courier courier = CourierGenerator.getRandom();
        CourierCredentials courierCredentials = new CourierCredentials(courier.getLogin(), courier.getPassword());

        Response createResponse = courierClient.create(courier);

        createResponse
                .then()
                .log().all()
                .assertThat()
                .statusCode(201)
                .body("ok", equalTo(true));

        Response loginResponse = courierClient.login(courierCredentials);
        loginResponse
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("id", notNullValue());

        int courierId = loginResponse.path("id");

        Response deleteResponse = courierClient.delete(courierId);
        deleteResponse
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Нельзя создать двух одинаковых курьеров")
    public void courierCanBeCreatedOnlyUnique() {
        CourierClient courierClient = new CourierClient();
        Courier courier = CourierGenerator.getRandom();
        CourierCredentials courierCredentials = new CourierCredentials(courier.getLogin(), courier.getPassword());

        courierClient.create(courier);
        Response createResponse = courierClient.create(courier);

        createResponse
                .then()
                .log().all()
                .assertThat()
                .statusCode(409)
                .body("code", equalTo(409))
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));

        Response loginResponse = courierClient.login(courierCredentials);
        int courierId = loginResponse.path("id");

        courierClient.delete(courierId);
    }

    @Test
    @DisplayName("Ошибка если одного из полей нет")
    public void courierCanBeCreatedErrorWithoutRequiredFields() {
        CourierClient courierClient = new CourierClient();
        Courier courier = CourierGenerator.getRandomWithoutPassword();
        CourierCredentials courierCredentials = new CourierCredentials(courier.getLogin(), courier.getPassword());

        Response createResponse = courierClient.create(courier);

        createResponse
                .then()
                .log().all()
                .assertThat()
                .statusCode(400)
                .body("code", equalTo(400))
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));

        Response loginResponse = courierClient.login(courierCredentials);

        int statusCode = loginResponse.statusCode();
        if (statusCode == 200) {
            int courierId = loginResponse.path("id");
            courierClient.delete(courierId);
        }
    }
}
