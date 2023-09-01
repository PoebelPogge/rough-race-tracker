package solutions.pge.websockets.encoder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.websocket.EncodeException;
import jakarta.websocket.Encoder;
import jakarta.websocket.EndpointConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class JsonEncoder<T> implements Encoder.Text<T> {

    public abstract ObjectMapper provideMapper();

    @Override
    public String encode(T data) throws EncodeException {
        try {
            var result = provideMapper().writeValueAsString(data);
            return result;
        } catch (JsonProcessingException e) {
            throw new EncodeException(data, "Unable to encode data", e);
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
