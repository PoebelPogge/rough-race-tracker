package solutions.pge.events;

import solutions.pge.models.Measurement;

public record MeasurementEvent(Measurement measurement) {
    public static final String NAME = "update_clients";
}
