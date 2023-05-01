package ru.praktikum_services.qa_scooter;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class CourierCreateTest {
    @Test
    @DisplayName("Успешное создание курьера")
    public void courierCanBeCreated() {
        Courier courier = new Courier("ninja13081992", "1234", "saske");
        CourierClient courierClient = new CourierClient();

        Response createResponse = courierClient.create(courier);

        createResponse
                .then()
                .log().all()
                .assertThat()
                .statusCode(201)
                .body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Нельзя создать двух одинаковых курьеров")
    public void courierCanBeCreatedOnlyUnique() {
        Courier courier = new Courier("ninja777777", "1234", "saske");
        CourierClient courierClient = new CourierClient();

        Response createResponse = courierClient.create(courier);

        createResponse
                .then()
                .log().all()
                .assertThat()
                .statusCode(409)
                .body("code", equalTo(409))
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @DisplayName("Ошибка если одного из полей нет")
    public void courierCanBeCreatedErrorWithoutRequiredFields() {
        Courier courier = new Courier("ninja777777", "saske");
        CourierClient courierClient = new CourierClient();

        Response createResponse = courierClient.create(courier);

        createResponse
                .then()
                .log().all()
                .assertThat()
                .statusCode(400)
                .body("code", equalTo(400))
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
}
