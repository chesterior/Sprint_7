package ru.praktikum_services.qa_scooter;

public class CourierCredentials {
    private String login;
    private String password;

    public CourierCredentials(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public CourierCredentials(String password) {
        this.password = password;
    }

}
