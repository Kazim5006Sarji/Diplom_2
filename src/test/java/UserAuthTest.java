import api.client.UserClient;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;

public class UserAuthTest {

    private UserClient userClient;

    @Before
    @Step("Базовый URL и генерируемые данные")
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
        userClient = new UserClient();
        userClient.getCorrectResponse();
    }

    @Test
    @DisplayName("Логин под существующим пользователем")
    public void authExistingUser() {
        Response response = userClient.authUser();
        response.then()
                .statusCode(200)
                .and()
                .body("success", is(true));
    }

    @Test
    @DisplayName("Логин с неверным логином и паролем")
    public void authNonExistingUser() {
        Response response = userClient.authUserWithWrongCredits();
        response.then()
                .statusCode(401);
    }

    @After
    public void setDown() {
        userClient.deleteUser();
    }
}