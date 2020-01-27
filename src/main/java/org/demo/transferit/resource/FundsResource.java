package org.demo.transferit.resource;

import org.demo.transferit.dto.FundsConfirmation;
import org.demo.transferit.service.FundsService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.math.BigDecimal;

import javax.ws.rs.GET;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;

@Path("funds")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class FundsResource {

    @Inject
    FundsService fundsService;

    @GET
    @Path("/check")
    public FundsConfirmation check(@QueryParam("accountUuid") String accountUuid, @QueryParam("amount") BigDecimal amount) {
        return fundsService.checkFunds(accountUuid, amount);
    }
}
