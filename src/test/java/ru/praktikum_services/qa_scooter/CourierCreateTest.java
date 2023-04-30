package ru.praktikum_services.qa_scooter;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class CourierCreateTest {
    @Test
    @DisplayName("Успешное создание курьера статус 201")
    public void courierCanBeCreated() {
        Courier courier = new Courier("ninja777", "1234", "saske");
        CourierClient courierClient = new CourierClient();

        Response createResponse = courierClient.create(courier);

        createResponse
                .then()
                .assertThat()
                .statusCode(201)
                .body("ok", equalTo(true));
    }
}
