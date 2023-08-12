package solutions.pge.resources;

import io.vertx.core.eventbus.EventBus;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

import java.util.logging.Logger;

import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.RestQuery;
import solutions.pge.controller.RaceController;
import solutions.pge.events.CreateRaceEvent;
import solutions.pge.events.StartRaceEvent;
import solutions.pge.events.StopRaceEvent;
import solutions.pge.models.Buggy;
import solutions.pge.models.Race;

import java.util.List;

@Path("race")
public class RaceResource {

    private static final Logger LOGGER = Logger.getLogger("RaceResource");

    @Inject
    EventBus bus;

    @Inject
    RaceController raceController;

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Race create(List<String> tagIds){
        var buggies = tagIds.stream().map(Buggy::new).toList();
        var race = new Race(buggies);
        var createRaceEvent = new CreateRaceEvent(race);
        bus.publish(CreateRaceEvent.NAME,createRaceEvent);
        LOGGER.info("New Race created with ID: " + race.getId());
        return race;
    }

    @POST
    @Path("/start")
    @Produces(MediaType.TEXT_PLAIN)
    public String start(){
        var event = new StartRaceEvent();
        bus.publish(StartRaceEvent.NAME, event);
        LOGGER.info("Current race started");
        return "done";
    }

    @POST
    @Path("/stop")
    @Produces(MediaType.TEXT_PLAIN)
    public String stop(){
        var event = new StopRaceEvent();
        bus.publish(StopRaceEvent.NAME, event);
        LOGGER.info("Current race stopped");
        return "done";
    }

    @POST
    @Path("/racer")
    @Produces(MediaType.TEXT_PLAIN)
    public String addRacer(@RestQuery String tagId,
                           @RestQuery String number){
        raceController.addRacerByNumber(tagId, number);
        return "done";
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Race getRace(){
        return raceController.getCurrentRace();
    }

}
