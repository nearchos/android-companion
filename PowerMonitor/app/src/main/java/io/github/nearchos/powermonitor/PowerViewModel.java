package io.github.nearchos.powermonitor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class PowerViewModel extends ViewModel {

    private MutableLiveData<PowerInfo> powerInfoMutableLiveData = new MutableLiveData<>();

    // must set Java version 8 or higher to enable lambda expressions
    private LiveData<PowerInfo> powerInfoLiveData = Transformations.map(powerInfoMutableLiveData, input -> input);

    LiveData<PowerInfo> getPowerInfo() {
        return powerInfoLiveData;
    }

    public void setPowerInfo(final boolean powerConnected, final int batteryLevel) {
        final PowerInfo powerInfo = new PowerInfo(powerConnected, batteryLevel);
        powerInfoMutableLiveData.setValue(powerInfo);
    }
}