package org.demo.transferit.resource;

import org.demo.transferit.dto.UserSignUp;
import org.demo.transferit.dto.UserSummary;
import org.demo.transferit.service.UserProfileService;
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

@Path("users")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class UsersResource {

    @Inject
    UserProfileService userProfileService;

    @GET
    @Path("{uuid}")
    public UserSummary get(@PathParam String uuid) {
        return userProfileService.details(uuid);
    }

    @POST
    @Transactional
    public Response create(@Valid UserSignUp userSignUp) {
        userProfileService.signUp(userSignUp);
        return Response.ok().status(Response.Status.CREATED).build();
    }
}
