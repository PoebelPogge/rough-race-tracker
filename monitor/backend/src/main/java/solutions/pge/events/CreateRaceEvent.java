package solutions.pge.events;

import solutions.pge.models.Race;

public record CreateRaceEvent(Race race) {
    public static final String NAME = "create_race";

}
