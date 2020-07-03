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
