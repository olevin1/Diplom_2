package order.create;

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
import specification.OrderSpecification;
import specification.UserSpecification;

import static data.OrderTestData.*;
import static data.UserTestData.*;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.is;

@RunWith(Parameterized.class)
public class CreateOrderWithIngredientsParameterizedTest {
    private final UserSpecification userSpecification = new UserSpecification();
    private final OrderSpecification orderSpecification = new OrderSpecification();
    private final String[] ingredients;

    public CreateOrderWithIngredientsParameterizedTest(final String[] ingredients) {
        this.ingredients = ingredients;
    }

    @Parameterized.Parameters(name = "ingredients = {0}")
    public static Object[] getTestData() {
        return new Object[][]{
                {BURGER_FIRST_CHOICE},
                {BURGER_SECOND_CHOICE},
                {BURGER_THIRD_CHOICE},
                {BURGER_FOURTH_CHOICE}
        };
    }

    @Before
    public void setUp() {
        userSpecification.createUser(new UserCreateDto(RANDOM_STRING_EMAIL, RANDOM_STRING_PASSWORD, RANDOM_STRING_NAME));
    }

    @Test
    @DisplayName("Создание заказа - запрос с корректным набором ингредиентов (с авторизацией)")
    @Description("Создание заказа с авторизацией и существующими ингредиентами")
    public void createOrderWithAuthAndWithIngredients() {
        String accessToken = userSpecification.getAccessToken(new UserLoginDto(RANDOM_STRING_EMAIL, RANDOM_STRING_PASSWORD));
        orderSpecification.createOrderWithAuth(new OrderCreateDto(ingredients), accessToken)
                .then().assertThat()
                .body("success", is(true))
                .and()
                .statusCode(SC_OK);
    }

    @Test
    @DisplayName("Создание заказа - запрос с корректным набором ингредиентов (без авторизации)")
    @Description("Создание заказа без авторизации и существующими ингредиентами")
    public void createOrderWithoutAuthAndWithIngredients() {
        orderSpecification.createOrderWithoutAuth(new OrderCreateDto(ingredients))
                .then()
                .assertThat()
                .body("success", is(true))
                .and()
                .statusCode(SC_OK);
    }

    @After
    public void tearDown() {
        userSpecification.deleteUser(new UserLoginDto(RANDOM_STRING_EMAIL, RANDOM_STRING_PASSWORD));
    }
}