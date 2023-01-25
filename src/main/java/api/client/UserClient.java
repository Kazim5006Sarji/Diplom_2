package api.client;

import api.model.ResponseWithToken;
import api.model.User;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import net.datafaker.Faker;

import static io.restassured.RestAssured.given;

public class UserClient {
    Faker faker = new Faker();
    String email = faker.internet().emailAddress();
    String password = faker.internet().password();
    String name = faker.name().firstName();
    User user;

    String newEmail = faker.internet().emailAddress();

    @Step("Создание пользователя")
    public Response getCorrectResponse() {
        user = new User(email,password,name);
        return given()
                .header("Content-type", "application/json")
                .body(user)
                .when()
                .post("/api/auth/register");
    }

    @Step("Создание пользователя без обязательных полей")
    public Response getResponseWithEmptyRequiredField() {
        user = new User(email, password);
        return given()
                .header("Content-type", "application/json")
                .body(user)
                .when()
                .post("/api/auth/register");
    }

    @Step("Авторизация клиента")
    public Response authUser() {
        user = new User(email, password);
        return given()
                .header("Content-type", "application/json")
                .body(user)
                .when()
                .post("/api/auth/login");
    }

    @Step("Попытка авторизации с неправильным паролем")
    public Response authUserWithWrongCredits() {
        user.setName("Domovoi");
        user.setPassword("VRossiu3421");
        return given()
                .header("Content-type", "application/json")
                .body(user)
                .post("/api/auth/login");
    }

    @Step("Изменения данных авторизованного пользователя")
    public Response changeDataAuthUser() {
        Response response = authUser();
        ResponseWithToken token = response.body().as(ResponseWithToken.class);
        user.setEmail(newEmail);
        return given().header("Authorization", token.getAccessToken())
                .body(user)
                .patch("/api/auth/user");
    }

    @Step("Изменение данных не авторизованного пользователя")
    public Response changeDataWithoutAuth() {
        user.setEmail(newEmail);
        return given().header("Content-type", "application/json")
                .body(user)
                .patch("/api/auth/user");
    }

    @Step("Удаление пользователя")
    public Response deleteUser(){
        Response response = authUser();
        ResponseWithToken token = response.body().as(ResponseWithToken.class);

        return given().header("Authorization", token.getAccessToken())
                .delete("/api/auth/user");
    }

    @Step("Создание пользователя без email и пароля, но с именем")
    public Response getResponseWithEmptyEmailAndPasswordFields() {
        user = new User(name);
        return given()
                .header("Content-type", "application/json")
                .body(user)
                .when()
                .post("/api/auth/register");
    }

    @Step("Создание пользователя без email")
    public Response getResponseWithEmptyEmailField() {
        user = new User(name);
        user.setPassword(password);
        return given()
                .header("Content-type", "application/json")
                .body(user)
                .when()
                .post("/api/auth/register");
    }

    @Step("Создание пользователя без пароля")
    public Response getResponseWithEmptyPasswordFields() {
        user = new User(name);
        user.setEmail(email);
        return given()
                .header("Content-type", "application/json")
                .body(user)
                .when()
                .post("/api/auth/register");
    }


}
