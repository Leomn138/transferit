package org.demo.transferit.resource;

import org.demo.transferit.dto.FundsTransfer;
import org.demo.transferit.service.FundsService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("transfer")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class TransfersResource {

    @Inject
    FundsService fundsService;

    @POST
    @Transactional
    public Response transfer(@Valid FundsTransfer fundsTransfer) {
        fundsService.transfer(fundsTransfer);
        return Response.ok().status(Response.Status.OK).build();
    }
}
