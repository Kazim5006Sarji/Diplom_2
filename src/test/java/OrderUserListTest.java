import api.client.OrderClient;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

public class OrderUserListTest {
    private OrderClient orderClient;

    @Before
    @Step("Базовый URL и генерируемые данные")
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
        orderClient = new OrderClient();
    }

    @Test
    @DisplayName("Получение заказов авторизованного пользователя")
    public void getOrderListAuthUser() {
        Response response = orderClient.getOrderList();
        response.then().statusCode(200);
    }

    @Test
    @DisplayName("Получение заказов не авторизованного пользователя")
    public void getOrderListWithoutAuth() {
        Response response = orderClient.getOrderListWithoutAuth();
        response.then().statusCode(401);
    }
}