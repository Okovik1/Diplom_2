package loginuser;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import user.RandomGenerator;
import user.User;
import user.UserClient;
import user.UserCredentials;

import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class LoginWithoutEmailTest {

    UserClient userClient;
    User user;
    UserCredentials credentials;
    ValidatableResponse createResponse;
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
        createResponse = userClient.createUser(user);
        credentials = new UserCredentials(email, password);
    }

    @Test
    @DisplayName("Login without email")
    public void testUserCannotLoginWithNonExistentEmail() {
        ValidatableResponse loginResponse = userClient.loginUser(new UserCredentials(email.substring(0, 8) + "@yandex.ru", password));
        statusCode = loginResponse.extract().statusCode();
        String actual = loginResponse.extract().path("message");
        assertThat("Пользователь может залогиниться с несуществующим логином", statusCode, equalTo(SC_UNAUTHORIZED));
        assertThat("Нет сообщения 'email or password are incorrect'", actual, equalTo("email or password are incorrect"));
    }
}
