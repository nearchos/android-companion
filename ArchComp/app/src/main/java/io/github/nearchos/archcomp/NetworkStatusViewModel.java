/*
 * Copyright (c) 2020. Nearchos Paspallis
 * This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivs 3.0 United
 * States License, described at this URL: https://creativecommons.org/licenses/by-nc-nd/3.0/us/.
 * Unless required by applicable law or agreed to in writing, code listed in this site is
 * distributed on "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 */

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