package io.github.nearchos.archcomp.github_api;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import io.github.nearchos.archcomp.AppDao;
import io.github.nearchos.archcomp.AppDatabase;

/**
 * @author Nearchos
 * Created: 16-Jun-20
 */
class GithubFollowersRepository {

    private AppDao appDao;
    private LiveData<List<GithubFollower>> githubFollowersLiveData;

    public GithubFollowersRepository(final Application application) {
        final AppDatabase appDatabase = AppDatabase.getDatabase(application);
        this.appDao = appDatabase.appDao();
        this.githubFollowersLiveData = appDao.getFollowers();
    }

    public LiveData<List<GithubFollower>> getGithubFollowersLiveData() {
        return githubFollowersLiveData;
    }

    public void insert(final GithubFollower githubFollower) {
        new InsertAsyncTask(appDao).execute(githubFollower);
    }

    private static class InsertAsyncTask extends AsyncTask<GithubFollower, Void, Void> {

        private AppDao mAsyncTaskDao;

        InsertAsyncTask(final AppDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final GithubFollower... githubFollowers) {
            mAsyncTaskDao.insert(githubFollowers);
            return null;
        }
    }
}
