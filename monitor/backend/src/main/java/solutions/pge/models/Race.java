package solutions.pge.models;

import org.apache.commons.lang3.StringUtils;
import solutions.pge.exceptions.BuggyNotFoundException;

import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.util.*;

public class Race {
    private final UUID id;
    private final OffsetTime creationDate;
    private final List<Buggy> buggies;
    private RACE_STATE state;
    private Map<Buggy, List<Measurement>> measurements;

    public Race(List<Buggy> buggies) {
        this.id = UUID.randomUUID();
        this.creationDate = OffsetTime.now();
        this.state = RACE_STATE.CREATED;
        this.buggies = buggies;
        this.measurements = new HashMap<>();
    }

    public UUID getId() {
        return id;
    }

    public OffsetTime getCreationDate() {
        return creationDate;
    }

    public List<Buggy> getBuggies() {
        return buggies;
    }

    public RACE_STATE getState() {
        return state;
    }

    public void setState(RACE_STATE state) {
        this.state = state;
    }

    public Map<Buggy, List<Measurement>> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(Map<Buggy, List<Measurement>> measurements) {
        this.measurements = measurements;
    }

    public void start(){
        this.state = RACE_STATE.RUNNING;
        var timestamp = OffsetDateTime.now();
        for (Buggy buggy : buggies) {
            List<Measurement> times = new ArrayList<>();
            times.add(Measurement.start(timestamp));
            measurements.put(buggy, times);
        }
    }

    public void takeTime(String tagId){
        var buggy = getBuggy(tagId);
        var times = measurements.get(buggy);
        if(null == times){
            times = new ArrayList<>();
        }
        times.add(new Measurement(OffsetDateTime.now()));
    }

    public void stop(){
        this.state = RACE_STATE.DONE;
    }

    public void addRacer(String tagId, Racer racer){
        for (Buggy buggy : buggies) {
            if(StringUtils.equals(tagId, buggy.getTagId())){
                buggy.setRacer(racer);
            }
        }
    }


    private Buggy getBuggy(String tagId){
        return buggies.stream()
                .filter(b -> StringUtils.equals(b.getTagId(),tagId))
                .findFirst().orElseThrow(() -> new BuggyNotFoundException(tagId));
    }

    public enum RACE_STATE{
        CREATED,
        RUNNING,
        DONE
    }
}


