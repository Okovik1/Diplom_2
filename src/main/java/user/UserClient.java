package user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class UserClient extends BurgerRestClient {

    public static final String USER_PATH = "/api/auth";

    @Step("Create user")
    public ValidatableResponse createUser(User user) {
        return given()
                .spec(getBaseSpec())
                .body(user)
                .when()
                .post(USER_PATH+"/register")
                .then();
    }

    @Step("Create user")
    public ValidatableResponse loginUser(UserCredentials userCredentials) {
        return given()
                .spec(getBaseSpec())
                .body(userCredentials)
                .when()
                .post(USER_PATH+ "/login")
                .then();
    }

    @Step("Change user data")
    public ValidatableResponse changeUser(String accessToken, User user) {
        if(accessToken != null) {
            return given()
                    .spec(getBaseSpec())
                    .auth().oauth2(accessToken)
                    .and()
                    .body(user)
                    .when()
                    .patch(USER_PATH+ "/user")
                    .then();
        } else { return given()
                .spec(getBaseSpec())
                .and()
                .body(user)
                .when()
                .patch(USER_PATH+ "/user")
                .then();
        }
    }

    @Step("Delete user")
    public ValidatableResponse deleteUser(String accessToken) {
        return given()
                .spec(getBaseSpec())
                .auth().oauth2(accessToken)
                .when()
                .delete(USER_PATH+ "/user")
                .then();
    }

}
