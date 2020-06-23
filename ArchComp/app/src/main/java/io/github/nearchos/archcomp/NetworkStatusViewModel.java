package io.github.nearchos.archcomp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

/**
 * @author Nearchos
 * Created: 21-Jun-20
 */
public class NetworkStatusViewModel extends ViewModel {

    private MutableLiveData<Boolean> connectedMutableLiveData = new MutableLiveData<>();

    private LiveData<Boolean> connectedLiveData = Transformations.map(connectedMutableLiveData, input -> input);

    LiveData<Boolean> getConnected() {
        return connectedLiveData;
    }

    public void setConnected(final boolean connected) {
        connectedMutableLiveData.postValue(connected); // cannot use setValue (synchronous), must use postValue (asynchronous) when called from a background thread
    }
}