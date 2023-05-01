package ru.praktikum_services.qa_scooter;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class GettingListOfOrders extends RestClient {
    private static final String GETTING_LIST_OF_ORDERS_PATH = "/api/v1/orders";

    public Response getting() {
        return given()
                .spec(getBaseSpec())
                .log().all()
                .when()
                .get(GETTING_LIST_OF_ORDERS_PATH);
    }
}
