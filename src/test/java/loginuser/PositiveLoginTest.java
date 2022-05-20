package loginuser;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import user.RandomGenerator;
import user.User;
import user.UserClient;
import user.UserCredentials;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class PositiveLoginTest {

    UserClient userClient;
    User user;
    UserCredentials credentials;
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
        credentials = new UserCredentials(email, password);
    }


    @Test
    @DisplayName("Positive user login ")
    public void testUserCanLoginWithValidCredentials() {
        ValidatableResponse loginResponse = userClient.loginUser(credentials);
        statusCode = loginResponse.extract().statusCode();
        String actualEmail = loginResponse.extract().path("user.email");
        String actualName = loginResponse.extract().path("user.name");
        assertThat("Пользователь не может залогиниться", statusCode, equalTo(SC_OK));
        assertThat("Email в ответе не совпадает с email пользователя", actualEmail, equalTo(email));
        assertThat("Name в ответе не совпадает с именем пользователя", actualName, equalTo(name));
    }

    @After
    public void tearDown() {
        userClient.deleteUser(accessToken);
    }
}
