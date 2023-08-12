package solutions.pge;

import com.fazecast.jSerialComm.*;
import io.quarkus.runtime.Startup;
import io.quarkus.vertx.ConsumeEvent;
import io.vertx.core.eventbus.EventBus;
import jakarta.enterprise.context.ApplicationScoped;
import solutions.pge.events.StartRaceEvent;
import solutions.pge.events.StopRaceEvent;
import solutions.pge.events.TimeMeasurementEvent;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Startup
@ApplicationScoped
public class SerialPortInitializer {

    private static final Logger LOGGER = Logger.getLogger(SerialPortInitializer.class.getSimpleName());
    private static final String COMMAND_PATTERN = "^(\\w):(.*);$";
    final SerialPort port ;

    private final EventBus bus;


    public SerialPortInitializer(EventBus eventBus) {

        this.bus = eventBus;

        Pattern pattern = Pattern.compile(COMMAND_PATTERN);


        LOGGER.info("Serial Port initializing...");
        var ports = SerialPort.getCommPorts();

        //port = SerialPort.getCommPort("/dev/cu.usbserial-210");
        port = ports[7];

        port.setComPortTimeouts(SerialPort.TIMEOUT_NONBLOCKING, 0, 0);
        port.openPort();
        port.addDataListener(new SerialPortMessageListener()
        {
            @Override
            public int getListeningEvents() { return SerialPort.LISTENING_EVENT_DATA_RECEIVED; }

            @Override
            public byte[] getMessageDelimiter() { return "\n".getBytes(); }

            @Override
            public boolean delimiterIndicatesEndOfMessage() { return true; }

            @Override
            public void serialEvent(SerialPortEvent serialEvent)
            {
                byte[] delimitedMessage = serialEvent.getReceivedData();
                var data = new String(delimitedMessage).trim();
                LOGGER.info("New Event: -> " + data);
                Matcher matcher = pattern.matcher(data);
                if(matcher.find()){
                    String tagId = matcher.group(2);
                    TimeMeasurementEvent event = new TimeMeasurementEvent(tagId);
                    bus.publish(TimeMeasurementEvent.NAME, event);
                }

            }
        }
        );
    }

    //@ConsumeEvent(StartRaceEvent.NAME)
    public void startRace(){
        byte[] command = "C:RACE_START".getBytes();
        port.writeBytes(command, command.length);
    }

    //@ConsumeEvent(StopRaceEvent.NAME)
    public void stopRace(){
        byte[] command = "C:RACE_STOP".getBytes();
        port.writeBytes(command, command.length);
    }
}
