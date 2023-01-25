import api.client.UserClient;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;

public class UserCreationTest {
    private UserClient userClient;

    @Before
    @Step("Базовый URL и генерируемые данные")
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
        userClient = new UserClient();

    }

    @Test
    @DisplayName("Создать уникального пользователя")
    public void createNew() {
        Response correctResponse = userClient.getCorrectResponse();
        correctResponse.then()
                .assertThat()
                .statusCode(200)
                .and()
                .body("success", is(true));
        userClient.deleteUser();
    }

    @Test
    @DisplayName("Создать пользователя, который уже зарегистрирован")
    public void createTwoIdentical() {
        userClient.getCorrectResponse();
        Response response = userClient.getCorrectResponse();
        response.then()
                .assertThat()
                .statusCode(403)
                .and()
                .body("message", is("User already exists"));
    }

    @Test
    @DisplayName("Создать пользователя и не заполнить одно из обязательных полей")
    public void createWithoutName() {
        Response response = userClient.getResponseWithEmptyRequiredField();
        response.then()
                .assertThat()
                .statusCode(403)
                .and()
                .body("message", is("Email, password and name are required fields"));
    }


    @Test
    @DisplayName("Создать пользователя и не заполнить пароль и email")
    public void createWithoutEmailAndPassword() {
        Response response = userClient.getResponseWithEmptyEmailAndPasswordFields();
        response.then()
                .assertThat()
                .statusCode(403)
                .and()
                .body("message", is("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Создать пользователя и не заполнить email")
    public void createWithoutEmail() {
        Response response = userClient.getResponseWithEmptyEmailField();
        response.then()
                .assertThat()
                .statusCode(403)
                .and()
                .body("message", is("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Создать пользователя и не заполнить пароль")
    public void createWithoutPassword() {
        Response response = userClient.getResponseWithEmptyPasswordFields();
        response.then()
                .assertThat()
                .statusCode(403)
                .and()
                .body("message", is("Email, password and name are required fields"));
    }
}