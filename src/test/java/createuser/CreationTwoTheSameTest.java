package createuser;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import user.User;
import user.UserClient;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CreationTwoTheSameTest {
    UserClient userClient;
    User user;

    @Before
    public void setUp() {
        userClient = new UserClient();
        user = new User("TestLogin1@test.com", "TestPassword1", "TestName1");
    }

    @DisplayName("Create two users with the same credentials")
    @Description("Create two users with the same credentials, try to verify an exception in that case")
    @Test
    public void courierCreationTwoTheSameTest() {
        ValidatableResponse firstUserCreation = userClient.createUser(user);
        ValidatableResponse secondSameUserCreation = firstUserCreation;

        int statusCode = secondSameUserCreation.extract().statusCode();
        String bodyResponse = secondSameUserCreation.extract().path("message");

        assertThat("User already exists", statusCode, equalTo(SC_FORBIDDEN));
        assertThat("Текст ошибки не соответствует \"User already exists\"", bodyResponse, is("User already exists"));
    }
}