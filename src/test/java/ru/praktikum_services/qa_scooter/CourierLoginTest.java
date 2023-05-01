package ru.praktikum_services.qa_scooter;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CourierLoginTest {
    @Test
    @DisplayName("Успешный логин курьера статус 200")
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
}

