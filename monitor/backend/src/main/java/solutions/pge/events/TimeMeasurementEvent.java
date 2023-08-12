package solutions.pge.events;

public record TimeMeasurementEvent (String racerId){
    public static final String NAME = "take_time";
}
