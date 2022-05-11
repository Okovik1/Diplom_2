package orders;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import order.Order;
import order.OrderClient;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class OrderWithIncorrectHashTest {


    OrderClient orderClient;
    Order order;
    String accessToken;

    @Before
    public void setUp(){
        orderClient = new OrderClient();
        order = new Order(List.of("",""));
    }

    @Test
    @DisplayName("Create order without ingredients")
    public void createOrderWithIngredientTest(){
        ValidatableResponse createOrderResponse = orderClient.createOrder( accessToken, order);
        int statusCode = createOrderResponse.extract().statusCode();
        String bodyResponse = createOrderResponse.extract().path("message");
}
}
