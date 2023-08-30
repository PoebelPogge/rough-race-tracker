package solutions.pge.websockets;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.websocket.EncodeException;
import jakarta.websocket.Encoder;
import jakarta.websocket.EndpointConfig;
import solutions.pge.models.SystemStatus;

public class SystemStatusEncoder implements Encoder.Text<SystemStatus> {

    ObjectMapper mapper = new ObjectMapper();

    @Override
    public String encode(SystemStatus systemStatus) throws EncodeException {
        try {
            return mapper.writeValueAsString(systemStatus);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void init(EndpointConfig config) {
        Text.super.init(config);
    }

    @Override
    public void destroy() {
        Text.super.destroy();
    }
}
