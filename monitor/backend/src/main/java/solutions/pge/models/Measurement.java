package solutions.pge.models;

import java.time.OffsetDateTime;

public class Measurement {

    private final OffsetDateTime time;
    private MeasurementType type;

    public Measurement(OffsetDateTime time) {
        this.time = time;
        this.type = MeasurementType.UNKNOWN;
    }

    private Measurement(OffsetDateTime time, MeasurementType type) {
        this.time = time;
        this.type = type;
    }

    public static Measurement start(OffsetDateTime time){
        return new Measurement(time, MeasurementType.START);
    }

    public static Measurement stop(OffsetDateTime time){
        return new Measurement(time, MeasurementType.STOP);
    }

    public void setType(MeasurementType type) {
        this.type = type;
    }

    public OffsetDateTime getTime() {
        return time;
    }

    public MeasurementType getType() {
        return type;
    }

    public enum MeasurementType{
        START,
        STOP,
        INVALID,
        UNKNOWN
    }
}
