import api.client.OrderClient;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class OrderCreationTest {
    private OrderClient orderClient;

    @Before
    @Step("Базовый URL и генерируемые данные")
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
    }

    @Test
    @DisplayName("Создание заказа с авторизацией")
    public void createOrderWithAuth() {
        orderClient = new OrderClient();
        Response response = orderClient.getOrderAuth();
        response.then().statusCode(200);
    }

    @Test
    @DisplayName("Создание заказа без авторизацией")
    public void createOrderWithoutAuth() {
        orderClient = new OrderClient();
        Response response = orderClient.getOrderWithoutAuth();
        response.then().statusCode(200);
    }

    @Test
    @DisplayName("Создание заказа с неверным хэш")
    public void createOrderWithWrongHashIngredient() {
        orderClient = new OrderClient();
        Response response = orderClient.getOrderWithWrongHash();
        response.then().statusCode(500);
    }

    @Test
    @DisplayName("Создание заказа без ингредиентов")
    public void createOrderWithoutIngredients() {
        orderClient = new OrderClient();
        Response response = orderClient.getOrderWithoutIngredients();
        response.then().statusCode(400);
    }
    @After
    public void setDown() {
        orderClient.deleteOrders();
    }
}