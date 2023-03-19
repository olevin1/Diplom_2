package user.update;

import constants.ConstantsUserTestData;
import domain.user.UserCreateDto;
import domain.user.UserLoginDto;
import domain.user.UserUpdateDto;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import specification.UserSpecification;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.Matchers.is;

@RunWith(Parameterized.class)
public class UpdateUserParameterizedTest implements ConstantsUserTestData {
    private final String email;
    private final String password;
    private final String name;
    private final UserSpecification specification = new UserSpecification();


    public UpdateUserParameterizedTest(final String email, final String password, final String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    @Parameterized.Parameters(name = "email = {0} | password = {1} | name = {2}")
    public static Object[][] testData() {
        return new Object[][]{
                {"t5" + RANDOM_STRING_EMAIL, RANDOM_STRING_PASSWORD, RANDOM_STRING_NAME},
                {RANDOM_STRING_EMAIL, RANDOM_STRING_PASSWORD + "555", RANDOM_STRING_NAME},
                {RANDOM_STRING_EMAIL, RANDOM_STRING_PASSWORD, RANDOM_STRING_NAME + "tttF"},
                {"tff" + RANDOM_STRING_EMAIL, RANDOM_STRING_PASSWORD + "r", RANDOM_STRING_PASSWORD + "f"}
        };
    }

    @Before
    public void setUp() {
        specification.createUser(new UserCreateDto(RANDOM_STRING_EMAIL, RANDOM_STRING_PASSWORD, RANDOM_STRING_NAME));
    }

    @Test
    @DisplayName("Обновление пользователя - проверка изменения всех полей с авторизацией")
    @Description("Изменение любого атрибута пользователя с авторизацией")
    public void updateUserWithAuthorization() {
        String accessToken = specification.getAccessToken(new UserLoginDto(RANDOM_STRING_EMAIL, RANDOM_STRING_PASSWORD));
        specification.updateUserWithAuth(new UserUpdateDto(email, password, name), accessToken)
                .then()
                .assertThat()
                .body("success", is(true))
                .and()
                .statusCode(SC_OK);
    }

    @Test
    @DisplayName("Обновление пользователя - попытка изменения данных без авторизации")
    @Description("Изменение любого атрибута пользователя без авторизации")
    public void updateUserWithoutAuthorization() {
        specification.updateUserWithoutAuth(new UserUpdateDto(email, password, name))
                .then()
                .assertThat()
                .body("success", is(false))
                .and()
                .statusCode(SC_UNAUTHORIZED);
    }

    @After
    public void tearDown() {
        specification.deleteUser(new UserLoginDto(email, password));
        specification.deleteUser(new UserLoginDto(RANDOM_STRING_EMAIL, RANDOM_STRING_PASSWORD));
    }
}