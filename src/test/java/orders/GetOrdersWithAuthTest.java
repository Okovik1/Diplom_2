package orders;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import order.Order;
import order.OrderClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import user.RandomGenerator;
import user.User;
import user.UserClient;

import java.util.List;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GetOrdersWithAuthTest {

    UserClient userClient;
    OrderClient orderClient;
    User user;
    int statusCode;
    int orderNumber;
    String accessToken;
    ValidatableResponse createUserResponse;
    ValidatableResponse getOrdersResponse;
    ValidatableResponse createOrder;

    @Before
    public void setUp() {
        userClient = new UserClient();
        orderClient = new OrderClient();
        user = RandomGenerator.getRandom();
        createUserResponse = userClient.createUser(user);
        accessToken = createUserResponse.extract().path("accessToken");
        accessToken = accessToken.replace("Bearer ", "");
        createOrder = orderClient.createOrder(accessToken, new Order(List.of("61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f")));
        orderNumber = createOrder.extract().path("order.number");
    }

    @Test
    @DisplayName("Get user orders with authorization")
    public void testGetOrdersWithAuthorization() {
        getOrdersResponse = orderClient.getUserOrders(accessToken);
        statusCode = getOrdersResponse.extract().statusCode();
        int actual = getOrdersResponse.extract().path("orders.number.get(0)");
        assertThat("It is impossible to get unauthorized user orders", statusCode, equalTo(SC_OK));
        assertThat("Incorrect order number", actual, equalTo(orderNumber));
    }

    @After
    public void tearDown() {
        userClient.deleteUser(accessToken);
    }
}
