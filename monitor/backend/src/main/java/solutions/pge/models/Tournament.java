package solutions.pge.models;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class Tournament{
    private final OffsetDateTime timestamp;
    private final String city;
    private List<Racer> participants;
    private List<Race> races;

    public Tournament(String city) {
        this.timestamp = OffsetDateTime.now();
        this.city = city;
        this.participants = new ArrayList<>();
        this.races = new ArrayList<>();
    }

    public OffsetDateTime getTimestamp() {
        return timestamp;
    }

    public String getCity() {
        return city;
    }

    public List<Racer> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Racer> participants) {
        this.participants = participants;
    }

    public List<Race> getRaces() {
        return races;
    }

    public void setRaces(List<Race> races) {
        this.races = races;
    }

    public void addRacer(Racer racer){
        this.participants.add(racer);
    }

    public void addRace(Race race){
        this.races.add(race);
    }
}
