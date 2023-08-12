package solutions.pge.controller;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.commons.lang3.StringUtils;
import solutions.pge.exceptions.RacerNotFoundException;
import solutions.pge.models.Race;
import solutions.pge.models.Racer;
import solutions.pge.models.Tournament;

@ApplicationScoped
public class TournamentController {
    private Tournament tournament;

    public Tournament getTournament() {
        return tournament;
    }
    public Tournament create(String city){
        this.tournament = new Tournament(city);
        return tournament;
    }

    public void addRacer(Racer racer){
        this.tournament.addRacer(racer);
    }

    public Racer getRacerByNumber(String number){
        return this.tournament.getParticipants()
                .stream()
                .filter(r -> StringUtils.equals(r.getNumber(), number))
                .findFirst()
                .orElseThrow(() -> new RacerNotFoundException(number));
    }

    public void addRace(Race race){
        this.tournament.addRace(race);
    }
}
