package ru.praktikum_services.qa_scooter;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import static org.hamcrest.Matchers.*;

public class GetListOrdersTest {

    @Test
    @DisplayName("Получение списка заказов")
    public void getListOrders() {
        OrderClient getListOrders = new OrderClient();

        Response getResponse = getListOrders.get();

        getResponse
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("orders", is(notNullValue()))
                .body("orders[0].id", is(notNullValue()))
                .body("orders[1].id", is(notNullValue()))
                .body("orders[0].track", is(notNullValue()));
    }
}
