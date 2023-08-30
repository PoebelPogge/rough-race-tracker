package solutions.pge.serial;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.fazecast.jSerialComm.SerialPortMessageListener;
import io.vertx.core.eventbus.EventBus;
import solutions.pge.events.TimeMeasurementEvent;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RaceDeviceCommandsListener implements SerialPortMessageListener {

    private static final String MESSAGE_DELIMITER = "\n";
    private static final Logger LOGGER = Logger.getLogger(RaceDeviceCommandsListener.class.getSimpleName());
    private static final String COMMAND_PATTERN = "^(\\w):(.*);$";

    private final EventBus bus;
    private final Pattern pattern;

    public RaceDeviceCommandsListener(EventBus bus) {
        this.bus = bus;
        this.pattern = Pattern.compile(COMMAND_PATTERN);
    }

    @Override
    public byte[] getMessageDelimiter() {
        return MESSAGE_DELIMITER.getBytes();
    }

    @Override
    public boolean delimiterIndicatesEndOfMessage() {
        return true;
    }

    @Override
    public int getListeningEvents() {
        return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
    }

    @Override
    public void serialEvent(SerialPortEvent serialPortEvent) {
        byte[] delimitedMessage = serialPortEvent.getReceivedData();
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
