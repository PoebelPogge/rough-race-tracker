package solutions.pge.websockets;

import io.quarkus.vertx.ConsumeEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import solutions.pge.controller.RaceController;
import solutions.pge.events.MeasurementEvent;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/race/{clientId}", encoders = RaceEncoder.class)
@ApplicationScoped
public class RaceEndpoint {

    private Map<String, Session> sessions = new ConcurrentHashMap<>();

    @Inject
    RaceController raceController;

    @OnOpen
    public void onOpen(Session session, @PathParam("clientId") String clientId){
        sessions.put(clientId, session);
    }

    @OnClose
    public void onClose(Session session, @PathParam("clientId") String clientId){
        sessions.remove(clientId);
    }

    @OnError
    public void onError(Session session, @PathParam("clientId") String clientId, Throwable throwable) {
        sessions.remove(clientId);
    }

    @ConsumeEvent(MeasurementEvent.NAME)
    public void broadcast(MeasurementEvent event) {
        sessions.values().forEach(s -> {
            s.getAsyncRemote().sendObject(event.measurement(), result ->  {
                if (result.getException() != null) {
                    System.out.println("Unable to send message: " + result.getException());
                }
            });
        });
    }
}
