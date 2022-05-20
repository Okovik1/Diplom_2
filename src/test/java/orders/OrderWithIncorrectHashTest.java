package orders;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import order.Order;
import order.OrderClient;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.apache.http.HttpStatus.SC_INTERNAL_SERVER_ERROR;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class OrderWithIncorrectHashTest {


    OrderClient orderClient;
    Order order;
    String accessToken;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
        order = new Order(List.of("iaminvalidcash1", "iaminvalidcash2"));
    }

    @Test
    @DisplayName("Create order with invalid hashes")
    public void createOrderWithIngredientTest() {
        ValidatableResponse createOrderResponse = orderClient.createOrder(accessToken, order);
        int statusCode = createOrderResponse.extract().statusCode();

        assertThat("Internal server error, please check hashes of ingredients ", statusCode, equalTo(SC_INTERNAL_SERVER_ERROR));
    }
}
