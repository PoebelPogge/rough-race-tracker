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
import solutions.pge.models.Buggy;
import solutions.pge.models.Measurement;
import solutions.pge.websockets.encoder.MeasurementsEncoder;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/race/{clientId}/measurements", encoders = MeasurementsEncoder.class)
@ApplicationScoped
public class MeasurementsEndpoint {

    private final Map<String, Session> sessions = new ConcurrentHashMap<>();

    @Inject
    RaceController raceController;

    @OnOpen
    public void onOpen(Session session, @PathParam("clientId") String clientId){
        sessions.put(clientId, session);
        this.sendMessage(raceController.getCurrentRace().getMeasurements());
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
        this.sendMessage(raceController.getCurrentRace().getMeasurements());
    }

    private void sendMessage(String message){
        sessions.values().forEach(s -> {
            s.getAsyncRemote().sendText(message, result -> {
                if(result.getException() != null){
                    System.out.println("Unable to send message: " + result.getException());
                }
            });
        });
    }

    private void sendMessage(Map<Buggy, List<Measurement>> measurements){
        sessions.values().forEach(s -> {
            s.getAsyncRemote().sendObject(measurements, result ->  {
                if (result.getException() != null) {
                    System.out.println("Unable to send message: " + result.getException());
                }
            });
        });
    }
}
