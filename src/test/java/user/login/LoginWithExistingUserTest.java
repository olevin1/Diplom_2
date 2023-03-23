package user.login;

import client.UserClient;
import domain.user.UserCreateDto;
import domain.user.UserLoginDto;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static data.UserTestData.*;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.is;

public class LoginWithExistingUserTest {
    private final UserClient specification = new UserClient();

    @Before
    public void setUp() {
        specification.createUser(new UserCreateDto(RANDOM_STRING_EMAIL, RANDOM_STRING_PASSWORD, RANDOM_STRING_NAME));
    }

    @Test
    @DisplayName("Авторизация в системе - запрос с данными существующего пользователя")
    @Description("Логин под существующим пользователем")
    public void loginWithExistingUser() {
        specification.loginUser(new UserLoginDto(RANDOM_STRING_EMAIL, RANDOM_STRING_PASSWORD))
                .then()
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .body("success", is(true));
    }

    @After
    public void tearDown() {
        specification.deleteUser(new UserLoginDto(RANDOM_STRING_EMAIL, RANDOM_STRING_PASSWORD));
    }
}