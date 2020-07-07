package io.github.nearchos.powermonitor;

import java.io.Serializable;

public class PowerInfo implements Serializable {

    private boolean powerConnected;
    private int batteryLevel;

    PowerInfo(boolean powerConnected, int batteryLevel) {
        this.powerConnected = powerConnected;
        this.batteryLevel = batteryLevel;
    }

    public boolean isPowerConnected() {
        return powerConnected;
    }

    public int getBatteryLevel() {
        return batteryLevel;
    }
}
