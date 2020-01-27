package org.demo.transferit.resource;

import org.demo.transferit.dto.AccountOpenning;
import org.demo.transferit.dto.AccountSummary;
import org.demo.transferit.service.AccountService;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Response;

@Path("accounts")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class AccountsResource {

    @Inject
    AccountService accountService;

    @GET
    @Path("{uuid}")
    public AccountSummary details(@PathParam String uuid) {
        return accountService.getDetails(uuid);
    }

    @GET
    @Path("{uuid}/transfers")
    public AccountSummary transfers(@PathParam String uuid) {
        return accountService.getTransfers(uuid);
    }

    @POST
    @Transactional
    public Response open(@Valid AccountOpenning accountOpenning) {
        accountService.open(accountOpenning);
        return Response.ok().status(Response.Status.CREATED).build();
    }
}
