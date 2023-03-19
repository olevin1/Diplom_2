package order.get;

import constants.ConstantsOrderTestData;
import constants.ConstantsUserTestData;
import domain.order.OrderCreateDto;
import domain.user.UserCreateDto;
import domain.user.UserLoginDto;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import specification.OrderSpecification;
import specification.UserSpecification;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class GetOrderForUserTest implements ConstantsOrderTestData, ConstantsUserTestData {
    private final static String MESSAGE_GET_ORDER_WITHOUT_AUTH = "You should be authorised";
    private final UserSpecification userSpecification = new UserSpecification();
    private final OrderSpecification orderSpecification = new OrderSpecification();

    @Before
    public void setUp() {
        userSpecification.createUser(new UserCreateDto(RANDOM_STRING_EMAIL, RANDOM_STRING_PASSWORD, RANDOM_STRING_NAME));
        String accessToken = userSpecification.getAccessToken(new UserLoginDto(RANDOM_STRING_EMAIL, RANDOM_STRING_PASSWORD));
        orderSpecification.createOrderWithAuth(new OrderCreateDto(BURGER_FIRST_CHOICE), accessToken);
        orderSpecification.createOrderWithAuth(new OrderCreateDto(BURGER_SECOND_CHOICE), accessToken);
        orderSpecification.createOrderWithAuth(new OrderCreateDto(BURGER_THIRD_CHOICE), accessToken);
    }

    @Test
    @DisplayName("Получение заказов конкретного пользователя - авторизованный пользователь")
    @Description("Получение существующих заказов пользователей с указанием accessToken")
    public void getOrderForUserWithAuth() {
        String accessToken = userSpecification.getAccessToken(new UserLoginDto(RANDOM_STRING_EMAIL, RANDOM_STRING_PASSWORD));
        orderSpecification.getOrderForUserWithAuth(accessToken)
                .then()
                .assertThat()
                .body("success", is(true))
                .and()
                .statusCode(SC_OK);
    }

    @Test
    @DisplayName("Получение заказов конкретного пользователя - неавторизованный пользователь")
    @Description("Получение существующих заказов пользователей без указания accessToken")
    public void getOrderForUserWithoutAuth() {
        orderSpecification.getOrderForUserWithoutAuth()
                .then()
                .assertThat()
                .body("success", is(false))
                .and()
                .body("message", equalTo(MESSAGE_GET_ORDER_WITHOUT_AUTH))
                .and()
                .statusCode(SC_UNAUTHORIZED);
    }

    @After
    public void tearDown() {
        userSpecification.deleteUser(new UserLoginDto(RANDOM_STRING_EMAIL, RANDOM_STRING_PASSWORD));
    }
}