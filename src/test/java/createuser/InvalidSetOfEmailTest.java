package createuser;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import user.User;
import user.UserClient;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class InvalidSetOfEmailTest {

    UserClient userClient;
    User userWithoutLogin;

    @Before
    public void setUp() {
        userClient = new UserClient();
        userWithoutLogin = new User(null, RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(10));
    }

    @DisplayName("Create user without email")
    @Description("Create new user without email and try to verify exception message")
    @Test
    public void invalidSetOfEmailTest() {
        ValidatableResponse responseWithoutLogin = userClient.createUser(userWithoutLogin);

        int statusCodeWithoutLogin = responseWithoutLogin.extract().statusCode();
        String bodyResponseWithoutLogin = responseWithoutLogin.extract().path("message");

        assertThat("Email, password and name are required fields", statusCodeWithoutLogin, equalTo(SC_FORBIDDEN));
        assertThat("Текст ошибки не соответствует \"Email, password and name are required fields\" for user without email ", bodyResponseWithoutLogin, is("Email, password and name are required fields"));
    }
}


