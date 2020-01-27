package org.demo.transferit.unit;

import org.demo.transferit.mapper.ErrorMapper;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class ErrorMapperTest {

    @Test
    public void toResponse_returnsInternalServerError_whenAnExceptionIsThrown() {
        Response response = new ErrorMapper().toResponse(new IllegalArgumentException());
        assertEquals(response.getStatus(), Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
    }

    @Test
    public void toResponse_returnsCustomError_whenAWebApplicationExceptionIsThrown() {
        WebApplicationException exception = new WebApplicationException(Response.Status.NOT_FOUND.getStatusCode());
        Response response = new ErrorMapper().toResponse(exception);
        assertEquals(response.getStatus(), exception.getResponse().getStatus());
    }

    @Test
    public void toResponse_returnsTheErrorMessage_whenExceptionMessageisNotNull() {
        String message = "WebApplicationExceptionMessage";
        WebApplicationException exception = new WebApplicationException(message);
        Response response = new ErrorMapper().toResponse(exception);
        assertTrue(response.getEntity().toString().contains(message));
    }
}
