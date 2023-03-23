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
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static data.OrderTestData.EMPTY_INGREDIENTS;
import static data.UserTestData.*;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@RunWith(Parameterized.class)
public class CreateOrderWithoutIngredientsParameterizedTest {
    private final static String MESSAGE_NO_INGREDIENTS = "Ingredient ids must be provided";
    private final UserClient userSpecification = new UserClient();
    private final OrderClient orderSpecification = new OrderClient();
    private final String[] ingredients;

    public CreateOrderWithoutIngredientsParameterizedTest(final String[] ingredients) {
        this.ingredients = ingredients;
    }

    @Parameterized.Parameters(name = "ingredients = {0}")
    public static Object[] getTestData() {
        return new Object[][]{
                {EMPTY_INGREDIENTS},
                null
        };
    }

    @Before
    public void setUp() {
        userSpecification.createUser(new UserCreateDto(RANDOM_STRING_EMAIL, RANDOM_STRING_PASSWORD, RANDOM_STRING_NAME));
    }

    @Test
    @DisplayName("Создание заказа - запрос без ингредиентов (с авторизацией)")
    @Description("Создание заказа с авторизацией и без ингредиентов")
    public void createOrderWithAuthAndWithIngredients() {
        String accessToken = userSpecification.getAccessToken(new UserLoginDto(RANDOM_STRING_EMAIL, RANDOM_STRING_PASSWORD));
        orderSpecification.createOrderWithAuth(new OrderCreateDto(ingredients), accessToken)
                .then()
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .body("success", is(false));
    }

    @Test
    @DisplayName("Создание заказа - запрос без ингредиентов (без авторизации)")
    @Description("Создание заказа без авторизации и без ингредиентов")
    public void createOrderWithoutAuthAndWithIngredients() {
        orderSpecification.createOrderWithoutAuth(new OrderCreateDto())
                .then()
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .body("success", is(false))
                .and()
                .body("message", equalTo(MESSAGE_NO_INGREDIENTS));
    }

    @After
    public void tearDown() {
        userSpecification.deleteUser(new UserLoginDto(RANDOM_STRING_EMAIL, RANDOM_STRING_PASSWORD));
    }
}