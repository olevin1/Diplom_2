package user.create;

import domain.user.UserCreateDto;
import domain.user.UserLoginDto;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import specification.UserSpecification;

import static data.UserTestData.*;
import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@RunWith(Parameterized.class)
public class CreateUserWithoutRequiredFieldsParameterizedTest {
    private final static String MESSAGE_NO_REQUIRED_FIELDS = "Email, password and name are required fields";
    private final String email;
    private final String password;
    private final String name;
    private final UserSpecification specification = new UserSpecification();

    public CreateUserWithoutRequiredFieldsParameterizedTest(final String email, final String password, final String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    @Parameterized.Parameters(name = "email = {0} | password = {1} | name = {2}")
    public static Object[][] testData() {
        return new Object[][]{
                {null, RANDOM_STRING_PASSWORD, RANDOM_STRING_NAME},
                {"", RANDOM_STRING_PASSWORD, RANDOM_STRING_NAME},
                {RANDOM_STRING_EMAIL, null, RANDOM_STRING_NAME},
                {RANDOM_STRING_EMAIL, "", RANDOM_STRING_NAME},
                {RANDOM_STRING_EMAIL, RANDOM_STRING_PASSWORD, null},
                {RANDOM_STRING_EMAIL, RANDOM_STRING_PASSWORD, ""}
        };
    }

    @Test
    @DisplayName("Создание пользователя - запрос без заполнения одного из обязательных полей")
    @Description("Создания пользователя и без заполнения одного из обязательных полей")
    public void createUserWithoutRequiredFields() {
        specification.createUser(new UserCreateDto(email, password, name))
                .then()
                .assertThat()
                .body("message", equalTo(MESSAGE_NO_REQUIRED_FIELDS))
                .and()
                .body("success", is(false))
                .and()
                .statusCode(SC_FORBIDDEN);
    }

    @After
    public void tearDown() {
        specification.deleteUser(new UserLoginDto(email, password));
    }
}