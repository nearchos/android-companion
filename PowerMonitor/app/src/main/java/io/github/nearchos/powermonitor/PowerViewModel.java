/*
 * Copyright (c) 2020. Nearchos Paspallis
 * This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivs 3.0 United
 * States License, described at this URL: https://creativecommons.org/licenses/by-nc-nd/3.0/us/.
 * Unless required by applicable law or agreed to in writing, code listed in this site is
 * distributed on "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 */

package io.github.nearchos.powermonitor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class PowerViewModel extends ViewModel {

    private MutableLiveData<PowerInfo> powerInfoMutableLiveData = new MutableLiveData<>();

    // must set to Java 8 or higher to enable lambda expressions
    private LiveData<PowerInfo> powerInfoLiveData = Transformations.map(powerInfoMutableLiveData, input -> input);

    LiveData<PowerInfo> getPowerInfo() {
        return powerInfoLiveData;
    }

    void setPowerInfo(final boolean powerConnected, final int batteryLevel) {
        final PowerInfo powerInfo = new PowerInfo(powerConnected, batteryLevel);
        powerInfoMutableLiveData.setValue(powerInfo);
    }
}