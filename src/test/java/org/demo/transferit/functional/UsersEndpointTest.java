package org.demo.transferit.functional;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;

@QuarkusTest
public class UsersEndpointTest {

    @Test
    public void get_returnsUser_whenUserExists() {
        given()
              .when().get("/users/1c3153cc-3f76-11ea-95a0-2e728ce88125")
              .then()
              .statusCode(Response.Status.OK.getStatusCode())
              .body(
                      containsString("1c3153cc-3f76-11ea-95a0-2e728ce88125"),
                      containsString("Edson"),
                      containsString("Nascimento"),
                      containsString("edson.nascimento@example.com")
               );
    }

    @Test
    public void get_returnsNotFound_whenUserDoesNotExists() {
        given()
                .when().get("/users/2c3153cc-3f76-11ea-95a0-2e728ce88125")
                .then()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    public void get_returnsBadRequest_whenUuidIsMalformed() {
        given()
                .when().get("/users/1c3153cc3f7611ea95a02e728ce88125")
                .then()
                .statusCode(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void create_returnsCreated_whenUserIsCreated() {
        given()
                .when()
                .body("{\"firstName\": \"Adriano\", \"lastName\" : \"Ribeiro\", \"email\": \"adriano.ribeiro@example.com\"}")
                .contentType("application/json")
                .post("/users")
                .then()
                .statusCode(Response.Status.CREATED.getStatusCode());
    }
}
