package solutions.pge.websockets;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import solutions.pge.controller.RaceController;
import solutions.pge.models.Race;
import solutions.pge.websockets.encoder.RaceEncoder;
import solutions.pge.websockets.encoder.SystemStatusEncoder;

@ServerEndpoint(value = "/race/{clientId}", encoders = RaceEncoder.class)
@ApplicationScoped
public class RaceEndpoint extends AbstractJsonEndpoint<Race> {

    @Inject
    RaceController raceController;

    @OnOpen
    public void onOpen(Session session, @PathParam("clientId") String clientId){
        openSession(session, clientId);
    }

    @OnClose
    public void onClose(Session session, @PathParam("clientId") String clientId){
        closeSession(session, clientId);
    }


    @OnError
    public void onError(Session session, @PathParam("clientId") String clientId, Throwable throwable) {
        sessionError(session, clientId, throwable);
    }
    @Override
    Race sendInitialMessage() {
        return raceController.getCurrentRace();
    }


}
