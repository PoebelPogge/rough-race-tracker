package solutions.pge.websockets.encoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import solutions.pge.models.Buggy;
import solutions.pge.models.Measurement;

import java.util.List;
import java.util.Map;

public class MeasurementsEncoder extends JsonEncoder<Map<Buggy, List<Measurement>>> {

    private final ObjectMapper mapper = new ObjectMapper();

    public MeasurementsEncoder() {
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Override
    public ObjectMapper provideMapper() {
        return mapper;
    }
}
