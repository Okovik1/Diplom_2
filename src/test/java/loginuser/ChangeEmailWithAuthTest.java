package loginuser;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import user.RandomGenerator;
import user.User;
import user.UserClient;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ChangeEmailWithAuthTest {

    UserClient userClient;
    User user;
    ValidatableResponse createResponse;
    int statusCode;
    String email;
    String password;
    String name;
    String accessToken;

    @Before
    public void setUp() {
        userClient = new UserClient();
        user = RandomGenerator.getRandom();
        email = user.getEmail();
        password = user.getPassword();
        name = user.getName();
        createResponse = userClient.createUser(user);
        accessToken = createResponse.extract().path("accessToken");
        accessToken = accessToken.replace("Bearer ", "");
    }

    @Test
    @DisplayName("Change email auth user")
    public void testAuthorizedUserCanChangeEmail() {
        email = email.substring(0, 8) + "@yandex.ru";
        user = new User(email, password, name);
        ValidatableResponse changeResponse = userClient.changeUser(accessToken, user);
        statusCode = changeResponse.extract().statusCode();
        String actualEmail = changeResponse.extract().path("user.email");
        assertThat("Пользователь не изменен", statusCode, equalTo(SC_OK));
        assertThat("Email в ответе не совпадает с новым email пользователя", actualEmail, equalTo(email));
    }

    @After
    public void tearDown() {
        userClient.deleteUser(accessToken);
    }
}
