package solutions.pge.resources;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.RestQuery;
import solutions.pge.controller.TournamentController;
import solutions.pge.models.Racer;
import solutions.pge.models.Tournament;

@Path("tournament")
public class TournamentResource {

    @Inject
    TournamentController tournamentController;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Tournament getCurrent(){
        return tournamentController.getTournament();
    }


    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Tournament create(@RestQuery String city){
        var tournament = tournamentController.create(city);
        return tournament;
    }

    @POST
    @Path("/racer")
    @Produces(MediaType.APPLICATION_JSON)
    public Racer create(Racer racer){
        tournamentController.addRacer(racer);
        return racer;
    }
}
