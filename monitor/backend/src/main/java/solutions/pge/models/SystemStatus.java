package solutions.pge.models;

public class SystemStatus {
    private String version;
    private Boolean deviceConnected;
    private String connectionMode;

    public SystemStatus(String version, Boolean deviceConnected, String connectionMode) {
        this.version = version;
        this.deviceConnected = deviceConnected;
        this.connectionMode = connectionMode;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Boolean getDeviceConnected() {
        return deviceConnected;
    }

    public void setDeviceConnected(Boolean deviceConnected) {
        this.deviceConnected = deviceConnected;
    }

    public String getConnectionMode() {
        return connectionMode;
    }

    public void setConnectionMode(String connectionMode) {
        this.connectionMode = connectionMode;
    }
}
