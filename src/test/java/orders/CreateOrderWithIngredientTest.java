package orders;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import order.Order;
import order.OrderClient;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CreateOrderWithIngredientTest {

    OrderClient orderClient;
    Order order;
    String accessToken;

    @Before
    public void setUp(){
        orderClient = new OrderClient();
        order = new Order(List.of("61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f"));
    }

    @Test
    @DisplayName("Create order with ingredients")
    public void createOrderWithIngredientTest(){
        ValidatableResponse createOrderResponse = orderClient.createOrder( accessToken,order);
        int statusCode = createOrderResponse.extract().statusCode();
        Boolean bodyResponse = createOrderResponse.extract().path("success");

        assertThat("Something went wrong, status != 200", statusCode, equalTo(SC_OK));
        assertThat("Boolean expression is not true", bodyResponse, is(true));
    }

}
