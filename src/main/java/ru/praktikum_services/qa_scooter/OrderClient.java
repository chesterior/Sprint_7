package ru.praktikum_services.qa_scooter;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrderClient extends RestClient{
    private static final String ORDER_PATH = "/api/v1/orders";
    private static final String GETTING_LIST_OF_ORDERS_PATH = "/api/v1/orders";

    public Response create(Order order) {
        return given()
                .spec(getBaseSpec())
                .body(order)
                .log().all()
                .when()
                .post(ORDER_PATH);
    }

    public Response get() {
        return given()
                .spec(getBaseSpec())
                .log().all()
                .when()
                .get(GETTING_LIST_OF_ORDERS_PATH);
    }
}
