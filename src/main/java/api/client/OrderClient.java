package api.client;

import api.model.AllIngredients;
import api.model.ResponseWithToken;
import com.google.gson.Gson;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class OrderClient {
    UserClient userClient;
    Gson gson = new Gson();


    @Step("Создание заказа c авторизацией")
    public Response getOrderAuth() {
        userClient = new UserClient();
        userClient.getCorrectResponse();
        Response response = userClient.authUser();
        ResponseWithToken token = response.body().as(ResponseWithToken.class);

        List<String> ingredients = given()
                .header("Content-type", "application/json")
                .when()
                .get("/api/ingredients")
                .then()
                .extract()
                .body()
                .jsonPath().get("data._id");

        AllIngredients order = new AllIngredients();
        List<String> orders = new ArrayList<>();
        orders.add(ingredients.get(0));
        order.setIngredients(orders);
        String json = gson.toJson(order);

        return given()
                .header("Content-type", "application/json")
                .header("Authorization", token.getAccessToken())
                .body(json)
                .post("/api/orders");
    }

    @Step("Создание заказа без авторизации")
    public Response getOrderWithoutAuth() {
        userClient = new UserClient();
        userClient.getCorrectResponse();
        Response response = userClient.authUser();
        ResponseWithToken token = response.body().as(ResponseWithToken.class);

        List<String> ingredients = given()
                .header("Content-type", "application/json")
                .when()
                .get("/api/ingredients")
                .then()
                .extract()
                .body()
                .jsonPath().get("data._id");

        AllIngredients order = new AllIngredients();
        List<String> orders = new ArrayList<>();
        orders.add(ingredients.get(0));
        order.setIngredients(orders);
        String json = gson.toJson(order);

        return given()
                .header("Content-type", "application/json")
                .body(json)
                .post("/api/orders");
    }
    @Step("Создание заказа с неверным хэш")
    public Response getOrderWithWrongHash() {
        userClient = new UserClient();

        AllIngredients order = new AllIngredients();
        List<String> orders = new ArrayList<>();
        orders.add("НИЧЕГО НЕ ПОНИМАЮ");
        order.setIngredients(orders);
        String json = gson.toJson(order);

        return given()
                .header("Content-type", "application/json")
                .body(json)
                .post("/api/orders");
    }

    @Step("Создание заказа без ингредиентов")
    public Response getOrderWithoutIngredients() {
        userClient = new UserClient();

        AllIngredients order = new AllIngredients();
        List<String> orders = new ArrayList<>();
        order.setIngredients(orders);
        String json = gson.toJson(order);

        return given()
                .header("Content-type", "application/json")
                .body(json)
                .post("/api/orders");
    }

    @Step("Получение списка заказов")
    public Response getOrderList(){
        userClient = new UserClient();
        userClient.getCorrectResponse();
        Response response = userClient.authUser();
        ResponseWithToken token = response.body().as(ResponseWithToken.class);

        return given().header("Authorization", token.getAccessToken())
                .get("/api/orders");
    }
    @Step("Получение списка заказов без авторизации")
    public Response getOrderListWithoutAuth(){
        userClient = new UserClient();
        userClient.getCorrectResponse();

        return given().header("Content-type", "application/json")
                .get("/api/orders");
    }

    @Step("Удаление созданных заказов")
    public Response deleteOrders() {
        userClient = new UserClient();
        userClient.getCorrectResponse();
        Response response = userClient.authUser();
        ResponseWithToken token = response.body().as(ResponseWithToken.class);

        return given().header("Authorization", token.getAccessToken())
                .delete("/api/orders");

    }

}