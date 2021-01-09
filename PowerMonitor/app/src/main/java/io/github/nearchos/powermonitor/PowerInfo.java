/*
 * Copyright (c) 2020. Nearchos Paspallis
 * This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivs 3.0 United
 * States License, described at this URL: https://creativecommons.org/licenses/by-nc-nd/3.0/us/.
 * Unless required by applicable law or agreed to in writing, code listed in this site is
 * distributed on "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 */

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
