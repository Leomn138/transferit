package org.demo.transferit.functional;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;

@QuarkusTest
public class AccountsEndpointTest {

    @Test
    public void details_returnsAccountSummary_whenAccountExists() {
        given()
              .when().get("/accounts/7daba3ef-7a06-42af-ac57-a99087035191")
              .then()
              .statusCode(Response.Status.OK.getStatusCode())
              .body(
                      containsString("7daba3ef-7a06-42af-ac57-a99087035191"),
                      containsString("1c3153cc-3f76-11ea-95a0-2e728ce88125"),
                      containsString("Safety"),
                      containsString("EUR"),
                      containsString("500"),
                      not(containsString("transfers"))
               );
    }

    @Test
    public void details_returnsNotFound_whenAccountDoesNotExists() {
        given()
                .when().get("/accounts/8ebba3ef-7a06-42af-ac57-a99087035191")
                .then()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    public void details_returnsBadRequest_whenUuidIsMalformed() {
        given()
                .when().get("/accounts/8ebba3ef7a0642afac57a99087035191")
                .then()
                .statusCode(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void open_returnsCreated_whenAccountIsCreated() {
        given()
                .when()
                .body("{\"userUuid\" : \"86f7513b-f83c-4072-868c-1b4efc9d3425\", \"nickname\": \"newAccount\", \"currency\" : \"EUR\"}")
                .contentType("application/json")
                .post("/accounts")
                .then()
                .statusCode(Response.Status.CREATED.getStatusCode());
    }

    @Test
    public void open_returnsBadRequest_whenUserUuidIsMalformmated() {
        given()
                .when()
                .body("{\"userUuid\" : \"86f7513bf83c4072868c1b4efc9d3425\", \"nickname\": \"newAccount\", \"currency\" : \"EUR\"}")
                .contentType("application/json")
                .post("/accounts")
                .then()
                .statusCode(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void open_returnsBadRequest_whenCurrencyDoesNotExist() {
        given()
                .when()
                .body("{\"userUuid\" : \"86f7513b-f83c-4072-868c-1b4efc9d3555\", \"nickname\": \"newAccount\", \"currency\" : \"DOESNOTEXIST\"}")
                .contentType("application/json")
                .post("/accounts")
                .then()
                .statusCode(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void open_returnsNotFound_whenUserDoesNotExist() {
        given()
                .when()
                .body("{\"userUuid\" : \"86f7513b-f83c-4072-868c-1b4efc9d3555\", \"nickname\": \"newAccount\", \"currency\" : \"EUR\"}")
                .contentType("application/json")
                .post("/accounts")
                .then()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    public void transfers_returnsAccountSummaryAndEmptyTransfers_whenAccountExistsButNoTransfersAreFound() {
        given()
                .when().get("/accounts/7daba3ef-7a06-42af-ac57-a99087035191/transfers")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body(
                        containsString("7daba3ef-7a06-42af-ac57-a99087035191"),
                        containsString("1c3153cc-3f76-11ea-95a0-2e728ce88125"),
                        containsString("Safety"),
                        containsString("EUR"),
                        containsString("500"),
                        containsString("\"transfers\":[]")
                );
    }

    @Test
    public void transfers_returnsAccountSummaryAndTransfers_whenAccountExistsAndTransfersAreFound() {
        given()
                .when().get("/accounts/2d9c04b5-3b0f-4781-87f7-18ef9dd5b85f/transfers")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body(
                        containsString("2d9c04b5-3b0f-4781-87f7-18ef9dd5b85f"),
                        containsString("86f7513b-f83c-4072-868c-1b4efc9d3425"),
                        containsString("Main"),
                        containsString("EUR"),
                        containsString("0"),
                        containsString("transfers"),
                        containsString("BR1234"),
                        containsString("DEPOSIT"),
                        containsString("tooth implant"),
                        containsString("100")
                );
    }

    @Test
    public void tranfers_returnsNotFound_whenAccountDoesNotExists() {
        given()
                .when().get("/accounts/8ebba3ef-7a06-42af-ac57-a99087035191/transfers")
                .then()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    public void transfers_returnsBadRequest_whenUuidIsMalformed() {
        given()
                .when().get("/accounts/8ebba3ef7a0642afac57a99087035191/transfers")
                .then()
                .statusCode(Response.Status.BAD_REQUEST.getStatusCode());
    }

}
