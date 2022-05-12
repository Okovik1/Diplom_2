package orders;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import order.Order;
import order.OrderClient;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GetOrdersWithoutAuthTest {

    OrderClient orderClient;
    int statusCode;
    int orderNumber;
    ValidatableResponse getOrdersResponse;
    ValidatableResponse createOrder;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
        createOrder = orderClient.createOrder(null, new Order(List.of("61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f")));
        orderNumber = createOrder.extract().path("order.number");
    }

    @Test
    @DisplayName("Get user orders without authorization")
    public void testGetOrdersWithoutAuthorization() {
        getOrdersResponse = orderClient.getUserOrders(null);
        statusCode = getOrdersResponse.extract().statusCode();
        String actual = getOrdersResponse.extract().path("message");
        assertThat("Somehow you get orders of unauthorized user", statusCode, equalTo(SC_UNAUTHORIZED));
        assertThat("Нет сообщения 'You should be authorised'", actual, equalTo("You should be authorised"));
    }
}
