package solutions.pge.websockets;

import io.quarkus.vertx.ConsumeEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import solutions.pge.controller.SystemStatusController;
import solutions.pge.events.SystemStatusEvent;
import solutions.pge.models.SystemStatus;
import solutions.pge.websockets.encoder.SystemStatusEncoder;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/status/{clientId}", encoders = SystemStatusEncoder.class)
@ApplicationScoped
public class SystemStatusEndpoint {

    private final Map<String, Session> SESSIONS = new ConcurrentHashMap<>();
    private final SystemStatusController systemStatusController;

    public SystemStatusEndpoint(SystemStatusController systemStatusController) {
        this.systemStatusController = systemStatusController;
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("clientId") String clientId){
        SESSIONS.put(clientId, session);
        sendMessage(systemStatusController.getSystemStatus());
    }

    @OnClose
    public void onClose(Session session, @PathParam("clientId") String clientId){
        SESSIONS.remove(clientId);
    }


    @OnError
    public void onError(Session session, @PathParam("clientId") String clientId, Throwable throwable) {
        SESSIONS.remove(clientId);
    }



    @ConsumeEvent(SystemStatusEvent.NAME)
    public void broadcastStatus(SystemStatusEvent event){
        sendMessage(systemStatusController.getSystemStatus());
    }

    private void sendMessage(SystemStatus status){
        SESSIONS.values().forEach(s -> {
            s.getAsyncRemote().sendObject(status, result ->  {
                if (result.getException() != null) {
                    System.out.println("Unable to send message: " + result.getException());
                }
            });
        });
    }
}
