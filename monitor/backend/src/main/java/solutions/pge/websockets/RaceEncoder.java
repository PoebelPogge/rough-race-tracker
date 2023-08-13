package solutions.pge.websockets;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.inject.Inject;
import jakarta.websocket.EncodeException;
import jakarta.websocket.Encoder;
import jakarta.websocket.EndpointConfig;
import solutions.pge.models.Measurement;
import solutions.pge.models.Race;

public class RaceEncoder implements Encoder.Text<Measurement> {

    ObjectMapper mapper = new ObjectMapper();

    public RaceEncoder() {
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Override
    public String encode(Measurement measurement) throws EncodeException {
        try {
            var result = mapper.writeValueAsString(measurement);
            return result;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void init(EndpointConfig config) {
        Text.super.init(config);}

    @Override
    public void destroy() {
        Text.super.destroy();
    }
}
