package scoreboard.api.resource;

import scoreboard.api.domain.ScoreboardDto;
import scoreboard.api.service.ScoreboardService;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/scoreboard")
@Singleton
public class ScoreboardResource {

    @Inject
    ScoreboardService scoreboardService;

    @GET
    @Path("/details")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getScoreboardDetails(@Context HttpServletRequest request) {
        return Response.ok(scoreboardService.getScoreboardDetails()).build();
    }

    @POST
    @Path("/details")
    public Response addScoreboardDetails(@Context HttpServletRequest request, ScoreboardDto scoreboardDto) {
        return Response.ok(scoreboardService.addScoreboardDetails(scoreboardDto)).build();
    }
}
