package createuser;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import user.User;
import user.UserClient;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class PositiveCreationUserTest {
    UserClient userClient;
    User userPositive;

    @Before
    public void setUp() {
        userClient = new UserClient();
        userPositive = new User(RandomStringUtils.randomAlphabetic(4) + "@gmail.com", RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(10));
    }

    @DisplayName("Create user positive test")
    @Test
    public void positiveCreationUserTest() {
        ValidatableResponse response = userClient.createUser(userPositive);
        int statusCode = response.extract().statusCode();
        Boolean bodyResponse = response.extract().path("success");

        assertThat("Something went wrong, status != 200", statusCode, equalTo(SC_OK));
        assertThat("Boolean expression is not true", bodyResponse, is(true));
    }
}

