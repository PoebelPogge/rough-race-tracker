package solutions.pge.controller;

import io.quarkus.vertx.ConsumeEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import solutions.pge.SerialPortInitializer;
import solutions.pge.events.CreateRaceEvent;
import solutions.pge.events.StartRaceEvent;
import solutions.pge.events.StopRaceEvent;
import solutions.pge.events.TimeMeasurementEvent;
import solutions.pge.exceptions.RaceNotReadyException;
import solutions.pge.models.Buggy;
import solutions.pge.models.Race;
import solutions.pge.models.Racer;

@ApplicationScoped
public class RaceController {

    @Inject
    TournamentController tournamentController;

    @Inject
    SerialPortInitializer serialPortCommunication;

    private Race currentRace;

    public Race getCurrentRace() {
        return currentRace;
    }

    public void setCurrentRace(Race currentRace) {
        this.currentRace = currentRace;
    }

    public void addRacerByNumber(String tagId, String number){
        Racer racer = tournamentController.getRacerByNumber(number);
        currentRace.addRacer(tagId, racer);
    }

    @ConsumeEvent(CreateRaceEvent.NAME)
    public void create(CreateRaceEvent event){
        this.currentRace = event.race();
    }

    @ConsumeEvent(StartRaceEvent.NAME)
    public void start(StartRaceEvent event){
        for (Buggy buggy : currentRace.getBuggies()) {
            if(buggy.getRacer() == null){
                throw new RaceNotReadyException("Not all Racer for buggies have been set");
            }
        }
        currentRace.start();
        serialPortCommunication.startRace();
    }

    @ConsumeEvent(StopRaceEvent.NAME)
    public void stop(StopRaceEvent event){
        currentRace.stop();
        tournamentController.addRace(currentRace);
        serialPortCommunication.stopRace();
    }

    @ConsumeEvent(TimeMeasurementEvent.NAME)
    public void onTimeMeasurementEvent(TimeMeasurementEvent event){
        currentRace.takeTime(event.racerId());
    }
}
