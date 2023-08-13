package solutions.pge.models;

import java.time.OffsetDateTime;

public class Measurement {

    private final OffsetDateTime time;

    private final String tagId;

    private MeasurementType type;

    public Measurement(String tagId, OffsetDateTime time) {
        this.tagId = tagId;
        this.time = time;
        this.type = MeasurementType.UNKNOWN;
    }

    private Measurement(String tagId,OffsetDateTime time, MeasurementType type) {
        this.tagId = tagId;
        this.time = time;
        this.type = type;
    }

    public static Measurement start(String tagId,OffsetDateTime time){
        return new Measurement(tagId, time, MeasurementType.START);
    }

    public static Measurement stop(String tagId,OffsetDateTime time){
        return new Measurement(tagId, time, MeasurementType.STOP);
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

    public String getTagId() {
        return tagId;
    }

    public enum MeasurementType{
        START,
        STOP,
        INVALID,
        UNKNOWN
    }
}
