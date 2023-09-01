package solutions.pge.websockets;

import jakarta.websocket.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import solutions.pge.websockets.encoder.JsonEncoder;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractJsonEndpoint<T> {

    private static final Logger log = LoggerFactory.getLogger(AbstractJsonEndpoint.class);

    protected final Map<String, Session> sessions = new ConcurrentHashMap<>();

    abstract T sendInitialMessage();

    protected void openSession(Session session, String clientId){
        sessions.put(clientId, session);
        this.sendMessage(sendInitialMessage());
    }

    protected void closeSession(Session session,String clientId){
        sessions.remove(clientId);
    }

    protected void sessionError(Session session,String clientId, Throwable throwable) {
        sessions.remove(clientId);
        throw new RuntimeException(throwable);
    }

    protected void sendMessage(T data){
        if(data == null) return;
        sessions.values().forEach(s -> {
            s.getAsyncRemote().sendObject(data, result ->  {
                if (result.getException() != null) {
                    log.error("Unable to broadcast message", result.getException());
                }
            });
        });
    }

    protected void sendMessage(String message){
        sessions.values().forEach(s -> {
            s.getAsyncRemote().sendText(message, result ->  {
                if (result.getException() != null) {
                    log.error("Unable to broadcast message", result.getException());
                }
            });
        });
    }

}
