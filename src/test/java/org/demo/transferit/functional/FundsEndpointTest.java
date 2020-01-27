package org.demo.transferit.functional;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;

@QuarkusTest
public class FundsEndpointTest {

    @Test
    public void check_returnsNotFound_whenAccountDoesNotExists() {
        given()
                .contentType("application/json")
                .when()
                .get("funds/check?accountUuid=2c3153cc-3f76-11ea-95a0-2e728ce88125&amount=10")
                .then()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    public void check_returnsBadRequest_whenUuidIsMalformed() {
        given()
                .contentType("application/json")
                .when()
                .get("funds/check?accountUuid=2c3153cc&amount=10")
                .then()
                .statusCode(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void check_returnsTrue_whenAccountHasFunds() {
        given()
                .contentType("application/json")
                .when()
                .get("funds/check?accountUuid=7daba3ef-7a06-42af-ac57-a99087035191&amount=1")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body(containsString("true"));
    }

    @Test
    public void check_returnsFalse_whenAccountDoesNotHaveFunds() {
        given()
                .contentType("application/json")
                .when()
                .get("funds/check?accountUuid=7daba3ef-7a06-42af-ac57-a99087035191&amount=50000000")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body(containsString("false"));
    }
}
