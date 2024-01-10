package com.itm.space.backendresources.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.itm.space.backendresources.BaseIntegrationTest;
import io.restassured.http.ContentType;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import com.fasterxml.jackson.databind.ObjectMapper;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

class UserControllerIT extends BaseIntegrationTest {

    @Autowired
    private ObjectMapper objectMapper;

    private final String TOKEN = getBearerToken();

    private static final String ID = "4de909a6-614c-4d1f-ba6e-240a766f6a70";

    @Test
    @DisplayName("Метод hello() вернет статус 200 и строку c UUID пользователя")
    void hello_givenAuthenticatedUser_whenGetApiUsersHello_shouldReturnStatusOk_andString() {
        given()
                .header("Authorization", TOKEN)
                .when()
                .get("api/users/hello")
                .then()
                .assertThat()
                .statusCode(is(200))
                .body(is(notNullValue()));
    }

    @Test
    @DisplayName("Метод create() вернет статус 200 при успешном сохранении пользователя")
    void create_givenAuthenticatedUser_whenPostApiUsers_shouldReturnStatusOk() throws IOException {
        given()
                .header("Authorization", TOKEN)
                .contentType(ContentType.JSON)
                .body(jsonMapper("/json/good_request.json"))
                .when()
                .post("/api/users")
                .then()
                .assertThat()
                .statusCode(is(200));
    }

    @Test
    @DisplayName("Метод create() вернет статус 400 и ответ с невалидным полем и описанием ошибки")
    void create_givenAuthenticatedUser_whenPostApiUsers_shouldReturnBedRequest() throws IOException {
        given()
                .header("Authorization", TOKEN)
                .contentType(ContentType.JSON)
                .body(jsonMapper("/json/bad_request.json"))
                .when()
                .post("/api/users")
                .then()
                .assertThat()
                .body("email", equalTo("Email should be valid"))
                .statusCode(is(400));
    }

    @Test
    @DisplayName("Метод getUserById() вернет статус 200 и json с описанием пользователя")
    void getUserById_givenAuthenticatedUser_whenGetApiUsers_shouldReturnCorrectUser_andStatusOk() {
        given()
                .header("Authorization", TOKEN)
                .when()
                .get("/api/users/" + ID)
                .then()
                .body("firstName", equalTo("User"))
                .body("lastName", equalTo("UserUser"))
                .body("email", equalTo("user@mail.ru"))
                .body("roles", equalTo(List.of("MODERATOR", "default-roles-itm")))
                .body("groups", equalTo(List.of("Moderators")))
                .statusCode(is(200));
    }

    @Test
    @DisplayName("Метод getUserById() вернет статус 404 при получении не существующего пользователя")
    void getUserById_givenAuthenticatedUser_whenGetApiUsers_shouldReturnStatus404() {
        given()
                .header("Authorization", TOKEN)
                .when()
                .get("/api/users/" + " ")
                .then()
                .statusCode(is(404));
    }

    private Map<String, Object> jsonMapper(String path) throws IOException {
        Resource resource = new ClassPathResource(path);
        return objectMapper.readValue(resource.getInputStream(), new TypeReference<>() {
        });
    }
}