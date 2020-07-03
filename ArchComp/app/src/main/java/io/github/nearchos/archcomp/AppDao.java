package io.github.nearchos.archcomp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface AppDao {

    @Query("SELECT * FROM github_followers")
    LiveData<List<GithubFollower>> getFollowers();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(final GithubFollower... githubFollowers);
}
