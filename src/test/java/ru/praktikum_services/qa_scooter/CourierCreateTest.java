package ru.praktikum_services.qa_scooter;

import io.restassured.response.ValidatableResponse;
import org.junit.Test;

public class CourierCreateTest {
    @Test
    public void courierCanBeCreated() {
        Courier courier = new Courier();
        CourierClient courierClient = new CourierClient();

        ValidatableResponse createResponse = courierClient.create(courier);

        int statusCode = createResponse.extract().statusCode();
        boolean isCouriersCreated = createResponse.extract().path("ok");

        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));
        int courierId = loginResponse.extract().path("id");
    }
}
