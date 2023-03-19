package user.create;

import domain.user.UserCreateDto;
import domain.user.UserLoginDto;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import specification.UserSpecification;

import static data.UserTestData.*;
import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class CreateUserWithExistingDataTest {
    private final static String MESSAGE_DATA_EXISTS = "User already exists";
    private final UserSpecification specification = new UserSpecification();

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
                .body("message", equalTo(MESSAGE_DATA_EXISTS))
                .and()
                .body("success", is(false))
                .and()
                .statusCode(SC_FORBIDDEN);
    }

    @After
    public void tearDown() {
        specification.deleteUser(new UserLoginDto(RANDOM_STRING_EMAIL, RANDOM_STRING_PASSWORD));
    }
}