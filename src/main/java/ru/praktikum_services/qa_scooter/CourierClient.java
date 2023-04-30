package ru.praktikum_services.qa_scooter;

import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierClient extends RestClient {
    private static final String COURIER_PATH = "/api/v1/courier";
    private static final String COURIER_LOGIN_PATH = "/api/v1/courier/login";

    public ValidatableResponse create(Courier courier) {
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then();
    }

    public ValidatableResponse login(CourierCredentials credentials) {
        return given()
                .spec(getBaseSpec())
                .when()
                .body(credentials)
                .post(COURIER_LOGIN_PATH)
                .then();
    }
}
