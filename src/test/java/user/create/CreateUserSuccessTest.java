package user.create;

import domain.user.UserCreateDto;
import domain.user.UserLoginDto;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Test;
import specification.UserSpecification;

import static data.UserTestData.*;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.is;

public class CreateUserSuccessTest {
    private final UserSpecification specification = new UserSpecification();

    @Test
    @DisplayName("Создание нового пользователя - запрос с допустимыми значениями")
    @Description("Проверка создания уникального пользователя с допустимыми значениями всех полей")
    public void createUserSuccess() {
        specification.createUser(new UserCreateDto(RANDOM_STRING_EMAIL, RANDOM_STRING_PASSWORD, RANDOM_STRING_NAME))
                .then()
                .assertThat()
                .body("success", is(true))
                .and()
                .statusCode(SC_OK);
    }

    @After
    public void tearDown() {
        specification.deleteUser(new UserLoginDto(RANDOM_STRING_EMAIL, RANDOM_STRING_PASSWORD));
    }
}