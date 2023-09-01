package solutions.pge.websockets.encoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import solutions.pge.models.Race;

public class RaceEncoder extends JsonEncoder<Race> {

    private final ObjectMapper mapper;

    public RaceEncoder() {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }


    @Override
    public ObjectMapper provideMapper() {
        return mapper;
    }
}
