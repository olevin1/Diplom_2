package user.create;

import client.UserClient;
import domain.user.UserCreateDto;
import domain.user.UserLoginDto;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static data.UserTestData.*;
import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class CreateUserWithExistingDataTest {
    private final static String MESSAGE_DATA_EXISTS = "User already exists";
    private final UserClient specification = new UserClient();

    @Before
    public void setUp() {
        specification.createUser(new UserCreateDto(RANDOM_STRING_EMAIL, RANDOM_STRING_PASSWORD, RANDOM_STRING_NAME));
    }

    @Test
    @DisplayName("Создание дубликата пользователя - запрос с данными уже зарегистрированного пользователя")
    @Description("Создание пользователя, который уже зарегистрирован")
    public void createUserWithExistingData() {
        specification.createUser(new UserCreateDto(RANDOM_STRING_EMAIL, RANDOM_STRING_PASSWORD, RANDOM_STRING_NAME))
                .then()
                .assertThat()
                .statusCode(SC_FORBIDDEN)
                .and()
                .body("success", is(false))
                .and()
                .body("message", equalTo(MESSAGE_DATA_EXISTS));
    }

    @After
    public void tearDown() {
        specification.deleteUser(new UserLoginDto(RANDOM_STRING_EMAIL, RANDOM_STRING_PASSWORD));
    }
}