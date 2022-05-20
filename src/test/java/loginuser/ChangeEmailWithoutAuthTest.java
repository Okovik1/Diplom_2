package loginuser;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import user.RandomGenerator;
import user.User;
import user.UserClient;

import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ChangeEmailWithoutAuthTest {

    UserClient userClient;
    User user;
    int statusCode;
    String email;
    String password;
    String name;

    @Before
    public void setUp() {
        userClient = new UserClient();
        user = RandomGenerator.getRandom();
        email = user.getEmail();
        password = user.getPassword();
        name = user.getName();
    }

    @Test
    @DisplayName("Unauthorized user cannot be changed")
    public void testUnauthorizedUserCannotBeChanged() {
        email = email.substring(0, 8) + "@yandex.ru";
        password = password.substring(0, 8);
        name = name.substring(0, 8);
        user = new User(email, password, name);
        ValidatableResponse changeResponse = userClient.changeUser(null, user);
        statusCode = changeResponse.extract().statusCode();
        String actual = changeResponse.extract().path("message");
        assertThat("При изменении пользователя без авторизации - ответ сервера не 401", statusCode, equalTo(SC_UNAUTHORIZED));
        assertThat("Нет сообщения 'You should be authorised'", actual, equalTo("You should be authorised"));
    }
}
