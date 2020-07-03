package io.github.nearchos.archcomp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NetworkStatusViewModel extends ViewModel {

    private MutableLiveData<Boolean> connectedMutableLiveData
            = new MutableLiveData<>();

    LiveData<Boolean> getConnected() {
        return connectedMutableLiveData;
    }

    public void setConnected(final boolean connected) {
        // use 'postValue' - the alternative 'setValue' can
        // only be called from the main/UI thread
        connectedMutableLiveData.postValue(connected);
    }
}