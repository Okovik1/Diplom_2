package orders;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import order.Order;
import order.OrderClient;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class OrderWithoutIngredientsTest {

    OrderClient orderClient;
    Order order;
    String accessToken;

    @Before
    public void setUp(){
        orderClient = new OrderClient();
        order = new Order(null);
    }

    @Test
    @DisplayName("Create order without ingredients")
    public void createOrderWithIngredientTest(){
        ValidatableResponse createOrderResponse = orderClient.createOrder( accessToken, order);
        int statusCode = createOrderResponse.extract().statusCode();
        String bodyResponse = createOrderResponse.extract().path("message");

        assertThat("Ingredient ids must be provided", statusCode, equalTo(SC_BAD_REQUEST));
        assertThat("Текст ошибки не соответствует \"Ingredient ids must be provided\" for order without ingredients ", bodyResponse, is("Ingredient ids must be provided"));
    }

}
