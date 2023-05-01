package ru.praktikum_services.qa_scooter;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class OrderCreateTest {

    @Test
    @DisplayName("Успешное создание заказа c двумя цветами")
    public void orderCanBeCreatedWithTwoColor() {
        Order order = new Order("Naruto", "Uchiha", "Konoha, 142 apt.", "4",
                "+7 800 355 35 35", 5, "2023-05-03", "Saske, come back to Konoha",
                new String[]{"BLACK", "GREY"});
        OrderClient orderClient = new OrderClient();

        Response createResponse = orderClient.create(order);

        createResponse
                .then()
                .log().all()
                .assertThat()
                .statusCode(201)
                .body("track", is(notNullValue()));
    }

    @Test
    @DisplayName("Успешное создание заказа без указания цвета")
    public void orderCanBeCreatedWithoutColor() {
        Order order = new Order("Naruto", "Uchiha", "Konoha, 142 apt.", "4",
                "+7 800 355 35 35", 5, "2023-05-03", "Saske, come back to Konoha");
        OrderClient orderClient = new OrderClient();

        Response createResponse = orderClient.create(order);

        createResponse
                .then()
                .log().all()
                .assertThat()
                .statusCode(201)
                .body("track", is(notNullValue()));
    }
}
