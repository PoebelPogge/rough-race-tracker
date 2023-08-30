package solutions.pge.serial;

import io.quarkus.runtime.Startup;
import io.vertx.core.eventbus.EventBus;
import jakarta.enterprise.context.ApplicationScoped;
import solutions.pge.controller.SystemStatusController;

import java.util.logging.Logger;

@Startup
@ApplicationScoped
public class SerialPortInitializer {

    private static final Logger LOGGER = Logger.getLogger(SerialPortInitializer.class.getSimpleName());

    private final SerialCommunication serialCommunication;


    public SerialPortInitializer(EventBus eventBus, SystemStatusController systemStatusController) {
        LOGGER.info("Serial Port initializing...");
        this.serialCommunication = new SerialCommunication(eventBus);
        this.serialCommunication.connect();
    }

    public void startRace(){
        serialCommunication.startRace();
    }

    public void stopRace(){
        serialCommunication.stopRace();
    }
}
