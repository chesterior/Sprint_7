package ru.praktikum_services.qa_scooter;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CourierClient extends RestClient {
    private static final String COURIER_PATH = "/api/v1/courier";
    private static final String COURIER_LOGIN_PATH = "/api/v1/courier/login";
    private static final String COURIER_DELETE_PATH = "/api/v1/courier/";

    public Response create(Courier courier) {
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .log().all()
                .when()
                .post(COURIER_PATH);
    }

    public Response login(CourierCredentials credentials) {
        return given()
                .spec(getBaseSpec())
                .when()
                .body(credentials)
                .post(COURIER_LOGIN_PATH);
    }

    public Response delete(int courierDelete) {
        return given()
                .spec(getBaseSpec())
                .log().all()
                .when()
                .delete(COURIER_DELETE_PATH + courierDelete);
    }
}
