package ru.praktikum_services.qa_scooter;

import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;
public class CourierClient extends RestClient{
    private static final String COURIER_PATH = "/api/v1/courier";

    public ValidatableResponse create(Courier courier){
        return (ValidatableResponse) given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then()
                .extract();
    }
}
