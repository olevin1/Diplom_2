package user.login;

import domain.user.UserCreateDto;
import domain.user.UserLoginDto;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import specification.UserSpecification;

import static data.UserTestData.*;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@RunWith(Parameterized.class)
public class LoginWithIncorrectDataParameterizedTest {
    private final static String MESSAGE_INCORRECT_DATA = "email or password are incorrect";
    private final String email;
    private final String password;
    private final UserSpecification specification = new UserSpecification();

    public LoginWithIncorrectDataParameterizedTest(final String email, final String password) {
        this.email = email;
        this.password = password;
    }

    @Parameterized.Parameters(name = "email = {0} | password = {1}")
    public static Object[][] testData() {
        return new Object[][]{
                {null, RANDOM_STRING_PASSWORD},
                {"", RANDOM_STRING_PASSWORD},
                {"t" + RANDOM_STRING_EMAIL, RANDOM_STRING_PASSWORD},
                {RANDOM_STRING_EMAIL, null},
                {RANDOM_STRING_EMAIL, ""},
                {RANDOM_STRING_EMAIL, RANDOM_STRING_PASSWORD + "rr"},
                {"r" + RANDOM_STRING_EMAIL, RANDOM_STRING_PASSWORD + "t3"}
        };
    }

    @Before
    public void setUp() {
        specification.createUser(new UserCreateDto(RANDOM_STRING_EMAIL, RANDOM_STRING_PASSWORD, RANDOM_STRING_NAME));
    }

    @Test
    @DisplayName("Авторизация в системе - запрос с некорректными данными")
    @Description("Логин с неверным логином и (или) паролем")
    public void loginWithIncorrectData() {
        specification.loginUser(new UserLoginDto(email, password))
                .then()
                .assertThat()
                .body("message", equalTo(MESSAGE_INCORRECT_DATA))
                .and()
                .body("success", is(false))
                .and()
                .statusCode(SC_UNAUTHORIZED);
    }

    @After
    public void tearDown() {
        specification.deleteUser(new UserLoginDto(RANDOM_STRING_EMAIL, RANDOM_STRING_PASSWORD));
    }
}