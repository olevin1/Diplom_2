package order.create;

import client.OrderClient;
import client.UserClient;
import domain.order.OrderCreateDto;
import domain.user.UserCreateDto;
import domain.user.UserLoginDto;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static data.OrderTestData.INVALID_INGREDIENTS;
import static data.UserTestData.*;
import static org.apache.http.HttpStatus.SC_INTERNAL_SERVER_ERROR;

public class CreateOrderWithInvalidHashIngredientsTest {
    private final UserClient userSpecification = new UserClient();
    private final OrderClient orderSpecification = new OrderClient();

    @Before
    public void setUp() {
        userSpecification.createUser(new UserCreateDto(RANDOM_STRING_EMAIL, RANDOM_STRING_PASSWORD, RANDOM_STRING_NAME));
    }

    @Test
    @DisplayName("Создание заказа - запрос c несуществующим хешем ингредиента (с авторизацией)")
    @Description("Создание заказа с авторизацией и c несуществующим хешем ингредиента")
    public void createOrderWithAuthAndWithInvalidIngredients() {
        String accessToken = userSpecification.getAccessToken(new UserLoginDto(RANDOM_STRING_EMAIL, RANDOM_STRING_PASSWORD));
        orderSpecification.createOrderWithAuth(new OrderCreateDto(INVALID_INGREDIENTS), accessToken)
                .then()
                .assertThat()
                .statusCode(SC_INTERNAL_SERVER_ERROR);
    }

    @Test
    @DisplayName("Создание заказа - запрос c несуществующим хешем ингредиента (без авторизации)")
    @Description("Создание заказа без авторизации и c несуществующим хешем ингредиента")
    public void createOrderWithoutAuthAndWithInvalidIngredients() {
        orderSpecification.createOrderWithoutAuth(new OrderCreateDto(INVALID_INGREDIENTS))
                .then()
                .assertThat()
                .statusCode(SC_INTERNAL_SERVER_ERROR);
    }

    @After
    public void tearDown() {
        userSpecification.deleteUser(new UserLoginDto(RANDOM_STRING_EMAIL, RANDOM_STRING_PASSWORD));
    }
}