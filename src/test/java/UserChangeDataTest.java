import api.client.UserClient;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UserChangeDataTest {
    UserClient userClient;

    @Before
    @Step("Базовый URL и генерируемые данные")
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
        userClient = new UserClient();
        userClient.getCorrectResponse();
    }

    @Test
    @DisplayName("Изменение данных пользователя с авторизацией")
    public void changeUserDataWithAuth() {
        Response response = userClient.changeDataAuthUser();
        response.then().statusCode(200);
    }

    @Test
    @DisplayName("Изменение данных пользователя без авторизации")
    public void changeUserDataWithoutAuth() {
        Response response = userClient.changeDataWithoutAuth();
        response.then().statusCode(401);
    }

    @After
    public void setDown() {
        userClient.deleteUser();
    }
}