package order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import user.BurgerRestClient;

import static io.restassured.RestAssured.given;

public class OrderClient extends BurgerRestClient {

    public static final String ORDER_PATH = "/api/orders";


    @Step("Create an order")
    public ValidatableResponse createOrder(String accessToken, Order order) {
        if(accessToken != null) {
            return given()
                    .spec(getBaseSpec())
                    .auth().oauth2(accessToken)
                    .and()
                    .body(order)
                    .when()
                    .post(ORDER_PATH)
                    .then();
        } else { return given()
                .spec(getBaseSpec())
                .body(order)
                .when()
                .post(ORDER_PATH)
                .then();
        }
    }

    @Step("Get user orders")
    public ValidatableResponse getUserOrders(String accessToken) {
        if(accessToken != null) {
            return given()
                    .spec(getBaseSpec())
                    .auth().oauth2(accessToken)
                    .when()
                    .get(ORDER_PATH)
                    .then();
        } else { return given()
                .spec(getBaseSpec())
                .when()
                .get(ORDER_PATH)
                .then();
        }
    }
}
