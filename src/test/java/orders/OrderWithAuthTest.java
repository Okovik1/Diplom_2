package orders;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import order.Order;
import order.OrderClient;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import user.User;
import user.UserClient;

import java.util.List;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class OrderWithAuthTest {

    UserClient userClient;
    User user;
    OrderClient orderClient;
    Order order;
    String accessToken;
    ValidatableResponse createUserResponse;

    @Before
    public void setUp(){
        orderClient = new OrderClient();
        order = new Order(List.of("61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f"));
        userClient = new UserClient();
        user = new User(RandomStringUtils.randomAlphabetic(4) + "@gmail.com", RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(10));
        createUserResponse = userClient.createUser(user);
        accessToken = createUserResponse.extract().path("accessToken");
        accessToken = accessToken.replace("Bearer ", "").trim();
    }

    @DisplayName("Create order positive test with auth")
    @Test
    public void createOrderWithAuthTest(){
        ValidatableResponse createOrderResponse = orderClient.createOrder(accessToken, order);
        int statusCode = createOrderResponse.extract().statusCode();
        Boolean bodyResponse = createOrderResponse.extract().path("success");

        assertThat("Something went wrong, status != 200", statusCode, equalTo(SC_OK));
        assertThat("Boolean expression is not true", bodyResponse, is(true));
    }

    @After
    public void tearDown() {
        userClient.deleteUser(accessToken);
    }

}
