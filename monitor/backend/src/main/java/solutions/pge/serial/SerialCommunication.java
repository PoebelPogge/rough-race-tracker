package solutions.pge.serial;

import com.fazecast.jSerialComm.SerialPort;
import io.vertx.core.eventbus.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import solutions.pge.events.DeviceConnectedEvent;
import solutions.pge.events.DeviceDisconnectedEvent;

import java.util.Arrays;

public class SerialCommunication extends Thread{

    private static final Logger LOGGER = LoggerFactory.getLogger(SerialCommunication.class);
    private final EventBus bus;
    private SerialPort port;

    public SerialCommunication(EventBus bus) {
        this.bus = bus;
    }

    public void connect()  {
        this.start();
    }

    @Override
    public void run() {
        while (true){
            var ports = SerialPort.getCommPorts();
            var count  = Arrays.stream(ports)
                    .filter(p -> p.toString()
                            .toLowerCase()
                            .contains("arduino")).count();
            var maybePort = Arrays.stream(ports)
                    .filter(p -> p.toString()
                            .toLowerCase()
                            .contains("arduino")).findFirst();

            if(maybePort.isPresent() && count == 2){
                if(port != null) continue;
                this.port = maybePort.get();
                this.port.setComPortTimeouts(SerialPort.TIMEOUT_NONBLOCKING, 0, 0);
                this.port.openPort();
                this.port.addDataListener(new RaceDeviceCommandsListener(bus));
                bus.publish(DeviceConnectedEvent.NAME,new DeviceConnectedEvent());
            } else {
                port = null;
                bus.publish(DeviceDisconnectedEvent.NAME,new DeviceDisconnectedEvent());
            }
            try {
                LOGGER.warn("Device is not connected...");
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public boolean isConnected() {
        return port != null;
    }

    public void startRace(){
        if(this.port == null) return;
        byte[] command = "C:RACE_START".getBytes();
        port.writeBytes(command, command.length);
    }

    public void stopRace(){
        if(this.port == null) return;
        byte[] command = "C:RACE_STOP".getBytes();
        port.writeBytes(command, command.length);
    }

}
