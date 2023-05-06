package ru.praktikum_services.qa_scooter;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class OrderCreateWithParameterizedTest {
    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    private String[] color;

    public OrderCreateWithParameterizedTest(String firstName, String lastName, String address, String metroStation, String phone, int rentTime, String deliveryDate, String comment, String[] color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    @Parameterized.Parameters(name = "orderCanBeCreatedWithOneColor")
    public static Object[][] orderCreateData() {
        return new Object[][]{
                {"Naruto", "Uchiha", "Konoha, 142 apt.", "4",
                        "+7 800 355 35 35", 5, "2023-05-03", "Saske, come back to Konoha",
                        new String[]{"BLACK"}},
                {"Obi-van", "Kenobi", "Tatuin, 777 apt.", "1",
                        "+777 111 222 33 44", 24, "2023-05-05", "Obi-van travel to Tatuin",
                        new String[]{"GREY"}},
        };
    }

    @Test
    @DisplayName("Успешное создание заказа c одним цветом")
    public void orderCanBeCreatedWithOneColor() {
        Order order = new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment,
                color);
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
