package ru.praktikum_services.qa_scooter;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import static org.hamcrest.Matchers.*;

public class GettingListOfOrdersTest {

    @Test
    @DisplayName("Получение списка заказов")
    public void gettingListOfOrders() {
        GettingListOfOrders gettingListOfOrders = new GettingListOfOrders();

        Response gettingResponse = gettingListOfOrders.getting();

        gettingResponse
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("orders", is(notNullValue()));
    }
}
