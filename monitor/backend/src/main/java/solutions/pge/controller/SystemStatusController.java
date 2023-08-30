package solutions.pge.controller;

import io.quarkus.vertx.ConsumeEvent;
import io.quarkus.scheduler.Scheduled;
import io.vertx.core.eventbus.EventBus;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import solutions.pge.events.DeviceConnectedEvent;
import solutions.pge.events.DeviceDisconnectedEvent;
import solutions.pge.events.SystemStatusEvent;
import solutions.pge.models.SystemStatus;

@ApplicationScoped
public class SystemStatusController {

    private final EventBus eventBus;
    private final SystemStatus systemStatus;

    @ConfigProperty(name = "quarkus.application.version")
    public String version;

    public SystemStatusController(EventBus eventBus) {
        this.eventBus = eventBus;
        this.systemStatus = new SystemStatus(version, false, "local");
    }

    public SystemStatus getSystemStatus() {
        return systemStatus;
    }

    @ConsumeEvent(DeviceConnectedEvent.NAME)
    public void deviceConnected(DeviceConnectedEvent event){
        this.systemStatus.setDeviceConnected(true);
        this.eventBus.publish(SystemStatusEvent.NAME, new SystemStatusEvent());
    }

    @ConsumeEvent(DeviceDisconnectedEvent.NAME)
    public void deviceDisconnected(DeviceDisconnectedEvent event){
        this.systemStatus.setDeviceConnected(false);
        this.eventBus.publish(SystemStatusEvent.NAME, new SystemStatusEvent());
    }

    @Scheduled(every = "1s")
    void updateStatus(){
        if(!getSystemStatus().getDeviceConnected()){
            this.eventBus.publish(SystemStatusEvent.NAME, new SystemStatusEvent());
        }
    }
}
