package specification;

import domain.user.UserCreateDto;
import domain.user.UserLoginDto;
import domain.user.UserUpdateDto;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class UserSpecification extends BasicSpecification {
    private static final String CREATE_USER_PATH = "/api/auth/register";
    private static final String AUTH_USER_PATH = "/api/auth/login";
    private static final String UPDATE_USER_PATH = "/api/auth/user";
    private static final String DELETE_USER_PATH = "/api/auth/user";

    @Step("Создать пользователя")
    public Response createUser(UserCreateDto user) {
        return given()
                .spec(baseSpec())
                .and()
                .body(user)
                .when()
                .post(CREATE_USER_PATH);
    }

    @Step("Авторизоваться в системе")
    public Response loginUser(UserLoginDto user) {
        return given()
                .spec(baseSpec())
                .and()
                .body(user)
                .when()
                .post(AUTH_USER_PATH);
    }

    @Step("Получить токен пользователя")
    public String getAccessToken(UserLoginDto user) {
        return given()
                .spec(baseSpec())
                .and()
                .body(user)
                .when()
                .post(AUTH_USER_PATH)
                .then().extract().body().path("accessToken");
    }

    @Step("Обновить информацию о пользователе c авторизацией")
    public Response updateUserWithAuth(UserUpdateDto user, String accessToken) {
        return given()
                .spec(baseSpec())
                .header("Authorization", accessToken)
                .and()
                .body(user)
                .when()
                .patch(UPDATE_USER_PATH);
    }

    @Step("Обновить информацию о пользователе без авторизации")
    public Response updateUserWithoutAuth(UserUpdateDto user) {
        return given()
                .spec(baseSpec())
                .and()
                .body(user)
                .when()
                .patch(UPDATE_USER_PATH);
    }

    @Step("Удалить пользователя")
    public void deleteUser(UserLoginDto user) {
        String accessToken = getAccessToken(user);
        if (accessToken != null) {
            given()
                    .header("Authorization", accessToken)
                    .spec(baseSpec())
                    .and()
                    .delete(DELETE_USER_PATH)
                    .then()
                    .assertThat().body("success", is(true))
                    .statusCode(202);
        }
    }
}