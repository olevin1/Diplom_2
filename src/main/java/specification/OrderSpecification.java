package specification;

import constants.ConstantsOrderTestData;
import domain.order.OrderCreateDto;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrderSpecification extends BasicSpecification implements ConstantsOrderTestData {
    private final static String CREATE_ORDER_PATH = "/api/orders";
    private final static String GET_ORDERS_FOR_USER_PATH = "/api/orders";

    @Step("Создать заказ с авторизацией")
    public Response createOrderWithAuth(OrderCreateDto order, String accessToken) {
        return given()
                .spec(baseSpec())
                .header("Authorization", accessToken)
                .and()
                .body(order)
                .when()
                .post(CREATE_ORDER_PATH);
    }

    @Step("Создать заказ без авторизации")
    public Response createOrderWithoutAuth(OrderCreateDto order) {
        return given()
                .spec(baseSpec())
                .and()
                .body(order)
                .when()
                .post(CREATE_ORDER_PATH);
    }

    @Step("Получить заказ(ы) конкретного пользователя с авторизацией")
    public Response getOrderForUserWithAuth(String accessToken) {
        return given()
                .spec(baseSpec())
                .header("Authorization", accessToken)
                .when()
                .get(GET_ORDERS_FOR_USER_PATH);
    }

    @Step("Получить заказ(ы) конкретного пользователя без авторизации")
    public Response getOrderForUserWithoutAuth() {
        return given()
                .spec(baseSpec())
                .when()
                .get(GET_ORDERS_FOR_USER_PATH);
    }
}