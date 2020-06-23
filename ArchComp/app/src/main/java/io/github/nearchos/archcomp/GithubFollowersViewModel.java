package io.github.nearchos.archcomp;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

/**
 * @author Nearchos
 * Created: 16-Jun-20
 */
public class GithubFollowersViewModel extends AndroidViewModel {

    private final GithubFollowersRepository githubFollowersRepository;
    private final LiveData<List<GithubFollower>> liveData;

    public GithubFollowersViewModel(final Application application) {
        super(application);
        this.githubFollowersRepository = new GithubFollowersRepository(application);
        this.liveData = githubFollowersRepository.getGithubFollowersLiveData();
    }

    LiveData<List<GithubFollower>> getLiveData() {
        return liveData;
    }
}
