package org.demo.transferit.functional;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;

@QuarkusTest
public class TransferEndpointTest {
    @Test
    public void transfer_returnsNotFound_whenAccountDoesNotExists() {
        given()
                .when()
                .body("{\"reference\": \"doc\", \"description\" : \"tooth\", \"entries\": [{\"accountUuid\": \"2c3153cc-3f76-11ea-95a0-2e728ce88125\", \"currency\": \"EUR\", \"operation\": \"DEPOSIT\", \"amount\": 10}]}")
                .contentType("application/json")
                .post("/transfer")
                .then()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    public void transfer_returnsBadRequest_whenUuidIsMalformed() {
        given()
                .when()
                .body("{\"reference\": \"doc\", \"description\" : \"tooth\", \"entries\": [{\"accountUuid\": \"2c3153cc\", \"currency\": \"EUR\", \"operation\": \"DEPOSIT\", \"amount\": 10}]}")
                .contentType("application/json")
                .post("/transfer")
                .then()
                .statusCode(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void transfer_returnsBadRequest_whenAccountDoesNotHaveFunds() {
        given()
                .when()
                .body("{\"reference\": \"doc\", \"description\" : \"tooth\", \"entries\": [{\"accountUuid\": \"2d9c04b5-3b0f-4781-87f7-18ef9dd5b84f\", \"currency\": \"EUR\", \"operation\": \"WITHDRAW\", \"amount\": 100000000}]}")
                .contentType("application/json")
                .post("/transfer")
                .then()
                .statusCode(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void transfer_returnsBadRequest_whenAccountOperationIsMalformed() {
        given()
                .when()
                .body("{\"reference\": \"doc\", \"description\" : \"tooth\", \"entries\": [{\"accountUuid\": \"7daba3ef-7a06-42af-ac57-a99087035191\", \"currency\": \"EUR\", \"operation\": \"DEPOSIIT\", \"amount\": 100000000}]}")
                .contentType("application/json")
                .post("/transfer")
                .then()
                .statusCode(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void transfer_returnsBadRequest_whenAccountCurrencyIsMalformed() {
        given()
                .when()
                .body("{\"reference\": \"doc\", \"description\" : \"tooth\", \"entries\": [{\"accountUuid\": \"7daba3ef-7a06-42af-ac57-a99087035191\", \"currency\": \"EURO\", \"operation\": \"DEPOSIT\", \"amount\": 100000000}]}")
                .contentType("application/json")
                .post("/transfer")
                .then()
                .statusCode(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void transfer_returnsOk_whenAccountTransferSucceds() {
        given()
                .when()
                .body("{\"reference\": \"doc\", \"description\" : \"tooth\", \"entries\": [{\"accountUuid\": \"7daba3ef-7a06-42af-ac57-a99087035191\", \"currency\": \"EUR\", \"operation\": \"DEPOSIT\", \"amount\": 100000000}]}")
                .contentType("application/json")
                .post("/transfer")
                .then()
                .statusCode(Response.Status.OK.getStatusCode());

        given()
                .when().get("/accounts/7daba3ef-7a06-42af-ac57-a99087035191/transfers")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body(
                        containsString("100000500"),
                        containsString("100000000"),
                        containsString("doc")
                );
    }
}
