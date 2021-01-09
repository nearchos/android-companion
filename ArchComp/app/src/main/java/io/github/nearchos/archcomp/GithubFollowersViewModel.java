/*
 * Copyright (c) 2020. Nearchos Paspallis
 * This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivs 3.0 United
 * States License, described at this URL: https://creativecommons.org/licenses/by-nc-nd/3.0/us/.
 * Unless required by applicable law or agreed to in writing, code listed in this site is
 * distributed on "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 */

package io.github.nearchos.archcomp;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class GithubFollowersViewModel extends AndroidViewModel {

    private final LiveData<List<GithubFollower>> liveData;

    public GithubFollowersViewModel(final Application application) {
        super(application);
        final AppDatabase appDatabase = AppDatabase.getDatabase(application);
        final AppDao appDao = appDatabase.appDao();
        this.liveData = appDao.getFollowers();
    }

    LiveData<List<GithubFollower>> getLiveData() {
        return liveData;
    }
}
